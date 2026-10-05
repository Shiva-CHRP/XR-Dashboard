package superadmin.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ReviewQueue extends AbstractComponent {

	public ReviewQueue(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Header
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/modules/review']")
	private WebElement reviewQueueMenu;

	@FindBy(xpath = "//h1[contains(text(),'Module Review') or contains(text(),'Review Queue')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh')]")
	private WebElement refreshButton;

	// =========================================================================
	// 2. KPI StatCards
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Pending Review'] or .//div[contains(text(),'Pending Review')]]")
	private WebElement pendingReviewCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[contains(text(),'Overdue')] or .//div[contains(text(),'Overdue')]]")
	private WebElement overdueCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Approved'] or .//div[contains(text(),'Approved')]]")
	private WebElement approvedCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Rejected'] or .//div[contains(text(),'Rejected')]]")
	private WebElement rejectedCard;

	// =========================================================================
	// 3. Search & Filters
	// =========================================================================

	@FindBy(xpath = "//input[contains(@placeholder,'Search by module name') or contains(@placeholder,'Search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(@class,'rounded-md') and .//span[contains(text(),'Status') or contains(text(),'Pending') or contains(text(),'Approved') or contains(text(),'Rejected')]] | //select[.//option[contains(text(),'Status')]]")
	private WebElement statusFilterTrigger;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(@class,'rounded-md') and .//span[contains(text(),'Platform')]] | //select[.//option[contains(text(),'Platform')]]")
	private WebElement platformFilterTrigger;

	// =========================================================================
	// 4. Table Elements
	// =========================================================================

	@FindBy(xpath = "//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]")
	private List<WebElement> tableRows;

	// =========================================================================
	// 5. Review Detail Page Elements (/super-admin/modules/review/:versionId)
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Back to Review Queue') or contains(.,'Back to Queue')]")
	private WebElement backToQueueButton;

	@FindBy(xpath = "//h1")
	private WebElement detailModuleTitle;

	@FindBy(xpath = "//div[contains(@class,'flex')]//span[contains(@class,'rounded-full') and (contains(text(),'Pending') or contains(text(),'Approved') or contains(text(),'Rejected') or contains(text(),'Archived'))]")
	private WebElement detailStatusBadge;

	@FindBy(xpath = "//h2[contains(text(),'Module Overview')]/following-sibling::p")
	private WebElement detailDescription;

	@FindBy(xpath = "//p[text()='VERSION']/following-sibling::div//span")
	private WebElement detailVersion;

	@FindBy(xpath = "//p[text()='SIMULATION TYPE']/following-sibling::div")
	private WebElement detailSimulationType;

	@FindBy(xpath = "//p[text()='FILE TYPE']/following-sibling::div//span")
	private WebElement detailFileType;

	@FindBy(xpath = "//p[text()='DEVELOPER']/following-sibling::div//span[contains(@class,'text-sm')]")
	private WebElement detailDeveloper;

	@FindBy(xpath = "//p[text()='ORGANISATION']/following-sibling::div//span[contains(@class,'text-sm')]")
	private WebElement detailOrganisation;

	@FindBy(xpath = "//p[text()='FILE SIZE']/following-sibling::div")
	private WebElement detailFileSize;

	@FindBy(xpath = "//button[contains(.,'Download') or .//*[local-name()='svg' and contains(@class,'lucide-download')]]")
	private WebElement downloadModuleButton;

	// Decision Card (Right Panel)
	@FindBy(xpath = "//textarea[contains(@placeholder,'Describe the reason for your decision')]")
	private WebElement decisionRationaleTextarea;

	@FindBy(xpath = "//button[contains(.,'Approve') and .//*[local-name()='svg' and contains(@class,'lucide-check-circle')]]")
	private WebElement approveButton;

	@FindBy(xpath = "//button[contains(.,'Reject') and .//*[local-name()='svg' and contains(@class,'lucide-x-circle')]]")
	private WebElement rejectButton;

	// Decision Confirmation Modal
	@FindBy(xpath = "//div[@role='dialog']//h2 | //div[@role='dialog']//h3 | //div[contains(@class,'fixed')]//h2")
	private WebElement modalTitle;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel'] | //div[contains(@class,'fixed')]//button[normalize-space()='Cancel']")
	private WebElement modalCancelButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Approve & Assign' or normalize-space()='Confirm Rejection'] | //div[contains(@class,'fixed')]//button[normalize-space()='Approve & Assign' or normalize-space()='Confirm Rejection']")
	private WebElement modalConfirmButton;

	// =========================================================================
	// Action Methods: Navigation & KPI Cards
	// =========================================================================

	@StepName("Click on the Review Queue")
	public void clickReviewQueue() {
		waitElementToBeClickable(reviewQueueMenu);
		reviewQueueMenu.click();
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitElementToBeClickable(refreshButton);
		refreshButton.click();
	}

	@StepName("Click Pending Review KPI Card")
	public void clickPendingReviewCard() {
		waitElementToBeClickable(pendingReviewCard);
		pendingReviewCard.click();
	}

	@StepName("Click Overdue KPI Card")
	public void clickOverdueCard() {
		waitElementToBeClickable(overdueCard);
		overdueCard.click();
	}

	@StepName("Click Approved KPI Card")
	public void clickApprovedCard() {
		waitElementToBeClickable(approvedCard);
		approvedCard.click();
	}

	@StepName("Click Rejected KPI Card")
	public void clickRejectedCard() {
		waitElementToBeClickable(rejectedCard);
		rejectedCard.click();
	}

	@StepName("Get Pending Review Count")
	public String getPendingReviewCount() {
		return pendingReviewCard.getText();
	}

	@StepName("Get Approved Count")
	public String getApprovedCount() {
		return approvedCard.getText();
	}

	@StepName("Get Rejected Count")
	public String getRejectedCount() {
		return rejectedCard.getText();
	}

	// =========================================================================
	// Action Methods: Search & Filters
	// =========================================================================

	@StepName("Search Module by Name")
	public void searchModule(String query) {
		waitElementToBeClickable(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Filter Review Queue by Status")
	public void filterByStatus(String status) {
		waitElementToBeClickable(statusFilterTrigger);
		if ("select".equalsIgnoreCase(statusFilterTrigger.getTagName())) {
			new Select(statusFilterTrigger).selectByVisibleText(status);
		} else {
			statusFilterTrigger.click();
			WebElement option = driver.findElement(By.xpath(
					"//div[@role='menu']//div[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
							+ status.toLowerCase() + "')] | //button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
							+ status.toLowerCase() + "')] | //div[@role='option'][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
							+ status.toLowerCase() + "')]"));
			option.click();
		}
	}

	@StepName("Filter Review Queue by Platform")
	public void filterByPlatform(String platform) {
		waitElementToBeClickable(platformFilterTrigger);
		if ("select".equalsIgnoreCase(platformFilterTrigger.getTagName())) {
			new Select(platformFilterTrigger).selectByVisibleText(platform);
		} else {
			platformFilterTrigger.click();
			WebElement option = driver.findElement(By.xpath(
					"//div[@role='menu']//div[contains(text(),'" + platform + "')] | //button[contains(text(),'" + platform
							+ "')] | //div[@role='option'][contains(text(),'" + platform + "')]"));
			option.click();
		}
	}

	// =========================================================================
	// Action Methods: Queue Table
	// =========================================================================

	@StepName("Get Total Row Count in Review Queue Table")
	public int getRowCount() {
		return tableRows.size();
	}

	@StepName("Verify Module Present in Review Queue")
	public boolean isModulePresent(String moduleName) {
		try {
			searchModule(moduleName);
			WebElement row = driver.findElement(By.xpath(
					"//tr[.//span[contains(text(),'" + moduleName + "')] or .//td[contains(.,'" + moduleName + "')]]"));
			return row.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click on Module Row for Review")
	public void clickModuleForReview(String moduleName) {
		searchModule(moduleName);
		WebElement row = driver.findElement(By.xpath(
				"//tr[.//span[contains(text(),'" + moduleName + "')] or .//td[contains(.,'" + moduleName + "')]]"));
		waitElementToBeClickable(row);
		row.click();
	}

	@StepName("Get Module Status from Table Row")
	public String getModuleStatus(String moduleName) {
		try {
			WebElement statusBadge = driver.findElement(By.xpath(
					"//tr[.//span[contains(text(),'" + moduleName + "')] or .//td[contains(.,'" + moduleName + "')]]//span[contains(@class,'StatusBadge') or contains(@class,'rounded-full')]"));
			return statusBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	// =========================================================================
	// Action Methods: Review Detail Page & Decisions
	// =========================================================================

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	@StepName("Click Back to Review Queue")
	public void clickBackToQueue() {
		try {
			waitUtils.waitForClickable(backToQueueButton);
			clickUsingJS(backToQueueButton);
		} catch (Exception e) {
			clickUsingJS(reviewQueueMenu);
		}
		try {
			waitUtils.waitUntil(d -> !d.getCurrentUrl().matches(".*/modules/review/.+"));
		} catch (Exception e) {
			clickUsingJS(reviewQueueMenu);
			waitUtils.waitUntil(d -> !d.getCurrentUrl().matches(".*/modules/review/.+"));
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Get Detail Page Module Title")
	public String getDetailModuleTitle() {
		waitElementToBeClickable(detailModuleTitle);
		return detailModuleTitle.getText().trim();
	}

	@StepName("Get Detail Page Status Badge Text")
	public String getDetailStatus() {
		waitElementToBeClickable(detailStatusBadge);
		return detailStatusBadge.getText().trim();
	}

	@StepName("Get Detail Page Version")
	public String getDetailVersion() {
		return detailVersion.getText().trim();
	}

	@StepName("Get Detail Page Simulation Type")
	public String getDetailSimulationType() {
		return detailSimulationType.getText().trim();
	}

	@StepName("Get Detail Page File Type")
	public String getDetailFileType() {
		return detailFileType.getText().trim();
	}

	@StepName("Get Detail Page Developer Name")
	public String getDetailDeveloper() {
		return detailDeveloper.getText().trim();
	}

	@StepName("Get Detail Page Organisation Name")
	public String getDetailOrganisation() {
		return detailOrganisation.getText().trim();
	}

	@StepName("Click Download Module File")
	public void clickDownloadModuleFile() {
		waitElementToBeClickable(downloadModuleButton);
		downloadModuleButton.click();
	}

	@StepName("Enter Decision Rationale")
	public void enterDecisionRationale(String rationale) {
		waitElementToBeClickable(decisionRationaleTextarea);
		decisionRationaleTextarea.clear();
		decisionRationaleTextarea.sendKeys(rationale);
	}

	@StepName("Click Approve Button")
	public void clickApproveButton() {
		waitElementToBeClickable(approveButton);
		approveButton.click();
	}

	@StepName("Click Reject Button")
	public void clickRejectButton() {
		waitElementToBeClickable(rejectButton);
		rejectButton.click();
	}

	@StepName("Confirm Decision in Modal Dialog")
	public void confirmDecisionInModal() {
		waitElementToBeClickable(modalConfirmButton);
		modalConfirmButton.click();
	}

	@StepName("Cancel Decision Modal Dialog")
	public void cancelDecisionModal() {
		waitElementToBeClickable(modalCancelButton);
		modalCancelButton.click();
	}

	@StepName("Complete Approve Flow with Rationale")
	public void approveModuleWithRationale(String rationale) {
		enterDecisionRationale(rationale);
		clickApproveButton();
		confirmDecisionInModal();
	}

	@StepName("Complete Reject Flow with Rationale")
	public void rejectModuleWithRationale(String rationale) {
		enterDecisionRationale(rationale);
		clickRejectButton();
		confirmDecisionInModal();
	}

	@StepName("Open First Review Row Detail if available")
	public boolean openFirstReviewDetail() {
		try {
			waitForLoadingToComplete();
			By rowLocator = By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]");
			List<WebElement> rows = driver.findElements(rowLocator);
			if (rows.isEmpty()) {
				return false;
			}
			WebElement firstRow = rows.get(0);
			List<WebElement> actionBtns = firstRow.findElements(By.xpath(".//button[contains(.,'Review') or contains(.,'View')]"));
			if (!actionBtns.isEmpty()) {
				waitUtils.waitForClickable(actionBtns.get(0));
				clickUsingJS(actionBtns.get(0));
			} else {
				waitUtils.waitForClickable(firstRow);
				clickUsingJS(firstRow);
			}
			waitUtils.waitForUrlContains("/modules/review/");
			waitUtils.waitForVisibility(backToQueueButton);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Review Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(backToQueueButton);
			return backToQueueButton.isDisplayed() && driver.getCurrentUrl().contains("/modules/review/");
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Review Queue Page is Loaded")
	public boolean isPageLoaded() {
		try {
			boolean onQueueUrl = driver.getCurrentUrl().contains("/modules/review") && !driver.getCurrentUrl().matches(".*/modules/review/.+");
			if (!onQueueUrl) {
				return false;
			}
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			try {
				WebElement heading = waitUtils.waitForVisibility(By.xpath("//h1[contains(text(),'Module Review') or contains(text(),'Review Queue')]"));
				return heading.isDisplayed();
			} catch (Exception ex) {
				return false;
			}
		}
	}
}
