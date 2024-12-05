package msl.rpamonitoring.application.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
import msl.rpamonitoring.application.Enum.Status;

@Data
public class BotDto {           
    private Long processId;   
    private Long projectId;      
    private String processName;      
    private LocalTime startTime; 
    private LocalTime endTime; 
    private LocalTime notificationTime;
    private LocalDate date;
}