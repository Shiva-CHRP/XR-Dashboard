package superadmin.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurriculumData {

	@JsonProperty("name")
	private String name;

	@JsonProperty("code")
	private String code;

	@JsonProperty("industry")
	private String industry;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private String status;

	@JsonProperty("lessonNames")
	private List<String> lessonNames;

	public CurriculumData() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

	public List<String> getLessonNames() {
		return lessonNames;
	}

	public void setLessonNames(List<String> lessonNames) {
		this.lessonNames = lessonNames;
	}

	@Override
	public String toString() {
		return "CurriculumData{" +
				"name='" + name + '\'' +
				", code='" + code + '\'' +
				", industry='" + industry + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
