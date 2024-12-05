package msl.rpamonitoring.application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationSpecificProjects {
    private Long organizationId;
    private String organizationName;
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private Long userId;
    private boolean isAdmin;
    private String userFirstName;
    private String userLastName;
}
