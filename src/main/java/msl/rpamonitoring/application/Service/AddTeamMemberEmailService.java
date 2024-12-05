package msl.rpamonitoring.application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AddTeamMemberEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendCredentialsEmail(String email,String password){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Welcome to RPA Monitoring System");

        message.setText("Dear User, \n\n" +
                "You have been added to the RPA Monitoring System. \n\n" +
                "Your credentials are as follows: \n" +
                "Username: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "Please login to the system and change your password. \n\n" +
                "Thank you. \n\n" +
                "RPA Monitoring System");


        javaMailSender.send(message);
    }
}
