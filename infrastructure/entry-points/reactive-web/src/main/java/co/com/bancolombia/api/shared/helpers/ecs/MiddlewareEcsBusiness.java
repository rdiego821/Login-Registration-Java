package co.com.bancolombia.api.shared.helpers.ecs;

import co.com.bancolombia.api.shared.helpers.ecs.model.LogRecord;
import co.com.bancolombia.api.shared.helpers.ecs.model.LoggerEcs;
import co.com.bancolombia.api.shared.helpers.ecs.model.MiddlewareEcsLog;
import co.com.bancolombia.model.shared.exception.ecs.BusinessExceptionECS;

public class MiddlewareEcsBusiness implements MiddlewareEcsLog {
    private MiddlewareEcsLog middlewareEcsLog;

    @Override
    public void handler(Throwable request, String service) {
        if (request instanceof BusinessExceptionECS exp) {
            LogRecord.ErrorLog<String, String> optionalMap = new LogRecord.ErrorLog<>();
            optionalMap.setOptionalInfo(exp.getOptionalInfo());

            var messageId = exp.getMetaInfo().getMessageId();

            var logError = new LogRecord.ErrorLog<String, String>();
            logError.setOptionalInfo(optionalMap.getOptionalInfo());
            logError.setDescription(exp.getConstantBusinessException().getInternalMessage());
            logError.setMessage(exp.getConstantBusinessException().getMessage());

            var logExp = new LogRecord<String, String>();
            logExp.setError(logError);
            logExp.setLevel(LogRecord.Level.ERROR);
            logExp.setService(service);
            logExp.setMessageId(messageId);

            LoggerEcs.print(logExp);

        } else if (middlewareEcsLog != null) {
            middlewareEcsLog.handler(request, service);
        }
    }

    @Override
    public MiddlewareEcsLog setNext(MiddlewareEcsLog next) {
        this.middlewareEcsLog = next;
        return this;
    }
}
