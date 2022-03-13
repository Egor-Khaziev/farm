package shell.api.dto;


import shell.api.utils.TaskStatus;

import java.io.Serializable;

public class StatusHistoryDTO implements Serializable {

    private Long id;

    private String task;

    private TaskStatus status;

    private String created;

    public StatusHistoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {

        this.created = created;
    }

    public StatusHistoryDTO(Long id, String task, TaskStatus status, String created) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.created = created;
    }

    @Override
    public String toString() {
        return  "task=" + task +
                ", status=" + status +
                ", created=" + created;
    }
}
