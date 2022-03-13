package shell.server.core.converters;

import org.springframework.stereotype.Component;
import shell.api.dto.TaskDto;
import shell.server.core.entities.Task;

@Component
public class TaskConverter {
//    public Task dtoToEntity(TaskDto taskDto) {
//        return new Task(taskDto.getTitle(), taskDto.getStatus());
//    }

    public TaskDto entityToDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getStatus());
    }
}
