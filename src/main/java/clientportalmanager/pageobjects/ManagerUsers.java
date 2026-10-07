package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerUsers extends AbstractComponent {

	public ManagerUsers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Users'] or .//span[normalize-space()='Employees']]")
	private WebElement usersButton;

	@FindBy(xpath = "//h1[contains(.,'Users') or contains(.,'Employees')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Internal') or contains(normalize-space(),'MCL')]")
	private WebElement internalUsersTab;

	@FindBy(xpath = "//button[contains(normalize-space(),'Contractor')]")
	private WebElement contractorUsersTab;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> userRows;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@StepName("Click Manager Users")
	public void clickManagerUsers() {
		waitUtils.waitForClickable(usersButton);
		usersButton.click();
	}

	@StepName("Verify Manager Users Page is Loaded")
	public boolean isManagerUsersPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Switch to Internal Users Tab")
	public void clickInternalUsersTab() {
		waitUtils.waitForClickable(internalUsersTab);
		internalUsersTab.click();
	}

	@StepName("Switch to Contractor Users Tab")
	public void clickContractorUsersTab() {
		waitUtils.waitForClickable(contractorUsersTab);
		contractorUsersTab.click();
	}

	@StepName("Search Users")
	public void searchUsers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get User Rows Count")
	public int getUserRowsCount() {
		return userRows.size();
	}
}
