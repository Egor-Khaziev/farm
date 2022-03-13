package shell.server.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shell.api.dto.SchedulerRequest;
import shell.api.dto.StatusHistoryDTO;
import shell.api.dto.TaskDto;
import shell.server.core.converters.HistoryConverter;
import shell.server.core.services.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final HistoryConverter historyConverter;

    @PostMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id, @RequestBody String token) {
       return taskService.findById(id, token);
    }

    @PostMapping("/checkToComplete")
    public List<TaskDto> getTaskById(@RequestBody SchedulerRequest schedulerRequest) {
        return taskService.findByIndexTask(schedulerRequest);
    }


    @PostMapping("/history/{id}")
    public List<StatusHistoryDTO> getTaskHistoryById(@PathVariable Long id, @RequestBody String token) {
        return taskService.findHistoryById(id, token).stream().map(historyConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/all")
    public List<TaskDto> getAllTaskList(@RequestBody String token){
        return  taskService.findAllTasksByUser(token);
    }

    @PostMapping("/start/{taskTitle}")
    public TaskDto getAllTaskList(@PathVariable String taskTitle, @RequestBody String token){
        return  taskService.createTask(token, taskTitle);
    }
}
