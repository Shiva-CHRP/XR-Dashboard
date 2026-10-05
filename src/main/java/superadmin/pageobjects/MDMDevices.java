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
 * Enterprise Page Object for Super Admin MDM Devices:
 * 1. MDM Devices Listing (/super-admin/mdm-devices)
 * 2. Organisations Device Limits View (OrgDeviceLimits.jsx)
 * 3. Devices Fleet View (Table & KPI Cards)
 * 4. Device Management Actions (Edit Name, Deactivate, Delete, Activity Log)
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class MDMDevices extends AbstractComponent {

	public MDMDevices(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/mdm-devices']")
	private WebElement mdmDevicesLink;

	@FindBy(xpath = "//h1[contains(text(),'MDM Devices')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') or @title='Refresh']")
	private WebElement refreshButton;

	// Tabs: Organisations / Devices
	@FindBy(xpath = "//button[text()='Organisations' and not(contains(@class,'text-xs'))]")
	private WebElement organisationsTabButton;

	@FindBy(xpath = "//button[text()='Devices' and not(contains(@class,'text-xs'))]")
	private WebElement devicesTabButton;

	// =========================================================================
	// 2. DEVICES VIEW LOCATORS
	// =========================================================================

	// StatCards
	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Total devices')] or .//p[contains(text(),'Total devices')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement totalDevicesKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Active')] or .//p[contains(text(),'Active')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement activeDevicesKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Online now')] or .//p[contains(text(),'Online now')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement onlineDevicesKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Offline')] or .//p[contains(text(),'Offline')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement offlineDevicesKpi;

	// FilterBar
	@FindBy(xpath = "//input[contains(@placeholder,'Search by device name')]")
	private WebElement searchInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Status')]]")
	private WebElement statusFilterSelect;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Platforms')]]")
	private WebElement platformFilterSelect;

	@FindBy(xpath = "//button[contains(.,'Clear filters') or contains(@title,'Clear all filters')]")
	private WebElement clearFiltersButton;

	// Devices Table
	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> devicesTableRows;

	// =========================================================================
	// 3. ORGANISATIONS VIEW LOCATORS (OrgDeviceLimits)
	// =========================================================================

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> orgLimitsTableRows;

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Change device limit')] | //h3[contains(text(),'Change device limit')] | //div[@role='dialog']//h3[contains(text(),'Edit Device Limit')]")
	private WebElement editLimitModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//input[@type='number' or contains(@placeholder,'devices')]")
	private WebElement limitNumberInput;

	@FindBy(xpath = "//div[@role='dialog']//button[contains(text(),'Save') or contains(text(),'Update')]")
	private WebElement saveLimitButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel']")
	private WebElement cancelLimitButton;

	// =========================================================================
	// 4. ACTION MODALS LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Deactivate')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Deactivate')]")
	private WebElement deactivateModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(text(),'Deactivate')]")
	private WebElement confirmDeactivateButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Delete Device')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Delete Device')]")
	private WebElement deleteModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Delete') and (contains(@class,'danger') or contains(@class,'bg-titan-danger'))]")
	private WebElement confirmDeleteButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Activity Log')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Activity Log')]")
	private WebElement activityLogModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(text(),'Close')]")
	private WebElement closeActivityLogButton;


	// =========================================================================
	// ACTION METHODS: Navigation & Lifecycle
	// =========================================================================

	@StepName("Click on MDM Devices in Sidebar")
	public void clickMDMDevices() {
		waitUtils.waitForClickable(mdmDevicesLink);
		try {
			mdmDevicesLink.click();
		} catch (Exception e) {
			clickUsingJS(mdmDevicesLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if MDM Devices Page is Loaded")
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
		waitForLoadingToComplete();
	}

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: Tab Navigation
	// =========================================================================

	@StepName("Switch to Organisations Tab")
	public void clickOrganisationsTab() {
		waitUtils.waitForClickable(organisationsTabButton);
		organisationsTabButton.click();
		waitForLoadingToComplete();
	}

	@StepName("Switch to Devices Tab")
	public void clickDevicesTab() {
		waitUtils.waitForClickable(devicesTabButton);
		devicesTabButton.click();
		waitForLoadingToComplete();
	}

	// =========================================================================
	// ACTION METHODS: Devices KPI Stat Cards
	// =========================================================================

	@StepName("Get Total Devices KPI Count")
	public int getTotalDevicesCount() {
		try {
			waitUtils.waitForVisibility(totalDevicesKpi);
			String txt = totalDevicesKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Active Devices KPI Count")
	public int getActiveDevicesCount() {
		try {
			waitUtils.waitForVisibility(activeDevicesKpi);
			String txt = activeDevicesKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	// =========================================================================
	// ACTION METHODS: Filters & Search
	// =========================================================================

	@StepName("Search Devices: {0}")
	public void searchDevices(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
		waitForLoadingToComplete();
	}

	@StepName("Filter Devices by Status: {0}")
	public void filterByStatus(String status) {
		waitUtils.waitForVisibility(statusFilterSelect);
		selectByVisibleText(statusFilterSelect, status);
		waitForLoadingToComplete();
	}

	@StepName("Filter Devices by Platform: {0}")
	public void filterByPlatform(String platform) {
		waitUtils.waitForVisibility(platformFilterSelect);
		selectByVisibleText(platformFilterSelect, platform);
		waitForLoadingToComplete();
	}

	@StepName("Clear All Filters")
	public void clearAllFilters() {
		try {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForLoadingToComplete();
		} catch (Exception e) {
			searchInput.clear();
		}
	}

	// =========================================================================
	// ACTION METHODS: Devices Fleet Listing & Actions
	// =========================================================================

	@StepName("Get Displayed Device Names")
	public List<String> getDisplayedDeviceNames() {
		List<String> names = new ArrayList<>();
		for (WebElement row : devicesTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]//p[contains(@class,'font-semibold') or contains(@class,'font-medium')]"));
				names.add(nameEl.getText().trim());
			} catch (Exception ignored) {}
		}
		return names;
	}

	@StepName("Check if Device is Present: {0}")
	public boolean isDevicePresent(String deviceName) {
		return getDisplayedDeviceNames().stream()
				.anyMatch(n -> n.equalsIgnoreCase(deviceName) || n.contains(deviceName));
	}

	public WebElement findDeviceRow(String deviceName) {
		for (WebElement row : devicesTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]"));
				if (nameEl.getText().contains(deviceName)) {
					return row;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	public void openDeviceActionMenu(String deviceName) {
		WebElement row = findDeviceRow(deviceName);
		if (row != null) {
			WebElement menuBtn = row.findElement(By.xpath(".//button[contains(@class,'text-titan-muted') or @aria-label='Actions']"));
			waitUtils.waitForClickable(menuBtn);
			menuBtn.click();
		} else {
			throw new RuntimeException("Device row not found: " + deviceName);
		}
	}

	@StepName("Trigger Deactivate Device: {0}")
	public void triggerDeactivateDevice(String deviceName) {
		openDeviceActionMenu(deviceName);
		WebElement deactivateOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Deactivate')] | //button[contains(.,'Deactivate')]"));
		waitUtils.waitForClickable(deactivateOption);
		deactivateOption.click();
		waitUtils.waitForVisibility(deactivateModalTitle);
	}

	@StepName("Trigger Delete Device: {0}")
	public void triggerDeleteDevice(String deviceName) {
		openDeviceActionMenu(deviceName);
		WebElement deleteOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Delete')] | //button[contains(.,'Delete')]"));
		waitUtils.waitForClickable(deleteOption);
		deleteOption.click();
		waitUtils.waitForVisibility(deleteModalTitle);
	}

	@StepName("Open Activity Log for Device: {0}")
	public void openActivityLog(String deviceName) {
		openDeviceActionMenu(deviceName);
		WebElement logOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Activity Log')] | //button[contains(.,'Activity Log')]"));
		waitUtils.waitForClickable(logOption);
		logOption.click();
		waitUtils.waitForVisibility(activityLogModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Modals Operations
	// =========================================================================

	@StepName("Confirm Deactivate in Modal")
	public void confirmDeactivateDevice() {
		waitUtils.waitForClickable(confirmDeactivateButton);
		confirmDeactivateButton.click();
		waitUtils.waitForInvisibility(deactivateModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Confirm Delete in Modal")
	public void confirmDeleteDevice() {
		waitUtils.waitForClickable(confirmDeleteButton);
		confirmDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Close Activity Log Modal")
	public void closeActivityLogModal() {
		waitUtils.waitForClickable(closeActivityLogButton);
		closeActivityLogButton.click();
		waitUtils.waitForInvisibility(activityLogModalTitle);
	}



	@StepName("Open Edit Device Limit modal on first organisation row if present")
	public boolean openFirstOrgChangeLimitModal() {
		try {
			waitForLoadingToComplete();
			By btnBy = By.xpath("//button[contains(.,'Change limit')]");
			waitUtils.waitUntil(d -> !d.findElements(btnBy).isEmpty());
			List<WebElement> changeLimitBtns = driver.findElements(btnBy);
			if (!changeLimitBtns.isEmpty()) {
				WebElement btn = changeLimitBtns.get(0);
				waitUtils.waitForClickable(btn);
				clickUsingJS(btn);
				waitUtils.waitForVisibility(editLimitModalTitle);
				return true;
			}
		} catch (Exception e) {
			// No org rows or button not clickable
		}
		return false;
	}

	@StepName("Close / Cancel Edit Device Limit modal")
	public void cancelChangeLimitModal() {
		waitUtils.waitForClickable(cancelLimitButton);
		cancelLimitButton.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Check if Change Device Limit modal is displayed")
	public boolean isChangeLimitModalDisplayed() {
		try {
			return editLimitModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
