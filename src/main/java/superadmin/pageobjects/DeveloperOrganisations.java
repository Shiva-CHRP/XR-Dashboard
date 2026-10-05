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

	@FindBy(xpath = "//input[contains(@placeholder,'Safety') or contains(@placeholder,'module') or @name='moduleName']")
	private WebElement uploadModuleNameInput;

	@FindBy(xpath = "//select[@name='moduleType' or .//option[contains(text(),'Select module type')]]")
	private WebElement uploadModuleTypeSelect;

	@FindBy(xpath = "//select[@name='simulationType' or .//option[contains(text(),'Select simulation type')]]")
	private WebElement uploadSimulationTypeSelect;

	@FindBy(xpath = "//select[@name='setup' or .//option[contains(text(),'Select setup')]]")
	private WebElement uploadSetupSelect;

	@FindBy(xpath = "//input[@name='version' or @placeholder='1.0.0']")
	private WebElement uploadVersionInput;

	@FindBy(xpath = "//button[contains(.,'Select platforms…') or contains(.,'platforms selected')]")
	private WebElement uploadPlatformDropdownButton;

	@FindBy(xpath = "//textarea[@name='description' or contains(@placeholder,'description')]")
	private WebElement uploadDescriptionTextarea;

	@FindBy(xpath = "//button[contains(.,'Next')]")
	private WebElement uploadNextStepButton;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadFileInput;

	@FindBy(xpath = "//button[contains(.,'Submit Module')]")
	private WebElement uploadSubmitModuleButton;

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

	@StepName("Fill Module Details step: name={0}, type={1}, version={4}")
	public void fillModuleDetailsStep(String name, String type, String simType, String setup, String version, String desc, List<String> platforms) {
		waitUtils.waitForVisibility(uploadModuleNameInput);
		uploadModuleNameInput.clear();
		uploadModuleNameInput.sendKeys(name);

		if (type != null && !type.isEmpty()) {
			selectByVisibleText(uploadModuleTypeSelect, type);
		}
		if (simType != null && !simType.isEmpty()) {
			selectByVisibleText(uploadSimulationTypeSelect, simType);
		}
		if (setup != null && !setup.isEmpty()) {
			try { selectByVisibleText(uploadSetupSelect, setup); } catch (Exception ignored) {}
		}
		if (version != null && !version.isEmpty()) {
			uploadVersionInput.clear();
			uploadVersionInput.sendKeys(version);
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
					By platformCheckbox = By.xpath("//label[.//span[text()='" + platform + "']]//input");
					WebElement cb = driver.findElement(platformCheckbox);
					if (!cb.isSelected()) cb.click();
				} catch (Exception ignored) {}
			}
			uploadPlatformDropdownButton.click(); // close dropdown
		}

		waitUtils.waitForClickable(uploadNextStepButton);
		uploadNextStepButton.click();
	}

	@StepName("Upload build file: {0}")
	public void uploadBuildFile(String filePath, String fileType) {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new RuntimeException("Build file not found at path: " + filePath);
		}

		// Select file type option if available
		try {
			By fileTypeRadio = By.xpath("//label[contains(.,'" + fileType.toUpperCase() + "')]//input");
			WebElement radio = driver.findElement(fileTypeRadio);
			if (!radio.isSelected()) radio.click();
		} catch (Exception ignored) {}

		uploadFileInput.sendKeys(file.getAbsolutePath());

		waitUtils.waitForClickable(uploadNextStepButton);
		uploadNextStepButton.click();
	}

	@StepName("Submit Module on final review step")
	public void submitModule() {
		waitUtils.waitForClickable(uploadSubmitModuleButton);
		uploadSubmitModuleButton.click();
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.urlContains("/developer/organisations"),
				ExpectedConditions.urlContains("/developer/submissions")
		));
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
