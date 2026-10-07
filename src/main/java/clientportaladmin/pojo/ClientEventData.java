package clientportaladmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientEventData {

	@JsonProperty("eventName")
	private String eventName;

	@JsonProperty("moduleName")
	private String moduleName;

	@JsonProperty("scheduledDate")
	private String scheduledDate;

	@JsonProperty("scheduledTime")
	private String scheduledTime;

	@JsonProperty("trainerName")
	private String trainerName;

	@JsonProperty("location")
	private String location;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private String status;

	public ClientEventData() {
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
		return "ClientEventData{" +
				"eventName='" + eventName + '\'' +
				", moduleName='" + moduleName + '\'' +
				", trainerName='" + trainerName + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
