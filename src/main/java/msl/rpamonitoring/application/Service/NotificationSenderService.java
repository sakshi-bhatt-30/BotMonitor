package msl.rpamonitoring.application.Service;

import msl.rpamonitoring.application.Dto.BotDto;
import msl.rpamonitoring.application.Repository.DeviceInfoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class NotificationSenderService {

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    public ResponseEntity<?> sendNotification(BotDto botDto) {
        // Get necessary fields from BotDto
        String title = "Bot Notification";
        String body = "Bot ID: " + botDto.getProcessId() + 
                      ", Process Name: " + botDto.getProcessName() + 
                      ", Notification Time: " + botDto.getNotificationTime() + 
                      ", Date: " + botDto.getDate();

        Long projectId = botDto.getProjectId();

        List<String> deviceTokens =  deviceInfoRepository.findAllTokensByProjectId(projectId);


        // Check if there are any tokens to send
        if (deviceTokens.isEmpty()) {
            return ResponseEntity.badRequest().body("No device tokens available for notification");
        }
        
        // Track results of notifications sent
        int successCount = 0;
        int failureCount = 0;

        for (String token : deviceTokens) {
            try {
                // Send the notification to each token
                firebaseMessagingService.sendNotificationFromFireBase(token, title, body);
                successCount++;
            } catch (Exception e) {
                System.err.println("Failed to send notification to token " + token + ": " + e.getMessage());
                failureCount++;
            }
        }

        // Return a summary response
        String result = String.format("Notification sent successfully to %d devices, failed for %d devices.", successCount, failureCount);
        return ResponseEntity.ok(result);                 
    }
}
