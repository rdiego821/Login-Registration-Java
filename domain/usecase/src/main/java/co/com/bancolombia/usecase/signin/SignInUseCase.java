package co.com.bancolombia.usecase.signin;

import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.cqrs.Query;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.shared.labels.UseCase;
import co.com.bancolombia.model.signin.gateway.SignInProcessGateway;
import co.com.bancolombia.model.signin.model.SignInInformation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
public class SignInUseCase {
    private final SignInProcessGateway signInProcessGateway;

    public Mono<SignInInformation> signInProcess(Query<SignInInformation, ContextData> query) {
        return signInProcessGateway.signInProcess(query)
                .onErrorMap(error -> {
                    String errorMessage = error.getMessage();

                    if (errorMessage != null && errorMessage.startsWith("EMAIL_NOT_FOUND:")) {
                        return new BusinessException(
                                ConstantBusinessException.USER_NOT_FOUND,
                                error.getMessage());
                    }
                    if (errorMessage != null && errorMessage.startsWith("INVALID_CREDENTIALS:")) {
                        return new BusinessException(
                                ConstantBusinessException.INVALID_CREDENTIALS,
                                error.getMessage());
                    }
                    return error;
                });
    }
}
