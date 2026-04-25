package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.Department;
import models.Department.DepartmentFaculty;
import models.Department.DepartmentProject;
import models.ResearcherInfo;
import models.rest.RESTResponse;
import play.Logger;
import play.libs.Json;
import utils.Common;

import java.util.*;

/**
 * Service providing aggregated department-level statistics derived entirely
 * from existing tables (researcher_info, project, paper, author, author_paper,
 * user). No department table exists in the schema — all aggregations are
 * computed at query time and merged in-memory.
 */
public class DepartmentService {

    // ------------------------------------------------------------------
    // Public API
    // ------------------------------------------------------------------

    /**
     * Builds the full list of departments by running five SQL aggregation
     * queries and merging results in-memory by department name.
     *
     * <p>The five queries cover: (1) faculty count per department, (2) active
     * project count, (3) funded project count, (4) publication count in the
     * last 3 calendar years, and (5) keyword extraction via GROUP_CONCAT.
     * Results are merged into a {@link LinkedHashMap} so that insertion order
     * (alphabetical from the ORDER BY in Q1) is preserved.
     *
     * @return list of {@link Department} DTOs, one per distinct department name
     */
    public List<Department> buildAllDepartments() {
        // Keyed by lowercase+trimmed department name for case-insensitive merging
        Map<String, Department> depMap = new LinkedHashMap<>();

        // ------ Query 1: base list – school + faculty count ------ //
        String baseSql =
            "SELECT ri.department, ri.school, COUNT(*) AS faculty_count " +
            "FROM researcher_info ri " +
            "WHERE ri.department IS NOT NULL AND TRIM(ri.department) != '' " +
            "GROUP BY ri.department, ri.school " +
            "ORDER BY ri.department";

        for (SqlRow row : Ebean.createSqlQuery(baseSql).findList()) {
            String rawName = row.getString("department");
            if (rawName == null || rawName.trim().isEmpty()) continue;
            Department d = new Department();
            d.setName(rawName.trim());
            d.setSchool(row.getString("school"));
            Integer fc = row.getInteger("faculty_count");
            d.setFacultyCount(fc != null ? fc : 0);
            depMap.put(rawName.trim().toLowerCase(), d);
        }

        // ------ Query 2: active project count per department ------ //
        // is_active is varchar; accept 'true' (case-insensitive) and '1'
        String activeSql =
            "SELECT ri.department, COUNT(DISTINCT p.id) AS active_project_count " +
            "FROM researcher_info ri " +
            "JOIN project p ON p.principal_investigator_id = ri.user_id " +
            "WHERE (LOWER(p.is_active) = 'true' OR p.is_active = '1') " +
            "GROUP BY ri.department";

        for (SqlRow row : Ebean.createSqlQuery(activeSql).findList()) {
            Department d = depMap.get(safeKey(row.getString("department")));
            if (d != null) {
                Integer c = row.getInteger("active_project_count");
                d.setActiveProjectCount(c != null ? c : 0);
            }
        }

        // ------ Query 3: funded project count per department ------ //
        // "funded" = sponsor_organization_id IS NOT NULL
        String fundedSql =
            "SELECT ri.department, COUNT(DISTINCT p.id) AS funded_project_count " +
            "FROM researcher_info ri " +
            "JOIN project p ON p.principal_investigator_id = ri.user_id " +
            "WHERE p.sponsor_organization_id IS NOT NULL " +
            "GROUP BY ri.department";

        for (SqlRow row : Ebean.createSqlQuery(fundedSql).findList()) {
            Department d = depMap.get(safeKey(row.getString("department")));
            if (d != null) {
                Integer c = row.getInteger("funded_project_count");
                d.setFundedProjectCount(c != null ? c : 0);
            }
        }

        // ------ Query 4: publication count in the last 3 calendar years ------ //
        // author-to-user match is by first_name + last_name (case-insensitive, trimmed)
        // paper.year is varchar – cast to UNSIGNED before comparing
        int cutoff = Calendar.getInstance().get(Calendar.YEAR) - 3;
        String pubSql =
            "SELECT ri.department, COUNT(DISTINCT ap.paper_id) AS pub_count " +
            "FROM researcher_info ri " +
            "JOIN `user` u ON u.id = ri.user_id " +
            "JOIN author a " +
            "  ON LOWER(TRIM(a.first_name)) = LOWER(TRIM(u.first_name)) " +
            " AND LOWER(TRIM(a.last_name))  = LOWER(TRIM(u.last_name)) " +
            "JOIN author_paper ap ON ap.author_id = a.id " +
            "JOIN paper p ON p.id = ap.paper_id " +
            "WHERE p.year IS NOT NULL AND p.year != '' " +
            "  AND CAST(p.year AS UNSIGNED) >= :cutoff " +
            "GROUP BY ri.department";

        SqlQuery pubQuery = Ebean.createSqlQuery(pubSql);
        pubQuery.setParameter("cutoff", cutoff);
        for (SqlRow row : pubQuery.findList()) {
            Department d = depMap.get(safeKey(row.getString("department")));
            if (d != null) {
                Integer c = row.getInteger("pub_count");
                d.setPublicationCountLast3Years(c != null ? c : 0);
            }
        }

        // ------ Query 5: concatenated research_fields for keyword extraction ------ //
        String kwSql =
            "SELECT department, GROUP_CONCAT(research_fields SEPARATOR ',') AS all_fields " +
            "FROM researcher_info " +
            "WHERE department IS NOT NULL AND TRIM(department) != '' " +
            "GROUP BY department";

        for (SqlRow row : Ebean.createSqlQuery(kwSql).findList()) {
            Department d = depMap.get(safeKey(row.getString("department")));
            if (d != null) {
                d.setTopKeywords(extractTopKeywords(row.getString("all_fields"), 5));
            }
        }

        return new ArrayList<>(depMap.values());
    }

