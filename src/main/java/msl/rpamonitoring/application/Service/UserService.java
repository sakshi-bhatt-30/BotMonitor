package msl.rpamonitoring.application.Service;

import java.time.LocalDateTime;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import msl.rpamonitoring.application.Dto.EmailVerification;
import msl.rpamonitoring.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import msl.rpamonitoring.application.Dto.AdminDto;
import msl.rpamonitoring.application.Dto.UserResponseDto;
import msl.rpamonitoring.application.Entity.Organization;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.OrganizationRepository;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
@Slf4j
public class UserService {
    @Autowired
    private OrganizationRepository  organizationRepository;

    @Autowired
    UserRepository userRepo;

    @Autowired
    EmailService emailService;

    HashMap<Object,String> otpStore =new HashMap<>();

    public ResponseEntity<?> registerUserAdmin(AdminDto admin){
//          // Password confirmation check
          if (!admin.getPassword().equals(admin.getConfirmPassword())) {
                log.warn("Password does not match");
                return ResponseEntity.badRequest().body("Passwords do not match");
          }

          // Check if the user already exists based on mobile number or email
          if (userRepo.findByMobileNumber(admin.getMobileNumber()).isPresent() || userRepo.findByEmail(admin.getEmail()).isPresent()) {
                log.warn("User already exists");
                return ResponseEntity.badRequest().body("User already exists with this email or mobile number");
          }

         try{
               // Step 1: Create the user
               Users user = new Users();
               user.setFirstName(admin.getFirstName());
               user.setLastName(admin.getLastName());
               user.setEmail(admin.getEmail());
               user.setMobileNumber(admin.getMobileNumber());
               user.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
               user.setAdmin(true); // Mark user as admin
               user.setActive(true);
               user.setCreatedAt(LocalDateTime.now());
               user.setUpdatedAt(LocalDateTime.now());

               // Step 2: Create the organization
               Organization organization = new Organization();
               organization.setName(admin.getOrganizationName());
               organization.setDescription(admin.getOrganizationDescription());
               organization.setCreatedAt(LocalDateTime.now());
               organization.setUpdatedAt(LocalDateTime.now());

               // Set the relationship on the user
               user.setOrganization(organization);

               // Step 3: Save the organization (which cascades to the user)
               organization = organizationRepository.save(organization); // Save organization and cascades to save user

               // After saving the organization, set the user as the admin
               organization.getUser().add(user);

               // Step 4: Save the organization again to update the admin reference
               organizationRepository.save(organization); // Save organization again to persist admin reference
               // Step 5: Return the persisted user
               return ResponseEntity.ok(user);
         }catch(Exception e){
               return ResponseEntity.badRequest().body(e);
         }

    }
    public ResponseEntity<UserResponseDto> getUserById(Long userId) {
      Users user = userRepo.findById(userId)
              .orElseThrow(() -> new RuntimeException("User not found"));

      UserResponseDto userResponseDto = new UserResponseDto(
                  user.getFirstName(),
                  user.getLastName(),
                  user.getEmail(),
                  user.getMobileNumber()
      );

      return ResponseEntity.ok(userResponseDto);
    }
    public ResponseEntity<?> sendOtp(EmailVerification emailVerification) {

        if(emailVerification.getEmail()!=null){
            //        generate 6 digit otp
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            otpStore.put(emailVerification.getEmail(),otp);

//          send mail
            emailService.sendVerificationEmail(emailVerification.getEmail(), otp);
            return ResponseEntity.ok("Check Your email! Otp sent successfully");

        }else{
            return ResponseEntity.badRequest().body("Invalid email");
        }

    }

    public ResponseEntity<?> verifyEmail(EmailVerification emailVerification){
            String verificationToken=otpStore.get(emailVerification.getEmail());
            if (verificationToken == null ) {
                log.warn("invalid or expired token!! {}",verificationToken);
                return ResponseEntity.badRequest().body("invalid or expired token!! ");
            }
            if(verificationToken.equals(emailVerification.getOtp())){
                return ResponseEntity.ok().body("Email verified");
            }
        return ResponseEntity.badRequest().body("Invalid otp");
    }

    // Clear otpStore
    @Scheduled(cron = "0 */2 * * * *") // Runs every 2 minutes
    public void clearOtpStore(){
        otpStore.clear();
    }




}
