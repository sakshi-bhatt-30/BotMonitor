package msl.rpamonitoring.application.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrganisationDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
