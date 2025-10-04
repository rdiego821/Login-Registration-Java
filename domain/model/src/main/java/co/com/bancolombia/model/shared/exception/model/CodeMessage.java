package co.com.bancolombia.model.shared.exception.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeMessage {
    public static final String MISSING_REQUIRED_FIELDS_MESSAGE = "Faltan parámetros obligatorios";
    public static final String SERVICE_ERROR_MESSAGE = "Ha ocurrido un error interno en el servicio";
    public static final String MISSING_REQUIRED_HEADERS_MESSAGE = "Faltan cabeceras obligatorias";
    public static final String WEAK_PASSWORD = "Contraseña debil";
    public static final String INVALID_EMAIL_FORMAT = "Formato no valido del email";
    public static final String EMAIL_ALREADY_EXISTS = "Email ya registrado";
    public static final String INVALID_CREDENTIALS = "Credenciales no validas";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String MALFORMED_REQUEST = "Json no valido";
}
