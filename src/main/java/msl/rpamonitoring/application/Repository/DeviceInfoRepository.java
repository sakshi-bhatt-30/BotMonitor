package msl.rpamonitoring.application.Repository;

import java.util.List;
import java.util.Optional;

import msl.rpamonitoring.application.Entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo,Long> {
    Optional<DeviceInfo> findByUserId(Long userId);


    // // Custom Query to get all tokens.
    // @Query("SELECT d.deviceToken FROM DeviceInfo d")
    // List<String> findAllTokens();

    @Query("SELECT d.deviceToken FROM DeviceInfo d WHERE d.user.id IN (SELECT u.id FROM Users u JOIN u.projects p WHERE p.id = :projectId)")
    List<String> findAllTokensByProjectId(Long projectId);

}
