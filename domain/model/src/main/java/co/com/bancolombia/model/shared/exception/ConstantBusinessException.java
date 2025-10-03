package co.com.bancolombia.model.shared.exception;

import co.com.bancolombia.model.shared.exception.model.CodeMessage;
import co.com.bancolombia.model.shared.exception.model.InternalMessage;

import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

public enum ConstantBusinessException implements ErrorManagement {
    DEFAULT_EXCEPTION(HTTP_INTERNAL_ERROR,
            CodeMessage.SERVICE_ERROR_MESSAGE,
            InternalMessage.UNKNOWN_ERROR),
    MISSING_REQUIRED_FIELDS_EXCEPTION(HTTP_CONFLICT,
            CodeMessage.MISSING_REQUIRED_FIELDS_MESSAGE,
            InternalMessage.MISSING_REQUIRED_FIELDS_ERROR),
    MISSING_REQUIRED_FIELDS_SIGNUP_EXCEPTION(HTTP_CONFLICT,
            CodeMessage.MISSING_REQUIRED_FIELDS_MESSAGE,
            InternalMessage.MISSING_REQUIRED_FIELDS_ERROR);

    private final Integer status;
    private final String message;
    private final String internalMessage;

    ConstantBusinessException(Integer status, String message, String internalMessage) {
        this.status = status;
        this.message = message;
        this.internalMessage = internalMessage;
    }

    @Override
    public Integer getStatus() { return status; }

    @Override
    public String getMessage() { return message; }

    @Override
    public String getInternalMessage() { return internalMessage; }

}
