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
 * Enterprise Page Object for Super Admin Licence Management:
 * 1. Licence Listing & Consumption (/super-admin/license)
 * 2. KPI Cards (Total, Expired, Expiring Soon, Healthy)
 * 3. Search & Filter Bar
 * 4. Licence Table (Keys, Expiry, User & Device Consumption)
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class License extends AbstractComponent {

	public License(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & LISTING LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/license']")
	private WebElement licenseLink;

	@FindBy(xpath = "//h1[contains(text(),'Licence') or contains(text(),'License')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') or @title='Refresh']")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Export') or contains(@title,'Export')]")
	private WebElement exportButton;

	// KPI Cards
	@FindBy(xpath = "//div[contains(@class,'flex') or contains(@class,'grid')]//button[.//p[contains(text(),'Total Licences')]]//p[contains(@class,'text-4xl') or contains(@class,'font-bold')]")
	private WebElement totalLicencesKpi;

	@FindBy(xpath = "//div[contains(@class,'flex') or contains(@class,'grid')]//button[.//p[contains(text(),'Expired') and not(contains(text(),'Expiring'))]]//p[contains(@class,'text-4xl') or contains(@class,'font-bold')]")
	private WebElement expiredLicencesKpi;

	@FindBy(xpath = "//div[contains(@class,'flex') or contains(@class,'grid')]//button[.//p[contains(text(),'Expiring Soon')]]//p[contains(@class,'text-4xl') or contains(@class,'font-bold')]")
	private WebElement expiringSoonLicencesKpi;

	@FindBy(xpath = "//div[contains(@class,'flex') or contains(@class,'grid')]//button[.//p[contains(text(),'Healthy')]]//p[contains(@class,'text-4xl') or contains(@class,'font-bold')]")
	private WebElement healthyLicencesKpi;

	// FilterBar
	@FindBy(xpath = "//input[contains(@placeholder,'Search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(.,'Clear filters') or contains(@title,'Clear all filters')]")
	private WebElement clearFiltersButton;

	// Table Rows
	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> licenceTableRows;

	// Password Verification Modal
	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Verify Your Identity')] | //h3[contains(text(),'Verify Your Identity')]")
	private WebElement verifyIdentityModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//input[@type='password']")
	private WebElement verifyPasswordInput;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel']")
	private WebElement cancelVerifyButton;


	// =========================================================================
	// ACTION METHODS: Navigation & Lifecycle
	// =========================================================================

	@StepName("Click on License in Sidebar")
	public void clickLicense() {
		waitUtils.waitForClickable(licenseLink);
		try {
			licenseLink.click();
		} catch (Exception e) {
			clickUsingJS(licenseLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Licence Page is Loaded")
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

	@StepName("Click Export CSV Button")
	public void clickExport() {
		waitUtils.waitForClickable(exportButton);
		exportButton.click();
	}

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: KPI Cards
	// =========================================================================

	@StepName("Get Total Licences KPI Count")
	public int getTotalLicencesCount() {
		try {
			waitUtils.waitForVisibility(totalLicencesKpi);
			String txt = totalLicencesKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Expired Licences KPI Count")
	public int getExpiredLicencesCount() {
		try {
			waitUtils.waitForVisibility(expiredLicencesKpi);
			String txt = expiredLicencesKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Expiring Soon Licences KPI Count")
	public int getExpiringSoonLicencesCount() {
		try {
			waitUtils.waitForVisibility(expiringSoonLicencesKpi);
			String txt = expiringSoonLicencesKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Healthy Licences KPI Count")
	public int getHealthyLicencesCount() {
		try {
			waitUtils.waitForVisibility(healthyLicencesKpi);
			String txt = healthyLicencesKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Filter by KPI Card: {0}")
	public void clickKpiCard(String type) {
		WebElement card = null;
		if (type.equalsIgnoreCase("total")) card = totalLicencesKpi;
		else if (type.equalsIgnoreCase("expired")) card = expiredLicencesKpi;
		else if (type.equalsIgnoreCase("expiring")) card = expiringSoonLicencesKpi;
		else if (type.equalsIgnoreCase("healthy")) card = healthyLicencesKpi;

		if (card != null) {
			waitUtils.waitForClickable(card);
			card.click();
			waitForLoadingToComplete();
		}
	}

	// =========================================================================
	// ACTION METHODS: Search & Filters
	// =========================================================================

	@StepName("Search Licences: {0}")
	public void searchLicences(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
		waitForLoadingToComplete();
	}

	@StepName("Clear All Filters")
	public void clearAllFilters() {
		try {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForLoadingToComplete();
		} catch (Exception e) {
			searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE, org.openqa.selenium.Keys.ENTER);
			waitForLoadingToComplete();
		}
	}

	// =========================================================================
	// ACTION METHODS: Table Listing & Row Data
	// =========================================================================

	@StepName("Get Displayed Organisation Names")
	public List<String> getDisplayedOrganisationNames() {
		List<String> names = new ArrayList<>();
		for (WebElement row : licenceTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]//p[contains(@class,'font-semibold') or contains(@class,'font-medium')]"));
				names.add(nameEl.getText().trim());
			} catch (Exception ignored) {}
		}
		return names;
	}

	@StepName("Check if Organisation is Present: {0}")
	public boolean isOrganisationPresent(String orgName) {
		return getDisplayedOrganisationNames().stream()
				.anyMatch(n -> n.equalsIgnoreCase(orgName) || n.contains(orgName));
	}

	public WebElement findLicenceRow(String orgName) {
		for (WebElement row : licenceTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]"));
				if (nameEl.getText().contains(orgName)) {
					return row;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	@StepName("Copy Licence Key for: {0}")
	public void copyLicenceKey(String orgName) {
		WebElement row = findLicenceRow(orgName);
		if (row != null) {
			WebElement copyBtn = row.findElement(By.xpath(".//button[contains(@title,'Copy') or .//svg]"));
			waitUtils.waitForClickable(copyBtn);
			copyBtn.click();
		} else {
			throw new RuntimeException("Licence row not found for: " + orgName);
		}
	}

	@StepName("Get Licence Status Badge for: {0}")
	public String getLicenceStatus(String orgName) {
		WebElement row = findLicenceRow(orgName);
		if (row != null) {
			WebElement statusBadge = row.findElement(By.xpath(".//td[3]//span[contains(@class,'rounded-full')]"));
			return statusBadge.getText().trim();
		}
		return "";
	}

	@StepName("Search Licences with query: {0}")
	public void searchLicense(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (query != null && !query.isEmpty()) {
			searchInput.sendKeys(query);
		}
		waitForLoadingToComplete();
	}

	@StepName("Open First Copy Licence Key Verification Modal if present")
	public boolean openFirstCopyKeyVerificationModal() {
		try {
			clearAllFilters();
			waitUtils.waitUntil(d -> {
				List<WebElement> rows = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				return !rows.isEmpty() && rows.get(0).isDisplayed();
			});
			List<WebElement> copyBtns = driver.findElements(By.xpath("//button[@title='Copy licence key' or .//svg[contains(@class,'lucide-copy')]]"));
			if (!copyBtns.isEmpty() && copyBtns.get(0).isDisplayed()) {
				waitUtils.waitForClickable(copyBtns.get(0));
				copyBtns.get(0).click();
				waitUtils.waitForVisibility(verifyIdentityModalTitle);
				return true;
			}
		} catch (Exception e) {
			// Not present
		}
		return false;
	}

	@StepName("Cancel Password Verification Modal")
	public void cancelVerifyModal() {
		waitUtils.waitForClickable(cancelVerifyButton);
		cancelVerifyButton.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Check if Verify Identity Modal is Displayed")
	public boolean isVerifyModalDisplayed() {
		try {
			return verifyIdentityModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
