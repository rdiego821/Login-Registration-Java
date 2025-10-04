package co.com.bancolombia.loginregistrationrepo.signin.infra;

import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.shared.common.value.Password;
import co.com.bancolombia.model.signin.model.SignInInformation;
import reactor.core.publisher.Mono;

public interface SignInRepository {
    Mono<SignInInformation> findByEmail(SignInInformation information, Email email);

    Mono<SignInInformation> comparePassword(Password passwordRequest, Password passwordStored);
}
