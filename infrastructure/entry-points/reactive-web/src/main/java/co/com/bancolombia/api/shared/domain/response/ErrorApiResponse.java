package co.com.bancolombia.api.shared.domain.response;

import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import lombok.Data;

@Data
public class ErrorApiResponse {
    private Error error;

    private ErrorApiResponse(BusinessException exception) {
        this.error = getError(exception.getConstantBusinessException(), exception);
    }

    public static ErrorApiResponse build(BusinessException exception) {
        return new ErrorApiResponse(exception);
    }

    private static Error getError(ConstantBusinessException exception, BusinessException e) {
        return Error.builder().code(exception.getInternalMessage())
                .message(exception.getMessage())
                .details(new Detail(""))
                .correlation(Correlation.builder()
                        .messageId(e.getMetaInfo().getMessageId())
                        .xRequestId(e.getMetaInfo().getMessageId())
                        .build())
                .build();
    }
}
