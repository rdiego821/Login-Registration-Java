package co.com.bancolombia.model.shared.exception.ecs;

import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class BusinessExceptionECS extends RuntimeException {
    private static final String EMPTY = "";
    private static final String OPTIONAL = "OPTIONAL";
    private final ConstantBusinessException constantBusinessException;
    private final Map<String, String> optionalInfo;
    private final MetaInfo metaInfo;

    public BusinessExceptionECS(String message) {
        super(message);
        this.metaInfo = MetaInfo.builder().build();
        this.optionalInfo = new HashMap<>();
        this.constantBusinessException = ConstantBusinessException.UNEXPECTED_ERROR;
    }

    public BusinessExceptionECS(ConstantBusinessException message) {
        this(message, EMPTY);
    }

    public BusinessExceptionECS(ConstantBusinessException message, String optionalInfo) {
        this.constantBusinessException = validateMessage(message);
        this.optionalInfo = fillMap(optionalInfo != null ? optionalInfo : EMPTY);
        this.metaInfo = MetaInfo.builder().build();
    }

    private static ConstantBusinessException validateMessage(ConstantBusinessException message) {
        return (message == null)
                ? ConstantBusinessException.UNEXPECTED_ERROR
                : message;
    }

    private Map<String, String> fillMap(String value) {
        Map<String, String> optionalInfoMap = new HashMap<>();
        optionalInfoMap.put(OPTIONAL, value);
        return optionalInfoMap;
    }

    @Builder
    @Getter
    public static class MetaInfo implements Serializable {
        @Builder.Default
        private final String messageId = UUID.randomUUID().toString();
    }
}
