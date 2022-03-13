package shell.server.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shell.server.core.entities.StatusHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long>{
    //@Query("select s from StatusHistory s where s.task.id = ?1 and s.task.user.username = ?2")
    Optional<List<StatusHistory>> findAllByTaskId(Long id);
}
