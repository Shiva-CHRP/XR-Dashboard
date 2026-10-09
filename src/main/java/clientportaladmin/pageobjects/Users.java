package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Users extends AbstractComponent {

	public Users(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']] | //button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']]")
	private WebElement usersNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Users'] | //header//h1[contains(.,'Users')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[(contains(.,'Users') and not(contains(.,'Contractor')) and not(contains(.,'Partner'))) or contains(.,'Internal') or contains(.,'MCL') or contains(.,'JIL')]")
	private WebElement internalUsersTab;

	@FindBy(xpath = "//button[contains(.,'Contractor') or contains(.,'Partner')]")
	private WebElement contractorUsersTab;

	@FindBy(xpath = "//input[contains(translate(@placeholder,'SEARCH','search'),'search') or contains(@placeholder,'user') or contains(@placeholder,'contractor') or @type='search']")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add User')]")
	private WebElement addUserButton;

	@FindBy(xpath = "//button[contains(normalize-space(),'Import CSV')]")
	private WebElement importCsvButton;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	// Add User Modal Locators
	@FindBy(xpath = "//input[contains(@placeholder,'Rahul Sharma')] | //label[contains(.,'Full Name')]/..//input")
	private WebElement fullNameInput;

	@FindBy(xpath = "//input[contains(@placeholder,'EMP-01')] | //label[contains(.,'User ID')]/..//input")
	private WebElement employeeIdInput;

	@FindBy(xpath = "//input[contains(@placeholder,'acme.com')] | //label[contains(.,'Email')]/..//input")
	private WebElement emailInput;

	@FindBy(xpath = "//input[contains(@placeholder,'9876543210')] | //label[contains(.,'Phone')]/..//input")
	private WebElement phoneInput;

	@FindBy(xpath = "//div[contains(@class,'fixed') or @role='dialog']//button[normalize-space()='Add User']")
	private WebElement saveUserButton;

	@FindBy(xpath = "//div[contains(@class,'fixed') or @role='dialog']//button[normalize-space()='Cancel'] | //button[normalize-space()='Cancel']")
	private WebElement cancelModalButton;

	@FindBy(xpath = "//*[@role='dialog']//button[contains(@class,'rounded-full')][1]")
	private WebElement modalInternalChip;

	@FindBy(xpath = "//*[@role='dialog']//button[contains(@class,'rounded-full')][2] | //*[@role='dialog']//button[contains(.,'Contractor') or contains(.,'Partner')]")
	private WebElement modalContractorChip;

	// View Details Modal Locators
	@FindBy(xpath = "//*[@role='dialog']//*[normalize-space()='User Details']")
	private WebElement userDetailsModalTitle;

	@FindBy(xpath = "//*[@role='dialog']//button[.//svg or @aria-label='Close' or contains(@class,'close')]")
	private WebElement closeDetailsModalButton;

	// Table First Row Actions
	@FindBy(xpath = "//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']")
	private WebElement firstRowActionMenu;

	@FindBy(xpath = "//div[@role='menuitem' and contains(.,'View')]")
	private WebElement viewActionMenuItem;

	@FindBy(xpath = "//div[@role='menuitem' and contains(.,'Edit')]")
	private WebElement editActionMenuItem;

	@StepName("Click Users / Employees from Sidebar")
	public void clickUsers() {
		navigateToClientRoute(usersNavButton, "employees");
	}

	@StepName("Click Employees (Backward Compatibility)")
	public void clickEmployees() {
		clickUsers();
	}

	@StepName("Verify Users Page is Loaded")
	public boolean isUsersPageLoaded() {
		return waitUtils.waitForUrlContains("employees", 2)
				|| waitUtils.waitForUrlContains("users", 2)
				|| waitUtils.waitForUrlContains("contractor", 2)
				|| waitUtils.waitForUrlContains("partner", 2)
				|| !driver.findElements(By.xpath("//h1[contains(.,'User') or contains(.,'Employee') or contains(.,'Contractor') or contains(.,'Partner')] | //header//h1 | //h2[contains(.,'User') or contains(.,'Employee') or contains(.,'Contractor')]")).isEmpty();
	}

	@StepName("Switch to Internal Users Tab")
	public void switchToInternalUsersTab() {
		List<WebElement> tabs = driver.findElements(By.xpath("//button[(contains(.,'Users') and not(contains(.,'Contractor')) and not(contains(.,'Partner'))) or contains(.,'Internal') or contains(.,'MCL') or contains(.,'JIL')]"));
		if (!tabs.isEmpty()) {
			waitUtils.scrollIntoView(tabs.get(0));
			waitUtils.clickUsingJS(tabs.get(0));
		}
	}

	@StepName("Switch to Contractor Users Tab")
	public void switchToContractorUsersTab() {
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

	@StepName("Open Add User Modal")
	public void clickAddUser() {
		waitUtils.waitForClickable(addUserButton);
		try {
			addUserButton.click();
		} catch (Exception e) {
			clickUsingJS(addUserButton);
		}
		waitUtils.waitForVisibility(fullNameInput);
	}

	@StepName("Close Add User Modal")
	public void closeAddUserModal() {
		try {
			waitUtils.waitForClickable(cancelModalButton);
			cancelModalButton.click();
		} catch (Exception e) {
			clickUsingJS(cancelModalButton);
		}
		try {
			waitUtils.waitUntil(d -> d.findElements(org.openqa.selenium.By.xpath("//div[@role='dialog']")).isEmpty(), 5);
		} catch (Exception ignored) {
		}
	}

	@StepName("Open First Row View Details Modal")
	public void openFirstRowViewDetails() {
		waitUtils.waitForClickable(firstRowActionMenu);
		firstRowActionMenu.click();
		waitUtils.waitForClickable(viewActionMenuItem);
		viewActionMenuItem.click();
	}

	@StepName("Verify User Details Modal is Displayed")
	public boolean isUserDetailsModalDisplayed() {
		waitUtils.waitForVisibility(userDetailsModalTitle);
		return userDetailsModalTitle.isDisplayed();
	}

	@StepName("Close User Details Modal")
	public void closeUserDetailsModal() {
		waitUtils.waitForClickable(closeDetailsModalButton);
		closeDetailsModalButton.click();
	}

	@StepName("Get Table Row Count")
	public int getTableRowCount() {
		return driver.findElements(By.xpath("//table//tbody//tr[not(.//td[@colSpan])]")).size();
	}

	@StepName("Is Empty Table State Displayed")
	public boolean isEmptyTableStateDisplayed() {
		return !driver.findElements(By.xpath("//td[contains(.,'No') or contains(.,'found') or @colSpan] | //div[contains(.,'No') and contains(.,'found')]")).isEmpty()
				|| getTableRowCount() == 0;
	}

	@StepName("Clear Users Search")
	public void clearSearch() {
		searchUsers("");
	}

	@StepName("Enter Full Name in Add User Modal")
	public void enterFullName(String name) {
		waitUtils.waitForVisibility(fullNameInput);
		fullNameInput.clear();
		fullNameInput.sendKeys(name);
	}

	@StepName("Enter Email in Add User Modal")
	public void enterEmail(String email) {
		waitUtils.waitForVisibility(emailInput);
		emailInput.clear();
		emailInput.sendKeys(email);
	}

	@StepName("Is Save User Button Enabled")
	public boolean isSaveUserButtonEnabled() {
		try {
			return saveUserButton.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Save User")
	public void clickSaveUser() {
		try {
			saveUserButton.click();
		} catch (Exception e) {
			clickUsingJS(saveUserButton);
		}
	}

	@StepName("Select Internal Employee Chip in Modal")
	public void selectModalInternalEmployee() {
		try {
			WebElement chip = driver.findElement(By.xpath("//*[@role='dialog']//button[contains(@class,'rounded-full')][1]"));
			waitUtils.waitForClickable(chip);
			chip.click();
		} catch (Exception e) {
			try {
				clickUsingJS(modalInternalChip);
			} catch (Exception ignored) {
			}
		}
	}

	@StepName("Select Contractor Employee Chip in Modal")
	public void selectModalContractorEmployee() {
		try {
			WebElement chip = driver.findElement(By.xpath("//*[@role='dialog']//button[contains(@class,'rounded-full')][2] | //*[@role='dialog']//button[contains(.,'Contractor') or contains(.,'Partner')]"));
			waitUtils.waitForClickable(chip);
			chip.click();
		} catch (Exception e) {
			try {
				clickUsingJS(modalContractorChip);
			} catch (Exception ignored) {
			}
		}
	}

	public void selectInternalChip() {
		selectModalInternalEmployee();
	}

	public void selectContractorChip() {
		selectModalContractorEmployee();
	}
}
