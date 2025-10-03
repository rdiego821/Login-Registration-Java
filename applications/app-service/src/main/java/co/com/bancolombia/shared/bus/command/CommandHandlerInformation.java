package co.com.bancolombia.shared.bus.command;

import co.com.bancolombia.model.shared.bus.command.gateway.label.CommandDataBus;
import co.com.bancolombia.model.shared.bus.command.gateway.label.CommandHandler;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.shared.labels.DomainService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandHandlerInformation {
    private final Map<Class<? extends CommandDataBus>, Class<? extends CommandHandler>> indexedHandlers;
    private final ApplicationContext applicationContext;

    public CommandHandlerInformation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.indexedHandlers = new HashMap<>();
        registerCommandHandlers();
    }

    private void registerCommandHandlers() {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(applicationContext::getType)
                .filter(beanType -> beanType != null && beanType.isAnnotationPresent(DomainService.class))
                .forEach(beanType -> {
                    var genericType = (ParameterizedType) beanType.getGenericInterfaces()[0];
                    var commandType = (Class<? extends CommandDataBus>) genericType.getActualTypeArguments()[0];
                    indexedHandlers.put(commandType, (Class<? extends CommandHandler>) beanType);
                });
    }

    public Class<? extends CommandHandler> search(Class<? extends CommandDataBus> commandClass) {
        var commandHandler = indexedHandlers.get(commandClass);
        if (commandHandler == null) {
            throw new BusinessException(ConstantBusinessException.COMMAND_HANDLER_NOT_EXIST,
                    "The command handler does not exist for the command: " + commandClass.getName());
        }
        return commandHandler;
    }
}
