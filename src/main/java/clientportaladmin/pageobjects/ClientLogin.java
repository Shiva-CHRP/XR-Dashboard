package clientportaladmin.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ConfigReader;

public class ClientLogin extends AbstractComponent {

	public ClientLogin(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[normalize-space()='Organisation Code']/following-sibling::input")
	private WebElement organisationCode;

	@FindBy(xpath = "//button[.//span[normalize-space()='Continue']]")
	private WebElement continueButton;

	@FindBy(xpath = "//label[normalize-space()='Email address']/following-sibling::input")
	private WebElement emailAddress;

	@FindBy(xpath = "//label[normalize-space()='Password']/following-sibling::input")
	private WebElement password;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Sign In']")
	private WebElement signInButton;

	@FindBy(xpath = "//button[@type='button' and normalize-space()='Forgot password?']")
	private WebElement forgotPassword;

	@FindBy(xpath = "//button[normalize-space()='Change org']")
	private WebElement changeOrganisation;

	@StepName("Switch from Super Admin to Client Application")
	public void switchToClientApplication() {

		driver.switchTo().newWindow(WindowType.WINDOW);

		driver.get(ConfigReader.getClientUrl());
	}

	@StepName("Enter Organisation Code")
	public void enterOrganisationCode(String organisationCodeValue) {
		waitUtils.waitForVisibility(organisationCode);
		organisationCode.clear();
		organisationCode.sendKeys(organisationCodeValue);
	}

	@StepName("Click Continue")
	public void clickContinue() {
		waitUtils.waitForClickable(continueButton);
		continueButton.click();
	}

	@StepName("Enter Email Address")
	public void enterEmailAddress(String email) {
		waitUtils.waitForVisibility(emailAddress);
		emailAddress.clear();
		emailAddress.sendKeys(email);
	}

	@StepName("Enter Password")
	public void enterPassword(String passwordValue) {
		waitUtils.waitForVisibility(password);
		password.clear();
		password.sendKeys(passwordValue);
	}

	@StepName("Click Sign In")
	public void clickSignIn() {
		waitUtils.waitForClickable(signInButton);
		signInButton.click();
	}

	@StepName("Click Forgot Password")
	public void clickForgotPassword() {
		forgotPassword.click();
	}

	@StepName("Change Organisation")
	public void clickChangeOrganisation() {
		changeOrganisation.click();
	}

	@StepName("Login to Client Application with Credentials")
	public void loginToClient(String orgCode, String email, String passwordValue) {
		try {
			if (!driver.findElements(By.xpath("//label[normalize-space()='Organisation Code']/following-sibling::input")).isEmpty()) {
				enterOrganisationCode(orgCode);
				clickContinue();
			}
		} catch (Exception e) {
			// If already past organization code step, continue to credentials
		}
		enterEmailAddress(email);
		enterPassword(passwordValue);
		clickSignIn();
	}

}
