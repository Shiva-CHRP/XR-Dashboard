package clientportaladmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for MDM Hardware Device Management.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MDMDeviceData {

	@JsonProperty("deviceName")
	private String deviceName;

	@JsonProperty("deviceId")
	private String deviceId;

	@JsonProperty("deviceModel")
	private String deviceModel;

	@JsonProperty("serialNumber")
	private String serialNumber;

	@JsonProperty("operatingSystem")
	private String operatingSystem;

	@JsonProperty("assignedToUser")
	private String assignedToUser;

	@JsonProperty("status")
	private String status;

	public MDMDeviceData() {
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getAssignedToUser() {
		return assignedToUser;
	}

	public void setAssignedToUser(String assignedToUser) {
		this.assignedToUser = assignedToUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
