package co.com.bancolombia.usecase.signup;

import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.shared.common.value.Password;
import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.signup.gateway.SignUpProcessGateway;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SignUpUseCaseTest {
    @Mock
    private SignUpProcessGateway signUpProcessGateway;

    @InjectMocks
    private SignUpUseCase signUpUseCase;

    private static final String VALID_EMAIL = "user@example.com";
    private static final String DUPLICATE_EMAIL = "duplicate@example.com";
    private static final String INVALID_EMAIL = "invalid@email";
    private static final String STRONG_PASSWORD = "StrongP@ssw0rd";
    private static final String WEAK_PASSWORD = "weakp";
    private static final String NAME = "name";
    private static final String MESSAGE_ID = "48e23c6e-5091-4d79-b3f9-ff949a3da3f4";
    private static final String X_REQUEST_ID = "ee1599f3-8432-4416-8d1d-98644877afde";

    @Test
    void testSuccessSignUp() {
        Command<SignUpInformation, ContextData> command = createCommand(VALID_EMAIL);

        when(signUpProcessGateway.signUpProcess(command))
                .thenReturn(Mono.empty());

        Mono<Void> result = signUpUseCase.signUpProcess(command);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(signUpProcessGateway).signUpProcess(command);
    }

    @Test
    void testErrorWhenEmailAlreadyExists() {
        Command<SignUpInformation, ContextData> command = createCommand(DUPLICATE_EMAIL);

        when(signUpProcessGateway.signUpProcess(command))
                .thenReturn(Mono.error(new IllegalArgumentException("El correo electrónico ya está en uso.")));

        Mono<Void> result = signUpUseCase.signUpProcess(command);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof BusinessException &&
                        ((BusinessException) error).getConstantBusinessException()
                                .equals(ConstantBusinessException.EMAIL_ALREADY_EXISTS))
                .verify();

        verify(signUpProcessGateway).signUpProcess(command);
    }

    @Test
    void testErrorWhenEmailIsInvalid() {
        var exception = assertThrows(BusinessException.class, () ->
                new Email(INVALID_EMAIL));

        assertEquals(ConstantBusinessException.INVALID_EMAIL_FORMAT, exception.getConstantBusinessException());
    }

    @Test
    void testErrorPasswordIsWeak() {
        var exception = assertThrows(BusinessException.class, () ->
                new Password(WEAK_PASSWORD));

        assertEquals(ConstantBusinessException.WEAK_PASSWORD, exception.getConstantBusinessException());
    }

    private Command<SignUpInformation, ContextData> createCommand(String email) {
        SignUpInformation signUpInformation = new SignUpInformation.Builder()
                .email(email)
                .password(SignUpUseCaseTest.STRONG_PASSWORD)
                .name(SignUpUseCaseTest.NAME)
                .build();

        ContextData contextData = new ContextData(MESSAGE_ID, X_REQUEST_ID);

        return new Command<>(signUpInformation, contextData);
    }
}
