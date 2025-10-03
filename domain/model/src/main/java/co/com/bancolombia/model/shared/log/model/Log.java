package co.com.bancolombia.model.shared.log.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class Log {
    private final String action;
    private final String process;
    private final String messageId;
    private final String description;
    private String resultCode;
    private String resultDescription;
    private final LocalDateTime time;
}
