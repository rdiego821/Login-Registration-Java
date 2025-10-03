package co.com.bancolombia.loginregistrationrepo.shared.loginregistration.infra;

import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMapRepository implements UserRepository {

    private final ConcurrentHashMap<Email, SignUpInformation> userStorage = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> save(SignUpInformation user) {
        return Mono.defer(() -> {
            if (userStorage.containsKey(user.getEmail())) {
                return Mono.error(new IllegalArgumentException("El correo electrónico ya está en uso."));
            }
            userStorage.put(user.getEmail(), user);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Boolean> existsByEmail(Email email) {
        return Mono.defer(() -> Mono.just(userStorage.containsKey(email)));
    }

    @Override
    public Mono<SignUpInformation> findByEmail(Email email) {
        return Mono.defer(() -> {
            SignUpInformation user = userStorage.get(email);
            if (user == null) {
                return Mono.empty();
            }
            return Mono.just(user);
        });
    }
}
