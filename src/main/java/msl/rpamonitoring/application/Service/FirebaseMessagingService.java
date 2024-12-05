package msl.rpamonitoring.application.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {
     public String sendNotificationFromFireBase(String token, String title, String body) throws Exception {
        
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        // Send the message using FirebaseMessaging
        return FirebaseMessaging.getInstance().send(message);
    }

}
