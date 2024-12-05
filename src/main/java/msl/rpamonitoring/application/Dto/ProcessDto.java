package msl.rpamonitoring.application.Dto;

import lombok.Data;

@Data
public class ProcessDto {
    private Long userId;
    private Long projectId;
    private String processName;
    private int executionTime;
}
