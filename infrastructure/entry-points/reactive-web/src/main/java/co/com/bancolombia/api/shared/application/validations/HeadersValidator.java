package co.com.bancolombia.api.shared.application.validations;

import co.com.bancolombia.api.shared.domain.HeaderConstant;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@UtilityClass
public class HeadersValidator {

    public static Mono<ContextData> validateHeaders(ServerRequest request) {
        var messageId = request.headers().firstHeader(HeaderConstant.MESSAGE_ID.value());

        if (isNullOrEmpty(messageId)) {
            return Mono.error(new BusinessException(ConstantBusinessException.MISSING_REQUIRED_HEADERS_EXCEPTION));
        }

        try {
            var xRequestId = request.headers().firstHeader(HeaderConstant.X_REQUEST_ID.value());
            return Mono.just(new ContextData(messageId, xRequestId));
        } catch (IllegalArgumentException e) {
            return Mono.error(new Exception("Error", e));
        }
    }

    private static boolean isNullOrEmpty(String... values) {
        return Arrays.stream(values).anyMatch(value -> value == null || value.isEmpty());
    }
}
