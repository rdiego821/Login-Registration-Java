package co.com.bancolombia.api.shared.helpers.ecs;

import co.com.bancolombia.api.shared.helpers.ecs.model.MiddlewareEcsLog;
import reactor.core.publisher.Mono;

public final class Ecs {
    public static MiddlewareEcsLog build() {
        var ecsBusiness = new MiddlewareEcsBusiness();
        var ecsApp = new MiddlewareEcsApp();
        return ecsBusiness.setNext(ecsApp);
    }

    public static Mono<Throwable> build(Throwable throwable,
                                        String service) {
        build().handler(throwable, service);
        return Mono.just(throwable);
    }

    private Ecs() {
    }
}
