package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

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
	private List<WebElement> userRoleRows;

	@FindBy(xpath = "//button[contains(.,'All') and not(contains(.,'Roles'))]")
	private WebElement allTab;

	@FindBy(xpath = "//button[contains(.,'Managers')]")
	private WebElement managersTab;

	@FindBy(xpath = "//button[contains(.,'Trainers')]")
	private WebElement trainersTab;

	@FindBy(xpath = "//button[contains(.,'Admins')]")
	private WebElement adminsTab;

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

	@StepName("Switch Role Tab")
	public void switchRoleTab(String tabName) {
		String lower = tabName.toLowerCase().trim();
		By tabBy = By.xpath("//button[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + lower + "')]");
		List<WebElement> tabs = driver.findElements(tabBy);
		if (!tabs.isEmpty()) {
			waitUtils.scrollIntoView(tabs.get(0));
			try {
				tabs.get(0).click();
			} catch (Exception e) {
				clickUsingJS(tabs.get(0));
			}
		}
	}

	@StepName("Assign Role to User")
	public ToastResponse assignRoleToUser(String userSearch, String targetRole) {
		searchRoleAssignment(userSearch);
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

		By roleBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'" + targetRole + "')]");
		List<WebElement> roleBtns = driver.findElements(roleBtnBy);
		if (!roleBtns.isEmpty()) {
			waitUtils.scrollIntoView(roleBtns.get(0));
			try {
				roleBtns.get(0).click();
			} catch (Exception e) {
				clickUsingJS(roleBtns.get(0));
			}
		}

		By saveBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Save Changes') or contains(.,'Save')]");
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
}