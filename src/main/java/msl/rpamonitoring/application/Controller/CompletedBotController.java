package msl.rpamonitoring.application.Controller;

import msl.rpamonitoring.application.Service.CompletedBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rbm/apis/completed-bots")
public class CompletedBotController {
    @Autowired
    CompletedBotService completedBotService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getCompletedBots(@PathVariable long projectId){
        return completedBotService.getCompletedBotByProjectId(projectId);
    }
}
