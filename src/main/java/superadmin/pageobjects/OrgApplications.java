package superadmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for Super Admin Organisation Applications:
 * Route: /super-admin/org-applications
 *
 * Covers:
 * 1. Applications Listing & Header Controls
 * 2. Client Application Link Card (Copy & External Navigation)
 * 3. Status StatCards (All, Approved, Pending review, Rejected)
 * 4. Search Filter & Clear Controls
 * 5. Applications Table & Review Detail Modal Inspection
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero ad-hoc WebDriverWait)
 * - Inherits ToastUtils helpers
 */
public class OrgApplications extends AbstractComponent {

	public OrgApplications(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/org-applications' or .//span[text()='Applications']]")
	private WebElement applicationsNavLink;

	@FindBy(xpath = "//h1[contains(text(),'Organisation Applications')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh')]")
	private WebElement refreshButton;

	// =========================================================================
	// 2. CLIENT APPLICATION LINK CARD
	// =========================================================================

	@FindBy(xpath = "//input[@aria-label='Application link']")
	private WebElement applicationLinkInput;

	@FindBy(xpath = "//button[contains(.,'Copy') or contains(.,'Copied')]")
	private WebElement copyLinkButton;

	@FindBy(xpath = "//a[@title='Open the form in a new tab']")
	private WebElement openFormNewTabLink;

	// =========================================================================
	// 3. STATCARDS / STATUS FILTER TABS
	// =========================================================================

	@FindBy(xpath = "//div[.//p[text()='All applications'] or .//span[text()='All applications']]")
	private WebElement allApplicationsTab;

	@FindBy(xpath = "//div[.//p[text()='Approved'] or .//span[text()='Approved']]")
	private WebElement approvedTab;

	@FindBy(xpath = "//div[.//p[text()='Pending review'] or .//span[text()='Pending review']]")
	private WebElement pendingReviewTab;

	@FindBy(xpath = "//div[.//p[text()='Rejected'] or .//span[text()='Rejected']]")
	private WebElement rejectedTab;

	// =========================================================================
	// 4. SEARCH BAR & TABLE
	// =========================================================================

	@FindBy(xpath = "//input[contains(@placeholder,'Search by organisation')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[@aria-label='Clear search']")
	private WebElement clearSearchButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> applicationsTableRows;

	// =========================================================================
	// 5. DETAIL MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']")
	private WebElement modalContainer;

	@FindBy(xpath = "//div[@role='dialog']//button[@aria-label='Close']")
	private WebElement modalCloseButton;

	// =========================================================================
	// 6. ACTION METHODS
	// =========================================================================

	@StepName("Click Applications in Super Admin Sidebar")
	public void clickApplications() {
		waitUtils.waitForClickable(applicationsNavLink);
		try {
			applicationsNavLink.click();
		} catch (Exception e) {
			clickUsingJS(applicationsNavLink);
		}
		waitUtils.waitForUrlContains("/super-admin/org-applications");
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Organisation Applications page is loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForUrlContains("/super-admin/org-applications");
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Application Link Value")
	public String getApplicationLinkValue() {
		waitUtils.waitForVisibility(applicationLinkInput);
		return applicationLinkInput.getAttribute("value");
	}

	@StepName("Click Copy Application Link Button")
	public void clickCopyLink() {
		waitUtils.waitForClickable(copyLinkButton);
		copyLinkButton.click();
	}

	@StepName("Filter Applications by Tab")
	public void filterByTab(String tabName) {
		WebElement targetTab;
		if ("Approved".equalsIgnoreCase(tabName)) {
			targetTab = approvedTab;
		} else if ("Pending review".equalsIgnoreCase(tabName) || "Pending".equalsIgnoreCase(tabName)) {
			targetTab = pendingReviewTab;
		} else if ("Rejected".equalsIgnoreCase(tabName)) {
			targetTab = rejectedTab;
		} else {
			targetTab = allApplicationsTab;
		}
		waitUtils.waitForClickable(targetTab);
		targetTab.click();
		pause(500);
	}

	@StepName("Search Applications")
	public void searchApplications(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
		pause(500);
	}

	@StepName("Clear Search Input")
	public void clearSearch() {
		try {
			if (clearSearchButton.isDisplayed()) {
				waitUtils.waitForClickable(clearSearchButton);
				clearSearchButton.click();
				pause(400);
				return;
			}
		} catch (Exception ignored) {
		}
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		pause(400);
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		pause(500);
	}

	@StepName("Get Applications Table Row Count")
	public int getApplicationsRowCount() {
		try {
			return applicationsTableRows.size();
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Review First Application if Available")
	public boolean viewFirstApplicationIfAvailable() {
		try {
			List<WebElement> reviewButtons = driver.findElements(By.xpath("//table//tbody//tr//button[contains(.,'Review')]"));
			if (!reviewButtons.isEmpty() && reviewButtons.get(0).isDisplayed()) {
				waitUtils.waitForClickable(reviewButtons.get(0));
				reviewButtons.get(0).click();
				waitUtils.waitForVisibility(modalContainer);
				return true;
			}
		} catch (Exception e) {
			// No actionable row or empty table
		}
		return false;
	}

	@StepName("Close Application Detail Modal")
	public void closeDetailModalIfOpen() {
		try {
			if (modalCloseButton.isDisplayed()) {
				waitUtils.waitForClickable(modalCloseButton);
				modalCloseButton.click();
				waitUtils.waitForInvisibility(modalContainer);
			}
		} catch (Exception ignored) {
		}
	}

	private void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
		}
	}
}
