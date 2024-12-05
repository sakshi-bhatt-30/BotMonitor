package msl.rpamonitoring.application.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msl.rpamonitoring.application.Enum.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BotExecutions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long projectId;
    private Long processId;
    private String processName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime notificationTime;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String remarks;
    private boolean isAccepted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}   
