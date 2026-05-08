package controllers;

import models.Department;
import models.Department.DepartmentFaculty;
import models.Department.DepartmentProject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

// Unit tests for the Department model - no database needed
public class DepartmentModelTest {

    @Test
    public void testDepartmentName() {
        Department dept = new Department();
        dept.setName("Computer Science");
        dept.setSchool("Lyle School of Engineering");
        dept.setFacultyCount(18);

        assertEquals("Computer Science", dept.getName());
        assertEquals("Lyle School of Engineering", dept.getSchool());
        assertEquals(18, dept.getFacultyCount());
    }

    @Test
    public void testKeywords() {
        Department dept = new Department();
        List<String> keywords = Arrays.asList("machine learning", "data science", "nlp");
        dept.setTopKeywords(keywords);

        assertEquals(3, dept.getTopKeywords().size());
        assertTrue(dept.getTopKeywords().contains("machine learning"));
    }

    @Test
    public void testFacultyList() {
        Department dept = new Department();
        DepartmentFaculty f = new DepartmentFaculty(1L, "Jia", "Zhang", null, "Machine Learning");
        dept.setFaculty(Arrays.asList(f));

        assertEquals(1, dept.getFaculty().size());
        assertEquals("Jia", dept.getFaculty().get(0).getFirstName());
        assertEquals("Zhang", dept.getFaculty().get(0).getLastName());
    }

    @Test
    public void testProjectList() {
        Department dept = new Department();
        DepartmentProject p = new DepartmentProject(10L, "AI Research", "true", "2023-01-01", 1L);
        dept.setProjects(Arrays.asList(p));

        assertEquals(1, dept.getProjects().size());
        assertEquals("AI Research", dept.getProjects().get(0).getTitle());
    }

    @Test
    public void testEmptyDepartment() {
        Department dept = new Department();
        assertNull(dept.getName());
        assertEquals(0, dept.getFacultyCount());
        assertNull(dept.getFaculty());
    }
}
