package co.com.bancolombia.model.shared.exception;

import co.com.bancolombia.model.shared.exception.model.CodeMessage;
import co.com.bancolombia.model.shared.exception.model.InternalMessage;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public enum ConstantBusinessException implements ErrorManagement {
    UNEXPECTED_ERROR(HTTP_INTERNAL_ERROR,
            CodeMessage.SERVICE_ERROR_MESSAGE,
            InternalMessage.UNKNOWN_ERROR),
    MISSING_REQUIRED_FIELDS_EXCEPTION(HTTP_CONFLICT,
            CodeMessage.MISSING_REQUIRED_FIELDS_MESSAGE,
            InternalMessage.MISSING_REQUIRED_FIELDS_ERROR),
    MISSING_REQUIRED_HEADERS_EXCEPTION(HTTP_BAD_REQUEST,
            CodeMessage.MISSING_REQUIRED_HEADERS_MESSAGE,
            InternalMessage.MISSING_REQUIRED_HEADERS_ERROR),
    UNKNOWN_ERROR_EXCEPTION(HTTP_INTERNAL_ERROR,
            CodeMessage.SERVICE_ERROR_MESSAGE,
            InternalMessage.UNKNOWN_ERROR),
    COMMAND_HANDLER_NOT_EXIST(HTTP_INTERNAL_ERROR,
            CodeMessage.SERVICE_ERROR_MESSAGE,
            InternalMessage.COMMAND_HANDLER_NOT_EXIST),
    WEAK_PASSWORD(HTTP_BAD_REQUEST,
            CodeMessage.WEAK_PASSWORD,
            InternalMessage.WEAK_PASSWORD),
    INVALID_EMAIL_FORMAT(HTTP_BAD_REQUEST,
            CodeMessage.INVALID_EMAIL_FORMAT,
            InternalMessage.INVALID_EMAIL_FORMAT),
    EMAIL_ALREADY_EXISTS(HTTP_CONFLICT,
            CodeMessage.EMAIL_ALREADY_EXISTS,
            InternalMessage.EMAIL_ALREADY_EXISTS),
    INVALID_CREDENTIALS(HTTP_UNAUTHORIZED,
            CodeMessage.INVALID_CREDENTIALS,
            InternalMessage.INVALID_CREDENTIALS),
    USER_NOT_FOUND(HTTP_NOT_FOUND,
            CodeMessage.USER_NOT_FOUND,
            InternalMessage.USER_NOT_FOUND),
    MALFORMED_REQUEST(HTTP_BAD_REQUEST,
            CodeMessage.MALFORMED_REQUEST,
            InternalMessage.MALFORMED_REQUEST)
    ;

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
