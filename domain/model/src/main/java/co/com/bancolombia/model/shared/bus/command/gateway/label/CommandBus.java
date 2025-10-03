package co.com.bancolombia.model.shared.bus.command.gateway.label;

import reactor.core.publisher.Mono;

public interface CommandBus {
    Mono<Void> dispatch(CommandDataBus commandDataBus);
}
