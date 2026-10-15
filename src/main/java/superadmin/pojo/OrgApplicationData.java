package superadmin.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Enterprise POJO for Client Organisation Application Onboarding Data.
 * Matches all 5 cards of the public registration form (/register-organisation)
 * and Super Admin review/approval with portal & branding configuration.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgApplicationData {

	// Card 1: Organisation details
	private String orgName;
	private String portalDomain;
	private String industry;
	private boolean isNewIndustry;
	private String country;
	private String timezone;
	private List<String> languages = new ArrayList<>();

	// Card 2: Primary administrator
	private String adminFullName;
	private String adminEmail;
	private String phoneNumber;
	private String temporaryPassword;

	// Card 3: Legal details & Document Uploads
	private String cinNumber;
	private String cinDocumentPath;
	private String gstNumber;
	private String gstDocumentPath;
	private String panNumber;
	private String panDocumentPath;

	// Card 4: Contacts (Contact 1 & Contact 2)
	private String contact1Name;
	private String contact1Designation;
	private String contact1EmpId;
	private String contact1Email;
	private String contact1Phone;

	private String contact2Name;
	private String contact2Designation;
	private String contact2EmpId;
	private String contact2Email;
	private String contact2Phone;

	// Card 5: Licence you'd like
	private String licenceType = "Licence"; // "Licence" or "Free trial"
	private String licencePeriod = "Annually";
	private String licenceStartDate;
	private String licenceEndDate;
	private String expectedUsers;
	private String expectedDevices;
	private String notes;

	// Super Admin Approval: Portal & Branding Configuration
	private String portalName;
	private String logoPath;
	private String faviconPath;
	private String primaryColor;
	private String sidebarStyle;
	private String navColor;
	private String approvalRationale;

	public OrgApplicationData() {
	}

	// Getters and Setters

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPortalDomain() {
		return portalDomain;
	}

	public void setPortalDomain(String portalDomain) {
		this.portalDomain = portalDomain;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public boolean isNewIndustry() {
		return isNewIndustry;
	}

	public void setNewIndustry(boolean newIndustry) {
		isNewIndustry = newIndustry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public String getAdminFullName() {
		return adminFullName;
	}

	public void setAdminFullName(String adminFullName) {
		this.adminFullName = adminFullName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTemporaryPassword() {
		return temporaryPassword;
	}

	public void setTemporaryPassword(String temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}

	public String getCinNumber() {
		return cinNumber;
	}

	public void setCinNumber(String cinNumber) {
		this.cinNumber = cinNumber;
	}

	public String getCinDocumentPath() {
		return cinDocumentPath;
	}

	public void setCinDocumentPath(String cinDocumentPath) {
		this.cinDocumentPath = cinDocumentPath;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getGstDocumentPath() {
		return gstDocumentPath;
	}

	public void setGstDocumentPath(String gstDocumentPath) {
		this.gstDocumentPath = gstDocumentPath;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getPanDocumentPath() {
		return panDocumentPath;
	}

	public void setPanDocumentPath(String panDocumentPath) {
		this.panDocumentPath = panDocumentPath;
	}

	// Legacy aliases for backward compatibility
	public String getBusinessRegistrationNumber() {
		return cinNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.cinNumber = businessRegistrationNumber;
	}

	public String getTaxIdNumber() {
		return gstNumber;
	}

	public void setTaxIdNumber(String taxIdNumber) {
		this.gstNumber = taxIdNumber;
	}

	public String getContactDesignation() {
		return contact1Designation;
	}

	public void setContactDesignation(String contactDesignation) {
		this.contact1Designation = contactDesignation;
	}

	public String getContact1Name() {
		return contact1Name;
	}

	public void setContact1Name(String contact1Name) {
		this.contact1Name = contact1Name;
	}

	public String getContact1Designation() {
		return contact1Designation;
	}

	public void setContact1Designation(String contact1Designation) {
		this.contact1Designation = contact1Designation;
	}

	public String getContact1EmpId() {
		return contact1EmpId;
	}

	public void setContact1EmpId(String contact1EmpId) {
		this.contact1EmpId = contact1EmpId;
	}

	public String getContact1Email() {
		return contact1Email;
	}

	public void setContact1Email(String contact1Email) {
		this.contact1Email = contact1Email;
	}

	public String getContact1Phone() {
		return contact1Phone;
	}

	public void setContact1Phone(String contact1Phone) {
		this.contact1Phone = contact1Phone;
	}

	public String getContact2Name() {
		return contact2Name;
	}

	public void setContact2Name(String contact2Name) {
		this.contact2Name = contact2Name;
	}

	public String getContact2Designation() {
		return contact2Designation;
	}

	public void setContact2Designation(String contact2Designation) {
		this.contact2Designation = contact2Designation;
	}

	public String getContact2EmpId() {
		return contact2EmpId;
	}

	public void setContact2EmpId(String contact2EmpId) {
		this.contact2EmpId = contact2EmpId;
	}

	public String getContact2Email() {
		return contact2Email;
	}

	public void setContact2Email(String contact2Email) {
		this.contact2Email = contact2Email;
	}

	public String getContact2Phone() {
		return contact2Phone;
	}

	public void setContact2Phone(String contact2Phone) {
		this.contact2Phone = contact2Phone;
	}

	public String getLicenceType() {
		return licenceType;
	}

	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}

	public String getLicencePeriod() {
		return licencePeriod;
	}

	public void setLicencePeriod(String licencePeriod) {
		this.licencePeriod = licencePeriod;
	}

	public String getLicenceStartDate() {
		return licenceStartDate;
	}

	public void setLicenceStartDate(String licenceStartDate) {
		this.licenceStartDate = licenceStartDate;
	}

	public String getLicenceEndDate() {
		return licenceEndDate;
	}

	public void setLicenceEndDate(String licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	public String getExpectedUsers() {
		return expectedUsers;
	}

	public void setExpectedUsers(String expectedUsers) {
		this.expectedUsers = expectedUsers;
	}

	public String getExpectedDevices() {
		return expectedDevices;
	}

	public void setExpectedDevices(String expectedDevices) {
		this.expectedDevices = expectedDevices;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getFaviconPath() {
		return faviconPath;
	}

	public void setFaviconPath(String faviconPath) {
		this.faviconPath = faviconPath;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSidebarStyle() {
		return sidebarStyle;
	}

	public void setSidebarStyle(String sidebarStyle) {
		this.sidebarStyle = sidebarStyle;
	}

	public String getNavColor() {
		return navColor;
	}

	public void setNavColor(String navColor) {
		this.navColor = navColor;
	}

	public String getApprovalRationale() {
		return approvalRationale;
	}

	public void setApprovalRationale(String approvalRationale) {
		this.approvalRationale = approvalRationale;
	}
}
