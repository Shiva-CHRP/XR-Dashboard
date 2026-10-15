package superadmin.pageobjects;

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

	@StepName("Review Application for Organization: {0}")
	public boolean reviewApplicationByName(String orgName) {
		try {
			searchApplications(orgName);
			By rowLocator = By.xpath("//table//tbody//tr[.//span[contains(text(),'" + orgName + "')] or .//td[contains(.,'" + orgName + "')]]//button[contains(.,'Review')]");
			List<WebElement> btns = driver.findElements(rowLocator);
			if (!btns.isEmpty() && btns.get(0).isDisplayed()) {
				waitUtils.waitForClickable(btns.get(0));
				btns.get(0).click();
				waitUtils.waitForVisibility(modalContainer);
				return true;
			}
		} catch (Exception e) {
			// Not found
		}
		return false;
	}

	@StepName("Accept New Industry in Review Modal")
	public void acceptNewIndustryIfPresent() {
		try {
			By acceptIndustryLocator = By.xpath("//div[@role='dialog']//button[contains(.,'Accept Industry') or contains(.,'Add Industry') or contains(.,'Accept new industry') or contains(.,'Add to Industries')] | //div[@role='dialog']//label[contains(.,'Accept') or contains(.,'Industry')]//input[@type='checkbox']");
			List<WebElement> acceptBtns = driver.findElements(acceptIndustryLocator);
			if (!acceptBtns.isEmpty() && acceptBtns.get(0).isDisplayed()) {
				waitUtils.waitForClickable(acceptBtns.get(0));
				acceptBtns.get(0).click();
			}
		} catch (Exception ignored) {}
	}

	@StepName("Enter Admin Temporary Password in Review Modal: {0}")
	public void enterAdminTemporaryPassword(String tempPassword) {
		try {
			By passwordLocator = By.xpath("//div[@role='dialog']//input[@type='password' or @name='temporaryPassword' or @name='tempPassword' or contains(@placeholder,'Password') or contains(@placeholder,'temporary')]");
			List<WebElement> passInputs = driver.findElements(passwordLocator);
			if (!passInputs.isEmpty() && passInputs.get(0).isDisplayed()) {
				passInputs.get(0).clear();
				passInputs.get(0).sendKeys(tempPassword);
			}
		} catch (Exception ignored) {}
	}

	@StepName("Configure Approval Portal & Branding: {0}")
	public void configureApprovalPortalAndBranding(String portalName, String logoPath, String faviconPath, String primaryColor, String sidebarStyle, String navColor) {
		// 1. Select Available Portal
		try {
			if (portalName != null && !portalName.isEmpty()) {
				By portalCardLocator = By.xpath("//div[@role='dialog']//button[contains(.,'" + portalName + "') or contains(.,'Select')] | //div[@role='dialog']//div[@role='radio' or @role='button'][contains(.,'" + portalName + "')]");
				List<WebElement> portals = driver.findElements(portalCardLocator);
				if (!portals.isEmpty() && portals.get(0).isDisplayed()) {
					waitUtils.waitForClickable(portals.get(0));
					portals.get(0).click();
				}
			}
		} catch (Exception ignored) {}

		// 2. Click Continue
		try {
			By continueBtnLocator = By.xpath("//div[@role='dialog']//button[normalize-space()='Continue' or contains(.,'Continue')]");
			List<WebElement> continueBtns = driver.findElements(continueBtnLocator);
			if (!continueBtns.isEmpty() && continueBtns.get(0).isDisplayed()) {
				waitUtils.waitForClickable(continueBtns.get(0));
				continueBtns.get(0).click();
			}
		} catch (Exception ignored) {}

		// 3. Upload Logo & Favicon in Assets
		try {
			if (logoPath != null && !logoPath.isEmpty()) {
				By logoInputLocator = By.xpath("//div[@role='dialog']//input[@type='file'][1]");
				List<WebElement> logoInputs = driver.findElements(logoInputLocator);
				if (!logoInputs.isEmpty()) {
					java.io.File file = new java.io.File(logoPath);
					if (file.exists()) {
						logoInputs.get(0).sendKeys(file.getAbsolutePath());
					}
				}
			}
			if (faviconPath != null && !faviconPath.isEmpty()) {
				By faviconInputLocator = By.xpath("//div[@role='dialog']//input[@type='file'][2]");
				List<WebElement> faviconInputs = driver.findElements(faviconInputLocator);
				if (!faviconInputs.isEmpty()) {
					java.io.File file = new java.io.File(faviconPath);
					if (file.exists()) {
						faviconInputs.get(0).sendKeys(file.getAbsolutePath());
					}
				}
			}
		} catch (Exception ignored) {}

		// 4. Primary Color
		try {
			if (primaryColor != null && !primaryColor.isEmpty()) {
				By primaryColorLocator = By.xpath("//div[@role='dialog']//input[@name='primaryColor' or @type='color' or contains(@placeholder,'#')]");
				List<WebElement> colorInputs = driver.findElements(primaryColorLocator);
				if (!colorInputs.isEmpty() && colorInputs.get(0).isDisplayed()) {
					colorInputs.get(0).clear();
					colorInputs.get(0).sendKeys(primaryColor);
				}
			}
		} catch (Exception ignored) {}

		// 5. Sidebar Style
		try {
			if (sidebarStyle != null && !sidebarStyle.isEmpty()) {
				By sidebarLocator = By.xpath("//div[@role='dialog']//button[contains(.,'" + sidebarStyle + "') or contains(@title,'" + sidebarStyle + "')] | //div[@role='dialog']//div[contains(@class,'card') and contains(.,'" + sidebarStyle + "')]");
				List<WebElement> sidebarBtns = driver.findElements(sidebarLocator);
				if (!sidebarBtns.isEmpty() && sidebarBtns.get(0).isDisplayed()) {
					sidebarBtns.get(0).click();
				}
			}
		} catch (Exception ignored) {}

		// 6. Navigation Colors
		try {
			if (navColor != null && !navColor.isEmpty()) {
				By navColorLocator = By.xpath("//div[@role='dialog']//input[@name='navColor' or @name='navigationColor' or contains(@placeholder,'#')][last()]");
				List<WebElement> navColorInputs = driver.findElements(navColorLocator);
				if (!navColorInputs.isEmpty() && navColorInputs.get(0).isDisplayed()) {
					navColorInputs.get(0).clear();
					navColorInputs.get(0).sendKeys(navColor);
				}
			}
		} catch (Exception ignored) {}
	}

	@StepName("Review, Configure and Approve Application")
	public void reviewConfigureAndApprove(OrgApplicationData data) {
		if (data.isNewIndustry()) {
			acceptNewIndustryIfPresent();
		}
		if (data.getTemporaryPassword() != null) {
			enterAdminTemporaryPassword(data.getTemporaryPassword());
		}
		configureApprovalPortalAndBranding(
				data.getPortalName() != null ? data.getPortalName() : "Client Portal",
				data.getLogoPath(),
				data.getFaviconPath(),
				data.getPrimaryColor(),
				data.getSidebarStyle(),
				data.getNavColor());
		approveApplicationInModal();
	}

	@StepName("Approve Application in Modal")
	public void approveApplicationInModal() {
		By approveBtnLocator = By.xpath("//div[@role='dialog']//button[contains(.,'Approve') or contains(.,'Create Organisation')]");
		waitUtils.waitForClickable(approveBtnLocator);
		WebElement approveBtn = driver.findElement(approveBtnLocator);
		approveBtn.click();
		// If confirmation dialog appears
		try {
			By confirmBtnLocator = By.xpath("//div[contains(@class,'fixed')]//button[contains(.,'Confirm') or contains(.,'Approve')]");
			List<WebElement> confirmBtns = driver.findElements(confirmBtnLocator);
			if (!confirmBtns.isEmpty() && confirmBtns.get(0).isDisplayed()) {
				confirmBtns.get(0).click();
			}
		} catch (Exception ignored) {}
	}

	@StepName("Reject Application in Modal with Reason: {0}")
	public void rejectApplicationInModal(String reason) {
		By rejectBtnLocator = By.xpath("//div[@role='dialog']//button[contains(.,'Reject')]");
		waitUtils.waitForClickable(rejectBtnLocator);
		WebElement rejectBtn = driver.findElement(rejectBtnLocator);
		rejectBtn.click();
		try {
			By reasonInput = By.xpath("//div[@role='dialog']//textarea | //div[contains(@class,'fixed')]//textarea");
			WebElement textarea = waitUtils.waitForVisibility(reasonInput);
			textarea.sendKeys(reason);
			By confirmBtnLocator = By.xpath("//div[contains(@class,'fixed')]//button[contains(.,'Confirm')]");
			waitUtils.waitForClickable(confirmBtnLocator);
			WebElement confirmBtn = driver.findElement(confirmBtnLocator);
			confirmBtn.click();
		} catch (Exception ignored) {}
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
