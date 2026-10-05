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
 * SubmissionTracker Page Object
 * 
 * Accurately models the VR Developer Submission Tracker at /developer/submissions
 * and submission details at /developer/submissions/:id
 * matching src/pages/Developer/Submissions/ (index.jsx, Detail.jsx).
 * 
 * Strictly utilizes waitUtils and AbstractComponent wait wrappers.
 */
public class SubmissionTracker extends AbstractComponent {

	public SubmissionTracker(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Header
	// =========================================================================

	@FindBy(xpath = "//a[@href='/developer/submissions']")
	private WebElement submissionTrackerMenuLink;

	@FindBy(xpath = "//h1[text()='Submission Tracker']")
	private WebElement pageTitle;

	@FindBy(xpath = "//p[contains(text(),'Track the status of all your module submissions')]")
	private WebElement pageSubtitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') and not(ancestor::div[contains(@class,'hidden')])]")
	private WebElement refreshButton;

	// =========================================================================
	// 2. Summary StatCards (Filter on Click)
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Total'] or .//span[text()='Total']]")
	private WebElement totalCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Approved'] or .//span[text()='Approved']]")
	private WebElement approvedCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Pending'] or .//span[text()='Pending']]")
	private WebElement pendingCard;

	@FindBy(xpath = "//div[contains(@class,'TitanCard') or contains(@class,'bg-titan-card')][.//p[text()='Rejected'] or .//span[text()='Rejected']]")
	private WebElement rejectedCard;

	// =========================================================================
	// 3. Filter Bar
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='Search by module or organisation…' or contains(@placeholder,'Search by module')]")
	private WebElement searchInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Status')]]")
	private WebElement statusSelect;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Orgs')]]")
	private WebElement organisationSelect;

	@FindBy(xpath = "//button[contains(.,'Clear Filters') or contains(.,'Clear all')]")
	private WebElement clearFiltersButton;

	// =========================================================================
	// 4. Submissions Table & Rows
	// =========================================================================

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> submissionRows;

	@FindBy(xpath = "//button[contains(.,'View Details')]")
	private WebElement menuViewDetailsButton;

	@FindBy(xpath = "//button[contains(.,'View Reason')]")
	private WebElement menuViewReasonButton;

	@FindBy(xpath = "//button[contains(.,'Re-upload')]")
	private WebElement menuReuploadButton;

	// =========================================================================
	// 5. Rejection Reason Modal / Dialog
	// =========================================================================

	@FindBy(xpath = "//div[contains(@role,'dialog')]//h2[contains(.,'Rejection Reason') or contains(.,'Reason')] | //h2[contains(.,'Rejection')]")
	private WebElement rejectionReasonModalTitle;

	@FindBy(xpath = "//div[contains(@role,'dialog')]//button[contains(@class,'lucide-x') or text()='Close']")
	private WebElement closeRejectionReasonModalButton;

	// =========================================================================
	// 6. Submission Details View (/developer/submissions/:id)
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Back to Submissions') or contains(.,'Back')]")
	private WebElement backToSubmissionsButton;

	@FindBy(xpath = "//h1[contains(@class,'text-2xl')]")
	private WebElement detailModuleTitle;

	@FindBy(xpath = "//div[contains(@class,'rounded-full') and (contains(text(),'Approved') or contains(text(),'Pending') or contains(text(),'Rejected'))]")
	private WebElement detailStatusBadge;

	@FindBy(xpath = "//h2[contains(text(),'Review Notes') or contains(text(),'Decision Rationale')]/following-sibling::p")
	private WebElement detailReviewNotes;

	// =========================================================================
	// ACTION METHODS: Navigation & Page State
	// =========================================================================

