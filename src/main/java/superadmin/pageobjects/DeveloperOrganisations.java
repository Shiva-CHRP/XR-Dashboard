package superadmin.pageobjects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * DeveloperOrganisations Page Object
 * 
 * Accurately models the VR Developer Organisations list, Organisation Detail view,
 * and Module Upload Wizard matching src/pages/Developer/Organisations/ (index.jsx, OrgDetail.jsx, Upload.jsx).
 * 
 * Strictly utilizes waitUtils and AbstractComponent wait wrappers.
 */
public class DeveloperOrganisations extends AbstractComponent {

	public DeveloperOrganisations(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Header
	// =========================================================================

	@FindBy(xpath = "//a[@href='/developer/organisations']")
	private WebElement organisationsMenuLink;

	@FindBy(xpath = "//h1[text()='Organisations']")
	private WebElement pageTitle;

	@FindBy(xpath = "//p[contains(text(),'Select an organisation to upload a VR module')]")
	private WebElement pageSubtitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') and not(ancestor::div[contains(@class,'hidden')])]")
	private WebElement refreshButton;

	// =========================================================================
	// 2. KPI Cards
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Total Orgs'] or .//span[text()='Total Orgs']]")
	private WebElement totalOrgsCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Active'] or .//span[text()='Active']]")
	private WebElement activeOrgsCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Inactive'] or .//span[text()='Inactive']]")
	private WebElement inactiveOrgsCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Modules Submitted'] or .//span[text()='Modules Submitted']]")
	private WebElement modulesSubmittedCard;

	// =========================================================================
	// 3. Filter Bar & View Toggle
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='Search organisations…' or contains(@placeholder,'Search organisations')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(.,'Clear') or contains(.,'Clear all')]")
	private WebElement clearFiltersButton;

	@FindBy(xpath = "//button[text()='All' and (contains(@class,'h-9') or contains(@class,'text-xs'))]")
	private WebElement statusAllButton;

	@FindBy(xpath = "//button[text()='Active' and (contains(@class,'h-9') or contains(@class,'text-xs'))]")
	private WebElement statusActiveButton;

	@FindBy(xpath = "//button[text()='Inactive' and (contains(@class,'h-9') or contains(@class,'text-xs'))]")
	private WebElement statusInactiveButton;

	@FindBy(xpath = "//button[@title='List view' or .//*[local-name()='svg' and contains(@class,'lucide-list')]]")
	private WebElement listViewButton;

	@FindBy(xpath = "//button[@title='Grid view' or .//*[local-name()='svg' and contains(@class,'lucide-layout-grid')]]")
	private WebElement gridViewButton;

