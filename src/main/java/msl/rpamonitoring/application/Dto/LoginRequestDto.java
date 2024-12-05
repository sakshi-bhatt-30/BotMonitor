package msl.rpamonitoring.application.Dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    private String deviceToken;
}
