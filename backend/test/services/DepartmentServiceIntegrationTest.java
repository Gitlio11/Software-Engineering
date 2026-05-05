package services;

import io.ebean.Ebean;
import models.Department;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * DB-backed integration tests for {@link DepartmentService}.
 *
 * <p>These tests require a running MySQL instance configured in application.conf.
 * A small fixture is inserted with IDs starting at 9000+ to avoid collisions
 * with production or other test data. The fixture is cleaned up in
 * {@link #tearDown()}.
 *
 * <p>Fixture layout:
 * <ul>
 *   <li>Organization 9001 (used as sponsor for a funded project)</li>
 *   <li>Users 9001 (Alice Smith, CS), 9002 (Bob Jones, CS), 9003 (Carol White, Math)</li>
 *   <li>ResearcherInfo: 9001+9002 → Computer Science / Engineering;
 *       9003 → Mathematics / Science</li>
 *   <li>Projects: 9001 active+funded (PI=Alice), 9002 active+unfunded (PI=Bob),
 *       9003 inactive (PI=Alice)</li>
 *   <li>Author 9001 (Alice Smith, matched to user 9001 by first+last name)</li>
 *   <li>Papers: 9001 (recent year, within 3-year window), 9002 (year 1990, excluded)</li>
 *   <li>author_paper: author 9001 linked to both papers</li>
 * </ul>
 */
public class DepartmentServiceIntegrationTest {

    static Application application;
    static DepartmentService service;

    // Year used for the "recent" test paper (current year - 1, always inside the window)
    static int recentYear = Calendar.getInstance().get(Calendar.YEAR) - 1;

    @BeforeClass
    public static void setup() {
        application = Helpers.fakeApplication();
        Helpers.start(application);
        service = new DepartmentService();
        seedFixture();
    }

    @AfterClass
    public static void tearDown() {
        cleanFixture();
        Helpers.stop(application);
    }

    // ------------------------------------------------------------------
    // buildAllDepartments tests
    // ------------------------------------------------------------------

    @Test
    public void buildAllDepartments_returnsBothDepartments() {
        List<Department> all = service.buildAllDepartments();
        boolean hasCS   = all.stream().anyMatch(d -> "Computer Science".equalsIgnoreCase(d.getName()));
        boolean hasMath = all.stream().anyMatch(d -> "Mathematics".equalsIgnoreCase(d.getName()));
        assertTrue("Should find Computer Science department", hasCS);
        assertTrue("Should find Mathematics department", hasMath);
    }

    @Test
    public void buildAllDepartments_facultyCountIsCorrect() {
        List<Department> all = service.buildAllDepartments();
        Department cs = findDept(all, "Computer Science");
        Department math = findDept(all, "Mathematics");
        assertNotNull(cs);
        assertNotNull(math);
        assertEquals("CS should have 2 faculty", 2, cs.getFacultyCount());
        assertEquals("Math should have 1 faculty", 1, math.getFacultyCount());
    }

    @Test
    public void buildAllDepartments_activeProjectCountExcludesInactive() {
        // CS has projects 9001 (active) and 9002 (active) and 9003 (inactive)
        // → active count for CS = 2
        List<Department> all = service.buildAllDepartments();
        Department cs = findDept(all, "Computer Science");
        assertNotNull(cs);
        assertEquals("CS should have 2 active projects", 2, cs.getActiveProjectCount());
    }

    @Test
    public void buildAllDepartments_fundedProjectCountUsesSponsorFlag() {
        // Only project 9001 has sponsor_organization_id set
        List<Department> all = service.buildAllDepartments();
        Department cs = findDept(all, "Computer Science");
        assertNotNull(cs);
        assertEquals("CS should have 1 funded project", 1, cs.getFundedProjectCount());
    }

    @Test
    public void buildAllDepartments_publicationCountRespects3YearWindow() {
        // Author Alice (9001) is linked to paper 9001 (recent) and 9002 (1990)
        // Only paper 9001 is within the 3-year window → pub count for CS = 1
        List<Department> all = service.buildAllDepartments();
        Department cs = findDept(all, "Computer Science");
        assertNotNull(cs);
        assertEquals("CS pub count should be 1 (1990 paper excluded)", 1,
                cs.getPublicationCountLast3Years());
    }

    @Test
    public void buildAllDepartments_topKeywordsRankedByFrequency() {
        // CS research_fields: "machine learning, deep learning, machine learning" (Alice)
        //                   + "machine learning, algorithms" (Bob)
        // → "machine learning" appears 3×, should be first keyword
        List<Department> all = service.buildAllDepartments();
        Department cs = findDept(all, "Computer Science");
        assertNotNull(cs);
        assertNotNull(cs.getTopKeywords());
        assertFalse(cs.getTopKeywords().isEmpty());
        assertEquals("machine learning should be top keyword", "machine learning",
                cs.getTopKeywords().get(0));
    }

    // ------------------------------------------------------------------
    // buildDepartmentDetail tests
    // ------------------------------------------------------------------

    @Test
    public void buildDepartmentDetail_populatesFacultyAndProjects() {
        Department detail = service.buildDepartmentDetail("Computer Science");
        assertNotNull(detail);
        assertNotNull("Faculty list should be populated", detail.getFaculty());
        assertNotNull("Projects list should be populated", detail.getProjects());
        assertEquals("CS should have 2 faculty in detail", 2, detail.getFaculty().size());
        // 3 projects total for CS PI members (9001, 9002, 9003 all have PI in CS)
        assertEquals("CS should have 3 projects in detail", 3, detail.getProjects().size());
    }

    @Test
    public void buildDepartmentDetail_returnsNullForUnknownDepartment() {
        Department result = service.buildDepartmentDetail("NoSuchDepartmentXYZ");
        assertNull(result);
    }

    @Test
    public void buildDepartmentDetail_returnsNullForNullInput() {
        Department result = service.buildDepartmentDetail(null);
        assertNull(result);
    }

    @Test
    public void buildDepartmentDetail_returnsNullForBlankInput() {
        Department result = service.buildDepartmentDetail("   ");
        assertNull(result);
    }

    // ------------------------------------------------------------------
    // Fixture helpers
    // ------------------------------------------------------------------

    private static void seedFixture() {
        // 1. Organization (needed for FK on funded project)
        Ebean.createSqlUpdate(
            "INSERT INTO organization (id, organization_name, number_of_employees, zip_code) " +
            "VALUES (9001, 'Test Sponsor Org F4', 0, 0)"
        ).execute();

        // 2. Users
        Ebean.createSqlUpdate(
            "INSERT INTO `user` (id, first_name, last_name, user_name, " +
            "rating, rating_count, recommend_rating, recommend_rating_count, " +
            "service_provider, service_execution_counts, service_user, unread_mention) VALUES " +
            "(9001, 'Alice', 'Smith', 'asmith_f4', 0, 0, 0, 0, 0, 0, 0, 0), " +
            "(9002, 'Bob',   'Jones', 'bjones_f4', 0, 0, 0, 0, 0, 0, 0, 0), " +
            "(9003, 'Carol', 'White', 'cwhite_f4', 0, 0, 0, 0, 0, 0, 0, 0)"
        ).execute();

        // 3. ResearcherInfo (CS: Alice + Bob; Math: Carol)
        Ebean.createSqlUpdate(
            "INSERT INTO researcher_info (user_id, department, school, research_fields) VALUES " +
            "(9001, 'Computer Science', 'Engineering', 'machine learning, deep learning, machine learning'), " +
            "(9002, 'Computer Science', 'Engineering', 'machine learning, algorithms'), " +
            "(9003, 'Mathematics',      'Science',     'topology, algebra')"
        ).execute();

        // 4. Projects: active+funded (9001), active+unfunded (9002), inactive (9003)
        Ebean.createSqlUpdate(
            "INSERT INTO project (id, is_active, title, is_popular, popular_ranking, " +
            "access_times, principal_investigator_id, sponsor_organization_id) VALUES " +
            "(9001, 'true',  'CS Active Funded Project',   0, 0, 0, 9001, 9001), " +
            "(9002, 'true',  'CS Active Unfunded Project', 0, 0, 0, 9002, NULL), " +
            "(9003, 'false', 'CS Inactive Project',        0, 0, 0, 9001, NULL)"
        ).execute();

        // 5. Author (matches Alice Smith by first+last name for the publication join)
        Ebean.createSqlUpdate(
            "INSERT INTO author (id, first_name, last_name, " +
            "rating, rating_count, recommend_rating, recommend_rating_count) VALUES " +
            "(9001, 'Alice', 'Smith', 0, 0, 0, 0)"
        ).execute();

        // 6. Papers: recent (inside 3-year window) and old (1990, outside window)
        Ebean.createSqlUpdate(
            "INSERT INTO paper (id, title, year) VALUES " +
            "(9001, 'F4 Recent Paper', '" + recentYear + "'), " +
            "(9002, 'F4 Old Paper',    '1990')"
        ).execute();

        // 7. Link author 9001 to both papers
        Ebean.createSqlUpdate(
            "INSERT INTO author_paper (author_id, paper_id) VALUES " +
            "(9001, 9001), (9001, 9002)"
        ).execute();
    }

    private static void cleanFixture() {
        // Delete in reverse FK dependency order
        Ebean.createSqlUpdate("DELETE FROM author_paper  WHERE author_id = 9001 AND paper_id IN (9001, 9002)").execute();
        Ebean.createSqlUpdate("DELETE FROM paper         WHERE id IN (9001, 9002)").execute();
        Ebean.createSqlUpdate("DELETE FROM author        WHERE id = 9001").execute();
        Ebean.createSqlUpdate("DELETE FROM project       WHERE id IN (9001, 9002, 9003)").execute();
        Ebean.createSqlUpdate("DELETE FROM researcher_info WHERE user_id IN (9001, 9002, 9003)").execute();
        Ebean.createSqlUpdate("DELETE FROM `user`        WHERE id IN (9001, 9002, 9003)").execute();
        Ebean.createSqlUpdate("DELETE FROM organization  WHERE id = 9001").execute();
    }

    /** Finds the first Department in a list that matches by name (case-insensitive). */
    private Department findDept(List<Department> list, String name) {
        return list.stream()
            .filter(d -> name.equalsIgnoreCase(d.getName()))
            .findFirst().orElse(null);
    }
}
