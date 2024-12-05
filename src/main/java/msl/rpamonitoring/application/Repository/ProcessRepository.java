package msl.rpamonitoring.application.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import msl.rpamonitoring.application.Entity.Process;

@Repository
public interface ProcessRepository extends JpaRepository<Process,Long> {

    @Query("SELECT p FROM Process p WHERE p.id = :processId")
    Optional<Process> findProcessById(@Param("processId") Long processId);


    @Query("SELECT p FROM Process p WHERE p.project.id = :projectId")
    List<Process> findAllProcessesByProjectId(@Param("projectId") Long projectId);
}
