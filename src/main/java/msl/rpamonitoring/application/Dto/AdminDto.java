package msl.rpamonitoring.application.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminDto {
    @Pattern(regexp ="[a-zA-Z]{1,15}" ,message = "first name must be between 3 to 15 character")
    private String firstName;

    @Pattern(regexp ="[a-zA-Z]{1,15}" ,message = "last name must be between 3 to 15 character")
    private String lastName;

    @Email(message = "Enter valid email")
    private String email;

    @Pattern(regexp ="[0-9]{10}",message = "Mobile Number Must be a 10-digit number")
    private String mobileNumber;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$",
            message = "Password must be greater than equal to 8! Password must contain at least 1 small , 1 capital, 1 special character and 1 digit")
    private String password;

    private String confirmPassword;

    @Size(min = 3)
//    @Pattern(regexp ="^[a-zA-Z0-9-@.{}#&!()]+(\\s[a-zA-Z0-9-@{}.#&!()]+)+(\\s[a-zA-Z-@.#&!()]+)?$",message = "Enter valid organization name" )
    private String organizationName;

    @Size(min = 20)
//   @Pattern(regexp ="^[a-zA-Z0-9-@.{}#&!()]+(\\s[a-zA-Z0-9-@{}.#&!()]+)+(\\s[a-zA-Z-@.#&!()]+)?$",message = "Enter valid Description" )
    private String organizationDescription;
}
