package co.com.bancolombia.api.signup.application;

import co.com.bancolombia.api.shared.application.HandleResponse;
import co.com.bancolombia.api.shared.application.validations.HeadersValidator;
import co.com.bancolombia.api.signup.infra.TransformRequest;
import co.com.bancolombia.model.shared.common.model.Constants;
import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.log.model.Log;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import co.com.bancolombia.usecase.signup.SignUpUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SignUpHandler {

    private final SignUpUseCase signUpUseCase;

    public Mono<ServerResponse> signUpUser(ServerRequest serverRequest) {
        return HeadersValidator.validateHeaders(serverRequest)
                .doOnNext(contextData -> contextData.setLog(buildLogData(contextData)))
                .flatMap(contextData -> TransformRequest.fromRequest(serverRequest, contextData)
                          .flatMap(signUpInformation -> callUseCase(signUpInformation, contextData))
                        .flatMap(signUpInformation -> buildResponse(serverRequest, contextData)));
    }

    private Log buildLogData(ContextData contextData) {
        return Log.builder()
                .action(Constants.ACTION_CREATE)
                .process(Constants.PROCESS_SIGNUP)
                .time(LocalDateTime.now())
                .messageId(contextData.getMessageId().toString())
                .description(Constants.SIGNUP_DESCRIPTION)
                .build();
    }

    private Mono<SignUpInformation> callUseCase(SignUpInformation signUpInformation,
                                                ContextData contextData) {
        var command = new Command<>(signUpInformation, contextData);
        return signUpUseCase.signUpProcess(command)
                .thenReturn(signUpInformation);
    }

    private Mono<ServerResponse> buildResponse(ServerRequest serverRequest,
                                               ContextData contextData) {
        return HandleResponse.createSuccessResponse(serverRequest, contextData, HttpStatus.CREATED);
    }
}
