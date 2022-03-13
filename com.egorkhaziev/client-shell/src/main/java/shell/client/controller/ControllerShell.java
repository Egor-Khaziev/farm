package shell.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import shell.client.services.ServerService;

@ShellComponent
@RequiredArgsConstructor
public class ControllerShell {

    private final ServerService serverService;

    @ShellMethod(key = "login", value = "Authenticate and obtain token")
    public String login() {
         return serverService.login();
    }

    @ShellMethod(key = "reg", value = "Registration and logIn")
    public String registration() { return
        serverService.registration();
    }

    @ShellMethod(key = "disconnect", value = "disconnect")
    public String disconnect() { return
        serverService.disconnect();
    }

    @ShellMethod(key = "task", value = "get one task by ID")
    public void getTask(@ShellOption({"num"}) int num) {
         serverService.getTask(num);
    }

    @ShellMethod(key = "get tasks", value = "All user getTasks")
    public void tasks() {
        serverService.getTasks();
    }

    @ShellMethod(key = "task history", value = "get task history by ID")
    public void getTaskHistory(@ShellOption({"num"}) int num) {
        serverService.getTaskHistory(num);
    }

    @ShellMethod(key = "start task", value = "start new task")
    public void startTask(@ShellOption({"title"}) String title) {
        serverService.startTask(title);
    }

}
