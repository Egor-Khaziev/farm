package shell.server.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shell.server.core.entities.Task;
import shell.server.core.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findAllTasksByUser(User user);

    Optional<Task> findByIdAndUser(Long id, User User);

    Optional<List<Task>> findAllByIdInAndUser(List<Long> listId, User user);



}
