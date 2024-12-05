package msl.rpamonitoring.application.Service;

import lombok.extern.slf4j.Slf4j;
import msl.rpamonitoring.application.utils.UserPrincipal;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user with username: {}", username);

        // Try to find the user by mobile number first
        Optional<Users> user = userRepo.findByMobileNumber(username);

        // If not found by mobile number, try to find by email
        if (user.isEmpty()) {
            log.info("User not found by mobile number, trying email...");
            user = userRepo.findByEmail(username);
        }

        // If a user is found, set the transient username field
        return user.map(u -> {
            u.setUsername(username); // Set the transient username field
            return new UserPrincipal(u); // Wrap the user in UserPrincipal
        }).orElseThrow(() -> {
            log.warn("User not found with mobile number or email: {}", username);
            return new UsernameNotFoundException("User not found with mobile number or email: " + username);
        });
    }
}