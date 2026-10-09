package superadmin.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class OrganizationPage extends AbstractComponent {

	public OrganizationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Header
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/organizations']")
	private WebElement organizationsMenu;

	@FindBy(xpath = "//button[contains(.,'Refresh')]")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Export')]")
	private WebElement exportButton;

	@FindBy(xpath = "//button[normalize-space()='XLS']")
	private WebElement exportFormatXls;

	@FindBy(xpath = "//button[normalize-space()='PDF']")
	private WebElement exportFormatPdf;

	@FindBy(xpath = "//button[contains(.,'All Organizations')]")
	private WebElement exportAllOrgsOption;

	@FindBy(xpath = "//button[contains(.,'Visible Organizations')]")
	private WebElement exportVisibleOrgsOption;

	// =========================================================================
	// 2. KPI Cards
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'transition-transform')][.//p[text()='Total Orgs'] or .//div[contains(text(),'Total Orgs')]]")
	private WebElement totalOrgsCard;

	@FindBy(xpath = "//div[contains(@class,'transition-transform')][.//p[text()='Active'] or .//div[contains(text(),'Active')]]")
	private WebElement activeOrgsCard;

	@FindBy(xpath = "//div[contains(@class,'transition-transform')][.//p[text()='Suspended'] or .//div[contains(text(),'Suspended')]]")
	private WebElement suspendedOrgsCard;

	@FindBy(xpath = "//div[contains(@class,'transition-transform')][.//p[text()='New This Month'] or .//div[contains(text(),'New This Month')]]")
	private WebElement newThisMonthCard;

	// =========================================================================
	// 3. Organization Table & Filters (Listing Page)
	// =========================================================================

	@FindBy(xpath = "//input[contains(@placeholder,'Search by name') or @name='org-search']")
	private WebElement organizationSearch;

	@FindBy(xpath = "//button[contains(.,'Add Organization') or .//*[local-name()='svg' and contains(@class,'lucide-plus')]]")
	private WebElement addOrganisationButton;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(@class,'rounded-md') and .//span[contains(text(),'Status') or contains(text(),'Active') or contains(text(),'Inactive') or contains(text(),'Suspended')]]")
	private WebElement statusFilterTrigger;

	// =========================================================================
	// 4. Modals (Suspend / Delete / GDPR Delete)
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='Organization name']")
	private WebElement deleteConfirmationInput;

	@FindBy(xpath = "//button[normalize-space()='Delete Permanently']")
	private WebElement deletePermanentlyButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel'] | //div[contains(@class,'fixed')]//button[normalize-space()='Cancel']")
	private WebElement modalCancelButton;

	@FindBy(xpath = "//button[normalize-space()='Suspend Organization' or (contains(@class,'bg-titan-warning') and normalize-space()='Suspend')]")
	private WebElement suspendConfirmButton;

	// =========================================================================
	// 5. Create Organization Wizard - Section 1: Org Details
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='e.g. Schneider Electric or SCHNEIDER']")
	private WebElement organizationName;

	@FindBy(xpath = "//input[@placeholder='e.g. SE']")
	private WebElement organizationCode;

	@FindBy(xpath = "//input[@placeholder='e.g. schneider.xrdashboard.com']")
	private WebElement portalDomain;

	@FindBy(xpath = "//button[@aria-haspopup='listbox'][.//span[normalize-space()='Select Industry'] or preceding-sibling::label[contains(text(),'Industry')]]")
	private WebElement industryDropdown;

	@FindBy(xpath = "//button[@aria-haspopup='listbox'][.//span[normalize-space()='Select Country'] or preceding-sibling::label[contains(text(),'Country')]]")
	private WebElement countryDropdown;

	@FindBy(xpath = "//label[contains(text(),'Timezone')]/following-sibling::div//button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'Timezone') or contains(text(),'UTC')]]")
	private WebElement timezoneDropdown;

	@FindBy(xpath = "//button[@aria-haspopup='listbox'][.//span[normalize-space()='Select Languages'] or preceding-sibling::label[contains(text(),'Language')]]")
	private WebElement defaultLanguageDropdown;

	// =========================================================================
	// 6. Create Organization Wizard - Section 2: Admin Account
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='Full name']")
	private WebElement adminFullName;

	@FindBy(xpath = "//input[@type='tel'][@placeholder='e.g. 9876543210' or contains(@placeholder,'9876543210')]")
	private WebElement phoneNumber;

	@FindBy(xpath = "//input[@type='email'][@placeholder='admin@company.com']")
	private WebElement adminEmail;

	@FindBy(xpath = "//input[@type='password'][@placeholder='Min 8 characters']")
	private WebElement temporaryPassword;

	// =========================================================================
	// 7. Create Organization Wizard - Section 3: License / Trial
	// =========================================================================

	@FindBy(xpath = "//button[contains(text(),'Configure License')]")
	private WebElement configureLicense;

	@FindBy(xpath = "//button[contains(text(),'Start with Trial')]")
	private WebElement startWithTrial;

	@FindBy(xpath = "//label[contains(text(),'License Period')]/following-sibling::button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'License Period')]]")
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

	@FindBy(xpath = "//label[contains(text(),'Trial Period')]/following-sibling::button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'Trial Period')]]")
	private WebElement trialPeriodDropdown;

	@FindBy(xpath = "//span[contains(text(),'Valid:')]/following-sibling::span[1]")
	private WebElement trialStartDate;

	@FindBy(xpath = "//span[contains(text(),'Valid:')]/following-sibling::span[3]")
	private WebElement trialEndDate;

	// =========================================================================
	// 8. Create Organization Wizard - Section 4: KYC & Contacts
	// =========================================================================

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

	// =========================================================================
	// 9. Portal & Branding Wizard + Submit
	// =========================================================================

	@FindBy(xpath = "//p[contains(text(),'Organisation Logo')]/ancestor::div[contains(@class,'flex-1')][1]//input[@type='file']")
	private WebElement organisationLogo;

	@FindBy(xpath = "//p[contains(text(),'Favicon')]/ancestor::div[contains(@class,'flex-1')][1]//input[@type='file']")
	private WebElement favicon;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;

	@FindBy(xpath = "//button[contains(text(),'Create Organization') or contains(text(),'Create Organisation')]")
	private WebElement createOrganizationButton;

	@FindBy(xpath = "//button[contains(text(),'Next') or contains(.,'Next')]")
	private WebElement wizardNextButton;

	@FindBy(xpath = "//button[contains(text(),'Back') or contains(.,'Back')]")
	private WebElement wizardBackButton;

	// =========================================================================
	// 10. Post-Creation Success Modal
	// =========================================================================

	@FindBy(xpath = "//h3[contains(text(),'Organisation Created Successfully') or contains(text(),'Organization Created Successfully')]")
	private WebElement successModalTitle;

	@FindBy(xpath = "//button[@title='Copy licence key' or .//*[local-name()='svg' and contains(@class,'lucide-copy')]]")
	private WebElement copyLicenseKeyButton;

	@FindBy(xpath = "//button[contains(text(),'Continue to Organisation') or contains(text(),'Continue to Organization')]")
	private WebElement continueToOrgButton;

	@FindBy(xpath = "//button[contains(text(),'Go to Organisations List') or contains(text(),'Go to Organizations List')]")
	private WebElement goToOrgsListButton;

	// =========================================================================
	// 11. Manage Organization Tabs (Organizations/Manage/...)
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Back to Organizations') or contains(.,'Back to Organisations') or .//*[local-name()='svg' and contains(@class,'lucide-arrow-left')]]")
	private WebElement backToOrganizationsButton;

	@FindBy(xpath = "//button[normalize-space()='Overview']")
	private WebElement overviewTab;

	@FindBy(xpath = "//button[normalize-space()='Admin Users']")
	private WebElement adminUsersTab;

	@FindBy(xpath = "//button[normalize-space()='Branding']")
	private WebElement brandingTab;

	@FindBy(xpath = "//button[normalize-space()='Security']")
	private WebElement securityTab;

	@FindBy(xpath = "//button[normalize-space()='Offline Config']")
	private WebElement offlineConfigTab;

	@FindBy(xpath = "//button[contains(text(),'License & Devices') or contains(text(),'Licence & Devices') or contains(text(),'License & Tokens') or contains(text(),'Licence & Tokens')]")
	private WebElement licenseAndTokensTab;

	@FindBy(xpath = "//button[normalize-space()='Storage']")
	private WebElement storageTab;

	@FindBy(xpath = "//button[normalize-space()='Notifications']")
	private WebElement notificationsTab;

	@FindBy(xpath = "//button[normalize-space()='Activity Logs']")
	private WebElement activityLogsTab;

	@FindBy(xpath = "//button[normalize-space()='Assigned Modules']")
	private WebElement assignedModulesTab;

	@FindBy(xpath = "//button[normalize-space()='Licence' or normalize-space()='License']")
	private WebElement licenceTab;

	@FindBy(xpath = "//button[normalize-space()='Devices']")
	private WebElement devicesTab;

	@FindBy(xpath = "//button[normalize-space()='Billing']")
	private WebElement billingTab;

	@FindBy(xpath = "//button[contains(.,'Create Admin')]")
	private WebElement createAdminButton;

	@FindBy(xpath = "//button[contains(.,'Assign Modules')]")
	private WebElement assignModulesButton;

	// =========================================================================
	// Page Action Methods: Navigation & Listing
	// =========================================================================

	@StepName("Click on the Organisation Menu")
	public void clickOrganizations() {
		waitElementToBeClickable(organizationsMenu);
		organizationsMenu.click();
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitElementToBeClickable(refreshButton);
		refreshButton.click();
	}

	@StepName("Click on the Add Organisation Button")
	public void clickAddOrganization() {
		waitElementToBeClickable(addOrganisationButton);
		addOrganisationButton.click();
	}

	@StepName("Is Add Organization Modal Displayed")
	public boolean isAddOrganizationModalDisplayed() {
		return !driver.findElements(By.xpath("//input[@placeholder='e.g. Schneider Electric or SCHNEIDER'] | //div[@role='dialog']")).isEmpty();
	}

	@StepName("Search Organization")
	public void searchOrganization(String searchText) {
		waitElementToBeClickable(organizationSearch);
		organizationSearch.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (searchText != null && !searchText.isEmpty()) {
			organizationSearch.sendKeys(searchText);
		}
	}

	@StepName("Clear Organization Search")
	public void clearSearch() {
		try {
			waitElementToBeClickable(organizationSearch);
			organizationSearch.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
			organizationSearch.sendKeys(org.openqa.selenium.Keys.ENTER);
			waitUtils.waitUntil(d -> {
				List<WebElement> rows = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				return !rows.isEmpty() && rows.get(0).isDisplayed();
			});
		} catch (Exception ignored) {
		}
	}

	@StepName("Get Organizations Table Row Count")
	public int getTableRowCount() {
		return driver.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]")).size();
	}

	@StepName("Is Empty Table State Displayed")
	public boolean isEmptyTableStateDisplayed() {
		return !driver.findElements(By.xpath("//td[contains(.,'No') or contains(.,'found') or @colSpan] | //div[contains(.,'No') and contains(.,'found')]")).isEmpty()
				|| getTableRowCount() == 0;
	}

	@StepName("Filter Organizations by Status")
	public void filterByStatus(String status) {
		waitElementToBeClickable(statusFilterTrigger);
		statusFilterTrigger.click();
		WebElement option = driver.findElement(By.xpath(
				"//div[@role='menu']//div[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
						+ status.toLowerCase() + "')] | //button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
						+ status.toLowerCase() + "')]"));
		option.click();
	}

	@StepName("Click Total Orgs KPI Card")
	public void clickTotalOrgsCard() {
		waitElementToBeClickable(totalOrgsCard);
		totalOrgsCard.click();
	}

	@StepName("Click Active Orgs KPI Card")
	public void clickActiveOrgsCard() {
		waitElementToBeClickable(activeOrgsCard);
		activeOrgsCard.click();
	}

	@StepName("Click Suspended Orgs KPI Card")
	public void clickSuspendedOrgsCard() {
		waitElementToBeClickable(suspendedOrgsCard);
		suspendedOrgsCard.click();
	}

	@StepName("Export Organizations")
	public void exportOrganizations(String format, String scope) {
		waitElementToBeClickable(exportButton);
		exportButton.click();

		if ("PDF".equalsIgnoreCase(format)) {
			waitElementToBeClickable(exportFormatPdf);
			exportFormatPdf.click();
		} else {
			waitElementToBeClickable(exportFormatXls);
			exportFormatXls.click();
		}

		if ("visible".equalsIgnoreCase(scope)) {
			waitElementToBeClickable(exportVisibleOrgsOption);
			exportVisibleOrgsOption.click();
		} else {
			waitElementToBeClickable(exportAllOrgsOption);
			exportAllOrgsOption.click();
		}
	}

	@StepName("Click Organization Row directly")
	public void clickOrganization(String organizationName) {
		WebElement organizationRow = driver
				.findElement(By.xpath("//tr[.//p[contains(text(),'" + organizationName + "')] or .//td[contains(.,'" + organizationName + "')]]"));
		waitElementToBeClickable(organizationRow);
		organizationRow.click();
	}

	@StepName("Open Row Action 3-Dots Menu")
	public void openRowActionMenu(String organizationName) {
		WebElement menuBtn = driver.findElement(By.xpath(
				"//tr[.//p[contains(text(),'" + organizationName + "')] or .//td[contains(.,'" + organizationName + "')]]//button[.//svg[contains(@class,'lucide-more-horizontal')] or contains(@class,'p-1.5')]"));
		waitElementToBeClickable(menuBtn);
		menuBtn.click();
	}

	@StepName("Click Manage from Row Menu")
	public void clickManageFromRowMenu(String organizationName) {
		openRowActionMenu(organizationName);
		WebElement manageBtn = driver.findElement(By.xpath("//div[contains(@class,'z-[10001]')]//button[normalize-space()='Manage']"));
		waitElementToBeClickable(manageBtn);
		manageBtn.click();
	}

	@StepName("Click Suspend/Activate from Row Menu")
	public void clickToggleStatusFromRowMenu(String organizationName) {
		openRowActionMenu(organizationName);
		WebElement toggleBtn = driver.findElement(By.xpath("//div[contains(@class,'z-[10001]')]//button[normalize-space()='Suspend' or normalize-space()='Activate']"));
		waitElementToBeClickable(toggleBtn);
		toggleBtn.click();
	}

	@StepName("Click Delete from Row Menu")
	public void clickDeleteFromRowMenu(String organizationName) {
		openRowActionMenu(organizationName);
		WebElement deleteBtn = driver.findElement(By.xpath("//div[contains(@class,'z-[10001]')]//button[normalize-space()='Delete']"));
		waitElementToBeClickable(deleteBtn);
		deleteBtn.click();
	}

	@StepName("Confirm Delete in Modal with Organization Name")
	public void confirmDeleteOrganization(String orgName) {
		waitElementToBeClickable(deleteConfirmationInput);
		deleteConfirmationInput.clear();
		deleteConfirmationInput.sendKeys(orgName);
		waitElementToBeClickable(deletePermanentlyButton);
		deletePermanentlyButton.click();
	}

	@StepName("Confirm Suspend Modal")
	public void confirmSuspendOrganization() {
		waitElementToBeClickable(suspendConfirmButton);
		suspendConfirmButton.click();
	}

	@StepName("Cancel Any Open Modal")
	public void cancelModal() {
		waitElementToBeClickable(modalCancelButton);
		modalCancelButton.click();
	}

	@StepName("Verify Organization Present in List")
	public boolean isOrganizationPresent(String orgName) {
		try {
			searchOrganization(orgName);
			WebElement row = driver.findElement(By.xpath("//tr[.//p[contains(text(),'" + orgName + "')] or .//td[contains(.,'" + orgName + "')]]"));
			return row.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// =========================================================================
	// Page Action Methods: Create Form Fields
	// =========================================================================

	@StepName("Enter Organization Name")
	public void enterOrganizationName(String name) {
		waitElementToBeClickable(organizationName);
		organizationName.clear();
		organizationName.sendKeys(name);
	}

	@StepName("Get Organization Code")
	public String getOrganizationCode() {
		return organizationCode.getAttribute("value");
	}

	@StepName("Enter Portal Domain")
	public void enterPortalDomain(String domain) {
		waitElementToBeClickable(portalDomain);
		portalDomain.clear();
		portalDomain.sendKeys(domain);
	}

	@StepName("Select Industry")
	public void selectIndustry(String industry) {
		waitElementToBeClickable(industryDropdown);
		industryDropdown.click();
		WebElement option = driver.findElement(By.xpath(
				"//button[@role='option']//span[normalize-space()='" + industry + "'] | //div[@role='option'][normalize-space()='" + industry + "']"));
		option.click();
	}

	@StepName("Select Country")
	public void selectCountry(String country) {
		waitElementToBeClickable(countryDropdown);
		countryDropdown.click();

		try {
			WebElement searchCountry = driver.findElement(By.xpath("//input[@placeholder='Search country...']"));
			searchCountry.sendKeys(country);
		} catch (Exception ignored) {
		}

		WebElement option = driver.findElement(By.xpath(
				"//button[@role='option']//span[normalize-space()='" + country + "'] | //div[@role='option'][normalize-space()='" + country + "']"));
		option.click();
	}

	@StepName("Get Timezone")
	public String getTimezone() {
		return timezoneDropdown.getText();
	}

	@StepName("Select Default Languages")
	public void selectDefaultLanguages(String... languages) {
		waitElementToBeClickable(defaultLanguageDropdown);
		defaultLanguageDropdown.click();

		for (String language : languages) {
			try {
				WebElement searchLanguage = driver.findElement(By.xpath("//input[@placeholder='Search languages...']"));
				searchLanguage.clear();
				searchLanguage.sendKeys(language);
			} catch (Exception ignored) {
			}

			WebElement option = driver.findElement(By.xpath(
					"//button[@role='option']//span[normalize-space()='" + language + "'] | //div[@role='option'][normalize-space()='" + language + "']"));
			option.click();
		}
	}

	@StepName("Enter Admin Full Name")
	public void enterAdminFullName(String name) {
		waitElementToBeClickable(adminFullName);
		adminFullName.clear();
		adminFullName.sendKeys(name);
	}

	@StepName("Enter Phone Number")
	public void enterPhoneNumber(String phone) {
		waitElementToBeClickable(phoneNumber);
		phoneNumber.clear();
		phoneNumber.sendKeys(phone);
	}

	@StepName("Enter Admin Email")
	public void enterAdminEmail(String email) {
		waitElementToBeClickable(adminEmail);
		adminEmail.clear();
		adminEmail.sendKeys(email);
	}

	@StepName("Enter Temporary Password")
	public void enterTemporaryPassword(String password) {
		waitElementToBeClickable(temporaryPassword);
		temporaryPassword.clear();
		temporaryPassword.sendKeys(password);
	}

	@StepName("Select Send Link")
	public void selectSendLink() {
		waitElementToBeClickable(sendLinkTab);
		sendLinkTab.click();
	}

	@StepName("Select Manual Entry")
	public void selectManualEntry() {
		waitElementToBeClickable(manualEntryTab);
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

	@StepName("Enter Level 1 Contact Details")
	public void enterLevel1Contact(String name, String designation, String empId, String email, String phone) {
		level1FullName.clear();
		level1FullName.sendKeys(name);
		level1Designation.clear();
		level1Designation.sendKeys(designation);
		level1EmpId.clear();
		level1EmpId.sendKeys(empId);
		level1Email.clear();
		level1Email.sendKeys(email);
		level1Phone.clear();
		level1Phone.sendKeys(phone);
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
		waitElementToBeClickable(configureLicense);
		configureLicense.click();
	}

	@StepName("Select Start with Trial")
	public void selectStartWithTrial() {
		waitElementToBeClickable(startWithTrial);
		startWithTrial.click();
	}

	@StepName("Select License Period")
	public void selectLicensePeriod(String period) {
		waitElementToBeClickable(licensePeriodDropdown);
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
		waitElementToBeClickable(trialPeriodDropdown);
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
		waitElementToBeClickable(portal);
		portal.click();
	}

	@StepName("Click Wizard Next")
	public void clickWizardNext() {
		waitElementToBeClickable(wizardNextButton);
		wizardNextButton.click();
	}

	@StepName("Click Wizard Back")
	public void clickWizardBack() {
		waitElementToBeClickable(wizardBackButton);
		wizardBackButton.click();
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
		waitElementToBeClickable(cancelButton);
		try {
			cancelButton.click();
		} catch (Exception e) {
			clickUsingJS(cancelButton);
		}
		waitUtils.waitForInvisibility(cancelButton);
	}

	@StepName("Create Organization Submit")
	public void createOrganization() {
		waitElementToBeClickable(createOrganizationButton);
		createOrganizationButton.click();
	}

	// =========================================================================
	// Post-Creation Success Modal Methods
	// =========================================================

	@StepName("Verify Success Modal Displayed")
	public boolean isSuccessModalDisplayed() {
		try {
			waitUtils.waitForVisibility(successModalTitle);
			return successModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Copy Licence Key")
	public void clickCopyLicenceKey() {
		waitElementToBeClickable(copyLicenseKeyButton);
		copyLicenseKeyButton.click();
	}

	@StepName("Click Continue to Organisation from Modal")
	public void clickContinueToOrganization() {
		waitElementToBeClickable(continueToOrgButton);
		continueToOrgButton.click();
	}

	@StepName("Click Go to Organisations List from Modal")
	public void clickGoToOrganizationsList() {
		waitElementToBeClickable(goToOrgsListButton);
		goToOrgsListButton.click();
	}

	// =========================================================================
	// Manage Organization Tabs
	// =========================================================================

	@StepName("Open First Organization in Manage Mode")
	public boolean openFirstOrganizationManage() {
		try {
			clearSearch();

			waitUtils.waitUntil(d -> {
				List<WebElement> rows = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				return !rows.isEmpty() && rows.get(0).isDisplayed();
			});
			List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
			if (rows.isEmpty()) {
				return false;
			}
			WebElement firstRow = rows.get(0);
			waitUtils.scrollIntoView(firstRow);

			try {
				WebElement nameCell = firstRow.findElement(By.xpath("./td[1]"));
				waitElementToBeClickable(nameCell);
				nameCell.click();
			} catch (Exception clickErr) {
				try {
					clickUsingJS(firstRow);
				} catch (Exception jsErr) {
					WebElement actionMenu = firstRow.findElement(By.xpath(".//button[.//svg[contains(@class,'lucide-more-horizontal')]] | .//td[last()]//button"));
					By manageBy = By.xpath("//div[contains(@class,'fixed')]//button[normalize-space()='Manage']");
					waitUtils.waitForClickable(manageBy);
					driver.findElement(manageBy).click();
				}
			}
			waitUtils.waitForUrlContains("/manage");
			waitUtils.waitForVisibility(overviewTab);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Back to Organizations")
	public void clickBackToOrganizations() {
		try {
			waitElementToBeClickable(backToOrganizationsButton);
			backToOrganizationsButton.click();
		} catch (Exception e) {
			clickUsingJS(backToOrganizationsButton);
		}
		waitUtils.waitUntil(d -> d.getCurrentUrl().contains("/super-admin/organizations") && !d.getCurrentUrl().contains("/manage"));
		try {
			waitUtils.waitForVisibility(organizationSearch);
		} catch (Exception ignored) {
		}
	}

	@StepName("Click Overview Tab")
	public void clickOverviewTab() {
		waitUtils.scrollIntoView(overviewTab);
		try {
			waitElementToBeClickable(overviewTab);
			overviewTab.click();
		} catch (Exception e) {
			clickUsingJS(overviewTab);
		}
	}

	@StepName("Click Admin Users Tab")
	public void clickAdminUsersTab() {
		waitUtils.scrollIntoView(adminUsersTab);
		try {
			waitElementToBeClickable(adminUsersTab);
			adminUsersTab.click();
		} catch (Exception e) {
			clickUsingJS(adminUsersTab);
		}
	}

	@StepName("Click Branding Tab")
	public void clickBrandingTab() {
		waitUtils.scrollIntoView(brandingTab);
		try {
			waitElementToBeClickable(brandingTab);
			brandingTab.click();
		} catch (Exception e) {
			clickUsingJS(brandingTab);
		}
	}

	@StepName("Click Security Tab")
	public void clickSecurityTab() {
		waitUtils.scrollIntoView(securityTab);
		try {
			waitElementToBeClickable(securityTab);
			securityTab.click();
		} catch (Exception e) {
			clickUsingJS(securityTab);
		}
	}

	@StepName("Click Offline Config Tab")
	public void clickOfflineConfigTab() {
		waitUtils.scrollIntoView(offlineConfigTab);
		try {
			waitElementToBeClickable(offlineConfigTab);
			offlineConfigTab.click();
		} catch (Exception e) {
			clickUsingJS(offlineConfigTab);
		}
	}

	@StepName("Click License & Tokens Tab")
	public void clickLicenseAndTokensTab() {
		waitUtils.scrollIntoView(licenseAndTokensTab);
		try {
			waitElementToBeClickable(licenseAndTokensTab);
			licenseAndTokensTab.click();
		} catch (Exception e) {
			clickUsingJS(licenseAndTokensTab);
		}
	}

	@StepName("Click Storage Tab")
	public void clickStorageTab() {
		waitUtils.scrollIntoView(storageTab);
		try {
			waitElementToBeClickable(storageTab);
			storageTab.click();
		} catch (Exception e) {
			clickUsingJS(storageTab);
		}
	}

	@StepName("Click Notifications Tab")
	public void clickNotificationsTab() {
		waitUtils.scrollIntoView(notificationsTab);
		try {
			waitElementToBeClickable(notificationsTab);
			notificationsTab.click();
		} catch (Exception e) {
			clickUsingJS(notificationsTab);
		}
	}

	@StepName("Click Activity Logs Tab")
	public void clickActivityLogsTab() {
		waitUtils.scrollIntoView(activityLogsTab);
		try {
			waitElementToBeClickable(activityLogsTab);
			activityLogsTab.click();
		} catch (Exception e) {
			clickUsingJS(activityLogsTab);
		}
	}

	@StepName("Click Assigned Modules Tab")
	public void clickAssignedModulesTab() {
		waitUtils.scrollIntoView(assignedModulesTab);
		try {
			waitElementToBeClickable(assignedModulesTab);
			assignedModulesTab.click();
		} catch (Exception e) {
			clickUsingJS(assignedModulesTab);
		}
	}

	@StepName("Click Licence Tab")
	public void clickLicenceTab() {
		waitUtils.scrollIntoView(licenceTab);
		try {
			waitElementToBeClickable(licenceTab);
			licenceTab.click();
		} catch (Exception e) {
			clickUsingJS(licenceTab);
		}
	}

	@StepName("Click Devices Tab")
	public void clickDevicesTab() {
		waitUtils.scrollIntoView(devicesTab);
		try {
			waitElementToBeClickable(devicesTab);
			devicesTab.click();
		} catch (Exception e) {
			clickUsingJS(devicesTab);
		}
	}

	@StepName("Click Billing Tab")
	public void clickBillingTab() {
		waitUtils.scrollIntoView(billingTab);
		try {
			waitElementToBeClickable(billingTab);
			billingTab.click();
		} catch (Exception e) {
			clickUsingJS(billingTab);
		}
	}

	public boolean isOverviewTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//h3[contains(text(),'Organisation Details') or contains(text(),'Organization Details') or contains(text(),'Client Onboarding Details')] | //p[text()='USERS']")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAdminUsersTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//button[contains(.,'Create Admin')] | //input[contains(@placeholder,'Search by name or email')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBrandingTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//h3[contains(text(),'Primary Color') or contains(text(),'Navigation Colors') or contains(text(),'Sidebar Style') or contains(text(),'Logo Size')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSecurityTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//h2[contains(text(),'Basic Security')] | //p[contains(text(),'Session Timeout')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isOfflineConfigTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//h3[contains(text(),'Offline Portal')] | //*[contains(text(),'Enable Offline Portal') or contains(text(),'Desktop App') or contains(text(),'Android App')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isLicenseAndTokensTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//button[normalize-space()='Licence' or normalize-space()='License'] | //button[normalize-space()='Devices']")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isStorageTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//h2[contains(text(),'Storage Usage Summary')] | //*[contains(text(),'Storage Usage')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isNotificationsTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//h2[contains(text(),'Notifications')] | //*[contains(text(),'Enable Notifications')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isActivityLogsTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//input[contains(@placeholder,'User, action, entity, IP')] | //button[contains(.,'All Actions') or contains(.,'Action Type')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAssignedModulesTabLoaded() {
		try {
			return waitUtils.waitUntil(d -> !d.findElements(By.xpath("//button[contains(.,'Assign Modules')] | //*[contains(text(),'No modules assigned yet')] | //*[contains(text(),'Simulation Type')]")).isEmpty());
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open Create Admin Modal")
	public void openCreateAdminModal() {
		waitUtils.scrollIntoView(createAdminButton);
		waitElementToBeClickable(createAdminButton);
		createAdminButton.click();
		waitUtils.waitForVisibility(By.xpath("//div[@role='dialog']//input[@placeholder='Full name']"));
	}

	@StepName("Close Create Admin Modal")
	public void closeCreateAdminModal() {
		WebElement cancelBtn = driver.findElement(By.xpath("//div[@role='dialog']//button[normalize-space()='Cancel']"));
		waitElementToBeClickable(cancelBtn);
		cancelBtn.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Open Assign Modules Modal")
	public void openAssignModulesModal() {
		waitUtils.scrollIntoView(assignModulesButton);
		waitElementToBeClickable(assignModulesButton);
		assignModulesButton.click();
		waitUtils.waitForVisibility(By.xpath("//div[@role='dialog']//button[normalize-space()='Cancel']"));
	}

	@StepName("Close Assign Modules Modal")
	public void closeAssignModulesModal() {
		WebElement cancelBtn = driver.findElement(By.xpath("//div[@role='dialog']//button[normalize-space()='Cancel']"));
		waitElementToBeClickable(cancelBtn);
		cancelBtn.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Check if Organizations Page is Loaded")
	public boolean isPageLoaded() {
		try {
			return driver.getCurrentUrl() != null && driver.getCurrentUrl().contains("/super-admin/organizations");
		} catch (Exception e) {
			return false;
		}
	}
}
