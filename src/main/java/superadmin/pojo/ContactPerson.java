package superadmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPerson {

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("designation")
	private String designation;

	@JsonProperty("empId")
	private String empId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("phone")
	private String phone;

	public ContactPerson() {
	}

	public ContactPerson(String fullName, String designation, String empId, String email, String phone) {
		this.fullName = fullName;
		this.designation = designation;
		this.empId = empId;
		this.email = email;
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "ContactPerson{" +
				"fullName='" + fullName + '\'' +
				", designation='" + designation + '\'' +
				", empId='" + empId + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}
