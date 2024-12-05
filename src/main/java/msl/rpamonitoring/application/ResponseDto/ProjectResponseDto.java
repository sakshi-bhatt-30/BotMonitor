package msl.rpamonitoring.application.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResponseDto {
    private Long projectId;
    private String projectName;
    private String projectDescription;
}
