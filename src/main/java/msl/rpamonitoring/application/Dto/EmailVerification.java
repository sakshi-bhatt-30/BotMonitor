package msl.rpamonitoring.application.Dto;

import lombok.Data;

@Data
public class EmailVerification {
    String otp;
    String email;
}
