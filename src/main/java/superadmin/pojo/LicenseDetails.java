package superadmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LicenseDetails {

	@JsonProperty("licenseType")
	private String licenseType;

	@JsonProperty("licensePeriod")
	private String licensePeriod;

	@JsonProperty("trialPeriod")
	private String trialPeriod;

	@JsonProperty("maxActiveUsers")
	private String maxActiveUsers;

	@JsonProperty("maxActiveDevices")
	private String maxActiveDevices;

	@JsonProperty("licenseNotes")
	private String licenseNotes;

	public LicenseDetails() {
	}

	public LicenseDetails(String licenseType, String licensePeriod, String trialPeriod,
			String maxActiveUsers, String maxActiveDevices, String licenseNotes) {
		this.licenseType = licenseType;
		this.licensePeriod = licensePeriod;
		this.trialPeriod = trialPeriod;
		this.maxActiveUsers = maxActiveUsers;
		this.maxActiveDevices = maxActiveDevices;
		this.licenseNotes = licenseNotes;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicensePeriod() {
		return licensePeriod;
	}

	public void setLicensePeriod(String licensePeriod) {
		this.licensePeriod = licensePeriod;
	}

	public String getTrialPeriod() {
		return trialPeriod;
	}

	public void setTrialPeriod(String trialPeriod) {
		this.trialPeriod = trialPeriod;
	}

	public String getMaxActiveUsers() {
		return maxActiveUsers;
	}

	public void setMaxActiveUsers(String maxActiveUsers) {
		this.maxActiveUsers = maxActiveUsers;
	}

	public String getMaxActiveDevices() {
		return maxActiveDevices;
	}

	public void setMaxActiveDevices(String maxActiveDevices) {
		this.maxActiveDevices = maxActiveDevices;
	}

	public String getLicenseNotes() {
		return licenseNotes;
	}

	public void setLicenseNotes(String licenseNotes) {
		this.licenseNotes = licenseNotes;
	}

	@Override
	public String toString() {
		return "LicenseDetails{" +
				"licenseType='" + licenseType + '\'' +
				", licensePeriod='" + licensePeriod + '\'' +
				", trialPeriod='" + trialPeriod + '\'' +
				", maxActiveUsers='" + maxActiveUsers + '\'' +
				", maxActiveDevices='" + maxActiveDevices + '\'' +
				", licenseNotes='" + licenseNotes + '\'' +
				'}';
	}
}