	// =========================================================================
	// 4. Organisation Items (Grid & List)
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'rounded-2xl') and contains(@class,'bg-titan-card')]")
	private List<WebElement> organisationCards;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> organisationTableRows;

	// =========================================================================
	// 5. Organisation Detail View (/developer/organisations/:orgId)
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Back to Organisations')]")
	private WebElement backToOrganisationsButton;

	@FindBy(xpath = "//h1[contains(text(),'Organisation Overview')]")
	private WebElement orgOverviewTitle;

	@FindBy(xpath = "//div[contains(@class,'rounded-xl')]//p[contains(@class,'text-2xl') and contains(@class,'font-bold')]")
	private WebElement orgDetailNameHeading;

	@FindBy(xpath = "//button[contains(.,'Upload New Module')]")
	private WebElement uploadNewModuleButton;

	@FindBy(xpath = "//ul//li[contains(@class,'flex flex-col')]")
	private List<WebElement> orgModuleItems;

	// =========================================================================
	// 6. Version History Modal
	// =========================================================================

	@FindBy(xpath = "//h2[contains(text(),'Version History')]")
	private WebElement versionHistoryModalTitle;

	@FindBy(xpath = "//button[text()='Close' and ancestor::div[contains(@role,'dialog')]] | //button[contains(@class,'lucide-x')]")
	private WebElement closeVersionHistoryButton;

	// =========================================================================
	// 7. Module Upload Wizard (/developer/organisations/:orgId/upload)
	// =========================================================================

	@FindBy(xpath = "//h1[contains(text(),'Upload Module')]")
	private WebElement uploadPageTitle;

	@FindBy(xpath = "//input[@placeholder='e.g. Fire Safety VR' or contains(@placeholder,'Safety') or contains(@placeholder,'module') or @name='moduleName']")
	private WebElement uploadModuleNameInput;

	@FindBy(xpath = "//div[./label[contains(text(),'Module Type')]]//select | //select[@name='moduleType' or .//option[contains(text(),'Safety Training') or contains(text(),'Select module type')]]")
	private WebElement uploadModuleTypeSelect;

	@FindBy(xpath = "//div[./label[contains(text(),'Simulation Type')]]//select | //select[@name='simulationType' or .//option[contains(text(),'Guided/Unguided') or contains(text(),'Select simulation type')]]")
	private WebElement uploadSimulationTypeSelect;

	@FindBy(xpath = "//div[./label[contains(text(),'Setup')]]//select | //select[@name='setup' or .//option[contains(text(),'Select setup')]]")
	private WebElement uploadSetupSelect;

	@FindBy(xpath = "//input[@name='version' or @placeholder='1.0.0']")
	private WebElement uploadVersionInput;

	@FindBy(xpath = "//button[contains(.,'Select platforms…') or contains(.,'platforms selected')]")
	private WebElement uploadPlatformDropdownButton;

	@FindBy(xpath = "//textarea[@name='description' or contains(@placeholder,'description') or contains(@placeholder,'Brief description')]")
	private WebElement uploadDescriptionTextarea;

	@FindBy(xpath = "//button[normalize-space()='Next' or (contains(.,'Next') and not(contains(.,'Version')))]")
	private WebElement uploadNextStepButton;

	@FindBy(xpath = "//button[normalize-space()='Back']")
	private WebElement uploadBackStepButton;

	@FindBy(xpath = "//button[normalize-space()='Cancel' and not(ancestor::div[contains(@role,'dialog')]) and not(ancestor::div[contains(@class,'fixed')])]")
	private WebElement uploadCancelWizardButton;

	@FindBy(xpath = "//div[./label[contains(text(),'File Type')]]//select | //select[.//option[contains(text(),'APK') or @value='apk']]")
	private WebElement uploadFileTypeSelect;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadFileInput;

	@FindBy(xpath = "//div[contains(@class,'border-titan-success')] | //*[contains(text(),'File attached & ready for submission')]")
	private WebElement fileAttachedIndicator;

	@FindBy(xpath = "//button[normalize-space()='Submit for Review' or normalize-space()='Submit Module' or contains(.,'Submit for Review') or contains(.,'Submit Module')]")
	private WebElement uploadSubmitModuleButton;

	// Leave-page confirmation modal
	@FindBy(xpath = "//div[@role='dialog']//h2[contains(text(),'Leave this page?') or contains(text(),'Leave')] | //h2[contains(text(),'Leave this page?')]")
	private WebElement leavePageModalTitle;

	@FindBy(xpath = "//button[normalize-space()='Stay on Page']")
	private WebElement stayOnPageButton;

	@FindBy(xpath = "//button[normalize-space()='Leave Page']")
	private WebElement leavePageButton;

	// In-progress upload modal cancel
	@FindBy(xpath = "//div[@role='dialog' or contains(@class,'fixed')]//button[normalize-space()='Cancel' and not(contains(.,'ghost'))]")
	private WebElement cancelUploadProgressButton;

	// =========================================================================
	// ACTION METHODS: Navigation & Page State
	// =========================================================================

	@StepName("Click on Developer Organisations in sidebar")
	public void clickDeveloperOrganisations() {
		waitUtils.waitForClickable(organisationsMenuLink);
		try {
			organisationsMenuLink.click();
		} catch (Exception e) {
			clickUsingJS(organisationsMenuLink);
		}
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.visibilityOf(pageTitle),
				ExpectedConditions.urlContains("/developer/organisations")
		));
	}

	@StepName("Verify Developer Organisations page is loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		waitForTableOrCardsToLoad();
	}

	// =========================================================================
	// ACTION METHODS: Filtering & Search
	// =========================================================================

	@StepName("Search organisations with query: {0}")
	public void searchOrganisation(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
		waitForTableOrCardsToLoad();
	}

	@StepName("Filter by status: {0}")
	public void filterByStatus(String status) {
		String lower = status.toLowerCase();
		if (lower.equals("active")) {
			waitUtils.waitForClickable(statusActiveButton);
			statusActiveButton.click();
		} else if (lower.equals("inactive")) {
			waitUtils.waitForClickable(statusInactiveButton);
			statusInactiveButton.click();
		} else {
			waitUtils.waitForClickable(statusAllButton);
			statusAllButton.click();
		}
		waitForTableOrCardsToLoad();
	}

	@StepName("Switch to Grid View")
	public void clickGridView() {
		waitUtils.waitForClickable(gridViewButton);
		gridViewButton.click();
	}

	@StepName("Switch to List View")
	public void clickListView() {
		waitUtils.waitForClickable(listViewButton);
		listViewButton.click();
	}

	@StepName("Toggle View Mode: {0}")
	public void toggleViewMode(String mode) {
		if ("list".equalsIgnoreCase(mode)) {
			clickListView();
		} else {
			clickGridView();
		}
	}

	@StepName("Clear All Filters")
	public void clearFilters() {
		try {
			if (clearFiltersButton.isDisplayed()) {
				waitUtils.waitForClickable(clearFiltersButton);
				clearFiltersButton.click();
				waitForTableOrCardsToLoad();
				return;
			}
		} catch (Exception ignored) {}
		try {
			searchInput.clear();
			waitForTableOrCardsToLoad();
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: Organisation Selection & Detail Navigation
	// =========================================================================

	@StepName("Get displayed Organisation names")
	public List<String> getDisplayedOrganisationNames() {
		List<String> names = new ArrayList<>();
		if (isGridViewActive()) {
			for (WebElement card : organisationCards) {
				try {
					WebElement nameEl = card.findElement(By.xpath(".//p[contains(@class,'font-bold') and contains(@class,'text-white')]"));
					names.add(nameEl.getText().trim());
				} catch (Exception ignored) {}
			}
		} else {
			for (WebElement row : organisationTableRows) {
				try {
					WebElement nameEl = row.findElement(By.xpath(".//td[1]//p[contains(@class,'font-medium')]"));
					names.add(nameEl.getText().trim());
				} catch (Exception ignored) {}
			}
		}
		return names;
	}

	@StepName("Check if organisation with name '{0}' exists")
	public boolean isOrganisationPresent(String orgName) {
		return getDisplayedOrganisationNames().stream()
				.anyMatch(name -> name.equalsIgnoreCase(orgName) || name.contains(orgName));
	}

	@StepName("Click 'Manage Modules' for organisation: {0}")
	public void clickManageModules(String orgName) {
		if (isGridViewActive()) {
			for (WebElement card : organisationCards) {
				try {
					WebElement nameEl = card.findElement(By.xpath(".//p[contains(@class,'font-bold') and contains(@class,'text-white')]"));
					if (nameEl.getText().toLowerCase().contains(orgName.toLowerCase())) {
						WebElement manageBtn = card.findElement(By.xpath(".//button[contains(.,'Manage Modules')]"));
						waitUtils.waitForClickable(manageBtn);
						manageBtn.click();
						waitUtils.waitForVisibility(orgOverviewTitle);
						return;
					}
				} catch (Exception ignored) {}
			}
		} else {
			for (WebElement row : organisationTableRows) {
				try {
					WebElement nameEl = row.findElement(By.xpath(".//td[1]//p[contains(@class,'font-medium')]"));
					if (nameEl.getText().toLowerCase().contains(orgName.toLowerCase())) {
						WebElement manageBtn = row.findElement(By.xpath(".//button[contains(.,'Manage Modules')]"));
						waitUtils.waitForClickable(manageBtn);
						manageBtn.click();
						waitUtils.waitForVisibility(orgOverviewTitle);
						return;
					}
				} catch (Exception ignored) {}
			}
		}
		throw new RuntimeException("Organisation not found or Manage Modules button disabled: " + orgName);
	}

	// =========================================================================
	// ACTION METHODS: Organisation Overview Detail Page
	// =========================================================================

	@StepName("Click 'Back to Organisations'")
	public void clickBackToOrganisations() {
		waitUtils.waitForClickable(backToOrganisationsButton);
		backToOrganisationsButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Get Organisation Name on Overview page")
	public String getOrgDetailName() {
		waitUtils.waitForVisibility(orgDetailNameHeading);
		return orgDetailNameHeading.getText().trim();
	}

	@StepName("Click 'Upload New Module' button on Overview page")
	public void clickUploadNewModule() {
		waitUtils.waitForClickable(uploadNewModuleButton);
		uploadNewModuleButton.click();
		waitUtils.waitForUrlContains("/upload");
	}

	@StepName("Cancel Module Upload Wizard and return to Org Detail")
	public void cancelModuleUploadWizard() {
		try {
			By backBtn = By.xpath("//button[contains(.,'Back to Organisation Overview')]");
			waitUtils.waitForClickable(backBtn);
			driver.findElement(backBtn).click();
			// If confirmation modal appears
			By leaveBtn = By.xpath("//div[@role='dialog']//button[contains(.,'Leave Page')]");
			List<WebElement> leaveBtns = driver.findElements(leaveBtn);
			if (!leaveBtns.isEmpty() && leaveBtns.get(0).isDisplayed()) {
				leaveBtns.get(0).click();
			}
		} catch (Exception ignored) {}
		waitUtils.waitForVisibility(orgOverviewTitle);
	}

	@StepName("Open First Organisation Manage/Overview if available")
	public boolean openFirstOrganisationDetail() {
		try {
			waitForTableOrCardsToLoad();
			if (isGridViewActive()) {
				By cardBy = By.xpath("//div[contains(@class,'grid')]//div[contains(@class,'rounded-2xl') and contains(@class,'bg-titan-card')]");
				List<WebElement> cards = driver.findElements(cardBy);
				if (!cards.isEmpty()) {
					WebElement manageBtn = cards.get(0).findElement(By.xpath(".//button[contains(.,'Manage Modules')]"));
					waitUtils.waitForClickable(manageBtn);
					clickUsingJS(manageBtn);
					waitUtils.waitForVisibility(orgOverviewTitle);
					return true;
				}
			} else {
				By rowBy = By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]");
				List<WebElement> rows = driver.findElements(rowBy);
				if (!rows.isEmpty()) {
					WebElement manageBtn = rows.get(0).findElement(By.xpath(".//button[contains(.,'Manage Modules')]"));
					waitUtils.waitForClickable(manageBtn);
					clickUsingJS(manageBtn);
					waitUtils.waitForVisibility(orgOverviewTitle);
					return true;
				}
			}
		} catch (Exception e) {
			// No orgs
		}
		return false;
	}

	@StepName("Open First Organisation Manage Modules (Alias)")
	public boolean openFirstOrganisationManageModules() {
		return openFirstOrganisationDetail();
	}

	@StepName("Upload Module Binary")
	public void uploadModuleBinary(String filePath) {
		clickUploadNewModule();
		uploadBuildFile(filePath, "APK");
	}

	@StepName("Enter Module Metadata")
	public void enterModuleMetadata(String name, String version, String desc, String rationale, String simType, String duration) {
		fillModuleDetailsStep(name, "Safety", simType, "Default", version, desc, java.util.Collections.emptyList());
	}

	@StepName("Submit Module Upload (Alias)")
	public void submitModuleUpload() {
		submitModule();
	}

	@StepName("Check if Organisation Overview is Loaded")
	public boolean isOrgOverviewLoaded() {
		try {
			waitUtils.waitForVisibility(orgOverviewTitle);
			return orgOverviewTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Module Upload Form is Loaded")
	public boolean isUploadFormLoaded() {
		try {
			waitUtils.waitForUrlContains("/upload");
			waitUtils.waitForVisibility(uploadPageTitle);
			return driver.getCurrentUrl().contains("/upload") && uploadPageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open Version History modal for module: {0}")
	public void openModuleVersionHistory(String moduleName) {
		By historyBtnLocator = By.xpath("//li[.//p[contains(text(),'" + moduleName + "')]]//button[contains(.,'Version History')]");
		waitUtils.waitForClickable(historyBtnLocator);
		driver.findElement(historyBtnLocator).click();
		waitUtils.waitForVisibility(versionHistoryModalTitle);
	}

	@StepName("Close Version History modal")
	public void closeVersionHistoryModal() {
		waitUtils.waitForClickable(closeVersionHistoryButton);
		closeVersionHistoryButton.click();
		waitUtils.waitForInvisibility(versionHistoryModalTitle);
	}

	@StepName("Click 'New Version' for module: {0}")
	public void clickUploadNewVersion(String moduleName) {
		By newVersionBtnLocator = By.xpath("//li[.//p[contains(text(),'" + moduleName + "')]]//button[contains(.,'New Version')]");
		waitUtils.waitForClickable(newVersionBtnLocator);
		driver.findElement(newVersionBtnLocator).click();
		waitUtils.waitForUrlContains("/upload-version/");
	}

	// =========================================================================
	// ACTION METHODS: Module Upload Wizard
	// =========================================================================

	@StepName("Check if Module Details Step is active")
	public boolean isModuleDetailsStepActive() {
		try {
			waitUtils.waitForVisibility(uploadModuleNameInput);
			return uploadModuleNameInput.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Enter Module Details (Step 1): name={0}, type={1}, version={4}")
	public void enterModuleDetails(String name, String type, String simType, String setup, String version, String desc, List<String> platforms) {
		waitUtils.waitForVisibility(uploadModuleNameInput);
		uploadModuleNameInput.clear();
		uploadModuleNameInput.sendKeys(name);

		if (type != null && !type.isEmpty()) {
			try {
				selectByVisibleText(uploadModuleTypeSelect, type);
			} catch (Exception e) {
				try {
					selectByValue(uploadModuleTypeSelect, type);
				} catch (Exception ignored) {}
			}
		}
		if (simType != null && !simType.isEmpty()) {
			try {
				selectByVisibleText(uploadSimulationTypeSelect, simType);
			} catch (Exception e) {
				try {
					selectByValue(uploadSimulationTypeSelect, simType);
				} catch (Exception ignored) {}
			}
		}
		if (setup != null && !setup.isEmpty()) {
			try { selectByVisibleText(uploadSetupSelect, setup); } catch (Exception ignored) {}
		}
		if (version != null && !version.isEmpty() && uploadVersionInput.isEnabled()) {
			try {
				uploadVersionInput.clear();
				uploadVersionInput.sendKeys(version);
			} catch (Exception ignored) {}
		}
		if (desc != null && !desc.isEmpty()) {
			uploadDescriptionTextarea.clear();
			uploadDescriptionTextarea.sendKeys(desc);
		}

		// Select platforms
		if (platforms != null && !platforms.isEmpty()) {
			waitUtils.waitForClickable(uploadPlatformDropdownButton);
			uploadPlatformDropdownButton.click();
			for (String platform : platforms) {
				try {
					By platformCheckbox = By.xpath("//label[.//span[contains(text(),'" + platform + "')]]//input");
					WebElement cb = driver.findElement(platformCheckbox);
					if (!cb.isSelected()) cb.click();
				} catch (Exception ignored) {}
			}
			uploadPlatformDropdownButton.click(); // close dropdown
		}
	}

	@StepName("Click Next button in Upload Wizard")
	public void clickNextStep() {
		waitUtils.waitForClickable(uploadNextStepButton);
		uploadNextStepButton.click();
	}

	@StepName("Click Back button in Upload Wizard")
	public void clickBackStep() {
		waitUtils.waitForClickable(uploadBackStepButton);
		uploadBackStepButton.click();
	}

	@StepName("Click Cancel button in Upload Wizard")
	public void clickCancelUploadWizard() {
		waitUtils.waitForClickable(uploadCancelWizardButton);
		uploadCancelWizardButton.click();
		waitUtils.waitForVisibility(leavePageModalTitle);
	}

	@StepName("Check if 'Leave this page?' modal is displayed")
	public boolean isLeavePageModalDisplayed() {
		try {
			waitUtils.waitForVisibility(leavePageModalTitle);
			return leavePageModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click 'Stay on Page' in Cancel modal")
	public void clickStayOnPage() {
		waitUtils.waitForClickable(stayOnPageButton);
		stayOnPageButton.click();
		waitUtils.waitForInvisibility(leavePageModalTitle);
	}

	@StepName("Click 'Leave Page' in Cancel modal")
	public void clickLeavePage() {
		waitUtils.waitForClickable(leavePageButton);
		leavePageButton.click();
		waitUtils.waitForVisibility(orgOverviewTitle);
	}

	@StepName("Check if Build Upload Step is active")
	public boolean isBuildUploadStepActive() {
		try {
			waitUtils.waitForVisibility(uploadFileTypeSelect);
			return uploadFileTypeSelect.isDisplayed();
		} catch (Exception e) {
			try {
				waitUtils.waitForVisibility(uploadFileInput);
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}

	@StepName("Select File Type in Build Upload Step: {0}")
	public void selectFileType(String fileType) {
		waitUtils.waitForVisibility(uploadFileTypeSelect);
		try {
			selectByVisibleText(uploadFileTypeSelect, fileType);
		} catch (Exception e) {
			try {
				selectByValue(uploadFileTypeSelect, fileType.toLowerCase());
			} catch (Exception ex) {
				// Try locating option containing the text
				By optionLocator = By.xpath(".//option[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + fileType.toLowerCase() + "')]");
				WebElement opt = uploadFileTypeSelect.findElement(optionLocator);
				opt.click();
			}
		}
	}

	@StepName("Upload Build File: {0}")
	public void uploadBuildFileOnly(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new RuntimeException("Build file not found at path: " + filePath);
		}
		uploadFileInput.sendKeys(file.getAbsolutePath());

		// Wait for upload indicator / ready state
		try {
			waitUtils.waitForVisibility(fileAttachedIndicator);
		} catch (Exception e) {
			// Fallback: wait briefly for client-side SHA256 computation to complete
			try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
		}
	}

	@StepName("Check if Review & Submit Step is active")
	public boolean isReviewSubmitStepActive() {
		try {
			waitUtils.waitForVisibility(uploadSubmitModuleButton);
			return uploadSubmitModuleButton.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Review Summary Field Value: {0}")
	public String getReviewDetailValue(String label) {
		try {
			By valueLocator = By.xpath("//div[./span[normalize-space()='" + label + "']]/div[contains(@class,'flex-1')]");
			WebElement el = waitUtils.waitForVisibility(valueLocator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Review Module Name")
	public String getReviewModuleName() {
		return getReviewDetailValue("Module Name");
	}

	@StepName("Get Review Module Type")
	public String getReviewModuleType() {
		return getReviewDetailValue("Module Type");
	}

	@StepName("Get Review Simulation Type")
	public String getReviewSimulationType() {
		return getReviewDetailValue("Simulation Type");
	}

	@StepName("Get Review Setup")
	public String getReviewSetup() {
		return getReviewDetailValue("Setup");
	}

	@StepName("Get Review Version")
	public String getReviewVersion() {
		return getReviewDetailValue("Version");
	}

	@StepName("Get Review Platform")
	public String getReviewPlatform() {
		return getReviewDetailValue("Platform");
	}

	@StepName("Get Review Description")
	public String getReviewDescription() {
		return getReviewDetailValue("Description");
	}

	@StepName("Get Review File Type")
	public String getReviewFileType() {
		return getReviewDetailValue("File Type");
	}

	@StepName("Get Review File Name")
	public String getReviewFileName() {
		return getReviewDetailValue("File Name");
	}

	@StepName("Get Review SHA-256")
	public String getReviewSha256() {
		return getReviewDetailValue("SHA-256");
	}

	@StepName("Submit Module for Review (Step 3)")
	public void submitForReview() {
		waitUtils.waitForClickable(uploadSubmitModuleButton);
		uploadSubmitModuleButton.click();
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.urlContains("/developer/organisations"),
				ExpectedConditions.urlContains("/developer/submissions")
		));
	}

	@StepName("Cancel upload in progress from modal")
	public void cancelUploadProgress() {
		try {
			waitUtils.waitForClickable(cancelUploadProgressButton);
			cancelUploadProgressButton.click();
		} catch (Exception ignored) {}
	}

	@StepName("Fill Module Details step (Backward Compatible): name={0}, type={1}, version={4}")
	public void fillModuleDetailsStep(String name, String type, String simType, String setup, String version, String desc, List<String> platforms) {
		enterModuleDetails(name, type, simType, setup, version, desc, platforms);
		clickNextStep();
	}

	@StepName("Upload build file (Backward Compatible): {0}")
	public void uploadBuildFile(String filePath, String fileType) {
		selectFileType(fileType);
		uploadBuildFileOnly(filePath);
		clickNextStep();
	}

	@StepName("Submit Module on final review step (Backward Compatible)")
	public void submitModule() {
		submitForReview();
	}

	// =========================================================================
	// INTERNAL HELPERS
	// =========================================================================

	private boolean isGridViewActive() {
		return !organisationCards.isEmpty();
	}

	private void waitForTableOrCardsToLoad() {
		try {
			waitUtils.waitUntil(driver -> {
				boolean hasCards = !driver.findElements(By.xpath("//div[contains(@class,'grid')]//div[contains(@class,'rounded-2xl')]")).isEmpty();
				boolean hasRows = !driver.findElements(By.xpath("//table//tbody//tr")).isEmpty();
				boolean empty = !driver.findElements(By.xpath("//*[contains(text(),'No organisations')]")).isEmpty();
				return hasCards || hasRows || empty;
			});
		} catch (Exception ignored) {}
	}
}
