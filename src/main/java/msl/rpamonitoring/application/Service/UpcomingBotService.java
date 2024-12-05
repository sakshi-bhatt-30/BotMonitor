package msl.rpamonitoring.application.Service;

import msl.rpamonitoring.application.Dto.BotDto;
import msl.rpamonitoring.application.Entity.BotExecutions;
import msl.rpamonitoring.application.Repository.BotExecutionRepository;
import msl.rpamonitoring.application.Repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpcomingBotService {
    @Autowired
    BotExecutionRepository botExecutionRepository;

    @Autowired
    ProcessRepository processRepository;


    public List<BotDto> getUpcomingBots() {
        List<BotExecutions> bots=botExecutionRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        // Filter the executions where the start time is in the future or is today but not yet started
        return bots.stream()
                .filter(execution -> isUpcoming(execution, now))
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
//        dto.setStatus(Status.UPCOMING);


        // Set notification time (example: 15 minutes before start time)
        dto.setNotificationTime(execution.getStartTime().minusMinutes(15));

        return dto;
    }
    private boolean isUpcoming(BotExecutions execution, LocalDateTime currentTime) {
        // Compare the date and start time with the current time
        LocalDateTime endWindow = currentTime.plusHours(48);
        LocalDateTime executionStartTime = LocalDateTime.of(execution.getDate(), execution.getStartTime());
        LocalDateTime executionEndTime = LocalDateTime.of(execution.getDate(), execution.getEndTime());

        return executionStartTime.isAfter(currentTime) && executionStartTime.isBefore(endWindow) && executionEndTime.isAfter(executionStartTime);
    }

    public List<BotDto> getUpcomingBotsProjectSpecific(Long projectId){
        List<BotDto> allUpcomingBots=getUpcomingBots();
        List<BotDto> response=new ArrayList<>();
        for(BotDto bot:allUpcomingBots){
            if(bot.getProjectId().equals(projectId)){
                response.add(bot);
            }
        }
        return response;
    }
}
