package msl.rpamonitoring.application.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import msl.rpamonitoring.application.Dto.AddTeamMemberDto;
import msl.rpamonitoring.application.Entity.Organization;
import msl.rpamonitoring.application.Entity.Project;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.OrganizationRepository;
import msl.rpamonitoring.application.Repository.ProjectRepository;
import msl.rpamonitoring.application.Repository.UserRepository;

@Service
public class AddTeamMemberService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AddTeamMemberEmailService addTeamMemberEmailService;

    @Autowired
    private UserRepository userRepository;

    public Users addTeamMember(AddTeamMemberDto teamMemberDto) {
        // Fetch the project
        Project project = projectRepository.findById(teamMemberDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Fetch the admin user
        Users adminUser = userRepository.findById(teamMemberDto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        Organization adminOrganization = adminUser.getOrganization();

        // Check if the admin user is an admin
        if (!adminUser.isAdmin()) {
            throw new RuntimeException("Admin user is not an admin");
        }

        // Verify the admin's organization contains the project
        if (!adminOrganization.getProjects().contains(project)) {
            throw new RuntimeException("Admin user's organization does not contain this project");
        }

        // Check if the user already exists by email
        Optional<Users> existingUserOpt = userRepository.findByEmail(teamMemberDto.getEmail());

        Users user;
        if (existingUserOpt.isPresent()) {
            // User exists; fetch the user
            user = existingUserOpt.get();

            // Check if the user already belongs to this project
            if (project.getUsers().contains(user)) {
                throw new RuntimeException("User already belongs to this project");
            }
        } else {
            // User does not exist; create a new one
            String temporaryPassword = generateTemporaryPassword();

            user = new Users();
            user.setFirstName(teamMemberDto.getFirstName());
            user.setLastName(teamMemberDto.getLastName());
            user.setEmail(teamMemberDto.getEmail());
            user.setMobileNumber(teamMemberDto.getMobileNumber());
            user.setPassword(new BCryptPasswordEncoder().encode(temporaryPassword));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setActive(true);
            user.setAdmin(false);

            // Link the user to the organization
            Organization organization = project.getOrganization();
            user.setOrganization(organization);

            // Save the new user
            user = userRepository.save(user);

            // Send an email to the new user with their credentials
            addTeamMemberEmailService.sendCredentialsEmail(user.getEmail(), temporaryPassword);
        }

        // Add the user to the project
        project.getUsers().add(user);
        projectRepository.save(project);

        return user;
    }


    // Utility method to generate a random temporary password
    private String generateTemporaryPassword() {
        // Define allowed characters for the password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Generate an 8-character password
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
