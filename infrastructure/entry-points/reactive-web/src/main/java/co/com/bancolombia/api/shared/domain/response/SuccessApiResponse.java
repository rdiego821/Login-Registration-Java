package co.com.bancolombia.api.shared.domain.response;

import co.com.bancolombia.model.shared.cqrs.ContextData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessApiResponse {
    private Object data;

    public static SuccessApiResponse response(ContextData contextData) {
        return new SuccessApiResponse(contextData.getMessageId().toString());
    }
}
