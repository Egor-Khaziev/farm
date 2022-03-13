package shell.client.services;

import shell.api.dto.TaskDto;
import shell.api.utils.Credential;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import shell.api.dto.StatusHistoryDTO;
import shell.api.utils.TaskStatus;
import shell.client.connect.ConnectServer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServerService {

    private Scanner in;
    private ObjectMapper mapper;
    private final ConnectServer connectServer;

    private List<TaskDto> renderingTaskList;


    @PostConstruct
    private void init(){
        in = new Scanner(System.in);
        mapper = new ObjectMapper();
        renderingTaskList = new ArrayList<>();
    }

    public String login() {

        if(connectServer.login(
                inputCredential()
        )){ renderingTaskList.clear();
            return "LogIn success";
        }

        return "wrong login/pass or Server not available";

    }

    public String registration() {

        if(connectServer.registration(
                inputCredential()
        )){renderingTaskList.clear();
            return "Registration complete. You are LogIn.";
        }

        return "login is not available";

    }

    public String disconnect() {
        if (connectServer.tokenEmpty()){
            unauthorizedPrint();
        } else {

            if (connectServer.quit()) {
                renderingTaskList.clear();
                return "LogOut success";
            }
            return "LogOut is not complete";
        }
        return "";
    }

    private Credential inputCredential(){

        System.out.print("login: ");
        String username = in.nextLine();

        System.out.print("password: ");
        String password = in.nextLine();

        return new Credential(username,password);
    }

    public void getTask(int num) {
        if (connectServer.tokenEmpty()){
            unauthorizedPrint();
        } else {
            System.out.println(connectServer.findTaskById(num));
        }
    }

    public void getTaskHistory(int num) {
        if (connectServer.tokenEmpty()){
            unauthorizedPrint();
            } else {
            String result = null;
            List<?> list = mapper.convertValue(connectServer.findTaskHistoryById(num), new TypeReference<List<StatusHistoryDTO>>() {});
            list.stream().forEach(System.out::println);
        }
    }

    public void getTasks() {
        if (connectServer.tokenEmpty()){
            unauthorizedPrint();
        } else {
            List<TaskDto> list = mapper.convertValue(connectServer.findAll(), new TypeReference<List<TaskDto>>() {});
            list.stream().forEach(System.out::println);
        }
    }


    public void startTask(String title) {
        if (connectServer.tokenEmpty()){
            unauthorizedPrint();
        } else {
            TaskDto taskDto = connectServer.startTask(title);
            renderingTaskList.add(taskDto);
            System.out.println(taskDto);
        }
    }

    private void unauthorizedPrint(){
        System.out.println(
                "You are not authorized.\n" +
                "Please, log in (command: login) with your account or register (command: reg)."
        );
    }

    @Scheduled(fixedDelay = 10000) //каждые 10 секунд для наглядности
    private void checkToComplete() {
        if (!connectServer.tokenEmpty()) {
            List<Long> indexTaskArray = renderingTaskList.stream().map(taskDto -> taskDto.getId()).collect(Collectors.toList());
            List<TaskDto> listTaskDto = mapper.convertValue(connectServer.findAllByIndex(indexTaskArray), new TypeReference<List<TaskDto>>() {});
                    listTaskDto.toString();
            if (listTaskDto.size() != 0) {
                List<TaskDto> completeList = new ArrayList<>();

                for (TaskDto taskDto : renderingTaskList) {

                    for (int i = 0; i < listTaskDto.size(); i++) {
                        if (TaskStatus.COMPLETE.equals(listTaskDto.get(i).getStatus())) {
                            completeList.add(taskDto);
                        }
                    }
                }
                if (completeList.size() > 0) {
                    renderingTaskList.removeAll(completeList);
                    System.out.println("Tasks is complete:");
                    completeList.stream().forEach(task -> System.out.println(" ID задачи: " + task.getId() + " | Имя задачи: " + task.getTitle() + " | текущий статус: " + TaskStatus.COMPLETE));
                }
            }
        }
    }

}
