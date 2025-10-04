package co.com.bancolombia.model.signin.gateway;

import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.cqrs.Query;
import co.com.bancolombia.model.signin.model.SignInInformation;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SignInProcessGateway {
    Mono<SignInInformation> signInProcess(Query<SignInInformation, ContextData> query);
}
