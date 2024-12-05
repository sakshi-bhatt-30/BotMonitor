package msl.rpamonitoring.application.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import msl.rpamonitoring.application.Dto.JwtToken;
import msl.rpamonitoring.application.Dto.LoginRequestDto;
import msl.rpamonitoring.application.Dto.ResetPassword;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class LoginService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    DeviceInfoService diService;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    EmailService emailService;


    HashMap<Object,String> otpStore=new HashMap<>();

    @Transactional
    public ResponseEntity<?> verify(LoginRequestDto loginDetails) {
        try {
            Optional<Users> user = userRepo.findByEmail(loginDetails.getUsername());
            if (user.isEmpty()) {
                user = userRepo.findByMobileNumber(loginDetails.getUsername());
            }

            if (user.isPresent()) {
                if (user.get().isActive()) {
                    System.out.println(loginDetails);
                    Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword()));

                    if (authentication.isAuthenticated()) {
                        System.out.println("login successful");

                        String deviceToken = loginDetails.getDeviceToken();
                        if (deviceToken != null) {
                            diService.saveDeviceInfo(user.get(), deviceToken);
                        } else {
                            System.out.println("Device token is null. Skipping device info save.");
                        }

//                  generate jwt token and set in jwt dto
                        JwtToken token = new JwtToken();
                        token.setJwtToken(jwtService.generateToken(loginDetails.getUsername()));


                        return ResponseEntity.ok(token);
                    } else {
                        ResponseEntity.badRequest().body("Invalid Credential");
                    }
                }
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Please verify your email! Before login");
    }


    public ResponseEntity<?> generateOtp(String email){
        Optional<Users> user=userRepo.findByEmail(email);
        if(user.isPresent()){
//            generate otp
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            otpStore.put(user.get().getEmail(),otp);

//            send mail
            emailService.sendForgetPasswordEmail(user.get().getEmail(),otp);

            return ResponseEntity.ok().body("Please check your email! To forget your password");
        }
       return ResponseEntity.badRequest().body("Please contact Your admin! You are not registered");
    }

    public ResponseEntity<?> resetPassword(ResetPassword resetPassword){
        Optional<Users> user = userRepo.findByEmail(resetPassword.getEmail());

        if(user.isPresent()){
            String verificationToken=otpStore.get(resetPassword.getEmail());
            System.out.println(otpStore);

            if (verificationToken == null ) {
                log.warn("invalid or expired token!! {}",verificationToken);
                return ResponseEntity.badRequest().body("invalid or expired token!! ");
            }else if(verificationToken.equals(resetPassword.getOtp())) {
//                remove otp from otpStore after validating
                otpStore.remove(resetPassword.getEmail());

                user.get().setPassword(new BCryptPasswordEncoder().encode(resetPassword.getNewPassword()));
                userRepo.save(user.get());
                log.info("Password forgotten successfully");
                return ResponseEntity.ok().body("Password forgotten successfully");
            }else{
                return ResponseEntity.badRequest().body("invalid otp");
            }
        }
        return ResponseEntity.badRequest().body("Please enter correct email");
    }


    // Clear otpStore
    @Scheduled(cron = "0 */2 * * * *") // Runs every 2 minutes
    public void clearOtpStore(){
        otpStore.clear();
    }
}
