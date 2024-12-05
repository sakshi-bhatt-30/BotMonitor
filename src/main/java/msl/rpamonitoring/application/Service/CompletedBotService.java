package msl.rpamonitoring.application.Service;

import msl.rpamonitoring.application.Dto.BotCompletedDto;
import msl.rpamonitoring.application.Dto.BotDto;
import msl.rpamonitoring.application.Entity.BotExecutions;
import msl.rpamonitoring.application.Enum.Status;
import msl.rpamonitoring.application.Repository.BotExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompletedBotService {
    @Autowired
    BotExecutionRepository botExecutionRepository;
    public List<BotCompletedDto> getCompletedBots() {
        List<BotExecutions> bots=botExecutionRepository.findAll();
        LocalDateTime now=LocalDateTime.now();

        return bots.stream()
                .filter(execution->isCompleted(execution,now))
                .sorted(Comparator
                        .comparing(BotExecutions::getDate) // Primary sort by date
                        .thenComparing(BotExecutions::getEndTime)//secondary by end time
                        .reversed())
                .map(this::mapToBotDto)
                .collect(Collectors.toList());
    }

    private boolean isCompleted(BotExecutions execution, LocalDateTime now) {
        LocalDateTime end=LocalDateTime.of(execution.getDate(), execution.getEndTime());
        return now.isAfter(end)||execution.getStatus()== Status.ACCEPTED||execution.getStatus()==Status.NOTACCEPTED;
    }


    private BotCompletedDto mapToBotDto(BotExecutions execution) {
        BotCompletedDto dto = new BotCompletedDto();

//        // Fetch process details using processId
//        Process process = processRepository.findById(execution.getProcessId())
//                .orElseThrow(() -> new RuntimeException("Process not found"));
        dto.setUserId(execution.getUserId());
        dto.setProjectId(execution.getProjectId());
        dto.setProcessId(execution.getProcessId());
        dto.setProcessName(execution.getProcessName()); // Set process name from Process entity
        dto.setStartTime(execution.getStartTime());
        dto.setEndTime(execution.getEndTime());
        dto.setDate(execution.getDate());
        // Set notification time (example: 30 minutes before start time)
        dto.setNotificationTime(execution.getStartTime().minusMinutes(15));
        dto.setRemarks(execution.getRemarks());
        dto.setAccepted(execution.isAccepted());
        dto.setAcceptedBy("Purshottam Mishra");
        dto.setStatus(execution.getStatus());

        return dto;
    }

    public ResponseEntity<?> getCompletedBotByProjectId(Long projectId){
        List<BotCompletedDto> bots=getCompletedBots();
        List<BotCompletedDto> response=new ArrayList<>();
        for(BotCompletedDto bot:bots){
            if(bot.getProjectId().equals(projectId)){
                response.add(bot);
            }
        }
        if(response.isEmpty()){
            ResponseEntity.badRequest().body("project not found!Wrong Project Id");
        }
        return  ResponseEntity.ok().body(response);
    }

//    /
}
