package AutoEntities.BasicEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class MessageError {

    @JsonProperty("messageCode")
    private String messageCode = "no message code";

    @JsonProperty("messageDetails")
    private String messageDetails = "no message details";

    private Map<String, Object> otherProps = new HashMap<String, Object>();

    public MessageError(String messageCode, String messageDetails, Map<String, Object> otherProps) {
        this.messageCode = messageCode;
        this.messageDetails = messageDetails;
        this.otherProps = otherProps;
    }

    public MessageError() {
    }

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getMessageDetails() {
        return messageDetails;
    }
}
