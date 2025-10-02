package co.com.bancolombia.api.signup.infra;

import co.com.bancolombia.api.signup.domain.SignUpRequest;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.signup.SignUpInformation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class TransformRequest {

    public static Mono<SignUpInformation> fromRequest(ServerRequest serverRequest, ContextData contextData) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        return serverRequest.bodyToMono(SignUpRequest.class)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new Exception("object data is required")))
                .flatMap(request -> {
                    Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);

                    if (!violations.isEmpty()) {
                        String errorMessage = violations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining(", "));
                        return Mono.error(new Exception("Validation failed: " + errorMessage));
                    }
                    return Mono.just(request);
                })
                .map(TransformRequest::mapperToModel);
    }

    private static SignUpInformation mapperToModel(SignUpRequest request) {
        var email = request.getEmail();
        var password = request.getPassword();
        var name = request.getName();

        return SignUpInformation.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
