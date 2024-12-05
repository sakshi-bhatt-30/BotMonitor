package msl.rpamonitoring.application.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import msl.rpamonitoring.application.Entity.BotExecutions;

@Repository
public interface BotExecutionRepository extends JpaRepository<BotExecutions,Long> {
    Optional<BotExecutions> findByProcessId(Long processId);
}
