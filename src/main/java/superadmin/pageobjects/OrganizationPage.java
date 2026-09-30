package superadmin.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class OrganizationPage extends AbstractComponent {

	public OrganizationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/super-admin/organizations' and .//span[normalize-space()='Organizations']]")
	private WebElement organizationsMenu;

	@FindBy(xpath = "//button[.//*[local-name()='svg' and contains(@class,'lucide-plus')]]")
	private WebElement addOrganisationButton;

	@FindBy(xpath = "//input[@placeholder='e.g. Schneider Electric or SCHNEIDER']")
	private WebElement organizationName;

	@FindBy(xpath = "//input[@placeholder='e.g. SE']")
	private WebElement organizationCode;

	@FindBy(xpath = "//input[@placeholder='e.g. schneider.xrdashboard.com']")
	private WebElement portalDomain;

	@FindBy(xpath = "//button[@aria-haspopup='listbox']//span[normalize-space()='Select Industry']")
	private WebElement industryDropdown;

	@FindBy(xpath = "//button[@aria-haspopup='listbox']//span[normalize-space()='Select Country']")
	private WebElement countryDropdown;

	@FindBy(xpath = "//button[@aria-haspopup='listbox']//span[contains(text(),'UTC')]")
	private WebElement timezoneDropdown;

	@FindBy(xpath = "//button[@aria-haspopup='listbox']//span[normalize-space()='Select Languages']")
	private WebElement defaultLanguageDropdown;

	@FindBy(xpath = "//input[@placeholder='Full name']")
	private WebElement adminFullName;

	@FindBy(xpath = "//input[@type='tel'][@placeholder='e.g. 9876543210']")
	private WebElement phoneNumber;

	@FindBy(xpath = "//input[@type='email'][@placeholder='admin@company.com']")
	private WebElement adminEmail;

	@FindBy(xpath = "//input[@type='password'][@placeholder='Min 8 characters']")
	private WebElement temporaryPassword;

	@FindBy(xpath = "//button[contains(text(),'Send Link')]")
	private WebElement sendLinkTab;

	@FindBy(xpath = "//button[contains(text(),'Manual Entry')]")
	private WebElement manualEntryTab;

	@FindBy(xpath = "//p[contains(text(),'Attach CIN Document')]/following::input[@type='file'][1]")
	private WebElement cinDocument;

	@FindBy(xpath = "//p[contains(text(),'Attach GST Document')]/following::input[@type='file'][1]")
	private WebElement gstDocument;

	@FindBy(xpath = "//p[contains(text(),'Attach PAN Document')]/following::input[@type='file'][1]")
	private WebElement panDocument;

	@FindBy(xpath = "//h4[contains(text(),'Level 1 / Invoice Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Full Name']")
	private WebElement level1FullName;

	@FindBy(xpath = "//h4[contains(text(),'Level 1 / Invoice Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Designation']")
	private WebElement level1Designation;

	@FindBy(xpath = "//h4[contains(text(),'Level 1 / Invoice Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Emp ID']")
	private WebElement level1EmpId;

	@FindBy(xpath = "//h4[contains(text(),'Level 1 / Invoice Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@type='email'][@placeholder='Email']")
	private WebElement level1Email;

	@FindBy(xpath = "//h4[contains(text(),'Level 1 / Invoice Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@type='tel'][@placeholder='e.g. 9876543210']")
	private WebElement level1Phone;

	@FindBy(xpath = "//h4[contains(text(),'Level 2 / Accounts Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Full Name']")
	private WebElement level2FullName;

	@FindBy(xpath = "//h4[contains(text(),'Level 2 / Accounts Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Designation']")
	private WebElement level2Designation;

	@FindBy(xpath = "//h4[contains(text(),'Level 2 / Accounts Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Emp ID']")
	private WebElement level2EmpId;

	@FindBy(xpath = "//h4[contains(text(),'Level 2 / Accounts Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@type='email'][@placeholder='Email']")
	private WebElement level2Email;

	@FindBy(xpath = "//h4[contains(text(),'Level 2 / Accounts Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@type='tel'][@placeholder='e.g. 9876543210']")
	private WebElement level2Phone;

	@FindBy(xpath = "//h4[contains(text(),'Level 3 / Legal Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Full Name']")
	private WebElement level3FullName;

	@FindBy(xpath = "//h4[contains(text(),'Level 3 / Legal Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Designation']")
	private WebElement level3Designation;

	@FindBy(xpath = "//h4[contains(text(),'Level 3 / Legal Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@placeholder='Emp ID']")
	private WebElement level3EmpId;

	@FindBy(xpath = "//h4[contains(text(),'Level 3 / Legal Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@type='email'][@placeholder='Email']")
	private WebElement level3Email;

	@FindBy(xpath = "//h4[contains(text(),'Level 3 / Legal Contact')]/ancestor::div[contains(@class,'rounded-xl')][1]//input[@type='tel'][@placeholder='e.g. 9876543210']")
	private WebElement level3Phone;

	@FindBy(xpath = "//button[contains(text(),'Configure License')]")
	private WebElement configureLicense;

	@FindBy(xpath = "//button[contains(text(),'Start with Trial')]")
	private WebElement startWithTrial;

	@FindBy(xpath = "//label[contains(text(),'License Period')]/following-sibling::button[@aria-haspopup='listbox']")
	private WebElement licensePeriodDropdown;

	@FindBy(xpath = "//span[contains(text(),'Valid:')]/following-sibling::span[1]")
	private WebElement licenseStartDate;

	@FindBy(xpath = "//span[contains(text(),'Valid:')]/following-sibling::span[3]")
	private WebElement licenseEndDate;

	@FindBy(xpath = "//label[contains(text(),'Max Active Users')]/following-sibling::input[@type='number']")
	private WebElement maxActiveUsers;

	@FindBy(xpath = "//label[contains(text(),'Max Active Devices')]/following-sibling::input[@type='number']")
	private WebElement maxActiveDevices;

	@FindBy(xpath = "//textarea[@placeholder='Optional notes...']")
	private WebElement licenseNotes;

	@FindBy(xpath = "//label[contains(text(),'Trial Period')]/following-sibling::button[@aria-haspopup='listbox']")
	private WebElement trialPeriodDropdown;

	@FindBy(xpath = "//span[contains(text(),'Valid:')]/following-sibling::span[1]")
	private WebElement trialStartDate;

	@FindBy(xpath = "//span[contains(text(),'Valid:')]/following-sibling::span[3]")
	private WebElement trialEndDate;

	@FindBy(xpath = "//p[contains(text(),'Organisation Logo')]/ancestor::div[contains(@class,'flex-1')][1]//input[@type='file']")
	private WebElement organisationLogo;

	@FindBy(xpath = "//p[contains(text(),'Favicon')]/ancestor::div[contains(@class,'flex-1')][1]//input[@type='file']")
	private WebElement favicon;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;

	@FindBy(xpath = "//button[contains(text(),'Create Organization')]")
	private WebElement createOrganizationButton;

	@FindBy(xpath = "//input[@name='org-search']")
	private WebElement organizationSearch;

	@FindBy(xpath = "//button[contains(text(),'Overview')]")
	private WebElement overviewTab;

	@FindBy(xpath = "//button[contains(text(),'Admin Users')]")
	private WebElement adminUsersTab;

	@FindBy(xpath = "//button[contains(text(),'Branding')]")
	private WebElement brandingTab;

	@FindBy(xpath = "//button[contains(text(),'Security')]")
	private WebElement securityTab;

	@FindBy(xpath = "//button[contains(text(),'License & Tokens')]")
	private WebElement licenseAndTokensTab;

	@FindBy(xpath = "//button[contains(text(),'Storage')]")
	private WebElement storageTab;

	@FindBy(xpath = "//button[contains(text(),'Notifications')]")
	private WebElement notificationsTab;

	@FindBy(xpath = "//button[contains(text(),'Activity Logs')]")
	private WebElement activityLogsTab;

	@FindBy(xpath = "//button[contains(text(),'Assigned Modules')]")
	private WebElement assignedModulesTab;

	@FindBy(xpath = "//button[contains(text(),'Licence')]")
	private WebElement licenceTab;

	@FindBy(xpath = "//button[contains(text(),'Devices')]")
	private WebElement devicesTab;

	@FindBy(xpath = "//button[contains(text(),'Billing')]")
	private WebElement billingTab;

	@StepName("Click on the Organisation")
	public void clickOrganizations() {
		organizationsMenu.click();
	}

	@StepName("Click on the Add Organisation Button")
	public void clickAddOrganization() {
		waitElementToBeClickable(addOrganisationButton);
		addOrganisationButton.click();
	}

	@StepName("Enter Organization Name")
	public void enterOrganizationName(String name) {
		organizationName.clear();
		organizationName.sendKeys(name);
	}

	@StepName("Get Organization Code")
	public String getOrganizationCode() {
		return organizationCode.getAttribute("value");
	}

	@StepName("Enter Portal Domain")
	public void enterPortalDomain(String domain) {
		portalDomain.clear();
		portalDomain.sendKeys(domain);
	}

	@StepName("Select Industry")
	public void selectIndustry(String industry) {
		industryDropdown.click();

		WebElement option = driver
				.findElement(By.xpath("//button[@role='option']//span[normalize-space()='" + industry + "']"));

		option.click();
	}

	@StepName("Select Country")
	public void selectCountry(String country) {
		countryDropdown.click();

		WebElement searchCountry = driver.findElement(By.xpath("//input[@placeholder='Search country...']"));

		searchCountry.sendKeys(country);

		WebElement option = driver
				.findElement(By.xpath("//button[@role='option']//span[normalize-space()='" + country + "']"));

		option.click();
	}

	@StepName("Get Timezone")
	public String getTimezone() {
		return timezoneDropdown.getText();
	}

	@StepName("Select Default Languages")
	public void selectDefaultLanguages(String... languages) {
		defaultLanguageDropdown.click();

		for (String language : languages) {
			WebElement searchLanguage = driver.findElement(By.xpath("//input[@placeholder='Search languages...']"));

			searchLanguage.clear();
			searchLanguage.sendKeys(language);

			WebElement option = driver
					.findElement(By.xpath("//button[@role='option']//span[normalize-space()='" + language + "']"));

			option.click();
		}
	}

	@StepName("Enter Admin Full Name")
	public void enterAdminFullName(String name) {
		adminFullName.clear();
		adminFullName.sendKeys(name);
	}

	@StepName("Enter Phone Number")
	public void enterPhoneNumber(String phone) {
		phoneNumber.clear();
		phoneNumber.sendKeys(phone);
	}

	@StepName("Enter Admin Email")
	public void enterAdminEmail(String email) {
		adminEmail.clear();
		adminEmail.sendKeys(email);
	}

	@StepName("Enter Temporary Password")
	public void enterTemporaryPassword(String password) {
		temporaryPassword.clear();
		temporaryPassword.sendKeys(password);
	}

	@StepName("Select Send Link")
	public void selectSendLink() {
		sendLinkTab.click();
	}

	@StepName("Select Manual Entry")
	public void selectManualEntry() {
		manualEntryTab.click();
	}

	@StepName("Upload CIN Document")
	public void uploadCinDocument(String filePath) {
		cinDocument.sendKeys(filePath);
	}

	@StepName("Upload GST Document")
	public void uploadGstDocument(String filePath) {
		gstDocument.sendKeys(filePath);
	}

	@StepName("Upload PAN Document")
	public void uploadPanDocument(String filePath) {
		panDocument.sendKeys(filePath);
	}

	@StepName("Enter Level 1 Contact Full Name")
	public void enterLevel1FullName(String name) {
		level1FullName.clear();
		level1FullName.sendKeys(name);
	}

	@StepName("Enter Level 1 Contact Designation")
	public void enterLevel1Designation(String designation) {
		level1Designation.clear();
		level1Designation.sendKeys(designation);
	}

	@StepName("Enter Level 1 Contact Employee ID")
	public void enterLevel1EmpId(String empId) {
		level1EmpId.clear();
		level1EmpId.sendKeys(empId);
	}

	@StepName("Enter Level 1 Contact Email")
	public void enterLevel1Email(String email) {
		level1Email.clear();
		level1Email.sendKeys(email);
	}

	@StepName("Enter Level 1 Contact Phone")
	public void enterLevel1Phone(String phone) {
		level1Phone.clear();
		level1Phone.sendKeys(phone);
	}

	@StepName("Enter Level 2 Contact Full Name")
	public void enterLevel2FullName(String name) {
		level2FullName.clear();
		level2FullName.sendKeys(name);
	}

	@StepName("Enter Level 2 Contact Designation")
	public void enterLevel2Designation(String designation) {
		level2Designation.clear();
		level2Designation.sendKeys(designation);
	}

	@StepName("Enter Level 2 Contact Employee ID")
	public void enterLevel2EmpId(String empId) {
		level2EmpId.clear();
		level2EmpId.sendKeys(empId);
	}

	@StepName("Enter Level 2 Contact Email")
	public void enterLevel2Email(String email) {
		level2Email.clear();
		level2Email.sendKeys(email);
	}

	@StepName("Enter Level 2 Contact Phone")
	public void enterLevel2Phone(String phone) {
		level2Phone.clear();
		level2Phone.sendKeys(phone);
	}

	@StepName("Enter Level 3 Contact Full Name")
	public void enterLevel3FullName(String name) {
		level3FullName.clear();
		level3FullName.sendKeys(name);
	}

	@StepName("Enter Level 3 Contact Designation")
	public void enterLevel3Designation(String designation) {
		level3Designation.clear();
		level3Designation.sendKeys(designation);
	}

	@StepName("Enter Level 3 Contact Employee ID")
	public void enterLevel3EmpId(String empId) {
		level3EmpId.clear();
		level3EmpId.sendKeys(empId);
	}

	@StepName("Enter Level 3 Contact Email")
	public void enterLevel3Email(String email) {
		level3Email.clear();
		level3Email.sendKeys(email);
	}

	@StepName("Enter Level 3 Contact Phone")
	public void enterLevel3Phone(String phone) {
		level3Phone.clear();
		level3Phone.sendKeys(phone);
	}

	@StepName("Select Configure License")
	public void selectConfigureLicense() {
		configureLicense.click();
	}

	@StepName("Select Start with Trial")
	public void selectStartWithTrial() {
		startWithTrial.click();
	}

	@StepName("Select License Period")
	public void selectLicensePeriod(String period) {
		licensePeriodDropdown.click();

		WebElement option = driver.findElement(By.xpath("//button[@role='option'][contains(.,'" + period + "')]"));

		option.click();
	}

	@StepName("Get License Start Date")
	public String getLicenseStartDate() {
		return licenseStartDate.getText();
	}

	@StepName("Get License End Date")
	public String getLicenseEndDate() {
		return licenseEndDate.getText();
	}

	@StepName("Enter Max Active Users")
	public void enterMaxActiveUsers(String users) {
		maxActiveUsers.clear();
		maxActiveUsers.sendKeys(users);
	}

	@StepName("Enter Max Active Devices")
	public void enterMaxActiveDevices(String devices) {
		maxActiveDevices.clear();
		maxActiveDevices.sendKeys(devices);
	}

	@StepName("Enter License Notes")
	public void enterLicenseNotes(String notes) {
		licenseNotes.clear();
		licenseNotes.sendKeys(notes);
	}

	@StepName("Select Trial Period")
	public void selectTrialPeriod(String period) {
		trialPeriodDropdown.click();

		WebElement option = driver.findElement(By.xpath("//button[@role='option'][contains(.,'" + period + "')]"));

		option.click();
	}

	@StepName("Get Trial Start Date")
	public String getTrialStartDate() {
		return trialStartDate.getText();
	}

	@StepName("Get Trial End Date")
	public String getTrialEndDate() {
		return trialEndDate.getText();
	}

	@StepName("Select Portal")
	public void selectPortal(String portalName) {
		WebElement portal = driver.findElement(By.xpath("//button[contains(.,'" + portalName + "')]"));

		portal.click();
	}

	@StepName("Upload Organisation Logo")
	public void uploadOrganisationLogo(String filePath) {
		organisationLogo.sendKeys(filePath);
	}

	@StepName("Upload Favicon")
	public void uploadFavicon(String filePath) {
		favicon.sendKeys(filePath);
	}

	@StepName("Cancel Organization Creation")
	public void cancelOrganizationCreation() {
		cancelButton.click();
	}

	@StepName("Create Organization")
	public void createOrganization() {
		createOrganizationButton.click();
	}

	@StepName("Search Organization")
	public void searchOrganization(String searchText) {
		organizationSearch.clear();
		organizationSearch.sendKeys(searchText);
	}

	@StepName("Click Organization")
	public void clickOrganization(String organizationName) {
		WebElement organizationRow = driver
				.findElement(By.xpath("//tr[.//p[contains(text(),'" + organizationName + "')]]"));

		organizationRow.click();
	}

	@StepName("Click Overview Tab")
	public void clickOverviewTab() {
		overviewTab.click();
	}

	@StepName("Click Admin Users Tab")
	public void clickAdminUsersTab() {
		adminUsersTab.click();
	}

	@StepName("Click Branding Tab")
	public void clickBrandingTab() {
		brandingTab.click();
	}

	@StepName("Click Security Tab")
	public void clickSecurityTab() {
		securityTab.click();
	}

	@StepName("Click License & Tokens Tab")
	public void clickLicenseAndTokensTab() {
		licenseAndTokensTab.click();
	}

	@StepName("Click Storage Tab")
	public void clickStorageTab() {
		storageTab.click();
	}

	@StepName("Click Notifications Tab")
	public void clickNotificationsTab() {
		notificationsTab.click();
	}

	@StepName("Click Activity Logs Tab")
	public void clickActivityLogsTab() {
		activityLogsTab.click();
	}

	@StepName("Click Assigned Modules Tab")
	public void clickAssignedModulesTab() {
		assignedModulesTab.click();
	}

	@StepName("Click Licence Tab")
	public void clickLicenceTab() {
		licenceTab.click();
	}

	@StepName("Click Devices Tab")
	public void clickDevicesTab() {
		devicesTab.click();
	}

	@StepName("Click Billing Tab")
	public void clickBillingTab() {
		billingTab.click();
	}

	@StepName("Verify Organization Present in List")
	public boolean isOrganizationPresent(String orgName) {
		try {
			searchOrganization(orgName);
			WebElement row = driver.findElement(By.xpath("//tr[.//p[contains(text(),'" + orgName + "')]]"));
			return row.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
