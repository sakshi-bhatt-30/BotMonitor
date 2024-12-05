package msl.rpamonitoring.application.ResponseDto;

import java.util.List;

import lombok.Data;

@Data
public class UserCreateResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private boolean isActive;
    private boolean isAdmin;
    private Long organizationId; 
    private List<Long> projectIds; 
}
