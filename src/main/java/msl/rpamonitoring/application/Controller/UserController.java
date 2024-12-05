package msl.rpamonitoring.application.Controller;

import jakarta.validation.Valid;
import msl.rpamonitoring.application.Dto.EmailVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import msl.rpamonitoring.application.Dto.AdminDto;
import msl.rpamonitoring.application.Dto.UserResponseDto;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rpabot")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/create/admin")
    public ResponseEntity<?> AdminUser(@Valid @RequestBody AdminDto adminDto, BindingResult result){
        if (result.hasErrors()) {
            // Handle validation errors
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        ResponseEntity<?> user = userService.registerUserAdmin(adminDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody EmailVerification emailVerification){
        return userService.sendOtp(emailVerification);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerification emailVerification){
        return userService.verifyEmail(emailVerification);
    }
}
