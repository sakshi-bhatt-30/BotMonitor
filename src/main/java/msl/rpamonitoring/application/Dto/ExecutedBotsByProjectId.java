package msl.rpamonitoring.application.Dto;

import lombok.Data;
import msl.rpamonitoring.application.Enum.Status;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExecutedBotsByProjectId {
    private long projectId;
    private long executedBotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime notificationTime;
    private String processName;
    private LocalDate date;
    private Status status;
    private String remarks;
    private String acceptedBy;
}
