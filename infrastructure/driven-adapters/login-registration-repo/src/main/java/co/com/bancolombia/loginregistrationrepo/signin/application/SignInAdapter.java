package co.com.bancolombia.loginregistrationrepo.signin.application;

import co.com.bancolombia.loginregistrationrepo.signin.infra.SignInRepository;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.cqrs.Query;
import co.com.bancolombia.model.signin.gateway.SignInProcessGateway;
import co.com.bancolombia.model.signin.model.SignInInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SignInAdapter implements SignInProcessGateway {

    private final SignInRepository signInRepository;

    @Override
    public Mono<SignInInformation> signInProcess(Query<SignInInformation, ContextData> query) {
        return signInRepository.findByEmail(query.payload(), query.payload().getEmail());
    }
}
