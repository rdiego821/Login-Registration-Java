package co.com.bancolombia.api.signin.infra;

import co.com.bancolombia.api.signin.application.SignInHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SignInRouter {
    @Bean
    public RouterFunction<ServerResponse> signInFunction(SignInHandler handler) {
        return route(POST("/signin"), handler::signInUser);
    }
}
