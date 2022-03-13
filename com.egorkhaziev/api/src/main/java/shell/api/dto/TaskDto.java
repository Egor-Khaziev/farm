package shell.api.dto;

import shell.api.utils.TaskStatus;

public class TaskDto {
    private Long id;

    private String title;

    public TaskDto() {
    }

    private TaskStatus status;

    public TaskDto(Long id, String title, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    @Override
    public String toString(){
        return " ID задачи: " + id + " | Имя задачи: " + title + " | текущий статус: " + status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
