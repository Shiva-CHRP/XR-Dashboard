package superadmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentData {

	@JsonProperty("title")
	private String title;

	@JsonProperty("code")
	private String code;

	@JsonProperty("passingPercentage")
	private String passingPercentage;

	@JsonProperty("timeLimitMinutes")
	private String timeLimitMinutes;

	@JsonProperty("maxAttempts")
	private String maxAttempts;

	@JsonProperty("description")
	private String description;

	@JsonProperty("curriculumName")
	private String curriculumName;

	@JsonProperty("status")
	private String status;

	public AssessmentData() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassingPercentage() {
		return passingPercentage;
	}

	public void setPassingPercentage(String passingPercentage) {
		this.passingPercentage = passingPercentage;
	}

	public String getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(String timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public String getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(String maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AssessmentData{" +
				"title='" + title + '\'' +
				", code='" + code + '\'' +
				", passingPercentage='" + passingPercentage + '\'' +
				", curriculumName='" + curriculumName + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
