package msl.rpamonitoring.application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendForgetPasswordEmail(String toEmail, String otp) {
        String emailBody = String.format(
                "Hii Dear User,\n\n" +
                        "A password reset request was sent for your account. " +
                        "If you did not make this request, please ignore this email.\n\n" +
                        " Please enter the  OTP to forget your password: %s\n" +
                        "\n\nIf you have any questions or need further assistance, feel free to contact our support team.\n\n" +
                        "Thank you,\n" +
                        "RPA Bot Monitoring Support",
                otp
        );
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Forget Password");
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendVerificationEmail(String toEmail, String otp) {
        String emailBody = String.format(
                "Hii Dear user,\n\n" +
                        "Thank you for registering! To complete your signup," +
                        " please verify your email by using OTP:\n %s\n" +
                        "\n\nThank you,\n" +
                        "RPA Bot Monitoring Support",
               otp
        );
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Email Verification");
        message.setText(emailBody);
        mailSender.send(message);
    }
}
