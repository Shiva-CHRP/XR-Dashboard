package clientportaladmin.pageobjects;

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

	@FindBy(xpath = "//button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']]")
	private WebElement usersNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Users'] | //header//h1[contains(.,'Users')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(.,'Internal') or contains(.,'MCL')]")
	private WebElement internalUsersTab;

	@FindBy(xpath = "//button[contains(.,'Contractor') or contains(.,'Partner')]")
	private WebElement contractorUsersTab;

	@FindBy(xpath = "//input[contains(@placeholder,'Search users')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add User')]")
	private WebElement addUserButton;

	@FindBy(xpath = "//button[contains(normalize-space(),'Import CSV')]")
	private WebElement importCsvButton;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	// Add User Modal Locators
	@FindBy(xpath = "//*[@role='dialog']//input[@placeholder='Full name' or @placeholder='John Doe' or contains(@placeholder,'name')]")
	private WebElement fullNameInput;

	@FindBy(xpath = "//*[@role='dialog']//input[@placeholder='EMP001' or contains(@placeholder,'ID') or @placeholder='User ID']")
	private WebElement employeeIdInput;

	@FindBy(xpath = "//*[@role='dialog']//input[@type='email' or contains(@placeholder,'acme.com')]")
	private WebElement emailInput;

	@FindBy(xpath = "//*[@role='dialog']//input[@type='tel' or contains(@placeholder,'9876543210')]")
	private WebElement phoneInput;

	@FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Save User' or normalize-space()='Save' or normalize-space()='Add User']")
	private WebElement saveUserButton;

	@FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Cancel']")
	private WebElement cancelModalButton;

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
		waitUtils.waitForClickable(usersNavButton);
		usersNavButton.click();
	}

	@StepName("Click Employees (Backward Compatibility)")
	public void clickEmployees() {
		clickUsers();
	}

	@StepName("Verify Users Page is Loaded")
	public boolean isUsersPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Switch to Internal Users Tab")
	public void switchToInternalUsersTab() {
		waitUtils.waitForClickable(internalUsersTab);
		internalUsersTab.click();
	}

	@StepName("Switch to Contractor Users Tab")
	public void switchToContractorUsersTab() {
		waitUtils.waitForClickable(contractorUsersTab);
		contractorUsersTab.click();
	}

	@StepName("Search Users")
	public void searchUsers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Open Add User Modal")
	public void clickAddUser() {
		waitUtils.waitForClickable(addUserButton);
		addUserButton.click();
	}

	@StepName("Close Add User Modal")
	public void closeAddUserModal() {
		waitUtils.waitForClickable(cancelModalButton);
		cancelModalButton.click();
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
}
