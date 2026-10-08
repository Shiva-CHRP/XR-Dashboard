package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class RoleAssignment extends AbstractComponent {

	public RoleAssignment(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Role Assignment']] | //button[.//span[normalize-space()='Role Assignment']]")
	private WebElement roleAssignmentNavButton;

	@FindBy(xpath = "//h1[contains(.,'Role Assignment') or contains(.,'Roles')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> userRoleRows;

	@StepName("Click Role Assignment from Sidebar")
	public void clickRoleAssignment() {
		navigateToClientRoute(roleAssignmentNavButton, "users");
	}

	@StepName("Verify Role Assignment Page is Loaded")
	public boolean isRoleAssignmentLoaded() {
		return waitUtils.waitForUrlContains("users") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Search Role Assignment")
	public void searchRoleAssignment(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get User Role Rows Count")
	public int getUserRoleRowsCount() {
		return userRoleRows.size();
	}
}
