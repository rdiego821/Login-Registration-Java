package co.com.bancolombia.model.shared.exception;

public interface ErrorManagement {
    Integer getStatus();

    String getMessage();

    String getInternalMessage();
}
