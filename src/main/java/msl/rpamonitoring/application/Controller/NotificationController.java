package msl.rpamonitoring.application.Controller;

import msl.rpamonitoring.application.Dto.BotDto;
import msl.rpamonitoring.application.Service.NotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationSenderService notificationSenderService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody BotDto botDto){
        return  notificationSenderService.sendNotification(botDto);
    }

}
