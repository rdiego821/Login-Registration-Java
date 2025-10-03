package co.com.bancolombia.api.signup.infra;

import co.com.bancolombia.api.signup.domain.SignUpRequest;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;

@UtilityClass
public class TransformRequest {

    public static Mono<SignUpInformation> fromRequest(ServerRequest serverRequest, ContextData contextData) {

        return serverRequest.bodyToMono(SignUpRequest.class)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error((new BusinessException(
                                ConstantBusinessException.MISSING_REQUIRED_FIELDS_EXCEPTION,
                                "object data is required"))))
                .map(TransformRequest::mapperToModel);
    }

    private static SignUpInformation mapperToModel(SignUpRequest request) {
        var email = request.getEmail().getValue();
        var password = request.getPassword().getValue();
        var name = request.getName().getValue();

        var builder = new SignUpInformation.Builder()
                .email(email)
                .password(password)
                .name(name);

        return builder.build();
    }
}