    /**
     * Builds a detailed department profile, including faculty list and project
     * list, for the given department name.
     *
     * <p>Returns {@code null} when the name is blank/null or no matching
     * department is found.
     *
     * @param name department name exactly as stored in researcher_info.department
     * @return populated {@link Department} with faculty + projects, or {@code null}
     */
    public Department buildDepartmentDetail(String name) {
        if (name == null || name.trim().isEmpty()) return null;

        List<Department> all = buildAllDepartments();
        Department match = null;
        for (Department d : all) {
            if (d.getName().equalsIgnoreCase(name.trim())) {
                match = d;
                break;
            }
        }
        if (match == null) return null;

        // Populate faculty list via Ebean ORM (ResearcherInfo eagerly loads User)
        List<ResearcherInfo> riList = ResearcherInfo.find.query()
            .where()
            .eq("department", match.getName())
            .findList();

        List<DepartmentFaculty> facultyList = new ArrayList<>();
        for (ResearcherInfo ri : riList) {
            if (ri.getUser() == null) continue;
            DepartmentFaculty df = new DepartmentFaculty(
                ri.getUser().getId(),
                ri.getUser().getFirstName(),
                ri.getUser().getLastName(),
                ri.getUser().getHomepage(),
                ri.getResearchFields()
            );
            facultyList.add(df);
        }
        match.setFaculty(facultyList);

        // Populate projects via raw SQL (projects whose PI is in this department)
        String projSql =
            "SELECT p.id, p.title, p.is_active, p.start_date, p.principal_investigator_id " +
            "FROM project p " +
            "JOIN researcher_info ri ON ri.user_id = p.principal_investigator_id " +
            "WHERE ri.department = :dep";

        SqlQuery projQuery = Ebean.createSqlQuery(projSql);
        projQuery.setParameter("dep", match.getName());

        List<DepartmentProject> projectList = new ArrayList<>();
        for (SqlRow row : projQuery.findList()) {
            DepartmentProject dp = new DepartmentProject(
                row.getLong("id"),
                row.getString("title"),
                row.getString("is_active"),
                row.getString("start_date"),
                row.getLong("principal_investigator_id")
            );
            projectList.add(dp);
        }
        match.setProjects(projectList);

        return match;
    }

