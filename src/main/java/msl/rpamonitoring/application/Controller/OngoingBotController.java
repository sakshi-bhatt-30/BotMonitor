package msl.rpamonitoring.application.Controller;

import java.util.List;

import msl.rpamonitoring.application.Dto.UpdateRemarksDto;
import msl.rpamonitoring.application.Service.OngoingBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import msl.rpamonitoring.application.Dto.BotDto;

@RestController
@RequestMapping("/ongoing-bots")
public class OngoingBotController {
    @Autowired
    private OngoingBotService ongoingBotService;

     @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BotDto>> getOngoingBotsProjectSpecific(@PathVariable Long projectId) {
        List<BotDto> projectSpecificOngoingBots = ongoingBotService.getOngoingBotsProjectSpecific(projectId);
        return new ResponseEntity<>(projectSpecificOngoingBots, HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<?> updateRemarksAndStatus(@RequestBody UpdateRemarksDto Data){
         return ongoingBotService.updateRemarks(Data);
    }
}
