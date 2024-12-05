package msl.rpamonitoring.application.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import msl.rpamonitoring.application.Dto.BotDto;

@Service
public class NotificationService{
    @Autowired
    private UpcomingBotService upcomingBotService;

    @Autowired
    private NotificationSenderService notificationSenderService;

    private final Set<Long> sentNotifications = new HashSet<>();

    @Scheduled(fixedRate = 60000)
    public void sendNotifications() {
        List<BotDto> upcomingBots = upcomingBotService.getUpcomingBots();
        LocalDateTime now = LocalDateTime.now();

        for (BotDto bot : upcomingBots) {
            LocalDateTime notificationTime = LocalDateTime.of(bot.getDate(), bot.getNotificationTime());

            // Check if the notification time is within the current minute
            if (ChronoUnit.MINUTES.between(notificationTime, now) == 0 && !sentNotifications.contains(bot.getProcessId())) {
                notificationSenderService.sendNotification(bot);
                sentNotifications.add(bot.getProcessId());
            }
        }
    }

    // Clear sent notifications at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetSentNotifications() {
        sentNotifications.clear();
    }
}
