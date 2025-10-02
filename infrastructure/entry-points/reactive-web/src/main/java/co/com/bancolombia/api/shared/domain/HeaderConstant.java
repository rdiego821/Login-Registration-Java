package co.com.bancolombia.api.shared.domain;

public enum HeaderConstant {
    MESSAGE_ID("message-id"),
    X_REQUEST_ID("x-request-id");

    private final String headerName;

    HeaderConstant(String headerName) {
        this.headerName = headerName;
    }

    public String value() {
        return headerName;
    }
}
