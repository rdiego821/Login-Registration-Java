package co.com.bancolombia.api.shared.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Correlation {
    @JsonProperty("message_id")
    private String messageId;

    @JsonProperty("x_request_id")
    private String xRequestId;
}
