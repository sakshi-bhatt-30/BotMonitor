package msl.rpamonitoring.application.Dto;



import lombok.Data;
import msl.rpamonitoring.application.Enum.Roles;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String password;
    private boolean isAdmin;
    private Roles role;
    private String deviceId;
}