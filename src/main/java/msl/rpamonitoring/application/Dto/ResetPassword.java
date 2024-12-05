package msl.rpamonitoring.application.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPassword {
    private String email;
    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$",
            message = "Password must be greater than equal to 8! Password must contain at least 1 small , 1 capital, 1 special character and 1 digit")
    private String newPassword;
    private String otp;
}
