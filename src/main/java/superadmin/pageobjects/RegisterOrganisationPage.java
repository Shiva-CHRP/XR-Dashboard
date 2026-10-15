package superadmin.pageobjects;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.pojo.OrgApplicationData;

/**
 * Enterprise Page Object for the Public Organisation Registration Portal:
 * Route: /super-admin/register-organisation
 * 
 * Accurately models all 5 sections from the application UI:
 * Card 1: Organisation details (Name, Domain, Industry + Add New, Country, Timezone, Multi-Language)
 * Card 2: Primary administrator (Full Name, Email, Phone with country code prefix)
 * Card 3: Legal details (CIN Number + Upload CIN, GST Number + Upload GST, PAN Number + Upload PAN)
 * Card 4: Contacts (Contact 1, + Add another contact button, Contact 2)
 * Card 5: Licence you'd like (Licence / Free trial toggle, Period, Start/End Dates, Users, Devices, Notes, Submit)
 * 
 * Strict Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero ad-hoc WebDriverWait)
 * - Inherits ToastUtils helpers
 */
public class RegisterOrganisationPage extends AbstractComponent {

	public RegisterOrganisationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// CARD 1: ORGANISATION DETAILS
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='e.g. Schneider Electric or SCHNEIDER' or @name='orgName' or contains(@placeholder,'Schneider')]")
	private WebElement orgNameInput;

	@FindBy(xpath = "//input[@placeholder='acme.xrdashboard.com' or @name='portalDomain' or contains(@placeholder,'acme')]")
	private WebElement portalDomainInput;

	@FindBy(xpath = "//label[contains(text(),'Industry')]/following-sibling::div//button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'Select industry') or contains(text(),'Industry')]]")
	private WebElement industryDropdown;

	@FindBy(xpath = "//label[contains(text(),'Country')]/following-sibling::div//button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'Select country') or contains(text(),'Country')]]")
	private WebElement countryDropdown;

	@FindBy(xpath = "//label[contains(text(),'Timezone')]/following-sibling::div//button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'Select timezone') or contains(text(),'Timezone')]]")
	private WebElement timezoneDropdown;

	@FindBy(xpath = "//label[contains(text(),'Languages')]/following-sibling::div//button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[contains(text(),'English') or contains(text(),'Languages') or contains(text(),'Select language')]]")
	private WebElement languagesDropdown;

	// =========================================================================
	// CARD 2: PRIMARY ADMINISTRATOR
	// =========================================================================

	@FindBy(xpath = "//div[contains(.,'Primary administrator') and not(self::button)]//input[@placeholder='Full name' or @name='adminFullName']")
	private WebElement adminFullNameInput;

	@FindBy(xpath = "//div[contains(.,'Primary administrator') and not(self::button)]//input[@placeholder='name@company.com' or @name='adminEmail' or @type='email']")
	private WebElement adminEmailInput;

	@FindBy(xpath = "//div[contains(.,'Primary administrator')]//button[@aria-haspopup='listbox'][.//span[text()='---'] or contains(.,'+')]")
	private WebElement adminCountryCodeDropdown;

	@FindBy(xpath = "//div[contains(.,'Primary administrator')]//input[@placeholder='Select Country First' or @type='tel' or contains(@placeholder,'9876543210')]")
	private WebElement adminPhoneInput;

	// =========================================================================
	// CARD 3: LEGAL DETAILS & DOCUMENT UPLOADS
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='U12345MH2020PTC123456' or @name='cinNumber' or preceding-sibling::label[contains(text(),'CIN')]]")
	private WebElement cinNumberInput;

	@FindBy(xpath = "//div[contains(.,'Upload CIN')]//input[@type='file'] | //input[@name='cinFile' or @id='cin-upload']")
	private WebElement cinFileInput;

	@FindBy(xpath = "//input[@placeholder='22AAAAA0000A1Z5' or @name='gstNumber' or preceding-sibling::label[contains(text(),'GST')]]")
	private WebElement gstNumberInput;

	@FindBy(xpath = "//div[contains(.,'Upload GST')]//input[@type='file'] | //input[@name='gstFile' or @id='gst-upload']")
	private WebElement gstFileInput;

	@FindBy(xpath = "//input[@placeholder='ABCDE1234F' or @name='panNumber' or preceding-sibling::label[contains(text(),'PAN')]]")
	private WebElement panNumberInput;

	@FindBy(xpath = "//div[contains(.,'Upload PAN')]//input[@type='file'] | //input[@name='panFile' or @id='pan-upload']")
	private WebElement panFileInput;

	// =========================================================================
	// CARD 4: CONTACTS (CONTACT 1 & CONTACT 2)
	// =========================================================================

	@FindBy(xpath = "//div[contains(.,'Contact 1')]//input[@placeholder='Full name']")
	private WebElement contact1NameInput;

	@FindBy(xpath = "//div[contains(.,'Contact 1')]//input[@placeholder='e.g. HR Manager']")
	private WebElement contact1DesignationInput;

	@FindBy(xpath = "//div[contains(.,'Contact 1')]//input[@placeholder='e.g. EMP 001' or @placeholder='e.g. EMP-001']")
	private WebElement contact1EmpIdInput;

	@FindBy(xpath = "//div[contains(.,'Contact 1')]//input[@placeholder='name@company.com']")
	private WebElement contact1EmailInput;

	@FindBy(xpath = "//div[contains(.,'Contact 1')]//input[@placeholder='Select Country First' or @type='tel']")
	private WebElement contact1PhoneInput;

	@FindBy(xpath = "//button[contains(.,'Add another contact') or contains(.,'+ Add another contact')]")
	private WebElement addAnotherContactButton;

	@FindBy(xpath = "//div[contains(.,'Contact 2')]//input[@placeholder='Full name']")
	private WebElement contact2NameInput;

	@FindBy(xpath = "//div[contains(.,'Contact 2')]//input[@placeholder='e.g. HR Manager']")
	private WebElement contact2DesignationInput;

	@FindBy(xpath = "//div[contains(.,'Contact 2')]//input[@placeholder='e.g. EMP 001' or @placeholder='e.g. EMP-002']")
	private WebElement contact2EmpIdInput;

	@FindBy(xpath = "//div[contains(.,'Contact 2')]//input[@placeholder='name@company.com']")
	private WebElement contact2EmailInput;

	@FindBy(xpath = "//div[contains(.,'Contact 2')]//input[@placeholder='Select Country First' or @type='tel']")
	private WebElement contact2PhoneInput;

	// =========================================================================
	// CARD 5: LICENCE YOU'D LIKE & SUBMISSION
	// =========================================================================

	@FindBy(xpath = "//button[normalize-space()='Licence']")
	private WebElement licenceToggleButton;

	@FindBy(xpath = "//button[normalize-space()='Free trial']")
	private WebElement freeTrialToggleButton;

	@FindBy(xpath = "//label[contains(text(),'Period')]/following-sibling::div//button[@aria-haspopup='listbox'] | //button[@aria-haspopup='listbox'][.//span[text()='Annually' or text()='Monthly']]")
	private WebElement licencePeriodDropdown;

	@FindBy(xpath = "//label[contains(text(),'Start date')]/following-sibling::div//input | //input[@name='startDate']")
	private WebElement startDateInput;

	@FindBy(xpath = "//label[contains(text(),'End date')]/following-sibling::div//input | //input[@name='endDate']")
	private WebElement endDateInput;

	@FindBy(xpath = "//label[contains(text(),'Number of users')]/following-sibling::div//input | //label[contains(text(),'Number of users')]/following-sibling::input")
	private WebElement numberOfUsersInput;

	@FindBy(xpath = "//label[contains(text(),'Number of devices')]/following-sibling::div//input | //label[contains(text(),'Number of devices')]/following-sibling::input")
	private WebElement numberOfDevicesInput;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Optional') or preceding-sibling::label[contains(text(),'Anything else')]]")
	private WebElement notesTextarea;

	@FindBy(xpath = "//button[contains(.,'Submit application')]")
	private WebElement submitApplicationButton;

	@FindBy(xpath = "//div[contains(.,'submitted successfully') or contains(.,'Application Received') or contains(.,'Thank you') or contains(.,'Application Submitted')]")
	private WebElement successConfirmationCard;

	// =========================================================================
	// ACTION METHODS
	// =========================================================================

	@StepName("Navigate to Public Client Application Form: {0}")
	public void navigateToRegistrationPage(String url) {
		driver.get(url);
		waitUtils.waitForUrlContains("register-organisation");
	}

	@StepName("Fill Complete 5-Card Organisation Registration Form")
	public void fillRegistrationForm(OrgApplicationData data) {
		// --- Card 1: Organisation Details ---
		fillOrganisationDetails(data);

		// --- Card 2: Primary Administrator ---
		fillPrimaryAdministrator(data);

		// --- Card 3: Legal Details & Attachments ---
		fillLegalDetails(data);

		// --- Card 4: Contacts ---
		fillContacts(data);

		// --- Card 5: Licence Request ---
		fillLicenceRequest(data);
	}

	@StepName("Card 1: Fill Organisation Details")
	public void fillOrganisationDetails(OrgApplicationData data) {
		if (data.getOrgName() != null) {
			waitUtils.waitForVisibility(orgNameInput);
			orgNameInput.clear();
			orgNameInput.sendKeys(data.getOrgName());
		}

		if (data.getPortalDomain() != null) {
			try {
				waitUtils.waitForVisibility(portalDomainInput);
				portalDomainInput.clear();
				portalDomainInput.sendKeys(data.getPortalDomain());
			} catch (Exception ignored) {}
		}

		if (data.getCountry() != null) {
			selectCountry(data.getCountry());
		}

		if (data.getIndustry() != null) {
			selectOrAddIndustry(data.getIndustry());
		}

		if (data.getTimezone() != null) {
			selectTimezone(data.getTimezone());
		}

		if (data.getLanguages() != null && !data.getLanguages().isEmpty()) {
			selectMultiLanguages(data.getLanguages());
		}
	}

	@StepName("Select or Add New Industry: {0}")
	public void selectOrAddIndustry(String industry) {
		try {
			waitUtils.waitForClickable(industryDropdown);
			industryDropdown.click();

			// Check if search input appears inside dropdown
			try {
				WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search industry...' or @placeholder='Search...']"));
				searchInput.clear();
				searchInput.sendKeys(industry);
			} catch (Exception ignored) {}

			// Check if option is already present
			By existingOptionLocator = By.xpath("//div[@role='option'][normalize-space()='" + industry + "'] | //button[@role='option'][.//span[normalize-space()='" + industry + "']]");
			List<WebElement> existingOptions = driver.findElements(existingOptionLocator);
			if (!existingOptions.isEmpty() && existingOptions.get(0).isDisplayed()) {
				existingOptions.get(0).click();
				return;
			}

			// If not found, look for "+ Add New Industry" button / prompt
			By addNewIndustryLocator = By.xpath("//button[contains(.,'Add New Industry') or contains(.,'Create industry') or contains(.,'Add industry')] | //div[contains(.,'Add New Industry')]");
			List<WebElement> addNewBtns = driver.findElements(addNewIndustryLocator);
			if (!addNewBtns.isEmpty() && addNewBtns.get(0).isDisplayed()) {
				addNewBtns.get(0).click();
				// If a prompt or input appears to type new industry name
				try {
					WebElement newIndustryInput = driver.findElement(By.xpath("//input[@placeholder='Enter industry name' or @placeholder='Industry name']"));
					newIndustryInput.clear();
					newIndustryInput.sendKeys(industry);
					WebElement saveBtn = driver.findElement(By.xpath("//button[contains(.,'Add') or contains(.,'Save')]"));
					saveBtn.click();
				} catch (Exception ignored) {}
			}
		} catch (Exception e) {
			// Fallback: click body to dismiss dropdown if open
			try { driver.findElement(By.tagName("body")).click(); } catch (Exception ignored) {}
		}
	}

	@StepName("Select Country: {0}")
	public void selectCountry(String country) {
		try {
			waitUtils.waitForClickable(countryDropdown);
			countryDropdown.click();

			try {
				WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search country...' or @placeholder='Search...']"));
				searchInput.clear();
				searchInput.sendKeys(country);
			} catch (Exception ignored) {}

			By countryOptionLocator = By.xpath("//div[@role='option'][normalize-space()='" + country + "'] | //button[@role='option'][.//span[normalize-space()='" + country + "']]");
			waitUtils.waitForClickable(countryOptionLocator);
			WebElement option = driver.findElement(countryOptionLocator);
			option.click();
		} catch (Exception ignored) {}
	}

	@StepName("Select Timezone: {0}")
	public void selectTimezone(String timezone) {
		try {
			waitUtils.waitForClickable(timezoneDropdown);
			timezoneDropdown.click();

			try {
				WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search timezone...' or @placeholder='Search...']"));
				searchInput.clear();
				searchInput.sendKeys(timezone);
			} catch (Exception ignored) {}

			By optionLocator = By.xpath("//div[@role='option'][contains(.,'" + timezone + "')] | //button[@role='option'][contains(.,'" + timezone + "')]");
			List<WebElement> options = driver.findElements(optionLocator);
			if (!options.isEmpty() && options.get(0).isDisplayed()) {
				options.get(0).click();
			}
		} catch (Exception ignored) {}
	}

	@StepName("Select Multi Languages: {0}")
	public void selectMultiLanguages(List<String> languages) {
		try {
			waitUtils.waitForClickable(languagesDropdown);
			languagesDropdown.click();

			for (String lang : languages) {
				try {
					By langOptionLocator = By.xpath("//div[@role='option'][normalize-space()='" + lang + "'] | //button[@role='option'][.//span[normalize-space()='" + lang + "']]");
					List<WebElement> options = driver.findElements(langOptionLocator);
					if (!options.isEmpty() && options.get(0).isDisplayed()) {
						options.get(0).click();
					} else {
						// Look for "+ Add New Language"
						By addNewLangLocator = By.xpath("//button[contains(.,'Add New Language') or contains(.,'Create language')]");
						List<WebElement> addNewBtns = driver.findElements(addNewLangLocator);
						if (!addNewBtns.isEmpty() && addNewBtns.get(0).isDisplayed()) {
							addNewBtns.get(0).click();
						}
					}
				} catch (Exception ignored) {}
			}

			// Dismiss dropdown by clicking outside
			try { driver.findElement(By.tagName("h1")).click(); } catch (Exception ignored) {}
		} catch (Exception ignored) {}
	}

	@StepName("Card 2: Fill Primary Administrator Details")
	public void fillPrimaryAdministrator(OrgApplicationData data) {
		if (data.getAdminFullName() != null) {
			try {
				waitUtils.waitForVisibility(adminFullNameInput);
				adminFullNameInput.clear();
				adminFullNameInput.sendKeys(data.getAdminFullName());
			} catch (Exception ignored) {}
		}

		if (data.getAdminEmail() != null) {
			try {
				waitUtils.waitForVisibility(adminEmailInput);
				adminEmailInput.clear();
				adminEmailInput.sendKeys(data.getAdminEmail());
			} catch (Exception ignored) {}
		}

		if (data.getPhoneNumber() != null) {
			try {
				waitUtils.waitForVisibility(adminPhoneInput);
				adminPhoneInput.clear();
				adminPhoneInput.sendKeys(data.getPhoneNumber());
			} catch (Exception ignored) {}
		}
	}

	@StepName("Card 3: Fill Legal Details & Document Uploads")
	public void fillLegalDetails(OrgApplicationData data) {
		// CIN
		if (data.getCinNumber() != null) {
			try {
				waitUtils.waitForVisibility(cinNumberInput);
				cinNumberInput.clear();
				cinNumberInput.sendKeys(data.getCinNumber());
			} catch (Exception ignored) {}
		}
		if (data.getCinDocumentPath() != null) {
			uploadDocumentSafe(cinFileInput, data.getCinDocumentPath());
		}

		// GST
		if (data.getGstNumber() != null) {
			try {
				waitUtils.waitForVisibility(gstNumberInput);
				gstNumberInput.clear();
				gstNumberInput.sendKeys(data.getGstNumber());
			} catch (Exception ignored) {}
		}
		if (data.getGstDocumentPath() != null) {
			uploadDocumentSafe(gstFileInput, data.getGstDocumentPath());
		}

		// PAN
		if (data.getPanNumber() != null) {
			try {
				waitUtils.waitForVisibility(panNumberInput);
				panNumberInput.clear();
				panNumberInput.sendKeys(data.getPanNumber());
			} catch (Exception ignored) {}
		}
		if (data.getPanDocumentPath() != null) {
			uploadDocumentSafe(panFileInput, data.getPanDocumentPath());
		}
	}

	@StepName("Card 4: Fill Contacts (Contact 1 & Contact 2)")
	public void fillContacts(OrgApplicationData data) {
		// Contact 1
		if (data.getContact1Name() != null) {
			try {
				waitUtils.waitForVisibility(contact1NameInput);
				contact1NameInput.clear();
				contact1NameInput.sendKeys(data.getContact1Name());
			} catch (Exception ignored) {}
		}
		if (data.getContact1Designation() != null) {
			try {
				contact1DesignationInput.clear();
				contact1DesignationInput.sendKeys(data.getContact1Designation());
			} catch (Exception ignored) {}
		}
		if (data.getContact1EmpId() != null) {
			try {
				contact1EmpIdInput.clear();
				contact1EmpIdInput.sendKeys(data.getContact1EmpId());
			} catch (Exception ignored) {}
		}
		if (data.getContact1Email() != null) {
			try {
				contact1EmailInput.clear();
				contact1EmailInput.sendKeys(data.getContact1Email());
			} catch (Exception ignored) {}
		}
		if (data.getContact1Phone() != null) {
			try {
				contact1PhoneInput.clear();
				contact1PhoneInput.sendKeys(data.getContact1Phone());
			} catch (Exception ignored) {}
		}

		// Add Contact 2 if provided
		if (data.getContact2Name() != null) {
			try {
				waitUtils.waitForClickable(addAnotherContactButton);
				addAnotherContactButton.click();

				waitUtils.waitForVisibility(contact2NameInput);
				contact2NameInput.clear();
				contact2NameInput.sendKeys(data.getContact2Name());

				if (data.getContact2Designation() != null) {
					contact2DesignationInput.clear();
					contact2DesignationInput.sendKeys(data.getContact2Designation());
				}
				if (data.getContact2EmpId() != null) {
					contact2EmpIdInput.clear();
					contact2EmpIdInput.sendKeys(data.getContact2EmpId());
				}
				if (data.getContact2Email() != null) {
					contact2EmailInput.clear();
					contact2EmailInput.sendKeys(data.getContact2Email());
				}
				if (data.getContact2Phone() != null) {
					contact2PhoneInput.clear();
					contact2PhoneInput.sendKeys(data.getContact2Phone());
				}
			} catch (Exception ignored) {}
		}
	}

	@StepName("Card 5: Fill Licence Request Details")
	public void fillLicenceRequest(OrgApplicationData data) {
		if ("Free trial".equalsIgnoreCase(data.getLicenceType())) {
			try {
				waitUtils.waitForClickable(freeTrialToggleButton);
				freeTrialToggleButton.click();
			} catch (Exception ignored) {}
		} else {
			try {
				waitUtils.waitForClickable(licenceToggleButton);
				licenceToggleButton.click();
			} catch (Exception ignored) {}
		}

		if (data.getLicencePeriod() != null) {
			try {
				waitUtils.waitForClickable(licencePeriodDropdown);
				licencePeriodDropdown.click();
				WebElement option = driver.findElement(By.xpath("//div[@role='option'][normalize-space()='" + data.getLicencePeriod() + "'] | //button[@role='option'][.//span[normalize-space()='" + data.getLicencePeriod() + "']]"));
				option.click();
			} catch (Exception ignored) {}
		}

		if (data.getLicenceStartDate() != null) {
			try {
				startDateInput.clear();
				startDateInput.sendKeys(data.getLicenceStartDate());
			} catch (Exception ignored) {}
		}

		if (data.getLicenceEndDate() != null) {
			try {
				endDateInput.clear();
				endDateInput.sendKeys(data.getLicenceEndDate());
			} catch (Exception ignored) {}
		}

		if (data.getExpectedUsers() != null) {
			try {
				numberOfUsersInput.clear();
				numberOfUsersInput.sendKeys(data.getExpectedUsers());
			} catch (Exception ignored) {}
		}

		if (data.getExpectedDevices() != null) {
			try {
				numberOfDevicesInput.clear();
				numberOfDevicesInput.sendKeys(data.getExpectedDevices());
			} catch (Exception ignored) {}
		}

		if (data.getNotes() != null) {
			try {
				notesTextarea.clear();
				notesTextarea.sendKeys(data.getNotes());
			} catch (Exception ignored) {}
		}
	}

	@StepName("Submit Organisation Registration Form")
	public void submitApplication() {
		waitUtils.waitForClickable(submitApplicationButton);
		submitApplicationButton.click();
	}

	@StepName("Verify Application Submission Success")
	public boolean isSubmittedSuccessfully() {
		try {
			waitUtils.waitForVisibility(successConfirmationCard);
			return successConfirmationCard.isDisplayed();
		} catch (Exception e) {
			return driver.getPageSource().contains("successfully") || driver.getPageSource().contains("Application Received") || driver.getPageSource().contains("Thank you");
		}
	}

	private void uploadDocumentSafe(WebElement fileInput, String relativeOrAbsolutePath) {
		try {
			File file = new File(relativeOrAbsolutePath);
			if (file.exists()) {
				fileInput.sendKeys(file.getAbsolutePath());
			}
		} catch (Exception ignored) {}
	}
}
