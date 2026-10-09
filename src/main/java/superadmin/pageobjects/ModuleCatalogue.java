package superadmin.pageobjects;

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
 * ModuleCatalogue Page Object
 * 
 * Accurately models the frontend Module Catalogue at /super-admin/modules/catalogue
 * and Module Catalogue Detail view at /super-admin/modules/catalogue/:id
 * matching src/pages/Admin/Catalogue/index.jsx and CatalogueDetail.jsx.
 * 
 * Utilizes the centralized waitUtils and AbstractComponent wait wrappers throughout.
 */
public class ModuleCatalogue extends AbstractComponent {

	public ModuleCatalogue(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Header
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/modules/catalogue']")
	private WebElement moduleCatalogue;

	@FindBy(xpath = "//h1[contains(text(),'Module Catalogue')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//p[contains(text(),'Manage approved modules')]")
	private WebElement pageSubtitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') and not(ancestor::div[contains(@class,'hidden')])]")
	private WebElement refreshButton;

	@FindBy(xpath = "//span[contains(@class,'text-titan-muted')][.//*[local-name()='svg' and contains(@class,'lucide-refresh')]]")
	private WebElement lastUpdatedText;

	// =========================================================================
	// 2. Active / Archived Tabs
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Active') and (contains(@class,'border-b-2') or contains(@class,'inline-flex'))]")
	private WebElement activeTabButton;

	@FindBy(xpath = "//button[contains(.,'Active')]//span[contains(@class,'rounded-full')]")
	private WebElement activeTabCount;

	@FindBy(xpath = "//button[contains(.,'Archived') and (contains(@class,'border-b-2') or contains(@class,'inline-flex'))]")
	private WebElement archivedTabButton;

	@FindBy(xpath = "//button[contains(.,'Archived')]//span[contains(@class,'rounded-full')]")
	private WebElement archivedTabCount;

	// =========================================================================
	// 3. Filter Bar & View Toggle
	// =========================================================================

	@FindBy(xpath = "//input[contains(@placeholder,'Search modules, developer, type') or contains(@placeholder,'Search modules')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(.,'Clear filters') or contains(.,'Clear all')]")
	private WebElement clearFiltersButton;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Module Types')]]")
	private WebElement moduleTypeSelect;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Organisations')]]")
	private WebElement organisationSelect;

	@FindBy(xpath = "//button[@aria-label='Grid view']")
	private WebElement gridViewButton;

	@FindBy(xpath = "//button[@aria-label='List view']")
	private WebElement listViewButton;

	@FindBy(xpath = "//span[contains(text(),'module') and contains(@class,'text-titan-muted')]")
	private WebElement moduleCountLabel;

	// =========================================================================
	// 4. Listing Content - Grid & List Views
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'rounded-xl') and contains(@class,'border-titan-border')]")
	private List<WebElement> moduleCards;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> moduleTableRows;

	@FindBy(xpath = "//table//thead//th")
	private List<WebElement> tableHeaders;

	// Actions Dropdown Menu (Opened via 3-dots)
	@FindBy(xpath = "//button[contains(.,'View Details')]")
	private WebElement menuViewDetailsOption;

	@FindBy(xpath = "//button[contains(.,'Manage Access')]")
	private WebElement menuManageAccessOption;

	@FindBy(xpath = "//button[contains(.,'Archive') and not(contains(.,'Archiving')) and not(contains(.,'Unarchive')) and not(contains(.,'module?'))]")
	private WebElement menuArchiveOption;

	@FindBy(xpath = "//button[contains(.,'Unarchive')]")
	private WebElement menuUnarchiveOption;

	// =========================================================================
	// 5. Manage Access Modal (Listing & Detail Page)
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Manage Access')] | //div[@role='dialog']//h2[contains(text(),'Manage Access')] | //h2[contains(text(),'Manage Access')]")
	private WebElement accessModalTitle;

	@FindBy(xpath = "//input[@placeholder='Search organisations…']")
	private WebElement accessSearchInput;

	@FindBy(xpath = "//p[contains(text(),'With Access')]/following-sibling::div//div[contains(@class,'rounded-lg')]")
	private List<WebElement> withAccessOrgRows;

	@FindBy(xpath = "//button[contains(.,'+ Add Organisation')]")
	private WebElement addOrgAccordionButton;

	@FindBy(xpath = "//button[contains(.,'+ Add Organisation')]/following-sibling::div//div[contains(@class,'rounded-lg')]")
	private List<WebElement> availableOrgRows;

	@FindBy(xpath = "//button[contains(.,'Save Access')]")
	private WebElement saveAccessButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel'] | //button[text()='Cancel' and ancestor::div[contains(@role,'dialog') or .//h2[contains(text(),'Manage Access')]]]")
	private WebElement cancelAccessButton;

	@FindBy(xpath = "//span[contains(text(),'have access')]")
	private WebElement accessSummaryText;

	// =========================================================================
	// 6. Archive Module Confirmation Modal (Listing Page)
	// =========================================================================

	@FindBy(xpath = "//h2[contains(.,'Archive module?')]")
	private WebElement archiveModalTitle;

	@FindBy(xpath = "//button[(contains(.,'Archive') or contains(.,'Archiving')) and not(contains(.,'Cancel')) and not(contains(.,'Unarchive')) and ancestor::div[contains(@role,'dialog') or .//h2[contains(.,'Archive module?')]]]")
	private WebElement confirmArchiveModuleButton;

	@FindBy(xpath = "//button[text()='Cancel' and ancestor::div[contains(@role,'dialog') or .//h2[contains(.,'Archive module?')]]]")
	private WebElement cancelArchiveModuleButton;

	// =========================================================================
	// 7. Detail Page Elements (/super-admin/modules/catalogue/:id)
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Back to Module Catalogue')]")
	private WebElement backToCatalogueButton;

	@FindBy(xpath = "//h1[contains(@class,'text-2xl')]")
	private WebElement detailModuleTitle;

	@FindBy(xpath = "//h1[contains(@class,'text-2xl')]/following-sibling::span[1]")
	private WebElement detailModuleTypeBadge;

	@FindBy(xpath = "//h1[contains(@class,'text-2xl')]/following-sibling::span[contains(.,'Archived')]")
	private WebElement detailArchivedBadge;

	@FindBy(xpath = "//div[contains(@class,'border-titan-warning')]//p[contains(text(),'This module is archived')]")
	private WebElement detailArchivedBanner;

	@FindBy(xpath = "//div[contains(@class,'border-titan-warning')]//button[contains(.,'Unarchive')]")
	private WebElement detailUnarchiveBannerButton;

	@FindBy(xpath = "//h2[contains(.,'Module Overview')]/following-sibling::p")
	private WebElement detailOverviewDescription;

	@FindBy(xpath = "//p[contains(text(),'Tags / Skills')]/following-sibling::div//span")
	private List<WebElement> detailTagChips;

	@FindBy(xpath = "//button[@title='Copy full hash']")
	private WebElement copyHashButton;

	@FindBy(xpath = "//button[contains(.,'Download')]")
	private WebElement downloadBuildButton;

	// Detail Page - Version Control Card
	@FindBy(xpath = "//h2[contains(.,'Version Control')]/following-sibling::div//div[contains(@class,'rounded-lg')]")
	private List<WebElement> versionGroupRows;

	// Detail Page - Side Card Organisation Access
	@FindBy(xpath = "//h2[contains(.,'Organisation Access')]/..//button[contains(.,'Manage Access')]")
	private WebElement sideCardManageAccessButton;

	// =========================================================================
	// 8. Detail Page Modals (Set Active, Archive Version, Unarchive, Restore)
	// =========================================================================

	@FindBy(xpath = "//h2[contains(text(),'Set Active Version?')]")
	private WebElement setActiveModalTitle;

	@FindBy(xpath = "//button[contains(.,'Set as Active')]")
	private WebElement confirmSetActiveButton;

	@FindBy(xpath = "//button[text()='Cancel' and ancestor::div[contains(@role,'dialog') or .//h2[contains(text(),'Set Active Version?')]]]")
	private WebElement cancelSetActiveButton;

	@FindBy(xpath = "//h2[contains(text(),'Archive v')]")
	private WebElement archiveVersionModalTitle;

	@FindBy(xpath = "//button[contains(.,'Archive Version')]")
	private WebElement confirmArchiveVersionButton;

	@FindBy(xpath = "//button[text()='Cancel' and ancestor::div[contains(@role,'dialog') or .//h2[contains(text(),'Archive v')]]]")
	private WebElement cancelArchiveVersionButton;

	@FindBy(xpath = "//h2[contains(text(),'Unarchive v')]")
	private WebElement unarchiveVersionModalTitle;

	@FindBy(xpath = "//button[contains(.,'Unarchive Version')]")
	private WebElement confirmUnarchiveVersionButton;

	@FindBy(xpath = "//button[text()='Cancel' and ancestor::div[contains(@role,'dialog') or .//h2[contains(text(),'Unarchive v')]]]")
	private WebElement cancelUnarchiveVersionButton;

	// Restore Version Step 1 (Rationale)
	@FindBy(xpath = "//h2[contains(text(),'Restore Previous Version')]")
	private WebElement restoreVersionModalTitle;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Explain why you are restoring')]")
	private WebElement restoreRationaleTextarea;

	@FindBy(xpath = "//button[contains(.,'Continue')]")
	private WebElement restoreContinueButton;

	@FindBy(xpath = "//button[text()='Cancel' and ancestor::div[contains(@role,'dialog') or .//h2[contains(text(),'Restore Previous Version')]]]")
	private WebElement restoreCancelButton;

	// Restore Version Step 2 (Password)
	@FindBy(xpath = "//h2[contains(text(),'Confirm Restore')]")
	private WebElement confirmRestorePasswordModalTitle;

	@FindBy(xpath = "//input[@placeholder='Enter your password']")
	private WebElement restorePasswordInput;

	@FindBy(xpath = "//button[contains(.,'Restore to v')]")
	private WebElement confirmRestoreFinalButton;

	// Version Detail Modal
	@FindBy(xpath = "//h2[contains(text(),'Version ')]")
	private WebElement versionDetailModalTitle;

	@FindBy(xpath = "//button[text()='Close' and ancestor::div[contains(@role,'dialog')]]")
	private WebElement closeVersionDetailModalButton;

	// =========================================================================
	// ACTION METHODS: Navigation & Page State
	// =========================================================================

	@StepName("Click on the Module Catalogue")
	public void clickModuleCatalogue() {
		waitUtils.waitForClickable(moduleCatalogue);
		try {
			moduleCatalogue.click();
		} catch (Exception e) {
			clickUsingJS(moduleCatalogue);
		}
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.visibilityOf(pageTitle),
				ExpectedConditions.urlContains("/super-admin/modules/catalogue")
		));
	}

	@StepName("Verify Module Catalogue page is loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Module Catalogue Page Title")
	public String getPageTitle() {
		waitUtils.waitForVisibility(pageTitle);
		return pageTitle.getText().trim();
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		waitForTableOrCardsToLoad();
	}

	@StepName("Get Last Updated text")
	public String getLastUpdatedText() {
		try {
			return lastUpdatedText.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	// =========================================================================
	// ACTION METHODS: Tab Navigation (Active / Archived)
	// =========================================================================

	@StepName("Switch to Active Tab")
	public void clickActiveTab() {
		waitUtils.waitForClickable(activeTabButton);
		activeTabButton.click();
		waitForTableOrCardsToLoad();
	}

	@StepName("Get Active Modules Count from tab badge")
	public int getActiveCount() {
		try {
			String text = activeTabCount.getText().replaceAll("[^0-9]", "").trim();
			return text.isEmpty() ? 0 : Integer.parseInt(text);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Switch to Archived Tab")
	public void clickArchivedTab() {
		waitUtils.waitForClickable(archivedTabButton);
		archivedTabButton.click();
		waitForTableOrCardsToLoad();
	}

	@StepName("Get Archived Modules Count from tab badge")
	public int getArchivedCount() {
		try {
			String text = archivedTabCount.getText().replaceAll("[^0-9]", "").trim();
			return text.isEmpty() ? 0 : Integer.parseInt(text);
		} catch (Exception e) {
			return 0;
		}
	}

	// =========================================================================
	// ACTION METHODS: Filtering, Search & View Modes
	// =========================================================================

	@StepName("Search modules with query: {0}")
	public void searchModule(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (query != null && !query.isEmpty()) {
			searchInput.sendKeys(query);
		}
		waitForTableOrCardsToLoad();
	}

	@StepName("Filter by Module Type: {0}")
	public void filterByModuleType(String moduleType) {
		waitUtils.waitForVisibility(moduleTypeSelect);
		selectByVisibleText(moduleTypeSelect, moduleType);
		waitForTableOrCardsToLoad();
	}

	@StepName("Filter by Organisation: {0}")
	public void filterByOrganisation(String orgName) {
		waitUtils.waitForVisibility(organisationSelect);
		selectByVisibleText(organisationSelect, orgName);
		waitForTableOrCardsToLoad();
	}

	@StepName("Clear all applied filters")
	public void clearAllFilters() {
		try {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForTableOrCardsToLoad();
		} catch (Exception e) {
			searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE, org.openqa.selenium.Keys.ENTER);
			waitForTableOrCardsToLoad();
		}
	}

	@StepName("Switch to Grid View")
	public void clickGridView() {
		waitUtils.waitForClickable(gridViewButton);
		gridViewButton.click();
		waitUtils.waitForAttributeToBe(gridViewButton, "aria-pressed", "true");
	}

	@StepName("Switch to List View")
	public void clickListView() {
		waitUtils.waitForClickable(listViewButton);
		listViewButton.click();
		waitUtils.waitForAttributeToBe(listViewButton, "aria-pressed", "true");
	}

	@StepName("Get total modules count text")
	public String getModuleCountSummary() {
		try {
			return moduleCountLabel.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Module Cards Count")
	public int getModuleCardsCount() {
		return moduleCards.size();
	}

	@StepName("Is Empty Catalogue State Displayed")
	public boolean isEmptyCatalogueStateDisplayed() {
		return !driver.findElements(By.xpath("//div[contains(.,'No') and (contains(.,'found') or contains(.,'available'))] | //p[contains(.,'No') and contains(.,'module')]")).isEmpty()
				|| moduleCards.isEmpty();
	}

	// =========================================================================
	// ACTION METHODS: Listing Items (Cards & Rows)
	// =========================================================================

	@StepName("Get titles of all modules currently displayed")
	public List<String> getDisplayedModuleTitles() {
		List<String> titles = new ArrayList<>();
		if (isGridViewActive()) {
			for (WebElement card : moduleCards) {
				try {
					WebElement h3 = card.findElement(By.tagName("h3"));
					String text = h3.getText().split("\n")[0].trim();
					titles.add(text);
				} catch (Exception ignored) {}
			}
		} else {
			for (WebElement row : moduleTableRows) {
				try {
					WebElement nameEl = row.findElement(By.xpath(".//td[1]//p"));
					titles.add(nameEl.getText().trim());
				} catch (Exception ignored) {}
			}
		}
		return titles;
	}

	@StepName("Check if module with name '{0}' exists")
	public boolean isModulePresent(String moduleName) {
		return getDisplayedModuleTitles().stream()
				.anyMatch(title -> title.equalsIgnoreCase(moduleName) || title.contains(moduleName));
	}

	@StepName("Open Module Details by clicking module card/row: {0}")
	public void openModuleDetail(String moduleName) {
		if (isGridViewActive()) {
			WebElement card = findCardByName(moduleName);
			if (card != null) {
				WebElement viewBtn = card.findElement(By.xpath(".//button[contains(.,'View Details')]"));
				waitUtils.waitForClickable(viewBtn);
				viewBtn.click();
			} else {
				throw new RuntimeException("Module card not found: " + moduleName);
			}
		} else {
			WebElement row = findRowByName(moduleName);
			if (row != null) {
				waitUtils.waitForClickable(row);
				row.click();
			} else {
				throw new RuntimeException("Module row not found: " + moduleName);
			}
		}
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.visibilityOf(detailModuleTitle),
				ExpectedConditions.urlContains("/catalogue/")
		));
	}

	@StepName("Open Manage Access from Card/Row directly: {0}")
	public void clickManageAccess(String moduleName) {
		if (isGridViewActive()) {
			WebElement card = findCardByName(moduleName);
			if (card != null) {
				WebElement accessBtn = card.findElement(By.xpath(".//button[contains(.,'Manage Access')]"));
				waitUtils.waitForClickable(accessBtn);
				accessBtn.click();
			} else {
				throw new RuntimeException("Module card not found: " + moduleName);
			}
		} else {
			openRow3DotsMenu(moduleName);
			waitUtils.waitForClickable(menuManageAccessOption);
			menuManageAccessOption.click();
		}
		waitUtils.waitForVisibility(accessModalTitle);
	}

	@StepName("Open 3-dots action menu for module: {0}")
	public void openModuleActions(String moduleName) {
		if (isGridViewActive()) {
			openCard3DotsMenu(moduleName);
		} else {
			openRow3DotsMenu(moduleName);
		}
	}

	@StepName("Click 'Archive' from 3-dots action menu for module: {0}")
	public void archiveModuleFromMenu(String moduleName) {
		openModuleActions(moduleName);
		waitUtils.waitForClickable(menuArchiveOption);
		menuArchiveOption.click();
		waitUtils.waitForVisibility(archiveModalTitle);
	}

	@StepName("Click 'Unarchive' from 3-dots action menu for module: {0}")
	public void unarchiveModuleFromMenu(String moduleName) {
		openModuleActions(moduleName);
		waitUtils.waitForClickable(menuUnarchiveOption);
		menuUnarchiveOption.click();
		waitForTableOrCardsToLoad();
	}

	// =========================================================================
	// ACTION METHODS: Manage Access Modal
	// =========================================================================

	@StepName("Search organisations inside Manage Access modal: {0}")
	public void searchOrganisationInAccessModal(String orgName) {
		waitUtils.waitForVisibility(accessSearchInput);
		accessSearchInput.clear();
		accessSearchInput.sendKeys(orgName);
	}

	@StepName("Add organisation access in modal: {0}")
	public void addOrganisationAccess(String orgName) {
		waitUtils.waitForVisibility(accessModalTitle);

		// Check if the accordion is open, if not click to expand
		try {
			if (addOrgAccordionButton.isDisplayed()) {
				addOrgAccordionButton.click();
			}
		} catch (Exception ignored) {}

		searchOrganisationInAccessModal(orgName);

		By addBtnLocator = By.xpath(".//div[contains(@class,'rounded-lg')][.//p[contains(text(),'" + orgName + "')]]//button[contains(.,'+ Add')]");
		waitUtils.waitForClickable(addBtnLocator);
		driver.findElement(addBtnLocator).click();
	}

	@StepName("Remove organisation access in modal: {0}")
	public void removeOrganisationAccess(String orgName) {
		By removeBtnLocator = By.xpath("//div[contains(@class,'rounded-lg')][.//p[contains(text(),'" + orgName + "')]]//button[contains(@class,'hover:text-titan-danger-text')]");
		waitUtils.waitForClickable(removeBtnLocator);
		driver.findElement(removeBtnLocator).click();
	}

	@StepName("Save organisation access changes")
	public void saveAccessChanges() {
		waitUtils.waitForClickable(saveAccessButton);
		saveAccessButton.click();
		waitUtils.waitForInvisibility(accessModalTitle);
		waitForTableOrCardsToLoad();
	}

	@StepName("Cancel Manage Access modal")
	public void cancelAccessModal() {
		waitUtils.waitForClickable(cancelAccessButton);
		cancelAccessButton.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Open First Module Detail View if available")
	public boolean openFirstModuleDetail() {
		try {
			if (isGridViewActive()) {
				if (!moduleCards.isEmpty() && moduleCards.get(0).isDisplayed()) {
					WebElement viewBtn = moduleCards.get(0).findElement(By.xpath(".//button[contains(.,'View Details')]"));
					waitUtils.waitForClickable(viewBtn);
					viewBtn.click();
					waitUtils.waitUntil(ExpectedConditions.or(
							ExpectedConditions.visibilityOf(detailModuleTitle),
							ExpectedConditions.urlContains("/catalogue/")
					));
					return true;
				}
			} else {
				if (!moduleTableRows.isEmpty() && moduleTableRows.get(0).isDisplayed()) {
					waitUtils.waitForClickable(moduleTableRows.get(0));
					moduleTableRows.get(0).click();
					waitUtils.waitUntil(ExpectedConditions.or(
							ExpectedConditions.visibilityOf(detailModuleTitle),
							ExpectedConditions.urlContains("/catalogue/")
					));
					return true;
				}
			}
		} catch (Exception e) {
			// No module cards/rows available
		}
		return false;
	}

	@StepName("Open First Manage Access Modal if available")
	public boolean openFirstManageAccessModal() {
		try {
			if (isGridViewActive()) {
				if (!moduleCards.isEmpty() && moduleCards.get(0).isDisplayed()) {
					WebElement accessBtn = moduleCards.get(0).findElement(By.xpath(".//button[contains(.,'Manage Access')]"));
					waitUtils.waitForClickable(accessBtn);
					accessBtn.click();
					waitUtils.waitForVisibility(accessModalTitle);
					return true;
				}
			} else {
				if (!moduleTableRows.isEmpty() && moduleTableRows.get(0).isDisplayed()) {
					WebElement menuBtn = moduleTableRows.get(0).findElement(By.xpath(".//button[@aria-label='Module actions']"));
					waitUtils.waitForClickable(menuBtn);
					menuBtn.click();
					waitUtils.waitForClickable(menuManageAccessOption);
					menuManageAccessOption.click();
					waitUtils.waitForVisibility(accessModalTitle);
					return true;
				}
			}
		} catch (Exception e) {
			// Not available
		}
		return false;
	}

	@StepName("Check if Manage Access Modal is displayed")
	public boolean isAccessModalDisplayed() {
		try {
			return accessModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(backToCatalogueButton);
			return backToCatalogueButton.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// =========================================================================
	// ACTION METHODS: Archive Module Confirmation Modal
	// =========================================================================

	@StepName("Confirm archiving module in modal")
	public void confirmArchiveModule() {
		waitUtils.waitForClickable(confirmArchiveModuleButton);
		confirmArchiveModuleButton.click();
		waitUtils.waitForInvisibility(archiveModalTitle);
		waitForTableOrCardsToLoad();
	}

	@StepName("Cancel archiving module in modal")
	public void cancelArchiveModule() {
		waitUtils.waitForClickable(cancelArchiveModuleButton);
		cancelArchiveModuleButton.click();
		waitUtils.waitForInvisibility(archiveModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Module Detail Page (/super-admin/modules/catalogue/:id)
	// =========================================================================

	@StepName("Click 'Back to Module Catalogue' button")
	public void clickBackToCatalogue() {
		waitUtils.waitForClickable(backToCatalogueButton);
		backToCatalogueButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Get Detail Page Module Title")
	public String getDetailTitle() {
		waitUtils.waitForVisibility(detailModuleTitle);
		return detailModuleTitle.getText().trim();
	}

	@StepName("Get Detail Page Module Type")
	public String getDetailModuleType() {
		try {
			return detailModuleTypeBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Check if module is archived on Detail Page")
	public boolean isDetailModuleArchived() {
		try {
			return detailArchivedBadge.isDisplayed() || detailArchivedBanner.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Unarchive button on Detail Page banner")
	public void unarchiveFromDetailBanner() {
		waitUtils.waitForClickable(detailUnarchiveBannerButton);
		detailUnarchiveBannerButton.click();
		waitUtils.waitForInvisibility(detailArchivedBanner);
	}

	@StepName("Get Detail Overview Description")
	public String getDetailDescription() {
		try {
			return detailOverviewDescription.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Detail Tags list")
	public List<String> getDetailTags() {
		List<String> tags = new ArrayList<>();
		for (WebElement chip : detailTagChips) {
			tags.add(chip.getText().trim());
		}
		return tags;
	}

	@StepName("Copy SHA-256 Hash")
	public void clickCopyHash() {
		waitUtils.waitForClickable(copyHashButton);
		copyHashButton.click();
	}

	@StepName("Download Build File")
	public void clickDownloadBuild() {
		waitUtils.waitForClickable(downloadBuildButton);
		downloadBuildButton.click();
	}

	@StepName("Open Manage Access from Detail Side Card")
	public void clickManageAccessFromSideCard() {
		waitUtils.waitForClickable(sideCardManageAccessButton);
		sideCardManageAccessButton.click();
		waitUtils.waitForVisibility(accessModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Detail Page Version Actions & Modals
	// =========================================================================

	@StepName("Click 'Make Active' for version: {0}")
	public void makeVersionActive(String versionNumber) {
		By makeActiveBtnLocator = By.xpath("//div[.//p[contains(text(),'v" + versionNumber + "')]]//button[contains(.,'Make Active')]");
		waitUtils.waitForClickable(makeActiveBtnLocator);
		driver.findElement(makeActiveBtnLocator).click();
		waitUtils.waitForVisibility(setActiveModalTitle);
	}

	@StepName("Confirm 'Set as Active' in modal")
	public void confirmSetActiveVersion() {
		waitUtils.waitForClickable(confirmSetActiveButton);
		confirmSetActiveButton.click();
		waitUtils.waitForInvisibility(setActiveModalTitle);
	}

	@StepName("Cancel 'Set as Active' in modal")
	public void cancelSetActiveVersion() {
		waitUtils.waitForClickable(cancelSetActiveButton);
		cancelSetActiveButton.click();
		waitUtils.waitForInvisibility(setActiveModalTitle);
	}

	@StepName("Click 'Archive' for version: {0}")
	public void archiveVersion(String versionNumber) {
		By archiveBtnLocator = By.xpath("//div[.//p[contains(text(),'v" + versionNumber + "')]]//button[contains(.,'Archive') and not(contains(.,'Unarchive'))]");
		waitUtils.waitForClickable(archiveBtnLocator);
		driver.findElement(archiveBtnLocator).click();
		waitUtils.waitForVisibility(archiveVersionModalTitle);
	}

	@StepName("Confirm 'Archive Version' in modal")
	public void confirmArchiveVersion() {
		waitUtils.waitForClickable(confirmArchiveVersionButton);
		confirmArchiveVersionButton.click();
		waitUtils.waitForInvisibility(archiveVersionModalTitle);
	}

	@StepName("Cancel 'Archive Version' in modal")
	public void cancelArchiveVersion() {
		waitUtils.waitForClickable(cancelArchiveVersionButton);
		cancelArchiveVersionButton.click();
		waitUtils.waitForInvisibility(archiveVersionModalTitle);
	}

	@StepName("Click 'Unarchive' for version: {0}")
	public void unarchiveVersion(String versionNumber) {
		By unarchiveBtnLocator = By.xpath("//div[.//p[contains(text(),'v" + versionNumber + "')]]//button[contains(.,'Unarchive')]");
		waitUtils.waitForClickable(unarchiveBtnLocator);
		driver.findElement(unarchiveBtnLocator).click();
		waitUtils.waitForVisibility(unarchiveVersionModalTitle);
	}

	@StepName("Confirm 'Unarchive Version' in modal")
	public void confirmUnarchiveVersion() {
		waitUtils.waitForClickable(confirmUnarchiveVersionButton);
		confirmUnarchiveVersionButton.click();
		waitUtils.waitForInvisibility(unarchiveVersionModalTitle);
	}

	@StepName("Cancel 'Unarchive Version' in modal")
	public void cancelUnarchiveVersion() {
		waitUtils.waitForClickable(cancelUnarchiveVersionButton);
		cancelUnarchiveVersionButton.click();
		waitUtils.waitForInvisibility(unarchiveVersionModalTitle);
	}

	@StepName("Open Version Details modal by clicking version: {0}")
	public void openVersionDetailsModal(String versionNumber) {
		By verRowLocator = By.xpath("//div[.//p[contains(text(),'v" + versionNumber + "')]]//button[contains(@class,'text-left')]");
		waitUtils.waitForClickable(verRowLocator);
		driver.findElement(verRowLocator).click();
		waitUtils.waitForVisibility(versionDetailModalTitle);
	}

	@StepName("Close Version Details modal")
	public void closeVersionDetailsModal() {
		waitUtils.waitForClickable(closeVersionDetailModalButton);
		closeVersionDetailModalButton.click();
		waitUtils.waitForInvisibility(versionDetailModalTitle);
	}

	@StepName("Restore version with rationale and password")
	public void restoreVersion(String rationale, String password) {
		// Step 1: Rationale modal
		waitUtils.waitForVisibility(restoreVersionModalTitle);
		waitUtils.waitForVisibility(restoreRationaleTextarea);
		restoreRationaleTextarea.clear();
		restoreRationaleTextarea.sendKeys(rationale);
		waitUtils.waitForClickable(restoreContinueButton);
		restoreContinueButton.click();

		// Step 2: Password modal
		waitUtils.waitForVisibility(confirmRestorePasswordModalTitle);
		waitUtils.waitForVisibility(restorePasswordInput);
		restorePasswordInput.clear();
		restorePasswordInput.sendKeys(password);
		waitUtils.waitForClickable(confirmRestoreFinalButton);
		confirmRestoreFinalButton.click();

		waitUtils.waitForInvisibility(confirmRestorePasswordModalTitle);
	}

	// =========================================================================
	// INTERNAL HELPERS
	// =========================================================================

	private boolean isGridViewActive() {
		try {
			return "true".equalsIgnoreCase(gridViewButton.getAttribute("aria-pressed"));
		} catch (Exception e) {
			return true;
		}
	}

	private WebElement findCardByName(String moduleName) {
		for (WebElement card : moduleCards) {
			try {
				WebElement h3 = card.findElement(By.tagName("h3"));
				if (h3.getText().toLowerCase().contains(moduleName.toLowerCase())) {
					return card;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	private WebElement findRowByName(String moduleName) {
		for (WebElement row : moduleTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]//p"));
				if (nameEl.getText().toLowerCase().contains(moduleName.toLowerCase())) {
					return row;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	private void openCard3DotsMenu(String moduleName) {
		WebElement card = findCardByName(moduleName);
		if (card == null) throw new RuntimeException("Card not found: " + moduleName);
		WebElement menuBtn = card.findElement(By.xpath(".//button[@aria-label='Module actions']"));
		waitUtils.waitForClickable(menuBtn);
		menuBtn.click();
	}

	private void openRow3DotsMenu(String moduleName) {
		WebElement row = findRowByName(moduleName);
		if (row == null) throw new RuntimeException("Row not found: " + moduleName);
		WebElement menuBtn = row.findElement(By.xpath(".//button[@aria-label='Module actions']"));
		waitUtils.waitForClickable(menuBtn);
		menuBtn.click();
	}

	private void waitForTableOrCardsToLoad() {
		try {
			waitUtils.waitUntil(driver -> {
				boolean hasCards = !driver.findElements(By.xpath("//div[contains(@class,'grid')]//div[contains(@class,'rounded-xl')]")).isEmpty();
				boolean hasRows = !driver.findElements(By.xpath("//table//tbody//tr")).isEmpty();
				boolean emptyCard = !driver.findElements(By.xpath("//p[contains(text(),'No modules in the catalogue yet') or contains(text(),'No modules match your filters')]")).isEmpty();
				return hasCards || hasRows || emptyCard;
			});
		} catch (Exception ignored) {}
	}
}
