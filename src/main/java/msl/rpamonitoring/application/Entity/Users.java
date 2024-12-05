package msl.rpamonitoring.application.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msl.rpamonitoring.application.Enum.Roles;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp ="[a-zA-Z]{1,15}" ,message = "first name must be between 3 to 15 character")
    private String firstName;

    @Pattern(regexp ="[a-zA-Z]{1,15}" ,message = "last name must be between 3 to 15 character")
    private String lastName;

    @Email(message = "Enter valid email")
    @Column(unique = true)
    private String email;

    @Pattern(regexp ="[0-9]{10}",message = "Mobile Number Must be a 10-digit number")
    private String mobileNumber;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$",
            message = "Password must be greater than equal to 8! Password must contain at least 1 small , 1 capital, 1 special character and 1 digit")
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isActive;

    private boolean isAdmin;
//    @Enumerated(EnumType.STRING)
//    private Roles role;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany(mappedBy = "users")
    private List<Project> projects;

    @Transient
    private String confirmPassword;

    @Transient
    private String username;
}
