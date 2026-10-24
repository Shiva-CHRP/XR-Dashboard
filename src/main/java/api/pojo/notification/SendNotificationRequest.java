package api.pojo.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendNotificationRequest {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    public SendNotificationRequest() {}

    public SendNotificationRequest(String userId, String type, String subject, String message) {
        this.userId = userId;
        this.type = type;
        this.subject = subject;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
