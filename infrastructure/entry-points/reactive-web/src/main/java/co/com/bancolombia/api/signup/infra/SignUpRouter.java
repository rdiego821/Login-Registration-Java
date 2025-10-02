package co.com.bancolombia.api.signup.infra;

import co.com.bancolombia.api.signup.application.SignUpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SignUpRouter {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(SignUpHandler handler) {
        return route(POST("/signup"), handler::signUpUser);
    }
}
