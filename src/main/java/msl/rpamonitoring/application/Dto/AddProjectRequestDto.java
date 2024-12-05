package msl.rpamonitoring.application.Dto;

import lombok.Data;

@Data
public class AddProjectRequestDto {
    private Long orgId;
    private String name;
    private Long userId;
    private String description;
}
