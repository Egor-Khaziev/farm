package shell.client.connect;

import shell.api.dto.SchedulerRequest;
import shell.api.dto.StatusHistoryDTO;
import shell.api.dto.TaskDto;
import shell.api.utils.AuthMessage;
import shell.api.utils.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConnectServer {

    private String token;
    private final RestTemplate restTemplate;



    @Value("${integrations.server.url}")
    private String productServiceUrl;

    public boolean tokenEmpty() {
        if (token.equals("")) {
            return true;
        }
        return false;
    }

    @PostConstruct
    private void init() {
        token = "";

    }

    public TaskDto findTaskById(int id) {
        TaskDto taskDto = restTemplate.postForObject(productServiceUrl + "/api/v1/tasks/" + id, token, TaskDto.class);
        return taskDto;
    }

    public List<StatusHistoryDTO> findTaskHistoryById(int id) {
        List<StatusHistoryDTO> StatusHistory = restTemplate.postForObject(productServiceUrl + "/api/v1/tasks/history/" + id, token, List.class);
        return StatusHistory;
    }

    public List<TaskDto> findAll() {
        List<TaskDto> taskDtoList = restTemplate.postForObject(productServiceUrl + "/api/v1/tasks/all", token, List.class);
        return taskDtoList;
    }

    public boolean login(Credential credential) {
        AuthMessage authMessage = restTemplate.postForObject(productServiceUrl + "/api/v1/auth/login", credential, AuthMessage.class);
        if (authMessage.isAuth()) {
            token = authMessage.getToken();
            return true;
        }
        return false;
    }

    public boolean registration(Credential credential) {
        AuthMessage authMessage = restTemplate.postForObject(productServiceUrl + "/api/v1/auth", credential, AuthMessage.class);
        if (authMessage.isAuth()) {
            token = authMessage.getToken();
            return true;
        }
        return false;
    }

    public boolean quit() {
        AuthMessage authMessage = restTemplate.postForObject(productServiceUrl + "/api/v1/auth/quit", token, AuthMessage.class);
        if (!authMessage.isAuth()) {
            token = "";
            return true;
        }
        return false;
    }

    public TaskDto startTask(String title) {
        TaskDto taskDto = restTemplate.
                postForObject(productServiceUrl + "/api/v1/tasks/start/" + title, token, TaskDto.class);

        return taskDto;
    }

    public List<TaskDto> findAllByIndex(List<Long> indexTaskArray) {
        SchedulerRequest schedulerRequest = new SchedulerRequest(token, indexTaskArray);
        List<TaskDto> taskDtoList = restTemplate.postForObject(productServiceUrl + "/api/v1/tasks/checkToComplete", schedulerRequest, List.class);
        return taskDtoList;
    }


}
