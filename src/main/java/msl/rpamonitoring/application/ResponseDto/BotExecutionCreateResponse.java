package msl.rpamonitoring.application.ResponseDto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BotExecutionCreateResponse {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime notificationTime;
    private String status;
    private String remarks;
    private boolean isAccepted;
    private Long acceptedByUserId;
    private Long processId;
    private String processName;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}