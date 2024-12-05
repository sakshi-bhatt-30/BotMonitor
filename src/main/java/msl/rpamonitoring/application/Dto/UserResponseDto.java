package msl.rpamonitoring.application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private String firstName;
    private String lastname;
    private String email;
    private String mobileNumber;
}
