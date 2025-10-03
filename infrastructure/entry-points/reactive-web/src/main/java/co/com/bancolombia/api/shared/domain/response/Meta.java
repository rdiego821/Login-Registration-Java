package co.com.bancolombia.api.shared.domain.response;

import co.com.bancolombia.model.shared.common.model.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Meta {
    @JsonProperty("_messageId")
    private String messageId;
    @JsonProperty("_requestDateTime")
    private String requestDate;

    public Meta(String messageId) {
        this.messageId = messageId;
        this.requestDate = getDate();
    }

    private String getDate() {
        var formatter = DateTimeFormatter.ofPattern(Constants.ISO_8601_DATE_FORMAT);
        var dateTime = ZonedDateTime.now(ZoneId.of(Constants.OFFSET_ID));
        return dateTime.format(formatter);
    }
}
