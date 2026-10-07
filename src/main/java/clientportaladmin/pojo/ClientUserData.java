package clientportaladmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientUserData {

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("role")
	private String role;

	@JsonProperty("department")
	private String department;

	@JsonProperty("designation")
	private String designation;

	@JsonProperty("contractorName")
	private String contractorName;

	@JsonProperty("employeeType")
	private String employeeType;

	@JsonProperty("status")
	private String status;

	public ClientUserData() {
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClientUserData{" +
				"fullName='" + fullName + '\'' +
				", employeeId='" + employeeId + '\'' +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				", department='" + department + '\'' +
				", employeeType='" + employeeType + '\'' +
				'}';
	}
}
