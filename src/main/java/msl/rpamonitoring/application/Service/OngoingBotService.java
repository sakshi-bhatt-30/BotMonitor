package msl.rpamonitoring.application.Service;

import msl.rpamonitoring.application.Dto.BotDto;
import msl.rpamonitoring.application.Dto.UpdateRemarksDto;
import msl.rpamonitoring.application.Entity.BotExecutions;
import msl.rpamonitoring.application.Enum.Status;
import msl.rpamonitoring.application.Repository.BotExecutionRepository;
import msl.rpamonitoring.application.Repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OngoingBotService {
    @Autowired
    BotExecutionRepository botExecutionRepository;

    @Autowired
    ProcessRepository processRepository;
    public List<BotDto> getOngoingBots() {
        List<BotExecutions> bots=botExecutionRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        return bots.stream()
                .filter(execution -> isOngoing(execution, now))
                .sorted(Comparator
                        .comparing(BotExecutions::getDate) // Primary sort by date
                        .thenComparing(BotExecutions::getStartTime)) // Secondary sort by start time);
                .map(this::mapToBotDto)
                .collect(Collectors.toList());
    }
    private BotDto mapToBotDto(BotExecutions execution) {
        BotDto dto = new BotDto();

//        // Fetch process details using processId
//        Process process = processRepository.findById(execution.getProcessId())
//                .orElseThrow(() -> new RuntimeException("Process not found"));

        dto.setProcessId(execution.getProcessId());
        dto.setProjectId(execution.getProjectId());
        dto.setProcessName(execution.getProcessName()); // Set process name from Process entity
        dto.setStartTime(execution.getStartTime());
        dto.setEndTime(execution.getEndTime());
        dto.setDate(execution.getDate());
//        dto.setStatus(Status.ONGOING);


        // Set notification time (example: 30 minutes before start time)
        dto.setNotificationTime(execution.getStartTime().minusMinutes(15));

        return dto;
    }

    private boolean isOngoing(BotExecutions execution, LocalDateTime currentTime) {
        // Compare the date and start time with the current time
        LocalDateTime executionStartTime = LocalDateTime.of(execution.getDate(), execution.getStartTime());
        LocalDateTime executionEndTime = LocalDateTime.of(execution.getDate(), execution.getEndTime());

        return currentTime.isAfter(executionStartTime) && currentTime.isBefore(executionEndTime)&&execution.getStatus()==Status.PENDING;
    }

    public List<BotDto> getOngoingBotsProjectSpecific(Long projectId){
        List<BotDto> allOngoingBots=getOngoingBots();
        List<BotDto> response=new ArrayList<>();
        for(BotDto bot:allOngoingBots){
            if(bot.getProjectId().equals(projectId)){
                response.add(bot);
            }
        }
        return response;
    }

    public ResponseEntity<?> updateRemarks(UpdateRemarksDto data) {
        System.out.println(data.getProcessId());
        Optional<BotExecutions> botExecution = botExecutionRepository.findByProcessId(data.getProcessId());
        if(botExecution.isPresent()&&botExecution.get().getStatus()==Status.PENDING){

                System.out.println("updating");
                if (data.getStatus() == Status.ACCEPTED) {
                    botExecution.get().setAccepted(true);
                }
                if(data.getStatus()==Status.NOTACCEPTED){
                    botExecution.get().setAccepted(false);
                }

//            update status and remarks;
                botExecution.get().setRemarks(data.getRemarks());
                botExecution.get().setStatus(data.getStatus());
                botExecution.get().setUpdatedAt(LocalDateTime.now());

                botExecutionRepository.save(botExecution.get());

                return ResponseEntity.ok().body("Remarks added");
        }
        return ResponseEntity.badRequest().body("Process Not found or already accepted or rejected");
    }
}
