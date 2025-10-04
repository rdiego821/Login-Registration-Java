package co.com.bancolombia.api.shared.domain.response;

import co.com.bancolombia.model.shared.cqrs.ContextData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessApiResponse {
    @JsonProperty("session_id")
    private Object sessionId;

    public static SuccessApiResponse response(ContextData contextData) {
        return new SuccessApiResponse(contextData.getMessageId().toString());
    }

    public static SuccessApiResponse response() {
        return new SuccessApiResponse(UUID.randomUUID().toString());
    }
}
