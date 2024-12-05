package msl.rpamonitoring.application.Repository;

import msl.rpamonitoring.application.Dto.OrganizationSpecificProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import msl.rpamonitoring.application.Entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("SELECT new msl.rpamonitoring.application.Dto.OrganizationSpecificProjects(" +
            "o.id, o.name, p.id, p.name, p.description, " +
            "u.id,u.isAdmin, u.firstName, u.lastName) " +
            "FROM Project p " +
            "JOIN p.organization o " +
            "LEFT JOIN p.users u " +
            "WHERE o.id = :orgId")
    List<OrganizationSpecificProjects> findByOrganizationId(Long orgId);

      @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    List<Project> findAllProjectsByUserId(@Param("userId") Long userId);
}
