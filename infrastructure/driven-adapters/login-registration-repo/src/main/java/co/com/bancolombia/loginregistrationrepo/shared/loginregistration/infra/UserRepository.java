package co.com.bancolombia.loginregistrationrepo.shared.loginregistration.infra;

import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Void> save(SignUpInformation user);

    Mono<Boolean> existsByEmail(Email email);

    Mono<SignUpInformation> findByEmail(Email email);
}
