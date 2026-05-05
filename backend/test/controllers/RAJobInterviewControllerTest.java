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

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

/**
 * End-to-end integration tests for the RA Job Interview scheduling feature.
 *
 * Table used by Ebean for RAJobInterview: rajob_interview (from 1.sql).
 * Fixture IDs start at 9200 to avoid collisions with other integration tests.
 * Applications 9201 and 9203 are seeded; 9201 is used by most tests while
 * 9203 is reserved for scheduleInterview_returns200AndCreatesRecord so that
 * test is order-independent (9201 always has a pre-existing interview).
 */
public class RAJobInterviewControllerTest {

    static Application application;
    static long createdInterviewId = -1;

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

    // -----------------------------------------------------------------------
    // 1. Schedule interview — happy path (uses application 9203, no prior interview)
    // -----------------------------------------------------------------------

    @Test
    public void scheduleInterview_returns200AndCreatesRecord() {
        Http.RequestBuilder request = fakeRequest(POST, "/rajobInterview/schedule")
                .bodyJson(buildScheduleBody(9203L));
        Result result = route(application, request);

        assertEquals("Schedule should return 200", OK, result.status());

        long newId = Long.parseLong(contentAsString(result).trim());
        assertTrue("Returned interview ID should be positive", newId > 0);
    }

    // -----------------------------------------------------------------------
    // 2. Retrieve by application ID (uses pre-seeded interview for 9201)
    // -----------------------------------------------------------------------

    @Test
    public void getByApplicationId_returns200WithCorrectFields() {
        Http.RequestBuilder request = fakeRequest(GET,
                "/rajobInterview/getByApplicationId/9201");
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode iv = Json.parse(contentAsString(result));
        assertFalse("Response should not be null JSON", iv.isNull());
        assertEquals("Interview date should match",
                "2026-06-10", iv.path("interviewDate").asText());
        assertEquals("Interview type should match",
                "in-person", iv.path("interviewType").asText());
        assertEquals("Status should be scheduled",
                "scheduled", iv.path("status").asText());
    }

    // -----------------------------------------------------------------------
    // 3. Retrieve by interview ID
    // -----------------------------------------------------------------------

    @Test
    public void getById_returns200WithInterviewData() {
        Http.RequestBuilder request = fakeRequest(GET,
                "/rajobInterview/getById/" + createdInterviewId);
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode iv = Json.parse(contentAsString(result));
        assertEquals(createdInterviewId, iv.path("id").asLong());
        assertEquals("SOE Seminar Room 3-01",
                iv.path("location").asText());
    }

    // -----------------------------------------------------------------------
    // 4. Student lists their interviews
    // -----------------------------------------------------------------------

    @Test
    public void getByApplicant_returnsStudentsInterview() {
        Http.RequestBuilder request = fakeRequest(GET,
                "/rajobInterview/getByApplicant/9202");
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode arr = Json.parse(contentAsString(result));
        assertTrue("Response should be an array", arr.isArray());
        assertTrue("Student should have at least one interview", arr.size() >= 1);

        boolean found = false;
        for (JsonNode iv : arr) {
            if (iv.path("id").asLong() == createdInterviewId) {
                found = true;
                break;
            }
        }
        assertTrue("Pre-seeded interview should appear in student list", found);
    }

    // -----------------------------------------------------------------------
    // 5. Faculty lists all interviews they scheduled
    // -----------------------------------------------------------------------

    @Test
    public void getByPublisher_returnsFacultyInterviews() {
        Http.RequestBuilder request = fakeRequest(GET,
                "/rajobInterview/getByPublisher/9200");
        Result result = route(application, request);

        assertEquals(OK, result.status());
        JsonNode arr = Json.parse(contentAsString(result));
        assertTrue("Response should be an array", arr.isArray());
        assertTrue("Faculty should have at least one interview scheduled", arr.size() >= 1);
    }

    // -----------------------------------------------------------------------
    // 6. Duplicate scheduling is rejected (9201 already has a scheduled interview)
    //    Common.badRequestWrapper returns HTTP 200 with {"error": "..."} body.
    // -----------------------------------------------------------------------

    @Test
    public void scheduleInterview_duplicateIsRejected() {
        Http.RequestBuilder request = fakeRequest(POST, "/rajobInterview/schedule")
                .bodyJson(buildScheduleBody(9201L));
        Result result = route(application, request);

        assertEquals("Duplicate schedule should return 200 with error body", OK, result.status());
        assertTrue("Response body should contain error field",
                contentAsString(result).contains("error"));
    }

    // -----------------------------------------------------------------------
    // 7. Cancel interview
    // -----------------------------------------------------------------------

