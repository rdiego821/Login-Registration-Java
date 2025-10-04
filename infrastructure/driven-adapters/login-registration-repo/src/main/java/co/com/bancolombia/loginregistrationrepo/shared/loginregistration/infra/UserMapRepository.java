package co.com.bancolombia.loginregistrationrepo.shared.loginregistration.infra;

import co.com.bancolombia.loginregistrationrepo.signin.infra.SignInRepository;
import co.com.bancolombia.loginregistrationrepo.signup.infra.SignUpRepository;
import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.shared.common.value.Password;
import co.com.bancolombia.model.signin.model.SignInInformation;
import co.com.bancolombia.model.signup.model.SignUpInformation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMapRepository implements SignUpRepository, SignInRepository {

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

    @Override
    public Mono<SignInInformation> findByEmail(SignInInformation information, Email email) {
        return Mono.defer(() -> {
            SignUpInformation user = userStorage.get(email);
            if (user == null) {
                return Mono.error(new IllegalArgumentException("EMAIL_NOT_FOUND: El correo electrónico no se encuentra."));
            }
            return comparePassword(information.getPassword(), user.getPassword())
                    .thenReturn(information);
        });
    }

    @Override
    public Mono<SignInInformation> comparePassword(Password passwordRequest, Password passwordStored) {
        return Mono.defer(() -> {
            if (!passwordRequest.equals(passwordStored)) {
                return Mono.error(new IllegalArgumentException("INVALID_CREDENTIALS: Credenciales no válidas"));
            }
            return Mono.empty();
        });
    }
}
