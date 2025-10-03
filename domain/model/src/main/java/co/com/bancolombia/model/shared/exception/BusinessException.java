package co.com.bancolombia.model.shared.exception;

import co.com.bancolombia.model.shared.exception.ecs.BusinessExceptionECS;
import co.com.bancolombia.model.shared.log.model.Log;
import lombok.Getter;

@Getter
public class BusinessException extends BusinessExceptionECS {
    private final transient Log log;

    public BusinessException(String message) {
        super(message);
        this.log = null;
    }

    public BusinessException(ConstantBusinessException message) {
        super(message);
        this.log = null;
    }

    public BusinessException(ConstantBusinessException message, String optionalInfo) {
        super(message, optionalInfo);
        this.log = null;
    }
}
