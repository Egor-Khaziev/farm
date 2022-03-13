package shell.server.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shell.api.dto.SchedulerRequest;
import shell.api.dto.TaskDto;
import shell.api.exceptions.ResourceNotFoundException;
import shell.api.utils.TaskStatus;
import shell.server.core.converters.TaskConverter;
import shell.server.core.entities.StatusHistory;
import shell.server.core.entities.Task;
import shell.server.core.entities.User;
import shell.server.core.repositories.StatusHistoryRepository;
import shell.server.core.repositories.TasksRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService extends Thread{
    private final TasksRepository tasksRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final UserService userService;
    private final TaskConverter taskConverter;


    @Transactional
    public TaskDto findById(Long id, String token) {
        User user = getUserByToken(token);
        Task task = tasksRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Task not found, id: " + id));

        return taskConverter.entityToDto(task);
    }

    public List<TaskDto> findAllTasksByUser(String token){
        User user = getUserByToken(token);
        List<Task> tasksList = tasksRepository.findAllTasksByUser(user).orElseThrow(() -> new ResourceNotFoundException("Tasks not found"));
        return tasksList.stream()
                .map(taskConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public void update(Task task) {
        task.setStatus(TaskStatus.COMPLETE);
        statusHistoryRepository.save(new StatusHistory(task, TaskStatus.COMPLETE));
        tasksRepository.save(task);
    }


    public List<StatusHistory> findHistoryById(Long id, String token) {
        User user = getUserByToken(token);
        List<StatusHistory> historyList = statusHistoryRepository.findAllByTaskId(id)
                .orElseThrow(() -> new ResourceNotFoundException("History not found"));

        return historyList;
    }

    @Transactional
    public TaskDto createTask(String token, String taskTitle) {
        User user = getUserByToken(token);
        Task newTask = new Task(user, taskTitle, TaskStatus.RENDERING);
        newTask = tasksRepository.save(newTask);
        StatusHistory statusHistory = statusHistoryRepository.save(new StatusHistory(newTask, TaskStatus.RENDERING));
        newTask.getStatusHistory().add(statusHistory);
        newTask = tasksRepository.save(newTask);

                Task finalNewTask = newTask;
                Thread myThready = new Thread(new Runnable()
                {
                    public void run()
                    {
                        rendering(finalNewTask);
                    }
                });
                myThready.start();

        return taskConverter.entityToDto(tasksRepository.save(newTask));
    }

    private User getUserByToken(String token){
        return userService.getActivUserTokenMap().get(token);
    }


    public void rendering(Task finalNewTask){
        int minSec = 60;
        int maxSec = 300;
        int renderingTime =  (int) ((Math.random() * (maxSec + 1-minSec))+minSec);

        renderingTime = renderingTime*50;

        try {
            sleep(renderingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        update(finalNewTask);
    }

    public List<TaskDto> findByIndexTask(SchedulerRequest schedulerRequest) {
        User user = getUserByToken(schedulerRequest.getToken());
        List<Task> taskList = tasksRepository.findAllByIdInAndUser(schedulerRequest.getIndexTaskArray(), user).
                orElseThrow(() -> new ResourceNotFoundException("Tasks not found"));

        List <TaskDto> taskDtoList = taskList.stream()
                .map(taskConverter::entityToDto)
                .collect(Collectors.toList());

        System.out.println(taskDtoList);

        return taskDtoList;
    }
}