	@StepName("Click on Submission Tracker in sidebar")
	public void clickSubmissionTracker() {
		waitUtils.waitForClickable(submissionTrackerMenuLink);
		try {
			submissionTrackerMenuLink.click();
		} catch (Exception e) {
			clickUsingJS(submissionTrackerMenuLink);
		}
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.visibilityOf(pageTitle),
				ExpectedConditions.urlContains("/developer/submissions")
		));
	}

	@StepName("Verify Submission Tracker page is loaded")
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
		waitForTableToLoad();
	}

	// =========================================================================
	// ACTION METHODS: Summary Cards (Filter Toggles)
	// =========================================================================

	@StepName("Click 'Total' summary card to clear status filter")
	public void clickTotalCard() {
		waitUtils.waitForClickable(totalCard);
		totalCard.click();
		waitForTableToLoad();
	}

	@StepName("Click 'Approved' summary card to filter approved submissions")
	public void clickApprovedCard() {
		waitUtils.waitForClickable(approvedCard);
		approvedCard.click();
		waitForTableToLoad();
	}

	@StepName("Click 'Pending' summary card to filter pending submissions")
	public void clickPendingCard() {
		waitUtils.waitForClickable(pendingCard);
		pendingCard.click();
		waitForTableToLoad();
	}

	@StepName("Click 'Rejected' summary card to filter rejected submissions")
	public void clickRejectedCard() {
		waitUtils.waitForClickable(rejectedCard);
		rejectedCard.click();
		waitForTableToLoad();
	}

	// =========================================================================
	// ACTION METHODS: Filtering & Search
	// =========================================================================

	@StepName("Search submissions: {0}")
	public void searchSubmissions(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
		waitForTableToLoad();
	}

	@StepName("Filter by status dropdown: {0}")
	public void filterByStatusDropdown(String status) {
		waitUtils.waitForVisibility(statusSelect);
		selectByVisibleText(statusSelect, status);
		waitForTableToLoad();
	}

	@StepName("Filter by organisation dropdown: {0}")
	public void filterByOrganisationDropdown(String orgName) {
		waitUtils.waitForVisibility(organisationSelect);
		selectByVisibleText(organisationSelect, orgName);
		waitForTableToLoad();
	}

	@StepName("Clear all applied filters")
	public void clearAllFilters() {
		try {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForTableToLoad();
		} catch (Exception e) {
			searchInput.clear();
		}
	}

	// =========================================================================
	// ACTION METHODS: Submissions Table & Actions
	// =========================================================================

	@StepName("Get displayed module titles in submissions table")
	public List<String> getDisplayedSubmissionTitles() {
		List<String> titles = new ArrayList<>();
		for (WebElement row : submissionRows) {
			try {
				WebElement tdName = row.findElement(By.xpath(".//td[1]//p"));
				titles.add(tdName.getText().trim());
			} catch (Exception ignored) {}
		}
		return titles;
	}

	@StepName("Check if submission for module '{0}' is present")
	public boolean isSubmissionPresent(String moduleName) {
		return getDisplayedSubmissionTitles().stream()
				.anyMatch(name -> name.equalsIgnoreCase(moduleName) || name.contains(moduleName));
	}

	@StepName("Click submission row to open details: {0}")
	public void openSubmissionDetail(String moduleName) {
		for (WebElement row : submissionRows) {
			try {
				WebElement tdName = row.findElement(By.xpath(".//td[1]//p"));
				if (tdName.getText().toLowerCase().contains(moduleName.toLowerCase())) {
					waitUtils.waitForClickable(row);
					row.click();
					waitUtils.waitForVisibility(detailModuleTitle);
					return;
				}
			} catch (Exception ignored) {}
		}
		throw new RuntimeException("Submission row not found for module: " + moduleName);
	}

	@StepName("Open 3-dots action menu for submission: {0}")
	public void openSubmissionActionMenu(String moduleName) {
		for (WebElement row : submissionRows) {
			try {
				WebElement tdName = row.findElement(By.xpath(".//td[1]//p"));
				if (tdName.getText().toLowerCase().contains(moduleName.toLowerCase())) {
					WebElement menuBtn = row.findElement(By.xpath(".//button[contains(@class,'hover:text-titan-text')]"));
					waitUtils.waitForClickable(menuBtn);
					menuBtn.click();
					return;
				}
			} catch (Exception ignored) {}
		}
		throw new RuntimeException("Submission action menu not found for module: " + moduleName);
	}

	@StepName("Click 'View Reason' from 3-dots menu for rejected submission: {0}")
	public void viewRejectionReason(String moduleName) {
		openSubmissionActionMenu(moduleName);
		waitUtils.waitForClickable(menuViewReasonButton);
		menuViewReasonButton.click();
		waitUtils.waitForVisibility(rejectionReasonModalTitle);
	}

	@StepName("Close Rejection Reason modal")
	public void closeRejectionReasonModal() {
		waitUtils.waitForClickable(closeRejectionReasonModalButton);
		closeRejectionReasonModalButton.click();
		waitUtils.waitForInvisibility(rejectionReasonModalTitle);
	}

	@StepName("Click 'Re-upload' from 3-dots menu for rejected submission: {0}")
	public void clickReupload(String moduleName) {
		openSubmissionActionMenu(moduleName);
		waitUtils.waitForClickable(menuReuploadButton);
		menuReuploadButton.click();
		waitUtils.waitForUrlContains("/upload-version/");
	}

	// =========================================================================
	// ACTION METHODS: Submission Detail Page (/developer/submissions/:id)
	// =========================================================================

	@StepName("Click 'Back to Submissions' button")
	public void clickBackToSubmissions() {
		try {
			waitUtils.waitForClickable(backToSubmissionsButton);
			clickUsingJS(backToSubmissionsButton);
		} catch (Exception e) {
			clickUsingJS(submissionTrackerMenuLink);
		}
		try {
			waitUtils.waitUntil(d -> !d.getCurrentUrl().matches(".*/submissions/.+"));
		} catch (Exception e) {
			clickUsingJS(submissionTrackerMenuLink);
			waitUtils.waitUntil(d -> !d.getCurrentUrl().matches(".*/submissions/.+"));
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Get Module Title on Submission Detail page")
	public String getDetailModuleTitle() {
		waitUtils.waitForVisibility(detailModuleTitle);
		return detailModuleTitle.getText().trim();
	}

	@StepName("Get Status on Submission Detail page")
	public String getDetailStatus() {
		try {
			waitUtils.waitForVisibility(detailStatusBadge);
			return detailStatusBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Review Notes on Submission Detail page")
	public String getDetailReviewNotes() {
		try {
			waitUtils.waitForVisibility(detailReviewNotes);
			return detailReviewNotes.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Open First Submission Detail if available")
	public boolean openFirstSubmissionDetail() {
		try {
			waitForTableToLoad();
			By rowBy = By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]");
			List<WebElement> rows = driver.findElements(rowBy);
			if (rows.isEmpty()) return false;
			WebElement firstRow = rows.get(0);
			List<WebElement> actionBtns = firstRow.findElements(By.xpath(".//button[contains(.,'View Details') or contains(.,'View')]"));
			if (!actionBtns.isEmpty()) {
				waitUtils.waitForClickable(actionBtns.get(0));
				clickUsingJS(actionBtns.get(0));
			} else {
				waitUtils.waitForClickable(firstRow);
				clickUsingJS(firstRow);
			}
			waitUtils.waitForVisibility(backToSubmissionsButton);
			return true;
		} catch (Exception e) {
			// No submissions
		}
		return false;
	}

	@StepName("Check if Submission Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(backToSubmissionsButton);
			return backToSubmissionsButton.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// =========================================================================
	// INTERNAL HELPERS
	// =========================================================================

	private void waitForTableToLoad() {
		try {
			waitUtils.waitUntil(driver -> {
				boolean hasRows = !driver.findElements(By.xpath("//table//tbody//tr")).isEmpty();
				boolean empty = !driver.findElements(By.xpath("//*[contains(text(),'No submissions')]")).isEmpty();
				return hasRows || empty;
			});
		} catch (Exception ignored) {}
	}
}
