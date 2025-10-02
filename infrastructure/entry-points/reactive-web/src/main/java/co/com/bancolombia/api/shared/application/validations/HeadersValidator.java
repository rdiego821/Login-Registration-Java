package co.com.bancolombia.api.shared.application.validations;

import co.com.bancolombia.api.shared.domain.HeaderConstant;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@UtilityClass
public class HeadersValidator {

    public static Mono<ContextData> validateHeaders(ServerRequest request) {
        var messageId = request.headers().firstHeader(HeaderConstant.MESSAGE_ID.value());

        if (isNullOrEmpty(messageId)) {
            return Mono.error(new Exception("Error"));
        }

        try {
            var xRequestId = request.headers().firstHeader(HeaderConstant.X_REQUEST_ID.value());
            return getContextData(messageId, xRequestId);
        } catch (IllegalArgumentException e) {
            return Mono.error(new Exception("Error", e));
        }
    }

    private static boolean isNullOrEmpty(String... values) {
        return Arrays.stream(values).anyMatch(value -> value == null || value.isEmpty());
    }

    private static Mono<ContextData> getContextData(String messageId, String xRequestId) {
        assert messageId != null;
        assert xRequestId != null;
        return Mono.just(new ContextData(UUID.fromString(messageId), UUID.fromString(xRequestId)));
    }
}
