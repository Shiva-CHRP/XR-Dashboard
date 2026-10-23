package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import clientportaladmin.pojo.ClientUserData;
import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class ManagerUsers extends AbstractComponent {

	public ManagerUsers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']]")
	private WebElement usersButton;

	@FindBy(xpath = "//h1[contains(.,'Users') or contains(.,'Employees')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[(contains(.,'Users') and not(contains(.,'Contractor')) and not(contains(.,'Partner'))) or contains(.,'Internal') or contains(.,'MCL') or contains(.,'JIL')]")
	private WebElement internalUsersTab;

	@FindBy(xpath = "//button[contains(.,'Contractor') or contains(.,'Partner')]")
	private WebElement contractorUsersTab;

	@FindBy(xpath = "//input[contains(translate(@placeholder,'SEARCH','search'),'search') or contains(@placeholder,'user') or contains(@placeholder,'contractor') or @type='search']")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add User')]")
	private WebElement addUserButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> userRows;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	// Add User Modal Elements
	@FindBy(xpath = "//input[contains(@placeholder,'Rahul Sharma')] | //label[contains(.,'Full Name')]/..//input")
	private WebElement fullNameInput;

	@FindBy(xpath = "//input[contains(@placeholder,'EMP-01')] | //label[contains(.,'User ID')]/..//input")
	private WebElement employeeIdInput;

	@FindBy(xpath = "//input[contains(@placeholder,'acme.com')] | //label[contains(.,'Email')]/..//input")
	private WebElement emailInput;

	@FindBy(xpath = "//div[contains(@class,'fixed') or @role='dialog']//button[normalize-space()='Add User']")
	private WebElement saveUserButton;

	@FindBy(xpath = "//*[@role='dialog']//button[contains(@class,'rounded-full')][1]")
	private WebElement modalInternalChip;

	@FindBy(xpath = "//*[@role='dialog']//button[contains(@class,'rounded-full')][2] | //*[@role='dialog']//button[contains(.,'Contractor') or contains(.,'Partner')]")
	private WebElement modalContractorChip;

	@FindBy(xpath = "//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']")
	private WebElement firstRowActionMenu;

	@FindBy(xpath = "//div[@role='menuitem' and contains(.,'Edit')]")
	private WebElement editActionMenuItem;

	@StepName("Click Manager Users")
	public void clickManagerUsers() {
		try {
			WebElement btn = driver.findElement(By.xpath("//aside//button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']] | //button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(usersButton, "employees");
		}
		waitUtils.waitForUrlContains("employees", 5);
	}

	@StepName("Verify Manager Users Page is Loaded")
	public boolean isManagerUsersPageLoaded() {
		return waitUtils.waitForUrlContains("employees", 2)
				|| !driver.findElements(By.xpath("//h1[contains(.,'Users') or contains(.,'Employees')] | //header//h1")).isEmpty();
	}

	@StepName("Switch to Internal Users Tab")
	public void clickInternalUsersTab() {
		List<WebElement> tabs = driver.findElements(By.xpath("//button[(contains(.,'Users') and not(contains(.,'Contractor')) and not(contains(.,'Partner'))) or contains(.,'Internal') or contains(.,'MCL') or contains(.,'JIL')]"));
		if (!tabs.isEmpty()) {
			waitUtils.scrollIntoView(tabs.get(0));
			waitUtils.clickUsingJS(tabs.get(0));
		}
	}

	@StepName("Switch to Contractor Users Tab")
	public void clickContractorUsersTab() {
		List<WebElement> tabs = driver.findElements(By.xpath("//button[contains(.,'Contractor') or contains(.,'Partner')]"));
		if (!tabs.isEmpty()) {
			waitUtils.scrollIntoView(tabs.get(0));
			waitUtils.clickUsingJS(tabs.get(0));
		}
	}

	@StepName("Search Users")
	public void searchUsers(String query) {
		By searchBy = By.xpath("//input[contains(translate(@placeholder,'SEARCH','search'),'search') or contains(@placeholder,'user') or contains(@placeholder,'contractor') or @type='search']");
		try {
			WebElement input = waitUtils.waitForVisibility(searchBy);
			waitUtils.waitForClickable(input);
			waitUtils.scrollIntoView(input);
			try {
				input.clear();
			} catch (Exception e) {
				input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
			}
			input.sendKeys(query);
		} catch (Exception e) {
			List<WebElement> inputs = driver.findElements(searchBy);
			if (!inputs.isEmpty()) {
				((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
						"arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
						inputs.get(0), query);
			}
		}
	}

	@StepName("Clear Search")
	public void clearSearch() {
		searchUsers("");
	}

	@StepName("Get User Rows Count")
	public int getUserRowsCount() {
		return driver.findElements(By.xpath("//table//tbody//tr[not(.//td[@colSpan])]")).size();
	}

	@StepName("Open Add User Modal")
	public void clickAddUser() {
		waitUtils.waitForClickable(addUserButton);
		try {
			addUserButton.click();
		} catch (Exception e) {
			clickUsingJS(addUserButton);
		}
		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[@role='dialog']")).isEmpty(), 5);
	}

	@StepName("Manager Create Internal User")
	public ToastResponse createInternalUser(ClientUserData user) {
		clickInternalUsersTab();
		clickAddUser();
		try {
			WebElement chip = driver.findElement(By.xpath("//*[@role='dialog']//button[contains(@class,'rounded-full')][1]"));
			waitUtils.waitForClickable(chip);
			chip.click();
		} catch (Exception ignored) {
		}

		By nameBy = By.xpath("//input[contains(@placeholder,'Rahul Sharma')] | //label[contains(.,'Full Name')]/..//input");
		WebElement nameInput = waitUtils.waitForVisibility(nameBy);
		nameInput.clear();
		nameInput.sendKeys(user.getFullName());

		if (user.getEmployeeId() != null && !user.getEmployeeId().isEmpty()) {
			By empIdBy = By.xpath("//input[contains(@placeholder,'EMP-01')] | //label[contains(.,'User ID')]/..//input");
			List<WebElement> empIdInputs = driver.findElements(empIdBy);
			if (!empIdInputs.isEmpty()) {
				empIdInputs.get(0).clear();
				empIdInputs.get(0).sendKeys(user.getEmployeeId());
			}
		}

		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
			By emailBy = By.xpath("//input[contains(@placeholder,'acme.com')] | //label[contains(.,'Email')]/..//input");
			List<WebElement> emailInputs = driver.findElements(emailBy);
			if (!emailInputs.isEmpty()) {
				emailInputs.get(0).clear();
				emailInputs.get(0).sendKeys(user.getEmail());
			}
		}

		By saveBtnBy = By.xpath("//div[contains(@class,'fixed') or @role='dialog']//button[normalize-space()='Add User']");
		WebElement saveBtn = waitUtils.waitForVisibility(saveBtnBy);
		waitUtils.waitForClickable(saveBtn);
		try {
			saveBtn.click();
		} catch (Exception e) {
			clickUsingJS(saveBtn);
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	@StepName("Manager Create Contractor User")
	public ToastResponse createContractorUser(ClientUserData user) {
		clickContractorUsersTab();
		clickAddUser();
		try {
			WebElement chip = driver.findElement(By.xpath("//*[@role='dialog']//button[contains(@class,'rounded-full')][2] | //*[@role='dialog']//button[contains(.,'Contractor') or contains(.,'Partner')]"));
			waitUtils.waitForClickable(chip);
			chip.click();
		} catch (Exception ignored) {
		}

		By nameBy = By.xpath("//input[contains(@placeholder,'Rahul Sharma')] | //label[contains(.,'Full Name')]/..//input");
		WebElement nameInput = waitUtils.waitForVisibility(nameBy);
		nameInput.clear();
		nameInput.sendKeys(user.getFullName());

		if (user.getEmployeeId() != null && !user.getEmployeeId().isEmpty()) {
			By empIdBy = By.xpath("//input[contains(@placeholder,'EMP-01')] | //label[contains(.,'User ID')]/..//input");
			List<WebElement> empIdInputs = driver.findElements(empIdBy);
			if (!empIdInputs.isEmpty()) {
				empIdInputs.get(0).clear();
				empIdInputs.get(0).sendKeys(user.getEmployeeId());
			}
		}

		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
			By emailBy = By.xpath("//input[contains(@placeholder,'acme.com')] | //label[contains(.,'Email')]/..//input");
			List<WebElement> emailInputs = driver.findElements(emailBy);
			if (!emailInputs.isEmpty()) {
				emailInputs.get(0).clear();
				emailInputs.get(0).sendKeys(user.getEmail());
			}
		}

		By saveBtnBy = By.xpath("//div[contains(@class,'fixed') or @role='dialog']//button[normalize-space()='Add User']");
		WebElement saveBtn = waitUtils.waitForVisibility(saveBtnBy);
		waitUtils.waitForClickable(saveBtn);
		try {
			saveBtn.click();
		} catch (Exception e) {
			clickUsingJS(saveBtn);
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	@StepName("Manager Edit User Details")
	public ToastResponse editUser(String currentName, String updatedName) {
		searchUsers(currentName);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By editItemBy = By.xpath("//div[@role='menuitem' and contains(.,'Edit')]");
		WebElement editItem = waitUtils.waitForVisibility(editItemBy);
		waitUtils.waitForClickable(editItem);
		try {
			editItem.click();
		} catch (Exception e) {
			clickUsingJS(editItem);
		}

		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[@role='dialog']")).isEmpty(), 5);
		By nameInputBy = By.xpath("//div[@role='dialog']//label[contains(.,'Full Name')]/..//input | //div[@role='dialog']//input[1]");
		WebElement nameInput = waitUtils.waitForVisibility(nameInputBy);
		nameInput.clear();
		nameInput.sendKeys(updatedName);

		By saveBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Save Changes') or contains(.,'Update') or contains(.,'Save')]");
		WebElement saveBtn = waitUtils.waitForVisibility(saveBtnBy);
		waitUtils.waitForClickable(saveBtn);
		try {
			saveBtn.click();
		} catch (Exception e) {
			clickUsingJS(saveBtn);
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	@StepName("Manager Toggle User Status (Deactivate / Reactivate)")
	public ToastResponse toggleUserStatus(String searchName) {
		searchUsers(searchName);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By toggleItemBy = By.xpath("//div[@role='menuitem' and (contains(.,'Deactivate') or contains(.,'Reactivate'))]");
		WebElement toggleItem = waitUtils.waitForVisibility(toggleItemBy);
		waitUtils.waitForClickable(toggleItem);
		try {
			toggleItem.click();
		} catch (Exception e) {
			clickUsingJS(toggleItem);
		}

		By confirmBtnBy = By.xpath("//div[@role='dialog']//button[contains(.,'Deactivate') or contains(.,'Confirm') or contains(.,'Yes')]");
		List<WebElement> confirmBtns = driver.findElements(confirmBtnBy);
		if (!confirmBtns.isEmpty()) {
			confirmBtns.get(0).click();
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}
}