package co.com.bancolombia.loginregistrationrepo.shared.loginregistration.infra;

import co.com.bancolombia.loginregistrationrepo.signup.infra.SignUpRepository;
import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMapRepository implements SignUpRepository {

    private final ConcurrentHashMap<Email, SignUpInformation> userStorage = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> save(SignUpInformation user) {
        return emailExists(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("El correo electrónico ya está en uso."));
                    }
                    userStorage.put(user.getEmail(), user);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> emailExists(Email email) {
        return Mono.defer(() -> {
            boolean exists = userStorage.containsKey(email);
            return Mono.just(exists);
        });
    }
}
