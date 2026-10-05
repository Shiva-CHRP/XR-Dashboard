package superadmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LicenseData {

	@JsonProperty("licenseKey")
	private String licenseKey;

	@JsonProperty("organizationName")
	private String organizationName;

	@JsonProperty("licenseType")
	private String licenseType;

	@JsonProperty("status")
	private String status;

	@JsonProperty("maxUsers")
	private String maxUsers;

	@JsonProperty("maxDevices")
	private String maxDevices;

	@JsonProperty("expiryDate")
	private String expiryDate;

	@JsonProperty("allocatedTokens")
	private String allocatedTokens;

	public LicenseData() {
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(String maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getMaxDevices() {
		return maxDevices;
	}

	public void setMaxDevices(String maxDevices) {
		this.maxDevices = maxDevices;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAllocatedTokens() {
		return allocatedTokens;
	}

	public void setAllocatedTokens(String allocatedTokens) {
		this.allocatedTokens = allocatedTokens;
	}

	@Override
	public String toString() {
		return "LicenseData{" +
				"licenseKey='" + licenseKey + '\'' +
				", organizationName='" + organizationName + '\'' +
				", licenseType='" + licenseType + '\'' +
				", status='" + status + '\'' +
				", maxUsers='" + maxUsers + '\'' +
				", maxDevices='" + maxDevices + '\'' +
				", expiryDate='" + expiryDate + '\'' +
				'}';
	}
}
