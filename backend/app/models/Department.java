package models;

import lombok.*;
import java.util.List;

/**
 * Plain DTO representing aggregated statistics for an academic department.
 * No {@code @Entity} annotation — never persisted; data is derived at query time
 * from {@code researcher_info}, {@code project}, {@code paper}, {@code author}, and
 * {@code author_paper} tables.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    /** Department name (acts as natural key; sourced from researcher_info.department). */
    private String name;

    /** School/college the department belongs to (sourced from researcher_info.school). */
    private String school;

    /** Number of faculty rows in researcher_info for this department. */
    private int facultyCount;

    /** Projects with is_active = 'true' or '1' whose PI is in this department. */
    private int activeProjectCount;

    /** Publications within the last 3 calendar years authored by faculty in this department. */
    private int publicationCountLast3Years;

    /** Projects whose sponsor_organization_id IS NOT NULL, PI in this department. */
    private int fundedProjectCount;

    /** Top-5 keywords extracted by frequency from research_fields of all faculty. */
    private List<String> topKeywords;

    /**
     * Populated only on detail responses (null on list endpoint responses).
     * Each entry corresponds to one ResearcherInfo row for this department.
     */
    private List<DepartmentFaculty> faculty;

    /**
     * Populated only on detail responses (null on list endpoint responses).
     * Projects whose PI belongs to this department.
     */
    private List<DepartmentProject> projects;

    // ------------------------------------------------------------------
    // Inner DTOs
    // ------------------------------------------------------------------

    /** Lightweight faculty summary used inside a department detail response. */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DepartmentFaculty {
        private Long userId;
        private String firstName;
        private String lastName;
        private String homepage;
        private String researchFields;
    }

    /** Lightweight project summary used inside a department detail response. */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DepartmentProject {
        private Long id;
        private String title;
        private String isActive;
        private String startDate;
        private Long principalInvestigatorId;
    }
}
