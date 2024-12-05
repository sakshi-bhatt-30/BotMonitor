package msl.rpamonitoring.application.Dto;

import lombok.Data;
import msl.rpamonitoring.application.Enum.DaysOfWeek;
import msl.rpamonitoring.application.Enum.RepeatOption;

import java.time.LocalTime;
import java.util.List;

@Data
public class ScheduledProcessDto {
    private Long processId;
    private  String processName;
    private List<LocalTime> scheduledTime;
    private RepeatOption repeatOption;
    private List<DaysOfWeek> daysOfWeek;
    private List<Integer> monthlyDays;
    private boolean isActive;
}
