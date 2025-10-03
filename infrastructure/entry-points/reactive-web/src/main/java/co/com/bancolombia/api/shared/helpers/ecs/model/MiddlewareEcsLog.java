package co.com.bancolombia.api.shared.helpers.ecs.model;

public interface MiddlewareEcsLog {
    void handler(Throwable request, String service);

    MiddlewareEcsLog setNext(MiddlewareEcsLog next);
}
