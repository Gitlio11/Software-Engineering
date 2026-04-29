package models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    private String name;
    private String school;
    private int facultyCount;
    private int activeProjectCount;
    private int publicationCountLast3Years;
    private int fundedProjectCount;
    private List<String> topKeywords;

    // null on list responses, populated on detail
    private List<DepartmentFaculty> faculty;
    private List<DepartmentProject> projects;

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
