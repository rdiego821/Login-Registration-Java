package co.com.bancolombia.api.shared.application;

import co.com.bancolombia.api.shared.domain.HeaderConstant;
import co.com.bancolombia.api.shared.domain.response.ErrorApiResponse;
import co.com.bancolombia.api.shared.helpers.ecs.Ecs;
import co.com.bancolombia.model.shared.bus.command.gateway.label.CommandBus;
import co.com.bancolombia.model.shared.common.model.Constants;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.usecase.shared.functionallog.SendLogUseCaseCommand;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Consumer;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    public static final String MS_NAME = "ms_login_registration";
    private final CommandBus commandBus;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer,
                                  CommandBus commandBus) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.commandBus = commandBus;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::buildErrorResponse);
    }

    private Mono<ServerResponse> buildErrorResponse(ServerRequest request) {
        return accessError(request)
                .flatMap(throwable -> Ecs.build(throwable, MS_NAME))
                .flatMap(Mono::error)
                .onErrorResume(BusinessException.class, exception -> createResponseFromBusiness(exception, request))
                .onErrorResume(throwable -> unknownError(throwable, request))
                .cast(ServerResponse.class);
    }

    public Mono<ServerResponse> createResponseFromBusiness(BusinessException exception, ServerRequest request) {
        return ServerResponse
                .status(exception.getConstantBusinessException().getStatus())
                .headers(buildHeaders(request))
                .bodyValue(ErrorApiResponse.build(exception))
                .doOnNext(serverResponse -> sendLog(exception).subscribe());
    }

    private Consumer<HttpHeaders> buildHeaders(ServerRequest serverRequest) {
        return headers -> {
            serverRequest.headers().asHttpHeaders().forEach(headers::addAll);
            headers.addAll(HeaderResponse.addAdditionalHeaders());
            if (headers.getFirst(HeaderConstant.X_REQUEST_ID.value()) == null
                    || headers.getFirst(HeaderConstant.X_REQUEST_ID.value()).isBlank()) {
                var messageId = headers.getFirst(HeaderConstant.MESSAGE_ID.value());
                if (messageId != null) {
                    headers.set(HeaderConstant.X_REQUEST_ID.value(), messageId);
                } else {
                    headers.set(HeaderConstant.X_REQUEST_ID.value(), UUID.randomUUID().toString());
                }
            }
        };
    }

    public Mono<ServerResponse> unknownError(Throwable exception, ServerRequest request) {

        if(exception.getCause() instanceof DecodingException) {
            var businessException = new BusinessException(ConstantBusinessException.MALFORMED_REQUEST,
                    exception.getMessage());
            return createResponseFromBusiness(businessException, request);
        }

        var businessException = new BusinessException(ConstantBusinessException.UNKNOWN_ERROR_EXCEPTION,
                exception.getMessage());
        return createResponseFromBusiness(businessException, request);
    }

    private Mono<Throwable> accessError(ServerRequest request) {
        return Mono.just(request)
                .map(this::getError);
    }

    public Mono<Void> sendLog(BusinessException exception) {
        if (exception.getLog() != null) {
            var errorCode = exception.getConstantBusinessException().getInternalMessage() + Constants.FAILED_SUFFIX;
            exception.getLog().setResultCode(errorCode);
            exception.getLog().setResultDescription(exception.getConstantBusinessException().getMessage());

            return commandBus.dispatch(new SendLogUseCaseCommand(exception.getLog(), null));
        }
        return Mono.empty();
    }
}
