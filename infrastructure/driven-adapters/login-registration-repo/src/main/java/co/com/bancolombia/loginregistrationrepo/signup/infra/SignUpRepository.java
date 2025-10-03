package co.com.bancolombia.loginregistrationrepo.signup.infra;

import co.com.bancolombia.model.signup.model.SignUpInformation;
import reactor.core.publisher.Mono;

public interface SignUpRepository {

    Mono<Void> save(SignUpInformation user);
}
