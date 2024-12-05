package msl.rpamonitoring.application.Controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import msl.rpamonitoring.application.Dto.EmailRequest;
import msl.rpamonitoring.application.Dto.LoginRequestDto;
import msl.rpamonitoring.application.Dto.ResetPassword;
import msl.rpamonitoring.application.Repository.UserRepository;
import msl.rpamonitoring.application.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/RBM")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDetails){
        System.out.println("trying to login");
        return loginService.verify(loginDetails);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody EmailRequest emailRequest) {
        log.info("Trying to forget your password {}",emailRequest.getEmail());
        String email=emailRequest.getEmail();

       return loginService.generateOtp(email);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword resetPassword, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        log.info("Trying to reset password");

        return loginService.resetPassword(resetPassword);
    }
}
