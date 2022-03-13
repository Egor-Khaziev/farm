package shell.api.dto;

import java.util.List;

public class SchedulerRequest {

    String token;

    List<Long> indexTaskArray;

    public SchedulerRequest() {
    }

    public SchedulerRequest(String token, List<Long> indexTaskArray) {
        this.token = token;
        this.indexTaskArray = indexTaskArray;
    }

    public String getToken() {
        return token;
    }

    public List<Long> getIndexTaskArray() {
        return indexTaskArray;
    }
}
