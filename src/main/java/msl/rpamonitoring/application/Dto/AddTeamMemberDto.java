package msl.rpamonitoring.application.Dto;

import lombok.Data;

@Data
public class AddTeamMemberDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private Long projectId;
    private Long adminId;
}
