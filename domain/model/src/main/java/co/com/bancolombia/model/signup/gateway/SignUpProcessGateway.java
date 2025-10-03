package co.com.bancolombia.model.signup.gateway;

import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SignUpProcessGateway {
    Mono<Void> signUpProcess(Command<SignUpInformation, ContextData> command);
}
