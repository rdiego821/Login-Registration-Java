package co.com.bancolombia.model.shared.cqrs;

import co.com.bancolombia.model.shared.common.value.MessageId;
import co.com.bancolombia.model.shared.common.value.XRequestId;
import co.com.bancolombia.model.shared.log.model.Log;
import lombok.Data;

@Data
public class ContextData {
    private final MessageId messageId;
    private final XRequestId xRequestId;
    private Log log;

    public ContextData(String messageId, String xRequestId) {
        this.messageId = new MessageId(messageId);
        this.xRequestId = resolveXRequestId(messageId, xRequestId);
    }

    private XRequestId resolveXRequestId(String messageId, String xRequestId) {
        if (xRequestId != null && !xRequestId.isBlank()) {
            return new XRequestId(xRequestId);
        }
        return new XRequestId(messageId);
    }
}
