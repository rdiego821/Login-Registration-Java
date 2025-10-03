package co.com.bancolombia.model.shared.exception.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeMessage {
    public static final String MISSING_REQUIRED_FIELDS_MESSAGE = "Faltan parámetros obligatorios";
    public static final String SERVICE_ERROR_MESSAGE = "Ha ocurrido un error interno en el servicio";
}
