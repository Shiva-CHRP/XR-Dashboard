package superadmin.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationData {

	// Section 1: Organisation Details
	@JsonProperty("orgName")
	private String orgName;

	@JsonProperty("orgCode")
	private String orgCode;

	@JsonProperty("domain")
	private String domain;

	@JsonProperty("industry")
	private String industry;

	@JsonProperty("country")
	private String country;

	@JsonProperty("timezone")
	private String timezone;

	@JsonProperty("languages")
	private List<String> languages;

	// Section 2: Admin Account
	@JsonProperty("adminName")
	private String adminName;

	@JsonProperty("adminPhone")
	private String adminPhone;

	@JsonProperty("adminEmail")
	private String adminEmail;

	@JsonProperty("adminPassword")
	private String adminPassword;

	// Section 3: Onboarding & KYC Documents
	@JsonProperty("onboardingMode")
	private String onboardingMode;

	@JsonProperty("cinNumber")
	private String cinNumber;

	@JsonProperty("cinDocumentPath")
	private String cinDocumentPath;

	@JsonProperty("gstNumber")
	private String gstNumber;

	@JsonProperty("gstDocumentPath")
	private String gstDocumentPath;

	@JsonProperty("panNumber")
	private String panNumber;

	@JsonProperty("panDocumentPath")
	private String panDocumentPath;

	// Section 4: Key Contacts
	@JsonProperty("level1Contact")
	private ContactPerson level1Contact;

	@JsonProperty("level2Contact")
	private ContactPerson level2Contact;

	@JsonProperty("level3Contact")
	private ContactPerson level3Contact;

	// Section 5: License & Subscription
	@JsonProperty("license")
	private LicenseDetails license;

	// Section 6: Portal & Branding
	@JsonProperty("branding")
	private BrandingDetails branding;

	// Compatibility fallback field
	@JsonProperty("trialPeriod")
	private String trialPeriod;

	public OrganizationData() {
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getOnboardingMode() {
		return onboardingMode;
	}

	public void setOnboardingMode(String onboardingMode) {
		this.onboardingMode = onboardingMode;
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

	public ContactPerson getLevel1Contact() {
		return level1Contact;
	}

	public void setLevel1Contact(ContactPerson level1Contact) {
		this.level1Contact = level1Contact;
	}

	public ContactPerson getLevel2Contact() {
		return level2Contact;
	}

	public void setLevel2Contact(ContactPerson level2Contact) {
		this.level2Contact = level2Contact;
	}

	public ContactPerson getLevel3Contact() {
		return level3Contact;
	}

	public void setLevel3Contact(ContactPerson level3Contact) {
		this.level3Contact = level3Contact;
	}

	public LicenseDetails getLicense() {
		return license;
	}

	public void setLicense(LicenseDetails license) {
		this.license = license;
	}

	public BrandingDetails getBranding() {
		return branding;
	}

	public void setBranding(BrandingDetails branding) {
		this.branding = branding;
	}

	public String getTrialPeriod() {
		if (trialPeriod != null && !trialPeriod.isEmpty()) {
			return trialPeriod;
		}
		if (license != null && license.getTrialPeriod() != null) {
			return license.getTrialPeriod();
		}
		return "";
	}

	public void setTrialPeriod(String trialPeriod) {
		this.trialPeriod = trialPeriod;
	}

	@Override
	public String toString() {
		return "OrganizationData{" +
				"orgName='" + orgName + '\'' +
				", orgCode='" + orgCode + '\'' +
				", domain='" + domain + '\'' +
				", industry='" + industry + '\'' +
				", country='" + country + '\'' +
				", timezone='" + timezone + '\'' +
				", languages=" + languages +
				", adminName='" + adminName + '\'' +
				", adminPhone='" + adminPhone + '\'' +
				", adminEmail='" + adminEmail + '\'' +
				", onboardingMode='" + onboardingMode + '\'' +
				", license=" + license +
				", branding=" + branding +
				'}';
	}
}
