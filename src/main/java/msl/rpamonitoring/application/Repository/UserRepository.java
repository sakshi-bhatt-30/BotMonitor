package msl.rpamonitoring.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import msl.rpamonitoring.application.Entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByMobileNumber(String mobileNumber);
    @Query("SELECT u FROM Users u WHERE u.id = :userId")
    Optional<Users> findUserById(@Param("userId") Long userId);
}
