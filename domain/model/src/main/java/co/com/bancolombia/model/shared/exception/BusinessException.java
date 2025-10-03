package co.com.bancolombia.model.shared.exception;

import co.com.bancolombia.model.shared.cqrs.ContextData;
import co.com.bancolombia.model.shared.exception.ecs.BusinessExceptionECS;
import lombok.Getter;

@Getter
public class BusinessException extends BusinessExceptionECS {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ConstantBusinessException message) {
        super(message);
    }

    public BusinessException(ConstantBusinessException message, String optionalInfo) {
        super(message, optionalInfo);
    }
}
