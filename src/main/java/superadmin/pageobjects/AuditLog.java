package superadmin.pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for Super Admin Immutable Audit Log:
 * 1. Audit Log Listing & Real-Time Monitoring (/super-admin/audit-logs)
 * 2. Header Actions (Refresh, Regulatory Notices Modal, Export Portal - XLS / PDF)
 * 3. FilterBar (Search, Date Presets & Custom Date Range, Category Combobox, Action Type Multi-Select, Clear All)
 * 4. Audit Log Table with Sortable Columns (Scope, Timestamp, User/Actor, Action Type, Category, Detail Toggle)
 * 5. Expandable In-Line Record Inspection (Event Summary, Performed By, Affected Item, What Changed diffs, Raw Details)
 * 6. Pagination Controls (Rows Per Page, Next/Prev Page)
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class AuditLog extends AbstractComponent {

	public AuditLog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/audit-logs']")
	private WebElement auditLogLink;

	@FindBy(xpath = "//h1[contains(text(),'Audit Log')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') and not(contains(.,'Export'))]")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Notices')]")
	private WebElement noticesButton;

	@FindBy(xpath = "//button[contains(.,'Export')]")
	private WebElement exportButton;

	// Export Portal Modal/Dropdown
	@FindBy(xpath = "//button[normalize-space()='xls' or normalize-space()='XLS']")
	private WebElement exportXlsFormatButton;

	@FindBy(xpath = "//button[normalize-space()='pdf' or normalize-space()='PDF']")
	private WebElement exportPdfFormatButton;

	@FindBy(xpath = "//button[.//p[text()='All Logs']]")
	private WebElement exportAllLogsButton;

	@FindBy(xpath = "//button[.//p[text()='Visible Logs']]")
	private WebElement exportVisibleLogsButton;

	// Regulatory Notices Modal
	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//h3[contains(text(),'Notices')]")
	private WebElement noticesModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//button[contains(@aria-label,'Close') or contains(@class,'close') or .//*[name()='svg']]")
	private WebElement closeNoticesModalButton;

	// =========================================================================
	// 2. FILTERBAR LOCATORS
	// =========================================================================

	@FindBy(xpath = "//input[contains(@placeholder,'Search by actor')]")
	private WebElement searchInput;

	// Date Range Controls
	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(.,'All Time') or contains(.,'Today') or contains(.,'Yesterday') or contains(.,'This Week') or contains(.,'This Month') or contains(.,'Last Month') or contains(.,'Custom Range')]")
	private WebElement datePresetDropdownButton;

	@FindBy(xpath = "//span[text()='From']/following-sibling::input[@type='date']")
	private WebElement dateFromInput;

	@FindBy(xpath = "//span[text()='To']/following-sibling::input[@type='date']")
	private WebElement dateToInput;

	// Category Filter
	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(.,'Category') or contains(.,'All Categories')]")
	private WebElement categoryDropdownButton;

	// Action Type Filter
	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(.,'Action Type') or contains(@placeholder,'Action Type')]")
	private WebElement actionTypeDropdownButton;

	// Clear All Filters
	@FindBy(xpath = "//button[contains(text(),'Clear') or contains(text(),'Clear all')]")
	private WebElement clearFiltersButton;

	// Total Entries Counter
	@FindBy(xpath = "//span[text()='entry' or text()='entries']/preceding-sibling::span[contains(@class,'text-titan-text')]")
	private WebElement totalEntriesBadge;

	// =========================================================================
	// 3. TABLE LOCATORS
	// =========================================================================

	@FindBy(xpath = "//span[contains(text(),'Showing')]")
	private WebElement showingEntriesText;

	@FindBy(xpath = "//span[text()='Rows per page:']/following-sibling::*//button")
	private WebElement rowsPerPageDropdownButton;

	// Column Headers (Sortable)
	@FindBy(xpath = "//th[contains(.,'Scope')]")
	private WebElement scopeHeader;

	@FindBy(xpath = "//th[contains(.,'Timestamp')]")
	private WebElement timestampHeader;

	@FindBy(xpath = "//th[contains(.,'User')]")
	private WebElement userHeader;

	@FindBy(xpath = "//th[contains(.,'Action Type')]")
	private WebElement actionTypeHeader;

	@FindBy(xpath = "//th[contains(.,'Category')]")
	private WebElement categoryHeader;

	// Table Rows (Main row items with 6 columns)
	@FindBy(xpath = "//table/tbody/tr[not(contains(@key,'-exp')) and not(contains(@class,'max-h-')) and count(td)>=5]")
	private List<WebElement> tableRows;

	// Empty State Container
	@FindBy(xpath = "//div[contains(@class,'flex flex-col items-center justify-center') and .//p[contains(text(),'No log entries found')]]")
	private WebElement emptyStateContainer;

	// Pagination Buttons
	@FindBy(xpath = "//button[contains(.,'Next') or @aria-label='Next page']")
	private WebElement nextPageButton;

	@FindBy(xpath = "//button[contains(.,'Previous') or @aria-label='Previous page']")
	private WebElement prevPageButton;


	// =========================================================================
	// 4. ACTION METHODS: Navigation & Page Lifecycle
	// =========================================================================

	@StepName("Click on Audit Log in Sidebar")
	public void clickAuditLog() {
		waitUtils.waitForClickable(auditLogLink);
		try {
			auditLogLink.click();
		} catch (Exception e) {
			clickUsingJS(auditLogLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Audit Log Page is Loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Audit Log Page Title")
	public String getPageTitle() {
		waitUtils.waitForVisibility(pageTitle);
		return pageTitle.getText().trim();
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		waitForTableLoading();
	}

	@StepName("Wait for Audit Log Table to Finish Loading")
	public void waitForTableLoading() {
		try {
			By loaderLocator = By.xpath("//div[contains(@class,'backdrop-blur')] | //div[contains(@class,'InlineLoader')]");
			waitUtils.waitForInvisibility(loaderLocator);
		} catch (Exception ignored) {
		}
	}


	// =========================================================================
	// 5. ACTION METHODS: Export Records Portal
	// =========================================================================

	@StepName("Open Export Dropdown Menu")
	public void openExportMenu() {
		waitUtils.waitForClickable(exportButton);
		exportButton.click();
		waitUtils.waitForVisibility(exportAllLogsButton);
	}

	@StepName("Select Export Format: {0}")
	public void selectExportFormat(String format) {
		if (format.equalsIgnoreCase("pdf")) {
			waitUtils.waitForClickable(exportPdfFormatButton);
			exportPdfFormatButton.click();
		} else {
			waitUtils.waitForClickable(exportXlsFormatButton);
			exportXlsFormatButton.click();
		}
	}

	@StepName("Export All Logs as {0}")
	public void exportAllLogs(String format) {
		openExportMenu();
		selectExportFormat(format);
		waitUtils.waitForClickable(exportAllLogsButton);
		exportAllLogsButton.click();
	}

	@StepName("Export Visible Filtered Logs as {0}")
	public void exportVisibleLogs(String format) {
		openExportMenu();
		selectExportFormat(format);
		waitUtils.waitForClickable(exportVisibleLogsButton);
		exportVisibleLogsButton.click();
	}


	// =========================================================================
	// 6. ACTION METHODS: Regulatory Notices Modal
	// =========================================================================

	@StepName("Open Regulatory Notices Modal")
	public void openNoticesModal() {
		waitUtils.waitForClickable(noticesButton);
		noticesButton.click();
		waitUtils.waitForVisibility(noticesModalTitle);
	}

	@StepName("Check if Notices Modal is Open")
	public boolean isNoticesModalOpen() {
		try {
			return noticesModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Close Notices Modal")
	public void closeNoticesModal() {
		if (isNoticesModalOpen()) {
			waitUtils.waitForClickable(closeNoticesModalButton);
			closeNoticesModalButton.click();
			waitUtils.waitForInvisibility(noticesModalTitle);
		}
	}

	@StepName("Get Notices Content Texts")
	public List<String> getNoticesTexts() {
		List<String> list = new ArrayList<>();
		List<WebElement> items = driver.findElements(By.xpath("//div[contains(@class,'TitanModal') or @role='dialog']//div[contains(@class,'rounded-xl')]"));
		for (WebElement item : items) {
			list.add(item.getText().trim());
		}
		return list;
	}


	// =========================================================================
	// 7. ACTION METHODS: Filters & Search
	// =========================================================================

	@StepName("Search Audit Logs: {0}")
	public void search(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (query != null && !query.isEmpty()) {
			searchInput.sendKeys(query, org.openqa.selenium.Keys.ENTER);
		}
		waitForTableLoading();
	}

	@StepName("Clear Search Input")
	public void clearSearch() {
		try {
			waitUtils.waitForVisibility(searchInput);
			searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE, org.openqa.selenium.Keys.ENTER);
			waitForTableLoading();
		} catch (Exception ignored) {}
	}

	@StepName("Select Date Preset: {0}")
	public void selectDatePreset(String presetLabel) {
		waitUtils.waitForClickable(datePresetDropdownButton);
		datePresetDropdownButton.click();
		By optionLocator = By.xpath("//button[normalize-space()='" + presetLabel + "' or contains(normalize-space(),'" + presetLabel + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Set Custom Date Range From: {0} To: {1}")
	public void setCustomDateRange(String fromDate, String toDate) {
		selectDatePreset("Custom Range");
		waitUtils.waitForVisibility(dateFromInput);
		dateFromInput.clear();
		dateFromInput.sendKeys(fromDate);
		dateToInput.clear();
		dateToInput.sendKeys(toDate);
		waitForTableLoading();
	}

	@StepName("Select Category Filter: {0}")
	public void selectCategory(String category) {
		waitUtils.waitForClickable(categoryDropdownButton);
		categoryDropdownButton.click();
		By optionLocator = By.xpath("//button[normalize-space()='" + category + "' or contains(normalize-space(),'" + category + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Select Action Type Filter: {0}")
	public void selectActionType(String actionType) {
		waitUtils.waitForClickable(actionTypeDropdownButton);
		actionTypeDropdownButton.click();
		By optionLocator = By.xpath("//button[normalize-space()='" + actionType + "' or contains(normalize-space(),'" + actionType + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		// Click outside to close dropdown if multi-select
		try {
			pageTitle.click();
		} catch (Exception ignored) {
		}
		waitForTableLoading();
	}

	@StepName("Clear All Active Filters")
	public void clearAllFilters() {
		if (isElementPresent(clearFiltersButton)) {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForTableLoading();
		}
	}

	@StepName("Get Total Filtered Entries Count Badge")
	public int getTotalEntriesCount() {
		try {
			waitUtils.waitForVisibility(totalEntriesBadge);
			String text = totalEntriesBadge.getText().replaceAll("[^0-9]", "");
			return Integer.parseInt(text);
		} catch (Exception e) {
			return 0;
		}
	}


	// =========================================================================
	// 8. ACTION METHODS: Table Inspection & Sorting
	// =========================================================================

	@StepName("Sort Table by Scope")
	public void sortByScope() {
		waitUtils.waitForClickable(scopeHeader);
		scopeHeader.click();
		waitForTableLoading();
	}

	@StepName("Sort Table by Timestamp")
	public void sortByTimestamp() {
		waitUtils.waitForClickable(timestampHeader);
		timestampHeader.click();
		waitForTableLoading();
	}

	@StepName("Sort Table by User/Actor")
	public void sortByUser() {
		waitUtils.waitForClickable(userHeader);
		userHeader.click();
		waitForTableLoading();
	}

	@StepName("Sort Table by Action Type")
	public void sortByActionType() {
		waitUtils.waitForClickable(actionTypeHeader);
		actionTypeHeader.click();
		waitForTableLoading();
	}

	@StepName("Sort Table by Category")
	public void sortByCategory() {
		waitUtils.waitForClickable(categoryHeader);
		categoryHeader.click();
		waitForTableLoading();
	}

	@StepName("Get Number of Displayed Log Rows")
	public int getTableRowsCount() {
		return tableRows.size();
	}

	@StepName("Check if Empty State is Displayed")
	public boolean isEmptyStateDisplayed() {
		return isElementPresent(emptyStateContainer);
	}

	private WebElement getRow(int rowIndex) {
		if (rowIndex < 1 || rowIndex > tableRows.size()) {
			throw new IndexOutOfBoundsException("Row index " + rowIndex + " out of bounds for table rows count " + tableRows.size());
		}
		return tableRows.get(rowIndex - 1);
	}

	@StepName("Get Scope of Row {0}")
	public String getRowScope(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement cell = row.findElement(By.xpath("./td[1]"));
		return cell.getText().trim();
	}

	@StepName("Get Timestamp of Row {0}")
	public String getRowTimestamp(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement cell = row.findElement(By.xpath("./td[2]"));
		return cell.getText().trim();
	}

	@StepName("Get User/Actor Name of Row {0}")
	public String getRowUser(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement cell = row.findElement(By.xpath("./td[3]//p[contains(@class,'font-semibold') or contains(@class,'text-titan-text')]"));
		return cell.getText().trim();
	}

	@StepName("Get Action Type of Row {0}")
	public String getRowActionType(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement cell = row.findElement(By.xpath("./td[4]"));
		return cell.getText().trim();
	}

	@StepName("Get Category of Row {0}")
	public String getRowCategory(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement cell = row.findElement(By.xpath("./td[5]"));
		return cell.getText().trim();
	}

	@StepName("Toggle Expansion for Row {0}")
	public void toggleRowExpansion(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement detailBtn = row.findElement(By.xpath("./td[6]//button | ./td[last()]//button"));
		waitUtils.waitForClickable(detailBtn);
		try {
			detailBtn.click();
		} catch (Exception e) {
			clickUsingJS(detailBtn);
		}
	}

	@StepName("Open First Audit Log Row Detail if available")
	public boolean openFirstRowDetail() {
		try {
			waitForTableLoading();
			By rowBy = By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]");
			List<WebElement> rows = driver.findElements(rowBy);
			if (rows.isEmpty()) return false;
			WebElement detailBtn = rows.get(0).findElement(By.xpath("./td[6]//button | ./td[last()]//button"));
			waitUtils.waitForClickable(detailBtn);
			clickUsingJS(detailBtn);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	// =========================================================================
	// 9. ACTION METHODS: Expandable Record Detail Inspection
	// =========================================================================

	@StepName("Get Event Summary of Expanded Row")
	public String getExpandedEventSummary() {
		try {
			By locator = By.xpath("//p[text()='Event Summary']/following-sibling::p");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Performed By Text of Expanded Row")
	public String getExpandedPerformedBy() {
		try {
			By locator = By.xpath("//p[contains(text(),'PERFORMED BY')]/ancestor::div[contains(@class,'rounded-xl')]//p[contains(@class,'font-semibold')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Affected Item Text of Expanded Row")
	public String getExpandedAffectedItem() {
		try {
			By locator = By.xpath("//p[contains(text(),'AFFECTED ITEM')]/ancestor::div[contains(@class,'rounded-xl')]//p[contains(@class,'font-semibold')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Changes (Diffs) from Expanded Row")
	public List<String> getExpandedWhatChanged() {
		List<String> changes = new ArrayList<>();
		List<WebElement> changeElements = driver.findElements(By.xpath("//p[text()='What changed']/following-sibling::div"));
		for (WebElement el : changeElements) {
			changes.add(el.getText().trim());
		}
		return changes;
	}

	@StepName("Get Details Map from Expanded Row")
	public Map<String, String> getExpandedDetailsMap() {
		Map<String, String> map = new HashMap<>();
		List<WebElement> rows = driver.findElements(By.xpath("//p[text()='Details']/following-sibling::dl//div"));
		for (WebElement r : rows) {
			try {
				String key = r.findElement(By.xpath(".//dt")).getText().trim();
				String val = r.findElement(By.xpath(".//dd")).getText().trim();
				map.put(key, val);
			} catch (Exception ignored) {
			}
		}
		return map;
	}


	// =========================================================================
	// 10. ACTION METHODS: Pagination
	// =========================================================================

	@StepName("Set Rows Per Page Limit: {0}")
	public void setRowsPerPage(int limit) {
		waitUtils.waitForClickable(rowsPerPageDropdownButton);
		rowsPerPageDropdownButton.click();
		By optionLocator = By.xpath("//button[normalize-space()='" + limit + "']");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Click Next Page Button")
	public void goToNextPage() {
		waitUtils.waitForClickable(nextPageButton);
		nextPageButton.click();
		waitForTableLoading();
	}

	@StepName("Click Previous Page Button")
	public void goToPreviousPage() {
		waitUtils.waitForClickable(prevPageButton);
		prevPageButton.click();
		waitForTableLoading();
	}

	@StepName("Get Showing Entries Summary Text")
	public String getShowingEntriesText() {
		try {
			waitUtils.waitForVisibility(showingEntriesText);
			return showingEntriesText.getText().trim();
		} catch (Exception e) {
			return "";
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
