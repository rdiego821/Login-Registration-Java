package co.com.bancolombia.model.shared.log.gateway;

import co.com.bancolombia.model.shared.cqrs.Command;
import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.log.model.Log;
import reactor.core.publisher.Mono;

public interface SaveLogGateway {
    Mono<Void> sendFunctionalLog(Command<Log, ContextData> command);
}
