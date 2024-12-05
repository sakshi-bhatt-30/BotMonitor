package msl.rpamonitoring.application.Dto;

import lombok.Data;
import msl.rpamonitoring.application.Enum.Status;

@Data
public class UpdateRemarksDto {
    private Long processId;
    private Status status;
    private String remarks;
}
