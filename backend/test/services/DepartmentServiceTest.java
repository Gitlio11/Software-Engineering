package services;

import models.Department;
import models.rest.RESTResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Pure-logic unit tests for {@link DepartmentService}.
 * No Play application or database connection is required.
 */
public class DepartmentServiceTest {

    private final DepartmentService service = new DepartmentService();

    // ------------------------------------------------------------------
    // extractTopKeywords
    // ------------------------------------------------------------------

    @Test
    public void extractTopKeywords_nullInput_returnsEmptyList() {
        List<String> result = service.extractTopKeywords(null, 5);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void extractTopKeywords_blankInput_returnsEmptyList() {
        List<String> result = service.extractTopKeywords("   ", 5);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void extractTopKeywords_dropsTokensShorterThan3Chars() {
        // "AI" and "ML" are exactly 2 chars and must be dropped
        String input = "AI, ML, machine learning, machine learning";
        List<String> result = service.extractTopKeywords(input, 5);
        assertFalse("'ai' should be dropped (< 3 chars)", result.contains("ai"));
        assertFalse("'ml' should be dropped (< 3 chars)", result.contains("ml"));
        assertTrue(result.contains("machine learning"));
    }

    @Test
    public void extractTopKeywords_ranksByFrequencyDescending() {
        // "deep learning" appears 3×, "nlp" appears 2×, "robotics" appears 1×
        String input = "deep learning, nlp, deep learning, robotics, nlp, deep learning";
        List<String> result = service.extractTopKeywords(input, 5);
        assertEquals("deep learning", result.get(0));
        assertEquals("nlp", result.get(1));
        assertEquals("robotics", result.get(2));
    }

    @Test
    public void extractTopKeywords_caseInsensitive() {
        // "Machine Learning" and "machine learning" should be treated as the same token
        String input = "Machine Learning, machine learning, Machine Learning";
        List<String> result = service.extractTopKeywords(input, 5);
        assertEquals(1, result.size());
        assertEquals("machine learning", result.get(0));
    }

    @Test
    public void extractTopKeywords_respectsTopNLimit() {
        String input = "alpha, beta, gamma, delta, epsilon, zeta";
        List<String> result = service.extractTopKeywords(input, 3);
        assertEquals(3, result.size());
    }

    @Test
    public void extractTopKeywords_acceptsSemicolonSeparator() {
        String input = "bioinformatics; genomics; bioinformatics";
        List<String> result = service.extractTopKeywords(input, 5);
        assertEquals("bioinformatics", result.get(0));
        assertTrue(result.contains("genomics"));
    }

    @Test
    public void extractTopKeywords_trimsWhitespace() {
        String input = "  data science  ,  data science  ,  algorithms  ";
        List<String> result = service.extractTopKeywords(input, 5);
        assertTrue(result.contains("data science"));
        assertTrue(result.contains("algorithms"));
        assertEquals("data science", result.get(0)); // highest frequency
    }

    @Test
    public void extractTopKeywords_emptyStringInput_returnsEmptyList() {
        List<String> result = service.extractTopKeywords("", 5);
        assertTrue(result.isEmpty());
    }

    // ------------------------------------------------------------------
    // paginateAndSort
    // ------------------------------------------------------------------

    @Test
    public void paginateAndSort_defaultSortIsNameAsc() {
        List<Department> deps = buildSampleList();
        RESTResponse response = service.paginateAndSort(
            deps, Optional.empty(), Optional.empty(), "name", "asc");
        // First item after name-asc sort should be "Computer Science" (C < M)
        String firstItem = response.getItems().get(0).get("name").asText();
        assertEquals("Computer Science", firstItem);
    }

    @Test
    public void paginateAndSort_sortByFacultyCountDesc() {
        List<Department> deps = buildSampleList();
        // Computer Science has facultyCount=5, Mathematics has facultyCount=2
        RESTResponse response = service.paginateAndSort(
            deps, Optional.empty(), Optional.empty(), "facultyCount", "desc");
        String firstItem = response.getItems().get(0).get("name").asText();
        assertEquals("Computer Science", firstItem);
    }

    @Test
    public void paginateAndSort_unknownSortKeyFallsBackToNameAsc() {
        List<Department> deps = buildSampleList();
        RESTResponse response = service.paginateAndSort(
            deps, Optional.empty(), Optional.empty(), "invalidKey", "asc");
        String firstItem = response.getItems().get(0).get("name").asText();
        assertEquals("Computer Science", firstItem);
    }

    @Test
    public void paginateAndSort_emptyList_returnsEmptyItems() {
        RESTResponse response = service.paginateAndSort(
            new ArrayList<>(), Optional.empty(), Optional.empty(), "name", "asc");
        assertEquals(0, response.getItems().size());
        assertEquals(0, response.getTotal());
    }

    @Test
    public void paginateAndSort_appliesPageLimit() {
        List<Department> deps = buildSampleList(); // 2 items
        RESTResponse response = service.paginateAndSort(
            deps, Optional.empty(), Optional.of(1), "name", "asc");
        assertEquals(1, response.getItems().size());
        assertEquals(2, response.getTotal());
    }

    @Test
    public void paginateAndSort_appliesOffset() {
        List<Department> deps = buildSampleList(); // 2 items: CS, Math
        RESTResponse response = service.paginateAndSort(
            deps, Optional.of(1), Optional.of(5), "name", "asc");
        // offset=1 skips Computer Science, returns Mathematics
        assertEquals(1, response.getItems().size());
        assertEquals("Mathematics", response.getItems().get(0).get("name").asText());
    }

    @Test
    public void paginateAndSort_sortByActiveProjectCountDesc() {
        List<Department> deps = buildSampleList();
        // CS has activeProjectCount=3, Math has activeProjectCount=1
        RESTResponse response = service.paginateAndSort(
            deps, Optional.empty(), Optional.empty(), "activeProjectCount", "desc");
        assertEquals("Computer Science", response.getItems().get(0).get("name").asText());
    }

    @Test
    public void paginateAndSort_sortByFundedProjectCountAsc() {
        List<Department> deps = buildSampleList();
        // Math has fundedProjectCount=0, CS has fundedProjectCount=2
        RESTResponse response = service.paginateAndSort(
            deps, Optional.empty(), Optional.empty(), "fundedProjectCount", "asc");
        assertEquals("Mathematics", response.getItems().get(0).get("name").asText());
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    /**
     * Builds a two-department sample list for pagination/sort tests.
     * Uses {@code new ArrayList<>} so the service can sort it in-place.
     */
    private List<Department> buildSampleList() {
        Department cs = new Department();
        cs.setName("Computer Science");
        cs.setSchool("Engineering");
        cs.setFacultyCount(5);
        cs.setActiveProjectCount(3);
        cs.setPublicationCountLast3Years(10);
        cs.setFundedProjectCount(2);
        cs.setTopKeywords(Arrays.asList("machine learning", "algorithms"));

        Department math = new Department();
        math.setName("Mathematics");
        math.setSchool("Science");
        math.setFacultyCount(2);
        math.setActiveProjectCount(1);
        math.setPublicationCountLast3Years(4);
        math.setFundedProjectCount(0);
        math.setTopKeywords(Arrays.asList("topology", "algebra"));

        return new ArrayList<>(Arrays.asList(cs, math));
    }
}
