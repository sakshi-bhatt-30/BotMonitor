package msl.rpamonitoring.application.Controller;

import java.util.List;

import msl.rpamonitoring.application.Service.UpcomingBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import msl.rpamonitoring.application.Dto.BotDto;

@RestController
@RequestMapping("/upcoming-bots")
public class UpcomingBotController {
    @Autowired
    private UpcomingBotService upComingBotService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BotDto>> getUpcomingBotsProjectSpecific(@PathVariable Long projectId) {
        List<BotDto> projectSpecificBots = upComingBotService.getUpcomingBotsProjectSpecific(projectId);
        return new ResponseEntity<>(projectSpecificBots, HttpStatus.OK);
    }
    

}
