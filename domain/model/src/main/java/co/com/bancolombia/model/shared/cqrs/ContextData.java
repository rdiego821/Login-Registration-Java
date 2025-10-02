package co.com.bancolombia.model.shared.cqrs;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ContextData {
    private final UUID messageId;
    private final UUID xRequestId;
}
