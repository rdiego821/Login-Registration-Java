package co.com.bancolombia.usecase.signup;

import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.labels.UseCase;
import co.com.bancolombia.model.signup.gateway.SignUpProcessGateway;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@UseCase
@RequiredArgsConstructor
public class SignUpUseCase {
    private final SignUpProcessGateway signUpProcessGateway;

    public Mono<Void> signUpProcess(Command<SignUpInformation, ContextData> command) {
        signUpProcessGateway.signUpProcess(command)
                .doOnSuccess(unused -> {
                    System.out.println("Proceso de sign-up finalizado: " + command);
                })
                .doOnError(error -> {
                    System.err.println("Error en el proceso de sign-up: " + error.getMessage());
                });
        return Mono.empty();
    }

    /*
    private Mono<Void> sendSuccessLog(ContextData contextData) {
        var commandLog = new SendLogUseCaseCommand(buildSuccessLog(contextData),
                contextData);
        return commandBus.dispatch(commandLog);
    }

     */
}
