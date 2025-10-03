package co.com.bancolombia.api.shared.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String code;
    private String message;
    private Detail details;
    private Correlation correlation;
}
