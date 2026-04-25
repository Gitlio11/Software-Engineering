package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Ebean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.Calendar;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

/**
 * HTTP-level integration tests for {@link DepartmentController}.
 *
 * <p>Requires a running MySQL instance. A minimal fixture seeds one Computer
 * Science faculty member so that the department endpoints return real data.
 * IDs start at 9100+ to avoid collisions with DepartmentServiceIntegrationTest.
 */
public class DepartmentControllerIntegrationTest {

    static Application application;
    static int recentYear = Calendar.getInstance().get(Calendar.YEAR) - 1;

    @BeforeClass
    public static void setup() {
        application = Helpers.fakeApplication();
        Helpers.start(application);
        seedFixture();
    }

    @AfterClass
    public static void tearDown() {
        cleanFixture();
        Helpers.stop(application);
    }

    // ------------------------------------------------------------------
    // Happy-path tests
    // ------------------------------------------------------------------

    /** GET /department/departmentList returns 200 with a non-empty items array. */
    @Test
    public void departmentList_returns200WithItems() {
        Http.RequestBuilder request = fakeRequest(GET, "/department/departmentList");
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode body = Json.parse(contentAsString(result));
        assertTrue("Response should have 'items' field", body.has("items"));
        assertTrue("items should be non-empty", body.get("items").size() > 0);
    }

    /** Response must include all required aggregate fields on each item. */
    @Test
    public void departmentList_itemsContainRequiredFields() {
        Http.RequestBuilder request = fakeRequest(GET, "/department/departmentList");
        Result result = route(application, request);

        JsonNode body  = Json.parse(contentAsString(result));
        JsonNode items = body.get("items");
        boolean foundCS = false;
        for (JsonNode item : items) {
            if ("Computer Science".equals(item.path("name").asText())) {
                foundCS = true;
                assertTrue(item.has("facultyCount"));
                assertTrue(item.has("activeProjectCount"));
                assertTrue(item.has("publicationCountLast3Years"));
                assertTrue(item.has("fundedProjectCount"));
                assertTrue(item.has("topKeywords"));
            }
        }
        assertTrue("Computer Science department should be in the list", foundCS);
    }

    /** sortBy=facultyCount&order=desc should still return 200. */
    @Test
    public void departmentList_acceptsSortByFacultyCountDesc() {
        Http.RequestBuilder request = fakeRequest(GET,
            "/department/departmentList?sortBy=facultyCount&order=desc");
        Result result = route(application, request);
        assertEquals(OK, result.status());
    }

    /** pageLimit=1&offset=0 should return exactly 1 item. */
    @Test
    public void departmentList_respectsPageLimit() {
        Http.RequestBuilder request = fakeRequest(GET,
            "/department/departmentList?pageLimit=1&offset=0");
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode body = Json.parse(contentAsString(result));
        assertEquals("Should return exactly 1 item", 1, body.get("items").size());
    }

    /** GET /department/departmentDetail/Computer%20Science returns 200 with faculty + projects. */
    @Test
    public void departmentDetail_returns200WithFacultyAndProjects() {
        Http.RequestBuilder request = fakeRequest(GET,
            "/department/departmentDetail/Computer%20Science");
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode body = Json.parse(contentAsString(result));
        assertEquals("Computer Science", body.path("name").asText());
        assertTrue("Detail should include faculty array",  body.has("faculty"));
        assertTrue("Detail should include projects array", body.has("projects"));
        assertTrue("Faculty list should not be empty", body.get("faculty").size() > 0);
    }

    // ------------------------------------------------------------------
    // Error-case (E2E) tests
    // ------------------------------------------------------------------

    /** GET /department/departmentDetail/DoesNotExistDept returns 404. */
    @Test
    public void departmentDetail_unknownDepartmentReturns404() {
        Http.RequestBuilder request = fakeRequest(GET,
            "/department/departmentDetail/DoesNotExistDept9999");
        Result result = route(application, request);
        assertEquals(NOT_FOUND, result.status());
    }

    // ------------------------------------------------------------------
    // Fixture helpers
    // ------------------------------------------------------------------

    private static void seedFixture() {
        Ebean.createSqlUpdate(
            "INSERT INTO `user` (id, first_name, last_name, user_name, " +
            "rating, rating_count, recommend_rating, recommend_rating_count, " +
            "service_provider, service_execution_counts, service_user, unread_mention) VALUES " +
            "(9101, 'Dave', 'Brown', 'dbrown_f4ctrl', 0, 0, 0, 0, 0, 0, 0, 0)"
        ).execute();

        Ebean.createSqlUpdate(
            "INSERT INTO researcher_info (user_id, department, school, research_fields) VALUES " +
            "(9101, 'Computer Science', 'Engineering', 'neural networks, computer vision')"
        ).execute();

        Ebean.createSqlUpdate(
            "INSERT INTO project (id, is_active, title, is_popular, popular_ranking, " +
            "access_times, principal_investigator_id) VALUES " +
            "(9101, 'true', 'Controller Test Project', 0, 0, 0, 9101)"
        ).execute();
    }

    private static void cleanFixture() {
        Ebean.createSqlUpdate("DELETE FROM project       WHERE id = 9101").execute();
        Ebean.createSqlUpdate("DELETE FROM researcher_info WHERE user_id = 9101").execute();
        Ebean.createSqlUpdate("DELETE FROM `user`        WHERE id = 9101").execute();
    }
}
