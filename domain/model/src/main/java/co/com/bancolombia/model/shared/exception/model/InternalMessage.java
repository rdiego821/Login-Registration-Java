package co.com.bancolombia.model.shared.exception.model;

public class InternalMessage {
    public static final String UNKNOWN_ERROR = "UNEXPECTED_ERROR";
    public static final String MISSING_REQUIRED_FIELDS_ERROR = "Faltan campos obligatorios al " +
            "crear el agregado CreateAuthorizationFlow";
    public static final String MISSING_REQUIRED_HEADERS_ERROR = "La cabecera message-id " +
            "es obligatoria";
    public static final String COMMAND_HANDLER_NOT_EXIST = "No se encontró un command handler asociado " +
            "al command class recibida";
    public static final String WEAK_PASSWORD = "WEAK_PASSWORD";
    public static final String INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
    public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String MALFORMED_REQUEST = "MALFORMED_REQUEST";
}