    /**
     * Sorts and paginates a list of departments, returning a {@link RESTResponse}
     * compatible with the existing API shape used by OrganizationController.
     *
     * <p>Supported sort keys: {@code name}, {@code facultyCount},
     * {@code activeProjectCount}, {@code publicationCountLast3Years},
     * {@code fundedProjectCount}. Falls back to {@code name asc} for any
     * unrecognised key. The list is sorted in-place then paginated.
     *
     * @param departments full list returned by {@link #buildAllDepartments()}
     * @param offset      0-based start index (absent = 0)
     * @param pageLimit   max items to return (absent = all)
     * @param sortBy      field name to sort by
     * @param order       "asc" or "desc" (case-insensitive)
     * @return paginated {@link RESTResponse}
     */
    public RESTResponse paginateAndSort(List<Department> departments,
                                        Optional<Integer> offset,
                                        Optional<Integer> pageLimit,
                                        String sortBy,
                                        String order) {
        // Build comparator from sortBy, falling back to name for unknown keys
        Comparator<Department> comparator;
        switch (sortBy == null ? "" : sortBy) {
            case "facultyCount":
                comparator = Comparator.comparingInt(Department::getFacultyCount);
                break;
            case "activeProjectCount":
                comparator = Comparator.comparingInt(Department::getActiveProjectCount);
                break;
            case "publicationCountLast3Years":
                comparator = Comparator.comparingInt(Department::getPublicationCountLast3Years);
                break;
            case "fundedProjectCount":
                comparator = Comparator.comparingInt(Department::getFundedProjectCount);
                break;
            case "name":
            default:
                comparator = Comparator.comparing(Department::getName, String.CASE_INSENSITIVE_ORDER);
                break;
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        departments.sort(comparator);

        // Pagination – mirrors OrganizationService.paginateResults logic
        RESTResponse response = new RESTResponse();
        int maxRows = departments.size();
        if (pageLimit.isPresent()) {
            maxRows = pageLimit.get();
        }
        int startIndex = 0;
        if (offset.isPresent()) {
            startIndex = offset.get();
        }
        if (startIndex >= departments.size() && !departments.isEmpty() && pageLimit.isPresent()) {
            startIndex = pageLimit.get() * ((departments.size() - 1) / pageLimit.get());
        }

        List<Department> paginated = Common.paginate(startIndex, maxRows, departments);
        response.setTotal(departments.size());
        response.setSort(sortBy + " " + order);
        response.setOffset(startIndex);
        response.setItems(departmentList2JsonArray(paginated));
        return response;
    }

    /**
     * Serializes a list of {@link Department} objects into a Jackson
     * {@link ArrayNode}, mirroring OrganizationService.organizationList2JsonArray.
     *
     * @param departmentList list to serialise
     * @return JSON array node
     */
    public ArrayNode departmentList2JsonArray(List<Department> departmentList) {
        ArrayNode node = Json.newArray();
        for (Department d : departmentList) {
            ObjectNode entry = (ObjectNode) Json.toJson(d);
            node.add(entry);
        }
        return node;
    }

    /**
     * Extracts the top-N keywords from a comma/semicolon-delimited string of
     * research field tags by counting token frequency.
     *
     * <p>Tokens shorter than 3 characters are dropped (note: this excludes
     * two-letter abbreviations such as "AI" and "ML" — a known limitation).
     * Matching is case-insensitive; the returned keywords are lowercased.
     *
     * @param concatenated raw string from GROUP_CONCAT, may be null or empty
     * @param topN         maximum number of keywords to return
     * @return ordered list (most frequent first) of up to topN keywords
     */
    public List<String> extractTopKeywords(String concatenated, int topN) {
        if (concatenated == null || concatenated.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String[] tokens = concatenated.split("[,;]+");
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (String token : tokens) {
            String t = token.trim().toLowerCase();
            if (t.length() < 3) continue;  // drop short tokens (AI, ML, etc.)
            freq.merge(t, 1, Integer::sum);
        }

        // Sort by frequency descending, then alphabetically for stable ordering
        return freq.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey()))
            .limit(topN)
            .map(Map.Entry::getKey)
            .collect(java.util.stream.Collectors.toList());
    }

    // ------------------------------------------------------------------
    // Private helpers
    // ------------------------------------------------------------------

    /** Normalises a department name to the map key form (lowercase + trimmed). */
    private static String safeKey(String raw) {
        return raw == null ? null : raw.trim().toLowerCase();
    }
}
