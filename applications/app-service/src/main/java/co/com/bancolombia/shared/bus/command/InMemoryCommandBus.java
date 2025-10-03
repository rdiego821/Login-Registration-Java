package co.com.bancolombia.shared.bus.command;

import co.com.bancolombia.model.shared.bus.command.gateway.label.CommandBus;
import co.com.bancolombia.model.shared.bus.command.gateway.label.CommandDataBus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class InMemoryCommandBus implements CommandBus {
    private final CommandHandlerInformation commandHandlerInformation;
    private final ApplicationContext applicationContext;

    @Override
    public Mono<Void> dispatch(CommandDataBus commandDataBus) {
        var handlerClass = commandHandlerInformation.search(commandDataBus.getClass());
        var handler = applicationContext.getBean(handlerClass);
        return handler.handler(commandDataBus);
    }
}
