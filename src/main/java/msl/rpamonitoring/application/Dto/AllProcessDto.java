package msl.rpamonitoring.application.Dto;

import lombok.Data;
import msl.rpamonitoring.application.Entity.Users;

import java.time.LocalDateTime;

@Data
public class AllProcessDto {
    private Long userId;
    private Long processId;
    private Long projectId;
    private String processName;
    private int executionTime;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
}
