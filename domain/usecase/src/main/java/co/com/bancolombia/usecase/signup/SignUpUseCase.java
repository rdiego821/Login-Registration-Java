package co.com.bancolombia.usecase.signup;

import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.shared.labels.UseCase;
import co.com.bancolombia.model.signup.gateway.SignUpProcessGateway;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@UseCase
@RequiredArgsConstructor
public class SignUpUseCase {
    private final SignUpProcessGateway signUpProcessGateway;

    public Mono<Void> signUpProcess(Command<SignUpInformation, ContextData> command) {
        signUpProcessGateway.signUpProcess(command)
                .onErrorResume(error -> Mono.error(new BusinessException(
                        ConstantBusinessException.EMAIL_ALREADY_EXISTS, error.toString())))
                .thenReturn(Mono.empty());
        return Mono.empty();
    }
}
