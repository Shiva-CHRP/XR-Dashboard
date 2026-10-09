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

	@FindBy(xpath = "//input[@placeholder='e.g. ACME-CORP' or contains(@placeholder,'ACME')] | //label[normalize-space()='Organisation Code']/following-sibling::input")
	private WebElement organisationCode;

	@FindBy(xpath = "//button[.//span[normalize-space()='Continue'] or normalize-space()='Continue']")
	private WebElement continueButton;

	@FindBy(xpath = "//input[@type='email' or @placeholder='you@company.com'] | //label[normalize-space()='Email address']/following-sibling::input")
	private WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password' or @placeholder='••••••••'] | //label[normalize-space()='Password']/following-sibling::input")
	private WebElement password;

	@FindBy(xpath = "//button[@type='submit' and (normalize-space()='Sign In' or contains(.,'Sign In'))]")
	private WebElement signInButton;

	@FindBy(xpath = "//button[@type='button' and (normalize-space()='Forgot password?' or contains(.,'Forgot'))]")
	private WebElement forgotPassword;

	@FindBy(xpath = "//button[normalize-space()='Change org' or contains(.,'Change org')]")
	private WebElement changeOrganisation;

	@StepName("Switch from Super Admin to Client Application")
	public void switchToClientApplication() {

		driver.switchTo().newWindow(WindowType.WINDOW);

		driver.get(ConfigReader.getClientUrl());
	}

	@StepName("Enter Organisation Code")
	public void enterOrganisationCode(String organisationCodeValue) {
		waitUtils.waitForVisibility(organisationCode);
		try {
			organisationCode.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		} catch (Exception ignored) {
		}
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
		try {
			emailAddress.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		} catch (Exception ignored) {
		}
		emailAddress.sendKeys(email);
	}

	@StepName("Enter Password")
	public void enterPassword(String passwordValue) {
		waitUtils.waitForVisibility(password);
		try {
			password.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		} catch (Exception ignored) {
		}
		password.sendKeys(passwordValue);
	}

	@StepName("Click Sign In")
	public void clickSignIn() {
		waitUtils.waitForClickable(signInButton);
		try {
			signInButton.click();
		} catch (Exception e) {
			clickUsingJS(signInButton);
		}
	}

	@StepName("Click Forgot Password")
	public void clickForgotPassword() {
		forgotPassword.click();
	}

	@StepName("Change Organisation")
	public void clickChangeOrganisation() {
		changeOrganisation.click();
	}

	@StepName("Proceed to Credentials Step")
	public void proceedToCredentialsStep(String orgCode) {
		try {
			waitUtils.waitUntil(d -> isOrganisationCodeFieldDisplayed() || isEmailFieldDisplayed(), 10);
			if (isOrganisationCodeFieldDisplayed()) {
				enterOrganisationCode(orgCode);
				clickContinue();
				try {
					waitUtils.waitUntil(d -> isEmailFieldDisplayed(), 6);
				} catch (Exception retry) {
					try {
						clickUsingJS(continueButton);
						waitUtils.waitUntil(d -> isEmailFieldDisplayed(), 6);
					} catch (Exception ignored) {
					}
				}
			}
		} catch (Exception ignored) {
		}
		try {
			waitUtils.waitUntil(d -> isEmailFieldDisplayed(), 6);
		} catch (Exception ignored) {
		}
	}

	@StepName("Login to Client Application with Credentials")
	public void loginToClient(String orgCode, String email, String passwordValue) {
		proceedToCredentialsStep(orgCode);
		enterEmailAddress(email);
		enterPassword(passwordValue);
		clickSignIn();
		try {
			waitUtils.waitUntil(d -> {
				if (isSessionLimitReached()) {
					handleSessionLimitIfPresent();
				}
				return !d.findElements(org.openqa.selenium.By.xpath("//aside//button")).isEmpty()
						|| d.getCurrentUrl().contains("dashboard")
						|| d.getCurrentUrl().contains("admin");
			}, 20);
		} catch (Exception ignored) {
		}
	}

	@StepName("Check if Session Limit Reached Screen is Displayed")
	public boolean isSessionLimitReached() {
		try {
			return !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(text(),'Session Limit Reached')]")).isEmpty()
					|| (driver.getCurrentUrl() != null && driver.getCurrentUrl().contains("session"));
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Handle Session Limit if Reached")
	public void handleSessionLimitIfPresent() {
		try {
			if (isSessionLimitReached()) {
				java.util.List<WebElement> logoutButtons = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(normalize-space(),'Log Out Device')]"));
				if (!logoutButtons.isEmpty()) {
					waitUtils.waitForClickable(logoutButtons.get(0));
					try {
						logoutButtons.get(0).click();
					} catch (Exception e) {
						clickUsingJS(logoutButtons.get(0));
					}
					try {
						waitUtils.waitUntil(d -> !isSessionLimitReached() && !d.findElements(org.openqa.selenium.By.xpath("//aside//button")).isEmpty(), 15);
					} catch (Exception ignored) {
					}
				}
			}
		} catch (Exception ignored) {
		}
	}

	@StepName("Check if Organisation Code Field is Displayed")
	public boolean isOrganisationCodeFieldDisplayed() {
		return !driver.findElements(By.xpath("//input[@placeholder='e.g. ACME-CORP' or contains(@placeholder,'ACME')] | //label[normalize-space()='Organisation Code']/following-sibling::input")).isEmpty();
	}

	@StepName("Check if Email Field is Displayed")
	public boolean isEmailFieldDisplayed() {
		return !driver.findElements(By.xpath("//input[@type='email' or @placeholder='you@company.com'] | //label[normalize-space()='Email address']/following-sibling::input")).isEmpty();
	}

	@StepName("Clear Organisation Code")
	public void clearOrganisationCode() {
		try {
			waitUtils.waitForVisibility(organisationCode);
			organisationCode.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		} catch (Exception ignored) {
		}
	}

	@StepName("Clear Credentials")
	public void clearCredentials() {
		try {
			emailAddress.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
			password.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		} catch (Exception ignored) {
		}
	}
}
