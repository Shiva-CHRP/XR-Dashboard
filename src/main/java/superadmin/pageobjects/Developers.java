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
 * Enterprise Page Object for Super Admin Developer Management:
 * 1. Developer Fleet Listing (/super-admin/developers)
 * 2. Create Developer Modal & Form
 * 3. Edit Developer Modal
 * 4. Reset Password Modal
 * 5. Deactivate / Reactivate Modal
 * 6. Delete Developer Modal
 * 7. View Submissions Modal
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class Developers extends AbstractComponent {

	public Developers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & LISTING LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/developers']")
	private WebElement developersLink;

	@FindBy(xpath = "//h1[contains(text(),'Developers') or contains(text(),'Developer Management')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Refresh') or @title='Refresh developers']")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Create Developer')]")
	private WebElement createDeveloperButton;

	// StatCards
	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Total Developers')] or .//p[contains(text(),'Total Developers')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl') or contains(@class,'text-4xl')]")
	private WebElement totalDevelopersKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Active')] or .//p[contains(text(),'Active')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl') or contains(@class,'text-4xl')]")
	private WebElement activeDevelopersKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Inactive')] or .//p[contains(text(),'Inactive')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl') or contains(@class,'text-4xl')]")
	private WebElement inactiveDevelopersKpi;

	// FilterBar
	@FindBy(xpath = "//input[contains(@placeholder,'Search by name') or contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Status')]]")
	private WebElement statusFilterSelect;

	@FindBy(xpath = "//button[contains(.,'Clear all') or contains(@class,'fb-clear') or contains(.,'Clear filters') or contains(@title,'Clear all filters')]")
	private WebElement clearFiltersButton;

	// Table Rows
	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> developerTableRows;

	// =========================================================================
	// 2. CREATE / EDIT DEVELOPER MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Create Developer') or contains(text(),'Edit Developer')] | //h3[contains(text(),'Create Developer') or contains(text(),'Edit Developer')]")
	private WebElement developerModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//div[.//label[contains(text(),'Full Name')]]//input")
	private WebElement modalFullNameInput;

	@FindBy(xpath = "//div[@role='dialog']//div[.//label[contains(text(),'Email')]]//input")
	private WebElement modalEmailInput;

	@FindBy(xpath = "//div[@role='dialog']//input[@placeholder='Min. 8 chars, 1 uppercase, 1 number' or (contains(@type,'password') and not(contains(@placeholder,'Repeat')))]")
	private WebElement modalPasswordInput;

	@FindBy(xpath = "//div[@role='dialog']//textarea")
	private WebElement modalNotesTextarea;

	@FindBy(xpath = "//div[@role='dialog']//button[@type='submit' or contains(text(),'Create Developer') or contains(text(),'Save Changes')]")
	private WebElement modalSubmitButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel'] | //button[normalize-space()='Cancel']")
	private WebElement modalCancelButton;

	// =========================================================================
	// 3. RESET PASSWORD MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Reset Password')] | //h3[contains(text(),'Reset Password')]")
	private WebElement resetPasswordModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//input[contains(@placeholder,'Enter new password')]")
	private WebElement resetNewPasswordInput;

	@FindBy(xpath = "//div[@role='dialog']//input[contains(@placeholder,'Repeat new password')]")
	private WebElement resetConfirmPasswordInput;

	@FindBy(xpath = "//div[@role='dialog']//button[@type='submit' or contains(text(),'Reset Password')]")
	private WebElement resetSubmitButton;

	// =========================================================================
	// 4. DEACTIVATE / REACTIVATE MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Deactivate') or contains(text(),'Reactivate')] | //h3[contains(text(),'Deactivate') or contains(text(),'Reactivate')]")
	private WebElement deactivateModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//button[contains(text(),'Deactivate') or contains(text(),'Reactivate')]")
	private WebElement confirmDeactivateButton;

	// =========================================================================
	// 5. DELETE DEVELOPER MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Delete Developer')] | //h3[contains(text(),'Delete Developer')]")
	private WebElement deleteModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//button[contains(.,'Delete') and (contains(@class,'danger') or contains(@class,'bg-titan-danger'))]")
	private WebElement confirmDeleteButton;

	// =========================================================================
	// 6. VIEW SUBMISSIONS MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Submissions')] | //h3[contains(text(),'Submissions')]")
	private WebElement viewSubmissionsModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Close'] | //button[normalize-space()='Close']")
	private WebElement closeSubmissionsModalButton;


	// =========================================================================
	// ACTION METHODS: Navigation & Lifecycle
	// =========================================================================

	@StepName("Click on Developers in Sidebar")
	public void clickDevelopers() {
		waitUtils.waitForClickable(developersLink);
		try {
			developersLink.click();
		} catch (Exception e) {
			clickUsingJS(developersLink);
		}
		waitUtils.waitForVisibility(pageTitle);
		waitForLoadingToComplete();
	}

	@StepName("Check if Developers page is loaded")
	public boolean isPageLoaded() {
		try {
			waitForLoadingToComplete();
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

	@StepName("Click Create Developer Button")
	public void clickCreateDeveloper() {
		waitUtils.waitForClickable(createDeveloperButton);
		createDeveloperButton.click();
		waitUtils.waitForVisibility(developerModalTitle);
	}

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: KPI Counts
	// =========================================================================

	@StepName("Get Total Developers KPI Count")
	public int getTotalDevelopersCount() {
		try {
			waitUtils.waitForVisibility(totalDevelopersKpi);
			String txt = totalDevelopersKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Active Developers KPI Count")
	public int getActiveDevelopersCount() {
		try {
			waitUtils.waitForVisibility(activeDevelopersKpi);
			String txt = activeDevelopersKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	// =========================================================================
	// ACTION METHODS: Search & Filters
	// =========================================================================

	@StepName("Search Developers: {0}")
	public void searchDevelopers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (query != null && !query.isEmpty()) {
			searchInput.sendKeys(query);
		}
		waitForLoadingToComplete();
	}

	@StepName("Filter Developers by Status: {0}")
	public void filterByStatus(String status) {
		try {
			List<WebElement> selects = driver.findElements(By.xpath("//select[.//option[contains(text(),'All Status')]]"));
			if (!selects.isEmpty() && selects.get(0).isDisplayed()) {
				selectByVisibleText(selects.get(0), status);
				waitForLoadingToComplete();
				return;
			}
			List<WebElement> triggers = driver.findElements(By.xpath("//button[@aria-haspopup='listbox' and (.//span[contains(text(),'Status')] or .//span[contains(text(),'Active')] or .//span[contains(text(),'Inactive')])]|//div[contains(@class,'fb-primary')]//button[@aria-haspopup='listbox']"));
			if (!triggers.isEmpty()) {
				triggers.get(0).click();
				WebElement opt = waitUtils.waitForVisibility(By.xpath("//div[@role='listbox' or contains(@class,'portal') or contains(@class,'z-50')]//button[contains(.,'" + status + "')]"));
				opt.click();
				waitForLoadingToComplete();
			}
		} catch (Exception ignored) {}
	}

	@StepName("Clear All Filters")
	public void clearAllFilters() {
		try {
			List<WebElement> clearBtns = driver.findElements(By.xpath("//button[contains(.,'Clear all') or contains(@class,'fb-clear') or contains(.,'Clear filters')]"));
			if (!clearBtns.isEmpty() && clearBtns.get(0).isDisplayed()) {
				clearBtns.get(0).click();
				waitForLoadingToComplete();
				return;
			}
		} catch (Exception ignored) {}
		try {
			waitUtils.waitForVisibility(searchInput);
			String currentText = searchInput.getAttribute("value");
			if (currentText != null && !currentText.isEmpty()) {
				searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
				waitForLoadingToComplete();
			}
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: Table Listing & Actions
	// =========================================================================

	@StepName("Get Displayed Developer Names")
	public List<String> getDisplayedDeveloperNames() {
		List<String> names = new ArrayList<>();
		for (WebElement row : developerTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]//p[contains(@class,'font-semibold') or contains(@class,'font-medium')]"));
				names.add(nameEl.getText().trim());
			} catch (Exception ignored) {}
		}
		return names;
	}

	@StepName("Check if Developer is Present: {0}")
	public boolean isDeveloperPresent(String name) {
		return getDisplayedDeveloperNames().stream()
				.anyMatch(n -> n.equalsIgnoreCase(name) || n.contains(name));
	}

	public WebElement findDeveloperRow(String name) {
		for (WebElement row : developerTableRows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//td[1]"));
				if (nameEl.getText().contains(name)) {
					return row;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	public void openActionMenu(String developerName) {
		WebElement row = findDeveloperRow(developerName);
		if (row != null) {
			WebElement menuBtn = row.findElement(By.xpath(".//button[contains(@class,'text-titan-muted') or @aria-label='Actions']"));
			waitUtils.waitForClickable(menuBtn);
			menuBtn.click();
		} else {
			throw new RuntimeException("Developer row not found: " + developerName);
		}
	}

	@StepName("Open Edit Developer for: {0}")
	public void openEditDeveloper(String developerName) {
		openActionMenu(developerName);
		WebElement editOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'fixed z-50') or contains(@class,'portal') or contains(@role,'menu') or contains(@class,'animate-fade-in')]//button[normalize-space()='Edit' or contains(.,'Edit')]"));
		waitUtils.waitForClickable(editOption);
		editOption.click();
		waitUtils.waitForVisibility(developerModalTitle);
	}

	@StepName("Open Reset Password for: {0}")
	public void openResetPassword(String developerName) {
		openActionMenu(developerName);
		WebElement resetOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Reset Password')] | //button[contains(.,'Reset Password')]"));
		waitUtils.waitForClickable(resetOption);
		resetOption.click();
		waitUtils.waitForVisibility(resetPasswordModalTitle);
	}

	@StepName("Trigger Deactivate / Reactivate for: {0}")
	public void triggerDeactivate(String developerName) {
		openActionMenu(developerName);
		WebElement toggleOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Deactivate') or contains(.,'Reactivate')] | //button[contains(.,'Deactivate') or contains(.,'Reactivate')]"));
		waitUtils.waitForClickable(toggleOption);
		toggleOption.click();
		waitUtils.waitForVisibility(deactivateModalTitle);
	}

	@StepName("Trigger Delete Developer for: {0}")
	public void triggerDeleteDeveloper(String developerName) {
		openActionMenu(developerName);
		WebElement deleteOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Delete')] | //button[contains(.,'Delete')]"));
		waitUtils.waitForClickable(deleteOption);
		deleteOption.click();
		waitUtils.waitForVisibility(deleteModalTitle);
	}

	@StepName("Open Submissions for: {0}")
	public void openSubmissions(String developerName) {
		openActionMenu(developerName);
		WebElement subsOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'z-50') or contains(@class,'portal') or contains(@role,'menu')]//button[contains(.,'Submissions')] | //button[contains(.,'Submissions')]"));
		waitUtils.waitForClickable(subsOption);
		subsOption.click();
		waitUtils.waitForVisibility(viewSubmissionsModalTitle);
	}

	public boolean openFirstRowActionMenu() {
		try {
			waitUtils.waitUntil(d -> {
				List<WebElement> rows = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				return !rows.isEmpty() && rows.get(0).isDisplayed();
			});
			List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
			if (rows.isEmpty()) return false;
			WebElement menuBtn = rows.get(0).findElement(By.xpath(".//button[contains(@class,'text-titan-muted') or @aria-label='Actions' or .//*[local-name()='svg' and contains(@class,'lucide-more')]]"));
			waitUtils.waitForClickable(menuBtn);
			menuBtn.click();
			waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'fixed z-50') or contains(@class,'animate-fade-in')]"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open First Developer for Edit")
	public boolean openFirstDeveloperEdit() {
		try {
			if (!openFirstRowActionMenu()) return false;
			WebElement editOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'fixed z-50') or contains(@class,'animate-fade-in')]//button[normalize-space()='Edit' or contains(.,'Edit')]"));
			waitUtils.waitForClickable(editOption);
			editOption.click();
			waitUtils.waitForVisibility(developerModalTitle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open First Developer Submissions View")
	public boolean openFirstDeveloperSubmissions() {
		try {
			waitUtils.waitUntil(d -> {
				List<WebElement> rows = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				return !rows.isEmpty() && rows.get(0).isDisplayed();
			});
			List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
			if (rows.isEmpty()) return false;

			List<WebElement> subsBtns = rows.get(0).findElements(By.xpath(".//td[4]//button | .//button[.//svg[contains(@class,'lucide-eye')]]"));
			if (!subsBtns.isEmpty() && subsBtns.get(0).isDisplayed()) {
				waitUtils.waitForClickable(subsBtns.get(0));
				subsBtns.get(0).click();
			} else {
				if (!openFirstRowActionMenu()) return false;
				WebElement subsOption = waitUtils.waitForVisibility(By.xpath("//div[contains(@class,'fixed z-50') or contains(@class,'animate-fade-in')]//button[contains(.,'View Submissions') or contains(.,'Submissions')]"));
				waitUtils.waitForClickable(subsOption);
				subsOption.click();
			}
			waitUtils.waitForVisibility(viewSubmissionsModalTitle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Developer Modal is Displayed")
	public boolean isDeveloperModalDisplayed() {
		try {
			return developerModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Submissions Modal is Displayed")
	public boolean isSubmissionsModalDisplayed() {
		try {
			return viewSubmissionsModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// =========================================================================
	// ACTION METHODS: Modals Operations
	// =========================================================================

	@StepName("Fill and Submit Create Developer Form: {0}, {1}")
	public void createDeveloper(String fullName, String email, String password, String notes) {
		waitUtils.waitForVisibility(modalFullNameInput);
		modalFullNameInput.clear();
		modalFullNameInput.sendKeys(fullName);

		waitUtils.waitForVisibility(modalEmailInput);
		modalEmailInput.clear();
		modalEmailInput.sendKeys(email);

		if (password != null) {
			waitUtils.waitForVisibility(modalPasswordInput);
			modalPasswordInput.clear();
			modalPasswordInput.sendKeys(password);
		}

		if (notes != null) {
			waitUtils.waitForVisibility(modalNotesTextarea);
			modalNotesTextarea.clear();
			modalNotesTextarea.sendKeys(notes);
		}

		waitUtils.waitForClickable(modalSubmitButton);
		modalSubmitButton.click();
		waitUtils.waitForInvisibility(developerModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Reset Password in Modal: {0}")
	public void resetPassword(String newPassword, String confirmPassword) {
		waitUtils.waitForVisibility(resetNewPasswordInput);
		resetNewPasswordInput.clear();
		resetNewPasswordInput.sendKeys(newPassword);

		waitUtils.waitForVisibility(resetConfirmPasswordInput);
		resetConfirmPasswordInput.clear();
		resetConfirmPasswordInput.sendKeys(confirmPassword);

		waitUtils.waitForClickable(resetSubmitButton);
		resetSubmitButton.click();
		waitUtils.waitForInvisibility(resetPasswordModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Confirm Deactivate / Reactivate in Modal")
	public void confirmDeactivate() {
		waitUtils.waitForClickable(confirmDeactivateButton);
		confirmDeactivateButton.click();
		waitUtils.waitForInvisibility(deactivateModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Confirm Delete Developer in Modal")
	public void confirmDeleteDeveloper() {
		waitUtils.waitForClickable(confirmDeleteButton);
		confirmDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Close Submissions Modal")
	public void closeSubmissionsModal() {
		waitUtils.waitForClickable(closeSubmissionsModalButton);
		try {
			closeSubmissionsModalButton.click();
		} catch (Exception e) {
			clickUsingJS(closeSubmissionsModalButton);
		}
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Cancel Developer Modal")
	public void cancelDeveloperCreation() {
		waitUtils.waitForClickable(modalCancelButton);
		try {
			modalCancelButton.click();
		} catch (Exception e) {
			clickUsingJS(modalCancelButton);
		}
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}
}
