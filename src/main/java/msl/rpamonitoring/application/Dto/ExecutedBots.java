package msl.rpamonitoring.application.Dto;

import lombok.Data;
import msl.rpamonitoring.application.Enum.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ExecutedBots {
    private long executedBotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime notificationTime;
    private LocalDate date;
    private Status status;
    private String remarks;
    private String acceptedBy;
}
