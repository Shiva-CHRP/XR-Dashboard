package superadmin.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for VR Module Lifecycle Data:
 * Developer Upload -> Review Queue Approval -> Module Catalogue Access & Archive.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VRModuleData {

	private String moduleName;
	private String moduleType;
	private String simulationType;
	private String setup;
	private String version;
	private List<String> platforms;
	private String description;
	private String buildFilePath;
	private String fileType;
	private String reviewRationale;
	private String rejectionReason;
	private List<String> grantAccessToOrgs;

	public VRModuleData() {
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getSimulationType() {
		return simulationType;
	}

	public void setSimulationType(String simulationType) {
		this.simulationType = simulationType;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<String> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<String> platforms) {
		this.platforms = platforms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuildFilePath() {
		return buildFilePath;
	}

	public void setBuildFilePath(String buildFilePath) {
		this.buildFilePath = buildFilePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getReviewRationale() {
		return reviewRationale;
	}

	public void setReviewRationale(String reviewRationale) {
		this.reviewRationale = reviewRationale;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public List<String> getGrantAccessToOrgs() {
		return grantAccessToOrgs;
	}

	public void setGrantAccessToOrgs(List<String> grantAccessToOrgs) {
		this.grantAccessToOrgs = grantAccessToOrgs;
	}
}