    @Test
    public void cancelInterview_setsStatusToCancelled() {
        Http.RequestBuilder cancel = fakeRequest(GET,
                "/rajobInterview/cancel/" + createdInterviewId);
        Result cancelResult = route(application, cancel);
        assertEquals(OK, cancelResult.status());

        Http.RequestBuilder check = fakeRequest(GET,
                "/rajobInterview/getById/" + createdInterviewId);
        Result checkResult = route(application, check);
        JsonNode iv = Json.parse(contentAsString(checkResult));
        assertEquals("Status should now be cancelled",
                "cancelled", iv.path("status").asText());

        // Reset so other tests that depend on a live interview still pass
        Ebean.createSqlUpdate(
                "UPDATE rajob_interview SET status='scheduled' WHERE id=" + createdInterviewId
        ).execute();
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static JsonNode buildScheduleBody(long applicationId) {
        return Json.parse(
            "{" +
            "\"rajobApplicationId\": " + applicationId + "," +
            "\"interviewDate\": \"2026-06-10\"," +
            "\"interviewTime\": \"14:00\"," +
            "\"location\": \"SOE Seminar Room 3-01\"," +
            "\"interviewType\": \"in-person\"," +
            "\"notes\": \"Please bring a copy of your CV and transcript.\"" +
            "}"
        );
    }

    // -----------------------------------------------------------------------
    // Fixture seed / clean
    // -----------------------------------------------------------------------

    private static void seedFixture() {
        // Professor (userType = 1)
        Ebean.createSqlUpdate(
            "INSERT INTO `user` (id, first_name, last_name, user_name, user_type, " +
            "rating, rating_count, recommend_rating, recommend_rating_count, " +
            "service_provider, service_execution_counts, service_user, unread_mention) VALUES " +
            "(9200, 'Alice', 'Prof', 'aliceprof_iv', 1, 0, 0, 0, 0, 0, 0, 0, 0)"
        ).execute();

        // Student (userType = 4)
        Ebean.createSqlUpdate(
            "INSERT INTO `user` (id, first_name, last_name, user_name, user_type, " +
            "rating, rating_count, recommend_rating, recommend_rating_count, " +
            "service_provider, service_execution_counts, service_user, unread_mention) VALUES " +
            "(9202, 'Bob', 'Student', 'bobstudent_iv', 4, 0, 0, 0, 0, 0, 0, 0, 0)"
        ).execute();

        // RA Job posted by the professor
        Ebean.createSqlUpdate(
            "INSERT INTO rajob (id, is_active, status, title, min_salary, max_salary, " +
            "ra_types, number_of_applicants, rajob_publisher_id) VALUES " +
            "(9200, 'true', 'open', 'ML Research Assistant (IV Test)', 1000, 2000, 1, 0, 9200)"
        ).execute();

        // Application 9201 — used by most tests (gets the pre-seeded interview)
        Ebean.createSqlUpdate(
            "INSERT INTO rajob_application (id, rajob_id, applicant_id, " +
            "apply_headline, rating, rating_count, recommend_rating, recommend_rating_count) VALUES " +
            "(9201, 9200, 9202, 'Interested in ML research', 0, 0, 0, 0)"
        ).execute();

        // Application 9203 — reserved for scheduleInterview_returns200AndCreatesRecord
        Ebean.createSqlUpdate(
            "INSERT INTO rajob_application (id, rajob_id, applicant_id, " +
            "apply_headline, rating, rating_count, recommend_rating, recommend_rating_count) VALUES " +
            "(9203, 9200, 9202, 'Second application for IV test', 0, 0, 0, 0)"
        ).execute();

        // Pre-seed one scheduled interview for application 9201
        Ebean.createSqlUpdate(
            "INSERT INTO rajob_interview " +
            "(rajob_application_id, interview_date, interview_time, location, " +
            " interview_type, notes, status, created_time) VALUES " +
            "(9201, '2026-06-10', '14:00', 'SOE Seminar Room 3-01', " +
            " 'in-person', 'Please bring a copy of your CV and transcript.', " +
            " 'scheduled', '2026-05-04 00:00:00')"
        ).execute();

        // Store the auto-generated interview ID for use in tests
        io.ebean.SqlRow row = Ebean.createSqlQuery(
            "SELECT id FROM rajob_interview WHERE rajob_application_id = 9201"
        ).findOne();
        createdInterviewId = row != null ? row.getLong("id") : -1;
    }

    private static void cleanFixture() {
        Ebean.createSqlUpdate(
            "DELETE FROM rajob_interview WHERE rajob_application_id IN (9201, 9203)"
        ).execute();
        Ebean.createSqlUpdate("DELETE FROM rajob_application WHERE id IN (9201, 9203)").execute();
        Ebean.createSqlUpdate("DELETE FROM rajob              WHERE id = 9200").execute();
        Ebean.createSqlUpdate("DELETE FROM `user`             WHERE id IN (9200, 9202)").execute();
    }
}
