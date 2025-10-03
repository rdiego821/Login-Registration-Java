package co.com.bancolombia.model.shared.exception.model;

public class InternalMessage {
    public static final String UNKNOWN_ERROR = "Error desconocido no tratado";
    public static final String MISSING_REQUIRED_FIELDS_ERROR = "Faltan campos obligatorios al " +
            "crear el agregado CreateAuthorizationFlow";
    public static final String MISSING_REQUIRED_FIELDS_SIGNUP_ERROR = "Los campos requeridos del " +
            "agregado SignUp no estan presentes o no son validos";
    public static final String MISSING_REQUIRED_HEADERS_ERROR = "La cabecera message-id " +
            "es obligatoria";
    public static final String COMMAND_HANDLER_NOT_EXIST = "No se encontró un command handler asociado " +
            "al command class recibida";
    public static final String WEAK_PASSWORD = "Error contraseña debil";
    public static final String INVALID_EMAIL_FORMAT = "Error formato no valido de email";
    public static final String EMAIL_ALREADY_EXISTS = "Error email ya existe";
    public static final String INVALID_CREDENTIALS = "Error credenciales no validas";
    public static final String USER_NOT_FOUND = "Error usuario no encontrado";
}
