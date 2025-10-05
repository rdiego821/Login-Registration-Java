package co.com.bancolombia.usecase.signin;

import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.cqrs.Query;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.signin.gateway.SignInProcessGateway;
import co.com.bancolombia.model.signin.model.SignInInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInUseCaseTest {

    @Mock
    private SignInProcessGateway signInProcessGateway;

    @InjectMocks
    private SignInUseCase signInUseCase;

    private static final String VALID_EMAIL = "user@example.com";
    private static final String INVALID_EMAIL = "nonexistent@example.com";
    private static final String VALID_PASSWORD = "StrongP@ssw0rd";
    private static final String INVALID_PASSWORD = "WrongP@ssw0rd";
    private static final String NAME = "User Name";
    private static final String MESSAGE_ID = "48e23c6e-5091-4d79-b3f9-ff949a3da3f4";
    private static final String X_REQUEST_ID = "ee1599f3-8432-4416-8d1d-98644877afde";

    @Test
    void testSuccessSignIn() {
        Query<SignInInformation, ContextData> query = createQuery(VALID_EMAIL, VALID_PASSWORD);
        SignInInformation expectedSignInInformation = query.payload();

        when(signInProcessGateway.signInProcess(query))
                .thenReturn(Mono.just(expectedSignInInformation));

        Mono<SignInInformation> result = signInUseCase.signInProcess(query);

        StepVerifier.create(result)
                .expectNext(expectedSignInInformation)
                .verifyComplete();

        verify(signInProcessGateway).signInProcess(query);
    }

    @Test
    void testErrorUserDoesNotExist() {
        Query<SignInInformation, ContextData> query = createQuery(INVALID_EMAIL, VALID_PASSWORD);

        when(signInProcessGateway.signInProcess(query))
                .thenReturn(Mono.error(
                        new IllegalArgumentException("EMAIL_NOT_FOUND: El correo electrónico no se encuentra.")
                ));

        Mono<SignInInformation> result = signInUseCase.signInProcess(query);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof BusinessException &&
                        ((BusinessException) error).getConstantBusinessException()
                                .equals(ConstantBusinessException.USER_NOT_FOUND))
                .verify();

        verify(signInProcessGateway).signInProcess(query);
    }

    private Query<SignInInformation, ContextData> createQuery(String email, String password) {
        SignInInformation signInInformation = new SignInInformation.Builder()
                .email(email)
                .password(password)
                .build();
        ContextData contextData = new ContextData(MESSAGE_ID, X_REQUEST_ID);
        return new Query<>(signInInformation, contextData);
    }

    @Test
    void testErrorCredentialsAreInvalid() {
        Query<SignInInformation, ContextData> query = createQuery(VALID_EMAIL, INVALID_PASSWORD);

        when(signInProcessGateway.signInProcess(query))
                .thenReturn(Mono.error(
                        new IllegalArgumentException("INVALID_CREDENTIALS: Credenciales no válidas")
                ));

        Mono<SignInInformation> result = signInUseCase.signInProcess(query);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof BusinessException &&
                        ((BusinessException) error).getConstantBusinessException()
                                .equals(ConstantBusinessException.INVALID_CREDENTIALS))
                .verify();

        verify(signInProcessGateway).signInProcess(query);
    }
}
