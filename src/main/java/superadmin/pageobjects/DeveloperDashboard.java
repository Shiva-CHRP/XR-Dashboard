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
 * DeveloperDashboard Page Object
 * 
 * Accurately models the VR Developer Dashboard at /developer
 * matching src/pages/Developer/Dashboard/index.jsx.
 * 
 * Strictly utilizes waitUtils and AbstractComponent wait wrappers.
 */
public class DeveloperDashboard extends AbstractComponent {

	public DeveloperDashboard(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Welcome Banner
	// =========================================================================

	@FindBy(xpath = "//a[@href='/developer']")
	private WebElement developerDashboardLink;

	@FindBy(xpath = "//div[contains(@class,'bg-titan-card')]//h1[contains(@class,'text-2xl')]")
	private WebElement developerNameHeading;

	@FindBy(xpath = "//div[contains(@class,'bg-titan-card')]//p[contains(text(),'Here')]")
	private WebElement welcomeSubtitle;

	@FindBy(xpath = "//span[contains(text(),'% approval rate')]")
	private WebElement approvalRateText;

	// =========================================================================
	// 2. KPI StatCards
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Total Submissions'] or .//span[text()='Total Submissions']]")
	private WebElement totalSubmissionsCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Approved'] or .//span[text()='Approved']]")
	private WebElement approvedCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Pending Review'] or .//span[text()='Pending Review']]")
	private WebElement pendingReviewCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Rejected'] or .//span[text()='Rejected']]")
	private WebElement rejectedCard;

	// =========================================================================
	// 3. Submission Pipeline Section
	// =========================================================================

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline') and contains(@class,'rounded-xl')]//button[contains(.,'View All')]")
	private WebElement pipelineViewAllButton;

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline')]//div[.//span[text()='Pending'] and contains(@class,'cursor-pointer')]")
	private WebElement pipelinePendingRow;

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline')]//div[.//span[text()='Approved'] and contains(@class,'cursor-pointer')]")
	private WebElement pipelineApprovedRow;

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline')]//div[.//span[text()='Rejected'] and contains(@class,'cursor-pointer')]")
	private WebElement pipelineRejectedRow;

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline')]//span[contains(@class,'text-titan-warning-text') or contains(@class,'dark:text-amber-400')]")
	private WebElement pipelinePendingCount;

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline')]//span[contains(@class,'text-titan-success-text') or contains(@class,'dark:text-emerald-400')]")
	private WebElement pipelineApprovedCount;

	@FindBy(xpath = "//div[contains(.,'Submission Pipeline')]//span[contains(@class,'text-titan-danger-text') or contains(@class,'dark:text-red-400')]")
	private WebElement pipelineRejectedCount;

	// =========================================================================
	// 4. Analytics Section
	// =========================================================================

	@FindBy(xpath = "//h2[text()='Analytics']")
	private WebElement analyticsHeading;

	@FindBy(xpath = "//h2[text()='Analytics']/../following-sibling::div//button")
	private List<WebElement> analyticsPeriodButtons;

	// =========================================================================
	// 5. Recent Submissions Section
	// =========================================================================

	@FindBy(xpath = "//div[contains(.,'Recent Submissions') and contains(@class,'rounded-xl')]//button[contains(.,'View All')]")
	private WebElement recentSubmissionsViewAllButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> recentSubmissionsRows;

	// =========================================================================
	// ACTION METHODS: Navigation & State Verification
	// =========================================================================

	@StepName("Click on the Developer Dashboard")
	public void clickDeveloperDashboard() {
		waitUtils.waitForClickable(developerDashboardLink);
		try {
			developerDashboardLink.click();
		} catch (Exception e) {
			clickUsingJS(developerDashboardLink);
		}
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.visibilityOf(developerNameHeading),
				ExpectedConditions.urlContains("/developer")
		));
	}

	@StepName("Verify Developer Dashboard is loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(developerNameHeading);
			return developerNameHeading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Developer Name from Welcome Banner")
	public String getDeveloperName() {
		waitUtils.waitForVisibility(developerNameHeading);
		return developerNameHeading.getText().trim();
	}

	@StepName("Get Approval Rate Text")
	public String getApprovalRate() {
		try {
			waitUtils.waitForVisibility(approvalRateText);
			return approvalRateText.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	// =========================================================================
	// ACTION METHODS: KPI Cards
	// =========================================================================

	@StepName("Get Total Submissions count from KPI card")
	public int getTotalSubmissionsCount() {
		return extractCountFromCard(totalSubmissionsCard);
	}

	@StepName("Get Approved Submissions count from KPI card")
	public int getApprovedCount() {
		return extractCountFromCard(approvedCard);
	}

	@StepName("Get Pending Submissions count from KPI card")
	public int getPendingCount() {
		return extractCountFromCard(pendingReviewCard);
	}

	@StepName("Get Rejected Submissions count from KPI card")
	public int getRejectedCount() {
		return extractCountFromCard(rejectedCard);
	}

	@StepName("Click Total Submissions KPI card")
	public void clickTotalSubmissionsCard() {
		waitUtils.waitForClickable(totalSubmissionsCard);
		totalSubmissionsCard.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Click Approved KPI card")
	public void clickApprovedCard() {
		waitUtils.waitForClickable(approvedCard);
		approvedCard.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Click Pending Review KPI card")
	public void clickPendingReviewCard() {
		waitUtils.waitForClickable(pendingReviewCard);
		pendingReviewCard.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Click Rejected KPI card")
	public void clickRejectedCard() {
		waitUtils.waitForClickable(rejectedCard);
		rejectedCard.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	// =========================================================================
	// ACTION METHODS: Submission Pipeline
	// =========================================================================

	@StepName("Click 'View All' in Submission Pipeline")
	public void clickPipelineViewAll() {
		waitUtils.waitForClickable(pipelineViewAllButton);
		pipelineViewAllButton.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Click Pending row in Submission Pipeline")
	public void clickPipelinePendingRow() {
		waitUtils.waitForClickable(pipelinePendingRow);
		pipelinePendingRow.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Click Approved row in Submission Pipeline")
	public void clickPipelineApprovedRow() {
		waitUtils.waitForClickable(pipelineApprovedRow);
		pipelineApprovedRow.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Click Rejected row in Submission Pipeline")
	public void clickPipelineRejectedRow() {
		waitUtils.waitForClickable(pipelineRejectedRow);
		pipelineRejectedRow.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	// =========================================================================
	// ACTION METHODS: Analytics & Recent Submissions
	// =========================================================================

	@StepName("Select Analytics period: {0}")
	public void selectAnalyticsPeriod(String periodLabel) {
		for (WebElement btn : analyticsPeriodButtons) {
			if (btn.getText().trim().equalsIgnoreCase(periodLabel)) {
				waitUtils.waitForClickable(btn);
				btn.click();
				return;
			}
		}
		throw new RuntimeException("Analytics period button not found: " + periodLabel);
	}

	@StepName("Click 'View All' in Recent Submissions")
	public void clickRecentSubmissionsViewAll() {
		waitUtils.waitForClickable(recentSubmissionsViewAllButton);
		recentSubmissionsViewAllButton.click();
		waitUtils.waitForUrlContains("/developer/submissions");
	}

	@StepName("Get Module Names of Recent Submissions")
	public List<String> getRecentSubmissionModuleNames() {
		List<String> names = new ArrayList<>();
		for (WebElement row : recentSubmissionsRows) {
			try {
				WebElement tdName = row.findElement(By.xpath(".//td[1]"));
				names.add(tdName.getText().trim());
			} catch (Exception ignored) {}
		}
		return names;
	}

	@StepName("Open Recent Submission by Module Name: {0}")
	public void openRecentSubmission(String moduleName) {
		for (WebElement row : recentSubmissionsRows) {
			try {
				WebElement tdName = row.findElement(By.xpath(".//td[1]"));
				if (tdName.getText().trim().equalsIgnoreCase(moduleName) || tdName.getText().contains(moduleName)) {
					waitUtils.waitForClickable(row);
					row.click();
					waitUtils.waitForUrlContains("/developer/submissions/");
					return;
				}
			} catch (Exception ignored) {}
		}
		throw new RuntimeException("Recent submission row not found for module: " + moduleName);
	}

	// =========================================================================
	// INTERNAL HELPERS
	// =========================================================================

	private int extractCountFromCard(WebElement card) {
		try {
			waitUtils.waitForVisibility(card);
			WebElement valueEl = card.findElement(By.xpath(".//div[contains(@class,'font-bold') or contains(@class,'text-2xl')]"));
			String digits = valueEl.getText().replaceAll("[^0-9]", "").trim();
			return digits.isEmpty() ? 0 : Integer.parseInt(digits);
		} catch (Exception e) {
			return 0;
		}
	}
}
