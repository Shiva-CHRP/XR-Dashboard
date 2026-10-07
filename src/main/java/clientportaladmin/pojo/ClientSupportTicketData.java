package clientportaladmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSupportTicketData {

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("category")
	private String category;

	@JsonProperty("priority")
	private String priority;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private String status;

	public ClientSupportTicketData() {
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClientSupportTicketData{" +
				"subject='" + subject + '\'' +
				", category='" + category + '\'' +
				", priority='" + priority + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
