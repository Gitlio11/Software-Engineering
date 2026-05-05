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
 * @project: SciHub
 * @description: service support methods for DepartmentController
 * @date: 2025-11-01
 */
public class DepartmentService {

    /**
     * Gets all departments by aggregating data from researcher_info, project, paper, and author tables.
     * No department table exists so everything is computed from joins.
     *
     * @return list of Department objects with aggregated stats
     */
    public List<Department> buildAllDepartments() {
        Map<String, Department> depMap = new LinkedHashMap<>();

        // get base department info and faculty count
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

        // get active project count per department (is_active is varchar, check 'true' and '1')
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

        // get funded project count (funded = has a sponsor organization)
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

        // get publication count for last 3 years
        // match authors to users by first + last name since there's no direct link
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
            "  AND CAST(p.year AS SIGNED) >= :cutoff " +
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

        // get research keywords by concatenating all research_fields per department
        String kwSql =
            "SELECT department, GROUP_CONCAT(research_fields) AS all_fields " +
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
     * Gets full department details including faculty list and project list
     *
     * @param name the department name
     * @return Department with faculty and projects populated, or null if not found
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

        // load faculty using ResearcherInfo finder (eagerly loads User)
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

        // load projects for this department
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
     * Gets a list of departments based on optional offset, pageLimit, sort field and order
     *
     * @param departments  all departments
     * @param offset       shows the start index of the rows we want to receive
     * @param pageLimit    shows the number of rows we want to receive
     * @param sortBy       field to sort by
     * @param order        asc or desc
     * @return paginated RESTResponse
     */
    public RESTResponse paginateAndSort(List<Department> departments,
                                        Optional<Integer> offset,
                                        Optional<Integer> pageLimit,
                                        String sortBy,
                                        String order) {
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
     * Turn department list into json array
     *
     * @param departmentList list of departments
     * @return json array of serialized departments
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
     * Extracts top N keywords from a comma/semicolon separated string by frequency
     *
     * @param concatenated raw string from GROUP_CONCAT
     * @param topN max number of keywords to return
     * @return list of keywords ordered by frequency
     */
    public List<String> extractTopKeywords(String concatenated, int topN) {
        if (concatenated == null || concatenated.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String[] tokens = concatenated.split("[,;]+");
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (String token : tokens) {
            String t = token.trim().toLowerCase();
            if (t.length() < 3) continue;
            freq.merge(t, 1, Integer::sum);
        }

        return freq.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey()))
            .limit(topN)
            .map(Map.Entry::getKey)
            .collect(java.util.stream.Collectors.toList());
    }

    private static String safeKey(String raw) {
        return raw == null ? null : raw.trim().toLowerCase();
    }
}
