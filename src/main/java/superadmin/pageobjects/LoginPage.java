package superadmin.pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class LoginPage extends AbstractComponent {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@type='email']")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password']")
	WebElement password;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement signinButton;

	@FindBy(xpath = "//button[.//span[normalize-space()='Logout']]")
	WebElement logoutButton;

	@FindBy(xpath = "//h1[normalize-space()='Governance Dashboard']")
	WebElement dashboardName;

	@FindBy(xpath = "//h1[normalize-space()='Session Limit Reached']")
	WebElement sessionLimit;

	@FindBy(xpath = "//button[.//*[local-name()='svg' and contains(@class,'lucide-log-out')]][1]")
	private WebElement sessionLogout;

	@FindBy(xpath = "//button[.//*[local-name()='svg' and contains(@class,'lucide-chevron-up')]]")
	private WebElement pofileButton;

	@FindBy(xpath = "//div[contains(@class,'relative') and contains(@class,'hidden') and contains(@class,'sm:block')]/button[1]")
	private WebElement roleDropdown;

	@FindBy(xpath = "//div[contains(@class,'absolute') and contains(@class,'w-48')]//button[.//span[contains(text(),'Super Admin')]]")
	private WebElement superAdminRole;

	@FindBy(xpath = "//div[contains(@class,'absolute') and contains(@class,'w-48')]//button[.//span[contains(text(),'VR Developer')]]")
	private WebElement vrDeveloperRole;

	@StepName("Enter User Email Address")
	public void enterUsername(String username) {
		waitForVisibility(emailAddress);
		waitElementToBeClickable(emailAddress);
		try {
			emailAddress.clear();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", emailAddress);
		}
		// emailAddress.clear();
		emailAddress.sendKeys(username);
	}

	@StepName("Enter User Password")
	public void enterPassword(String passwords) {
		waitForVisibility(password);
		waitElementToBeClickable(password);
		try {
			password.clear();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", password);
		}
		password.clear();
		password.sendKeys(passwords);
	}

	@StepName("Click on the Signin Button")
	public void clickLogin() {
		signinButton.click();
		waitElementToAppear(sessionLimit);
		if (sessionLimit.isDisplayed()) {
			sessionLogout.click();
		}
	}

	@StepName("Click on the Profile Button")
	public void clickProfile() {
		pofileButton.click();
	}

	@StepName("Click on the Logout Button")
	public void clickLogOut() {
		logoutButton.click();
		waitForVisibility(emailAddress);
	}

	public void dashboardName() {
		waitElementToAppear(dashboardName);
	}

	public void clearFields() {
		emailAddress.clear();
		password.clear();
	}

	@StepName("Switch to Super Admin Role")
	public void switchToSuperAdmin() {
		roleDropdown.click();
		superAdminRole.click();
	}

	@StepName("Switch to VR Developer Role")
	public void switchToVRDeveloper() {
		roleDropdown.click();
		vrDeveloperRole.click();
	}
}
