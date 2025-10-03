package co.com.bancolombia.model.shared.bus.command.gateway.label;

import reactor.core.publisher.Mono;

public interface CommandHandler<T> extends CommandDataBus {
    Mono<Void> handler(T command);
}
