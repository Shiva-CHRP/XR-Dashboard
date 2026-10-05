package superadmin.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for Super Admin Organization Sync Center:
 * 1. Sync Health Monitoring & Posture (/super-admin/organization-sync)
 * 2. KPI Section (Organizations Sync Health, Sync Runs 24h, Device Fleet Sync)
 * 3. Health Overview Donut & Live Status Indicator
 * 4. Sync Trends Chart Window Filtering (24h, 7d, 30d)
 * 5. Organizations Table (Search, Status Filter, Sync Runs, Devices, Writeback, Pagination)
 * 6. Organization Sync Detail View & Tabs (Overview, Sync Health, Sync History, Devices)
 * 7. Resync Database Trigger & Confirmation Modal
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class OrganizationSync extends AbstractComponent {

	public OrganizationSync(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/organization-sync']")
	private WebElement organizationSyncLink;

	@FindBy(xpath = "//h1[contains(text(),'Organization Sync Center')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//p[contains(text(),'Monitor synchronization health')]")
	private WebElement pageDescription;

	@FindBy(xpath = "//span[contains(text(),'Last updated:')]")
	private WebElement lastUpdatedText;

	@FindBy(xpath = "//span[contains(text(),'Auto-refresh')]")
	private WebElement autoRefreshBadge;

	@FindBy(xpath = "//button[contains(.,'Refresh') and not(contains(.,'Organizations'))]")
	private WebElement refreshAllButton;

	// =========================================================================
	// 2. KPI SECTION LOCATORS
	// =========================================================================

	@FindBy(xpath = "//span[text()='Total Organizations']/preceding-sibling::span")
	private WebElement totalOrganizationsKpi;

	@FindBy(xpath = "//span[text()='Total Sync Runs (24h)']/preceding-sibling::span")
	private WebElement totalSyncRunsKpi;

	@FindBy(xpath = "//span[text()='Total Registered Devices']/preceding-sibling::span")
	private WebElement totalDevicesKpi;

	// Health Overview Live Indicator
	@FindBy(xpath = "//h2[text()='Organization Health']/ancestor::div[contains(@class,'rounded-xl')]//span[contains(@class,'rounded-full')][span]")
	private WebElement healthLiveBadge;

	// =========================================================================
	// 3. TABLE & FILTER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//input[contains(@placeholder,'Search by organization name or code')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(@class,'SelectTrigger') or contains(.,'Status') or contains(.,'All Status')]")
	private WebElement statusFilterDropdown;

	@FindBy(xpath = "//button[contains(.,'Clear') or contains(.,'Clear all')]")
	private WebElement clearFiltersButton;

	@FindBy(xpath = "//table/tbody/tr[not(contains(@class,'animate-pulse')) and td[1]]")
	private List<WebElement> organizationTableRows;

	@FindBy(xpath = "//button[contains(.,'Next') or @aria-label='Next page']")
	private WebElement nextPageButton;

	@FindBy(xpath = "//button[contains(.,'Previous') or @aria-label='Previous page']")
	private WebElement prevPageButton;

	// =========================================================================
	// 4. DETAIL VIEW LOCATORS (/super-admin/organization-sync/:id)
	// =========================================================================

	@FindBy(xpath = "//button[.//svg[contains(@class,'lucide-arrow-left')] or contains(.,'Back')]")
	private WebElement backButton;

	@FindBy(xpath = "//button[@role='tab' and (normalize-space()='Overview' or contains(.,'Overview'))]")
	private WebElement overviewTab;

	@FindBy(xpath = "//button[@role='tab' and (normalize-space()='Sync Health' or contains(.,'Sync Health'))]")
	private WebElement syncHealthTab;

	@FindBy(xpath = "//button[@role='tab' and (normalize-space()='Sync History' or contains(.,'Sync History'))]")
	private WebElement syncHistoryTab;

	@FindBy(xpath = "//button[@role='tab' and (normalize-space()='Devices' or contains(.,'Devices'))]")
	private WebElement devicesTab;

	// Resync Controls inside Sync Health tab
	@FindBy(xpath = "//button[contains(.,'Resync') or contains(.,'Trigger Resync')]")
	private WebElement triggerResyncButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//button[contains(.,'Confirm') or contains(.,'Resync')]")
	private WebElement confirmResyncModalButton;


	// =========================================================================
	// 5. ACTION METHODS: Navigation & Lifecycle
	// =========================================================================

	@StepName("Click on Organization Sync in Sidebar")
	public void clickOrganizationSync() {
		waitUtils.waitForClickable(organizationSyncLink);
		try {
			organizationSyncLink.click();
		} catch (Exception e) {
			clickUsingJS(organizationSyncLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Organization Sync Center is Loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Page Title Text")
	public String getPageTitle() {
		waitUtils.waitForVisibility(pageTitle);
		return pageTitle.getText().trim();
	}

	@StepName("Click Refresh Button")
	public void clickRefreshAll() {
		waitUtils.waitForClickable(refreshAllButton);
		refreshAllButton.click();
		waitForTableLoading();
	}

	@StepName("Wait for Sync Center Table to Finish Loading")
	public void waitForTableLoading() {
		try {
			By loaderLocator = By.xpath("//div[contains(@class,'InlineLoader')] | //tr[.//div[contains(@class,'animate-pulse')]]");
			waitUtils.waitForInvisibility(loaderLocator);
		} catch (Exception ignored) {
		}
	}


	// =========================================================================
	// 6. ACTION METHODS: KPI Section & Posture
	// =========================================================================

	@StepName("Get Total Organizations KPI Count")
	public String getTotalOrganizationsCount() {
		try {
			waitUtils.waitForVisibility(totalOrganizationsKpi);
			return totalOrganizationsKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Total Sync Runs (24h) KPI Count")
	public String getTotalSyncRuns24h() {
		try {
			waitUtils.waitForVisibility(totalSyncRunsKpi);
			return totalSyncRunsKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Total Registered Devices KPI Count")
	public String getTotalRegisteredDevices() {
		try {
			waitUtils.waitForVisibility(totalDevicesKpi);
			return totalDevicesKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Health Overview Live Indicator Status")
	public String getHealthLiveStatus() {
		try {
			waitUtils.waitForVisibility(healthLiveBadge);
			return healthLiveBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}


	// =========================================================================
	// 7. ACTION METHODS: Trends Window Selection
	// =========================================================================

	@StepName("Select Sync Trend Window: {0}")
	public void selectTrendWindow(String window) {
		By windowBtnLocator = By.xpath("//button[normalize-space()='" + window + "']");
		WebElement btn = waitUtils.waitForVisibility(windowBtnLocator);
		waitUtils.waitForClickable(btn);
		btn.click();
	}


	// =========================================================================
	// 8. ACTION METHODS: Table Filters & Search
	// =========================================================================

	@StepName("Search Organization: {0}")
	public void searchOrganization(String nameOrCode) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(nameOrCode);
		waitForTableLoading();
	}

	@StepName("Clear Search Input")
	public void clearSearch() {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		waitForTableLoading();
	}

	@StepName("Filter Table by Status: {0}")
	public void filterByStatus(String statusLabel) {
		waitUtils.waitForClickable(statusFilterDropdown);
		statusFilterDropdown.click();
		By optionLocator = By.xpath("//div[@role='option' or contains(@class,'SelectItem')][contains(.,'" + statusLabel + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Clear All Active Filters")
	public void clearFilters() {
		if (isElementPresent(clearFiltersButton)) {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForTableLoading();
		}
	}


	// =========================================================================
	// 9. ACTION METHODS: Organization Table Inspection & Actions
	// =========================================================================

	@StepName("Get Total Number of Displayed Organization Rows")
	public int getOrganizationRowCount() {
		return organizationTableRows.size();
	}

	private WebElement getRow(int rowIndex) {
		if (rowIndex < 1 || rowIndex > organizationTableRows.size()) {
			throw new IndexOutOfBoundsException("Row index " + rowIndex + " out of bounds for table rows count " + organizationTableRows.size());
		}
		return organizationTableRows.get(rowIndex - 1);
	}

	@StepName("Get Organization Name of Row {0}")
	public String getOrganizationName(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement nameEl = row.findElement(By.xpath("./td[1]//p[contains(@class,'font-semibold')]"));
		return nameEl.getText().trim();
	}

	@StepName("Get Organization Code of Row {0}")
	public String getOrganizationCode(int rowIndex) {
		try {
			WebElement row = getRow(rowIndex);
			WebElement codeEl = row.findElement(By.xpath("./td[1]//p[contains(@class,'font-mono')]"));
			return codeEl.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Organization Sync Status of Row {0}")
	public String getOrganizationStatus(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement statusEl = row.findElement(By.xpath("./td[2]//span[contains(@class,'rounded-full') or text()]"));
		return statusEl.getText().trim();
	}

	@StepName("Get Devices Count of Row {0}")
	public String getOrganizationDevicesCount(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement devEl = row.findElement(By.xpath("./td[6]"));
		return devEl.getText().trim();
	}

	@StepName("Click View Details for Row {0}")
	public void clickViewDetails(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement viewDetailsBtn = row.findElement(By.xpath("./td[last()]//button | ./td[last()]//a | .//button[contains(.,'View Details')]"));
		waitUtils.waitForClickable(viewDetailsBtn);
		try {
			viewDetailsBtn.click();
		} catch (Exception e) {
			clickUsingJS(viewDetailsBtn);
		}
		waitUtils.waitForVisibility(overviewTab);
	}

	@StepName("Click View Details for Organization: {0}")
	public void clickOrganizationByName(String orgName) {
		By rowLocator = By.xpath("//table/tbody/tr[.//p[normalize-space()='" + orgName + "']]");
		WebElement row = waitUtils.waitForVisibility(rowLocator);
		WebElement viewDetailsBtn = row.findElement(By.xpath(".//button[contains(.,'View Details')] | ./td[last()]//button"));
		waitUtils.waitForClickable(viewDetailsBtn);
		try {
			viewDetailsBtn.click();
		} catch (Exception e) {
			clickUsingJS(viewDetailsBtn);
		}
		waitUtils.waitForVisibility(overviewTab);
	}

	@StepName("Open First Organization Sync Detail if available")
	public boolean openFirstOrganizationDetail() {
		try {
			if (!organizationTableRows.isEmpty() && organizationTableRows.get(0).isDisplayed()) {
				WebElement viewDetailsBtn = organizationTableRows.get(0).findElement(By.xpath(".//button[contains(.,'View Details')] | .//a[contains(.,'View Details')] | ./td[last()]//button"));
				waitUtils.waitForClickable(viewDetailsBtn);
				try {
					viewDetailsBtn.click();
				} catch (Exception e) {
					clickUsingJS(viewDetailsBtn);
				}
				waitUtils.waitForVisibility(overviewTab);
				return true;
			}
		} catch (Exception e) {
			// No rows
		}
		return false;
	}

	@StepName("Check if Organization Sync Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(overviewTab);
			return overviewTab.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}


	// =========================================================================
	// 10. ACTION METHODS: Detail View Tabs & Resync Trigger
	// =========================================================================

	@StepName("Click Back to Organization Sync Center")
	public void clickBackButton() {
		waitUtils.waitForClickable(backButton);
		backButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Switch to Overview Tab")
	public void clickOverviewTab() {
		waitUtils.waitForClickable(overviewTab);
		overviewTab.click();
	}

	@StepName("Switch to Sync Health Tab")
	public void clickSyncHealthTab() {
		waitUtils.waitForClickable(syncHealthTab);
		syncHealthTab.click();
	}

	@StepName("Switch to Sync History Tab")
	public void clickSyncHistoryTab() {
		waitUtils.waitForClickable(syncHistoryTab);
		syncHistoryTab.click();
	}

	@StepName("Switch to Devices Tab")
	public void clickDevicesTab() {
		waitUtils.waitForClickable(devicesTab);
		devicesTab.click();
	}

	@StepName("Trigger Database Resync")
	public void triggerResync() {
		clickSyncHealthTab();
		waitUtils.waitForClickable(triggerResyncButton);
		triggerResyncButton.click();
		// Confirm in modal if modal opens
		if (isElementPresent(confirmResyncModalButton)) {
			waitUtils.waitForClickable(confirmResyncModalButton);
			confirmResyncModalButton.click();
		}
	}

	// Helper to safely check element presence without throwing
	private boolean isElementPresent(WebElement element) {
		try {
			return element != null && element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
