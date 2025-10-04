package co.com.bancolombia.api.signin.infra;

import co.com.bancolombia.api.signin.domain.SignInRequest;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.signin.model.SignInInformation;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;

@UtilityClass
public class TransformRequest {
    public static Mono<SignInInformation> fromRequest(ServerRequest serverRequest, ContextData contextData) {

        return serverRequest.bodyToMono(SignInRequest.class)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error((new BusinessException(
                        ConstantBusinessException.MISSING_REQUIRED_FIELDS_EXCEPTION,
                        "object data is required"))))
                .map(TransformRequest::mapperToModel);
    }

    private static SignInInformation mapperToModel(SignInRequest request) {
        var email = request.getEmail().getValue();
        var password = request.getPassword().getValue();

        var builder = new SignInInformation.Builder()
                .email(email)
                .password(password);

        return builder.build();
    }
}
