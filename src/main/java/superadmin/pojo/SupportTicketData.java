package superadmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportTicketData {

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("organizationName")
	private String organizationName;

	@JsonProperty("category")
	private String category;

	@JsonProperty("subCategory")
	private String subCategory;

	@JsonProperty("priority")
	private String priority;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private String status;

	public SupportTicketData() {
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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
		return "SupportTicketData{" +
				"subject='" + subject + '\'' +
				", organizationName='" + organizationName + '\'' +
				", category='" + category + '\'' +
				", priority='" + priority + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
