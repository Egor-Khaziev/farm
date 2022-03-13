package shell.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import shell.client.DTO.TaskDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class ServerMessage {

    ServerMessageType type;
    String text;
    List<TaskDTO> taskList;
    String error;

    public ServerMessage(ServerMessageType type, String text) {
        this.type = type;
        this.text = text;
    }
}
