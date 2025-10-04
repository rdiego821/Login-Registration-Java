package co.com.bancolombia.api.signin.application;

import co.com.bancolombia.api.shared.application.HandleResponse;
import co.com.bancolombia.api.shared.application.validations.HeadersValidator;
import co.com.bancolombia.api.signin.infra.TransformRequest;
import co.com.bancolombia.model.shared.common.model.Constants;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.cqrs.Query;
import co.com.bancolombia.model.shared.log.model.Log;
import co.com.bancolombia.model.signin.model.SignInInformation;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import co.com.bancolombia.usecase.signin.SignInUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SignInHandler {

    private final SignInUseCase signInUseCase;

    public Mono<ServerResponse> signInUser(ServerRequest serverRequest) {
        return HeadersValidator.validateHeaders(serverRequest)
                .doOnNext(contextData -> contextData.setLog(buildLogData(contextData)))
                .flatMap(contextData -> TransformRequest.fromRequest(serverRequest, contextData)
                        .flatMap(signInInformation -> callUseCase(signInInformation, contextData))
                        .flatMap(signInInformation -> buildResponse(serverRequest, contextData)));
    }

    private Log buildLogData(ContextData contextData) {
        return Log.builder()
                .action(Constants.ACTION_GET)
                .process(Constants.PROCESS_SIGNIN)
                .time(LocalDateTime.now())
                .messageId(contextData.getMessageId().toString())
                .description(Constants.SIGNIN_DESCRIPTION)
                .build();
    }

    private Mono<SignInInformation> callUseCase(SignInInformation signInInformation,
                                                ContextData contextData) {
        var query = new Query<>(signInInformation, contextData);
        return signInUseCase.signInProcess(query)
                .thenReturn(signInInformation);
    }

    private Mono<ServerResponse> buildResponse(ServerRequest serverRequest,
                                               ContextData contextData) {
        return HandleResponse.createSuccessResponseSignIn(serverRequest, contextData, HttpStatus.OK);
    }
}
