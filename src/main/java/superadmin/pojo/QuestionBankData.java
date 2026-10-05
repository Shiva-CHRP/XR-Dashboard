package superadmin.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionBankData {

	@JsonProperty("questionText")
	private String questionText;

	@JsonProperty("topic")
	private String topic;

	@JsonProperty("questionType")
	private String questionType;

	@JsonProperty("options")
	private List<String> options;

	@JsonProperty("correctOption")
	private String correctOption;

	@JsonProperty("points")
	private String points;

	@JsonProperty("explanation")
	private String explanation;

	public QuestionBankData() {
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(String correctOption) {
		this.correctOption = correctOption;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public String toString() {
		return "QuestionBankData{" +
				"questionText='" + questionText + '\'' +
				", topic='" + topic + '\'' +
				", questionType='" + questionType + '\'' +
				", correctOption='" + correctOption + '\'' +
				", points='" + points + '\'' +
				'}';
	}
}
