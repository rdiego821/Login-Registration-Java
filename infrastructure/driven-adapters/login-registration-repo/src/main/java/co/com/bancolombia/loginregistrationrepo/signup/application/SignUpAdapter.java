package co.com.bancolombia.loginregistrationrepo.signup.application;

import co.com.bancolombia.loginregistrationrepo.shared.loginregistration.infra.UserRepository;
import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.signup.gateway.SignUpProcessGateway;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SignUpAdapter implements SignUpProcessGateway {

    private final UserRepository userRepository;

    @Override
    public Mono<Void> signUpProcess(Command<SignUpInformation, ContextData> command) {
        userRepository.save(command.payload())
          .subscribe(
          unused -> System.out.println("Usuario guardado"),
          error -> System.err.println("Error al guardar usuario: " + error.getMessage())
        );
        return Mono.empty();
    }
}
