package co.com.bancolombia.api.shared.application;

import co.com.bancolombia.api.shared.domain.response.SuccessApiResponse;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@UtilityClass
public class HandleResponse {

    public static Mono<ServerResponse> createSuccessResponse(ContextData contextData,
                                                             HttpStatus status, ServerRequest serverRequest) {
        return ServerResponse
                .status(status)
                //.headers(buildHeaders(serverRequest, contextData))
                .bodyValue(SuccessApiResponse.response(contextData));
    }
}
