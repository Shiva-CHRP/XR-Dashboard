package superadmin.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import superadmin.pojo.AssessmentData;
import superadmin.pojo.CurriculumData;
import superadmin.pojo.DeveloperData;
import superadmin.pojo.LicenseData;
import superadmin.pojo.OrganizationData;
import superadmin.pojo.QuestionBankData;
import superadmin.pojo.SupportTicketData;
import clientportaladmin.pojo.ClientUserData;
import clientportaladmin.pojo.ClientEventData;
import clientportaladmin.pojo.ClientSupportTicketData;
import clientportaladmin.pojo.ClientMasterData;

public class JsonReader {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
	}

	public static List<OrganizationData> getOrganizationData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<OrganizationData>>() {});
	}

	public static List<DeveloperData> getDeveloperData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<DeveloperData>>() {});
	}

	public static List<LicenseData> getLicenseData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<LicenseData>>() {});
	}

	public static List<CurriculumData> getCurriculumData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<CurriculumData>>() {});
	}

	public static List<AssessmentData> getAssessmentData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<AssessmentData>>() {});
	}

	public static List<QuestionBankData> getQuestionBankData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<QuestionBankData>>() {});
	}

	public static List<SupportTicketData> getSupportTicketData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<SupportTicketData>>() {});
	}

	public static List<ClientUserData> getClientUserData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<ClientUserData>>() {});
	}

	public static List<ClientEventData> getClientEventData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<ClientEventData>>() {});
	}

	public static List<ClientSupportTicketData> getClientSupportTicketData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<ClientSupportTicketData>>() {});
	}

	public static List<ClientMasterData> getClientMasterData(String jsonFilePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, new TypeReference<List<ClientMasterData>>() {});
	}

	public static <T> List<T> getJsonData(String jsonFilePath, TypeReference<List<T>> typeReference) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		return mapper.readValue(jsonContent, typeReference);
	}
}
