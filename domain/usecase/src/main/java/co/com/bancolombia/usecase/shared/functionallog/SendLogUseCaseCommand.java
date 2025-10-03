package co.com.bancolombia.usecase.shared.functionallog;

import co.com.bancolombia.model.shared.bus.command.gateway.label.CommandDataBus;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.log.model.Log;

public record SendLogUseCaseCommand(Log log, ContextData contextData) implements CommandDataBus {
}
