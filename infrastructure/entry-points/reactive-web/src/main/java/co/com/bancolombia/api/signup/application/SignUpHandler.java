package co.com.bancolombia.api.signup.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SignUpHandler {

    public Mono<ServerResponse> signUpUser(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("recibido");
    }
}
