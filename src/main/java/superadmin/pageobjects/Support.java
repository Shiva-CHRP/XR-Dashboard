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
 * Enterprise Page Object for Super Admin Support Ticket Management:
 * 1. Support Tickets Listing & Stat Cards (/super-admin/support)
 * 2. Status Scope Tabs (Active vs Resolved with Badge Counts)
 * 3. FilterBar (Organization, Category, Priority, Date Range Pickers)
 * 4. Ticket Table (Subject, Ticket Number, Organization, Category, Priority, Status, Created Date)
 * 5. Ticket Detail View (/super-admin/support/:id) with Status Transition & Tabs (Details, Identity, Device)
 * 6. Real-Time Ticket Chat Conversation (Reply Text, Send Message, Image Attachments)
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class Support extends AbstractComponent {

	public Support(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/support']")
	private WebElement supportLink;

	@FindBy(xpath = "//h1[contains(text(),'Support')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') or @aria-label='Refresh tickets']")
	private WebElement refreshButton;

	// =========================================================================
	// 2. STAT CARDS LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[text()='Open']]//p[contains(@class,'text-2xl') or contains(@class,'font-bold')]")
	private WebElement openTicketsKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[text()='In Progress']]//p[contains(@class,'text-2xl') or contains(@class,'font-bold')]")
	private WebElement inProgressTicketsKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[text()='Reopened']]//p[contains(@class,'text-2xl') or contains(@class,'font-bold')]")
	private WebElement reopenedTicketsKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[text()='Resolved']]//p[contains(@class,'text-2xl') or contains(@class,'font-bold')]")
	private WebElement resolvedTicketsKpi;

	// =========================================================================
	// 3. TABS & FILTER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//button[normalize-space()='Active' or contains(.,'Active')]")
	private WebElement activeTabButton;

	@FindBy(xpath = "//button[normalize-space()='Resolved' or contains(.,'Resolved')]")
	private WebElement resolvedTabButton;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(.,'Organization') or contains(.,'All Organizations')]")
	private WebElement organizationFilterDropdown;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(.,'Category') or contains(.,'All Categories')]")
	private WebElement categoryFilterDropdown;

	@FindBy(xpath = "//div[contains(@class,'FilterBar')]//button[contains(.,'Priority') or contains(.,'All Priorities')]")
	private WebElement priorityFilterDropdown;

	@FindBy(xpath = "//input[@aria-label='Raised from']")
	private WebElement dateFromInput;

	@FindBy(xpath = "//input[@aria-label='Raised to']")
	private WebElement dateToInput;

	@FindBy(xpath = "//button[contains(.,'Clear') or contains(.,'Clear all')]")
	private WebElement clearFiltersButton;

	// =========================================================================
	// 4. TICKETS TABLE LOCATORS
	// =========================================================================

	@FindBy(xpath = "//table/tbody/tr[not(contains(@class,'empty')) and td[1]]")
	private List<WebElement> ticketTableRows;

	@FindBy(xpath = "//button[contains(.,'Next') or @aria-label='Next page']")
	private WebElement nextPageButton;

	@FindBy(xpath = "//button[contains(.,'Previous') or @aria-label='Previous page']")
	private WebElement prevPageButton;

	// =========================================================================
	// 5. TICKET DETAIL VIEW LOCATORS (/super-admin/support/:id)
	// =========================================================================

	@FindBy(xpath = "//a[contains(.,'Back to Support') or .//svg[contains(@class,'lucide-arrow-left')]]")
	private WebElement backToSupportLink;

	@FindBy(xpath = "//h1[contains(@class,'text-lg') or contains(@class,'font-bold')]")
	private WebElement ticketSubjectHeading;

	@FindBy(xpath = "//span[contains(@class,'font-mono') and contains(@class,'font-semibold')]")
	private WebElement ticketNumberBadge;

	@FindBy(xpath = "//div[./p[text()='Status']]//button")
	private WebElement statusDropdownButton;

	@FindBy(xpath = "//button[@role='tab' and text()='Details']")
	private WebElement detailsSubTab;

	@FindBy(xpath = "//button[@role='tab' and text()='Identity']")
	private WebElement identitySubTab;

	@FindBy(xpath = "//button[@role='tab' and text()='Device']")
	private WebElement deviceSubTab;

	// Chat Composer Locators
	@FindBy(xpath = "//textarea[@placeholder='Reply to the customer…' or @aria-label='Message']")
	private WebElement chatMessageTextarea;

	@FindBy(xpath = "//button[@aria-label='Send message' or contains(.,'Send')]")
	private WebElement chatSendButton;

	@FindBy(xpath = "//input[@type='file' and @accept='image/*']")
	private WebElement chatImageUploadInput;


	// =========================================================================
	// 6. ACTION METHODS: Navigation & Page Lifecycle
	// =========================================================================

	@StepName("Click on Support in Sidebar")
	public void clickSupport() {
		waitUtils.waitForClickable(supportLink);
		try {
			supportLink.click();
		} catch (Exception e) {
			clickUsingJS(supportLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Support Page is Loaded")
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
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		waitForTableLoading();
	}

	@StepName("Wait for Support Table to Finish Loading")
	public void waitForTableLoading() {
		try {
			By loaderLocator = By.xpath("//div[contains(@class,'animate-spin')]");
			waitUtils.waitForInvisibility(loaderLocator);
		} catch (Exception ignored) {
		}
	}


	// =========================================================================
	// 7. ACTION METHODS: Stat Cards
	// =========================================================================

	@StepName("Get Open Tickets Count")
	public String getOpenTicketsCount() {
		try {
			waitUtils.waitForVisibility(openTicketsKpi);
			return openTicketsKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get In Progress Tickets Count")
	public String getInProgressTicketsCount() {
		try {
			waitUtils.waitForVisibility(inProgressTicketsKpi);
			return inProgressTicketsKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Reopened Tickets Count")
	public String getReopenedTicketsCount() {
		try {
			waitUtils.waitForVisibility(reopenedTicketsKpi);
			return reopenedTicketsKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Resolved Tickets Count")
	public String getResolvedTicketsCount() {
		try {
			waitUtils.waitForVisibility(resolvedTicketsKpi);
			return resolvedTicketsKpi.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}


	// =========================================================================
	// 8. ACTION METHODS: Tabs & Filters
	// =========================================================================

	@StepName("Switch to Active Tickets Tab")
	public void clickActiveTab() {
		waitUtils.waitForClickable(activeTabButton);
		activeTabButton.click();
		waitForTableLoading();
	}

	@StepName("Switch to Resolved Tickets Tab")
	public void clickResolvedTab() {
		waitUtils.waitForClickable(resolvedTabButton);
		resolvedTabButton.click();
		waitForTableLoading();
	}

	@StepName("Filter by Organization: {0}")
	public void filterByOrganization(String orgName) {
		waitUtils.waitForClickable(organizationFilterDropdown);
		organizationFilterDropdown.click();
		By optionLocator = By.xpath("//div[@role='option' or contains(@class,'option')][contains(.,'" + orgName + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Filter by Category: {0}")
	public void filterByCategory(String category) {
		waitUtils.waitForClickable(categoryFilterDropdown);
		categoryFilterDropdown.click();
		By optionLocator = By.xpath("//div[@role='option' or contains(@class,'option')][contains(.,'" + category + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Filter by Priority: {0}")
	public void filterByPriority(String priority) {
		waitUtils.waitForClickable(priorityFilterDropdown);
		priorityFilterDropdown.click();
		By optionLocator = By.xpath("//div[@role='option' or contains(@class,'option')][contains(.,'" + priority + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
		waitForTableLoading();
	}

	@StepName("Filter by Date Range From: {0} To: {1}")
	public void filterByDateRange(String fromDate, String toDate) {
		waitUtils.waitForVisibility(dateFromInput);
		dateFromInput.clear();
		dateFromInput.sendKeys(fromDate);
		dateToInput.clear();
		dateToInput.sendKeys(toDate);
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
	// 9. ACTION METHODS: Ticket Table Inspection & Row Click
	// =========================================================================

	@StepName("Get Total Displayed Tickets Count")
	public int getTicketsCount() {
		return ticketTableRows.size();
	}

	private WebElement getRow(int rowIndex) {
		if (rowIndex < 1 || rowIndex > ticketTableRows.size()) {
			throw new IndexOutOfBoundsException("Row index " + rowIndex + " out of bounds for tickets count " + ticketTableRows.size());
		}
		return ticketTableRows.get(rowIndex - 1);
	}

	@StepName("Get Ticket Subject of Row {0}")
	public String getTicketSubject(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement subjectEl = row.findElement(By.xpath("./td[1]//p[contains(@class,'font-medium')]"));
		return subjectEl.getText().trim();
	}

	@StepName("Get Ticket Number of Row {0}")
	public String getTicketNumber(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement numberEl = row.findElement(By.xpath("./td[1]//p[contains(@class,'font-mono')]"));
		return numberEl.getText().trim();
	}

	@StepName("Get Organization Name of Row {0}")
	public String getTicketOrganization(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement orgEl = row.findElement(By.xpath("./td[2]//p[contains(@class,'font-medium')]"));
		return orgEl.getText().trim();
	}

	@StepName("Get Priority of Row {0}")
	public String getTicketPriority(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement priorityEl = row.findElement(By.xpath("./td[4]"));
		return priorityEl.getText().trim();
	}

	@StepName("Get Status of Row {0}")
	public String getTicketStatus(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement statusEl = row.findElement(By.xpath("./td[5]"));
		return statusEl.getText().trim();
	}

	@StepName("Click Ticket Row {0} to Open Ticket Details")
	public void clickTicketRow(int rowIndex) {
		WebElement row = getRow(rowIndex);
		waitUtils.waitForClickable(row);
		try {
			row.click();
		} catch (Exception e) {
			clickUsingJS(row);
		}
		waitUtils.waitForVisibility(ticketSubjectHeading);
	}

	@StepName("Click Ticket by Subject: {0}")
	public void clickTicketBySubject(String subject) {
		By rowLocator = By.xpath("//table/tbody/tr[.//p[normalize-space()='" + subject + "']]");
		WebElement row = waitUtils.waitForVisibility(rowLocator);
		waitUtils.waitForClickable(row);
		try {
			row.click();
		} catch (Exception e) {
			clickUsingJS(row);
		}
		waitUtils.waitForVisibility(ticketSubjectHeading);
	}


	// =========================================================================
	// 10. ACTION METHODS: Ticket Detail View & Chat Conversation
	// =========================================================================

	@StepName("Click Back to Support Listing")
	public void clickBackToSupport() {
		waitUtils.waitForClickable(backToSupportLink);
		backToSupportLink.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Get Detail Ticket Subject")
	public String getDetailTicketSubject() {
		try {
			waitUtils.waitForVisibility(ticketSubjectHeading);
			return ticketSubjectHeading.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Detail Ticket Number")
	public String getDetailTicketNumber() {
		try {
			waitUtils.waitForVisibility(ticketNumberBadge);
			return ticketNumberBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Update Ticket Status: {0}")
	public void updateTicketStatus(String targetStatus) {
		waitUtils.waitForClickable(statusDropdownButton);
		statusDropdownButton.click();
		By optionLocator = By.xpath("//div[@role='option' or contains(@class,'dropdown-item') or contains(.,'" + targetStatus + "')]");
		WebElement option = waitUtils.waitForVisibility(optionLocator);
		option.click();
	}

	@StepName("Send Ticket Chat Reply: {0}")
	public void sendChatMessage(String messageText) {
		waitUtils.waitForVisibility(chatMessageTextarea);
		chatMessageTextarea.clear();
		chatMessageTextarea.sendKeys(messageText);
		waitUtils.waitForClickable(chatSendButton);
		chatSendButton.click();
	}

	@StepName("Attach and Send Image in Ticket Chat: {0}")
	public void sendChatWithImage(String messageText, String imageFilePath) {
		chatImageUploadInput.sendKeys(imageFilePath);
		if (messageText != null && !messageText.isEmpty()) {
			waitUtils.waitForVisibility(chatMessageTextarea);
			chatMessageTextarea.sendKeys(messageText);
		}
		waitUtils.waitForClickable(chatSendButton);
		chatSendButton.click();
	}

	// Helper to safely check element presence without throwing
	private boolean isElementPresent(WebElement element) {
		try {
			return element != null && element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Switch to Details Sub-Tab in Ticket Detail")
	public void clickDetailsSubTab() {
		try {
			waitUtils.waitForClickable(detailsSubTab);
			detailsSubTab.click();
		} catch (Exception e) {
			clickUsingJS(detailsSubTab);
		}
	}

	@StepName("Switch to Identity Sub-Tab in Ticket Detail")
	public void clickIdentitySubTab() {
		try {
			waitUtils.waitForClickable(identitySubTab);
			identitySubTab.click();
		} catch (Exception e) {
			clickUsingJS(identitySubTab);
		}
	}

	@StepName("Switch to Device Sub-Tab in Ticket Detail")
	public void clickDeviceSubTab() {
		try {
			waitUtils.waitForClickable(deviceSubTab);
			deviceSubTab.click();
		} catch (Exception e) {
			clickUsingJS(deviceSubTab);
		}
	}

	@StepName("Open First Ticket Detail if available")
	public boolean openFirstTicketDetail() {
		try {
			waitUtils.waitUntil(d -> {
				List<WebElement> rows = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				return !rows.isEmpty() && rows.get(0).isDisplayed();
			});
			List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
			if (!rows.isEmpty()) {
				waitUtils.waitForClickable(rows.get(0));
				try {
					rows.get(0).click();
				} catch (Exception e) {
					clickUsingJS(rows.get(0));
				}
				waitUtils.waitForVisibility(ticketSubjectHeading);
				return true;
			}
		} catch (Exception e) {
			// No tickets
		}
		return false;
	}

	@StepName("Check if Ticket Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(ticketSubjectHeading);
			return ticketSubjectHeading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
