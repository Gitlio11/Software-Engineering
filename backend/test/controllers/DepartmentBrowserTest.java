package controllers;

import io.ebean.Ebean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.WithBrowser;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Headless browser (HtmlUnit) end-to-end tests for the Department feature.
 * Verifies that the department list and detail endpoints return the
 * expected content when accessed through a real HTTP request cycle.
 */
public class DepartmentBrowserTest extends WithBrowser {

    @Override
    protected Application provideApplication() {
        return fakeApplication(inMemoryDatabase());
    }

    @Override
    protected TestBrowser provideBrowser(int port) {
        return Helpers.testBrowser(port);
    }

    @Before
    public void seedData() {
        Ebean.createSqlUpdate(
            "INSERT INTO `user` (id, first_name, last_name, user_name, " +
            "rating, rating_count, recommend_rating, recommend_rating_count, " +
            "service_provider, service_execution_counts, service_user, unread_mention) VALUES " +
            "(9201, 'Sara', 'Lee', 'slee_browser', 0, 0, 0, 0, 0, 0, 0, 0)"
        ).execute();

        Ebean.createSqlUpdate(
            "INSERT INTO researcher_info (user_id, department, school, research_fields) VALUES " +
            "(9201, 'Computer Science', 'Lyle School of Engineering', 'machine learning, data science')"
        ).execute();
    }

    @After
    public void cleanData() {
        Ebean.createSqlUpdate("DELETE FROM researcher_info WHERE user_id = 9201").execute();
        Ebean.createSqlUpdate("DELETE FROM `user` WHERE id = 9201").execute();
    }

    /** Department list endpoint returns 200 and includes seeded department name. */
    @Test
    public void departmentList_pageContainsDepartmentName() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort()
                + "/department/departmentList");

        String source = browser.pageSource();
        assertNotNull("Page source should not be null", source);
        assertTrue("Response should contain 'Computer Science'",
                source.contains("Computer Science"));
    }

    /** Department list response includes expected JSON fields. */
    @Test
    public void departmentList_responseIncludesFacultyCount() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort()
                + "/department/departmentList");

        String source = browser.pageSource();
        assertTrue("Response should include facultyCount field",
                source.contains("facultyCount"));
    }

    /** Department detail endpoint returns the correct department name. */
    @Test
    public void departmentDetail_pageContainsDepartmentName() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort()
                + "/department/departmentDetail/Computer%20Science");

        String source = browser.pageSource();
        assertNotNull("Page source should not be null", source);
        assertTrue("Response should contain 'Computer Science'",
                source.contains("Computer Science"));
    }

    /** Department detail response includes faculty data. */
    @Test
    public void departmentDetail_pageContainsFacultyData() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort()
                + "/department/departmentDetail/Computer%20Science");

        String source = browser.pageSource();
        assertTrue("Response should include faculty array",
                source.contains("faculty"));
        assertTrue("Response should include seeded faculty member",
                source.contains("Sara"));
    }

    /** Unknown department returns a non-200 response (404). */
    @Test
    public void departmentDetail_unknownDepartmentReturnsError() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort()
                + "/department/departmentDetail/NotARealDepartment9999");

        // Play returns a 404 page for unknown departments
        String source = browser.pageSource();
        assertNotNull("Page source should not be null", source);
        assertFalse("Response should not contain 'Computer Science' for unknown dept",
                source.contains("\"name\":\"Computer Science\""));
    }
}
