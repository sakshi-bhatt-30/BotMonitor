package msl.rpamonitoring.application.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;
import msl.rpamonitoring.application.Enum.Status;

@Data
public class BotCompletedDto {
    private Long userId;
    private Long projectId;
    private Long processId;
    private String processName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime notificationTime;
    private LocalDate date;
    private Status status;
    private String remarks;
    private boolean isAccepted;
    private String acceptedBy;  
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
