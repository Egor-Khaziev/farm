package shell.server.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shell.api.dto.StatusHistoryDTO;
import shell.server.core.entities.StatusHistory;

@Component
@RequiredArgsConstructor
public class HistoryConverter {
    private final TaskConverter taskConverter;

    public StatusHistoryDTO entityToDto(StatusHistory statusHistory) {
        StatusHistoryDTO statusHistoryDTO = new StatusHistoryDTO();
        statusHistoryDTO.setId(statusHistory.getId());
        statusHistoryDTO.setStatus(statusHistory.getStatus());
        statusHistoryDTO.setCreated(statusHistory.getCreatedAt().toString().substring(0, 19));
        statusHistoryDTO.setTask(statusHistory.getTask().getTitle());
        return statusHistoryDTO;
    }
}
