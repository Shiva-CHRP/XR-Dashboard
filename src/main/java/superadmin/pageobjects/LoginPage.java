package superadmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for CHRP Simulation Platform Authentication:
 * 1. User Sign In (/login) - Email, Password, Remember Me, Show/Hide Password
 * 2. Session Limit Handling (/session-selection) - Active Sessions, Force Logout & Login
 * 3. Validation & Error Handling (Inline Error Banners, Logout Reason Alerts, Toast Notifications)
 * 4. Forgot Password Recovery Link Navigation
 * 5. Role Switcher in Top Navigation (Super Admin vs. VR Developer)
 * 6. User Profile Menu & Sign Out from Sidebar
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class LoginPage extends AbstractComponent {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		this.pofileButton = this.profileButton;
	}

	// =========================================================================
	// 1. SIGN IN FORM LOCATORS
	// =========================================================================

	@FindBy(xpath = "//input[@type='email' or @placeholder='admin@company.com']")
	private WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password' or @placeholder='Enter your password' or contains(@class,'pr-10')]")
	private WebElement password;

	@FindBy(xpath = "//button[.//svg[contains(@class,'lucide-eye') or contains(@class,'lucide-eye-off')] or .//*[local-name()='svg' and contains(@class,'lucide-eye')]]")
	private WebElement togglePasswordVisibilityButton;

	@FindBy(xpath = "//input[@type='checkbox']")
	private WebElement rememberMeCheckbox;

	@FindBy(xpath = "//a[@href='/forgot-password' or contains(text(),'Forgot Password')]")
	private WebElement forgotPasswordLink;

	@FindBy(xpath = "//button[@type='submit' or contains(.,'Sign In') or contains(.,'Signing in')]")
	private WebElement signinButton;

	// Error & Alert Banners
	@FindBy(xpath = "//div[contains(@class,'text-titan-danger-text') or contains(@class,'bg-titan-danger')]")
	private WebElement errorMessageBanner;

	@FindBy(xpath = "//div[contains(@class,'border') and .//span[contains(text(),'session') or contains(text(),'Session')]]")
	private WebElement logoutReasonBanner;

	// =========================================================================
	// 2. SESSION LIMIT REACHED LOCATORS (/session-selection)
	// =========================================================================

	@FindBy(xpath = "//h1[normalize-space()='Session Limit Reached']")
	private WebElement sessionLimit;

	@FindBy(xpath = "//span[contains(text(),'Active Session') or contains(text(),'Active Sessions')]")
	private WebElement activeSessionsCountBadge;

	@FindBy(xpath = "//button[contains(.,'Log Out') or .//*[local-name()='svg' and contains(@class,'lucide-log-out')]][1]")
	private WebElement sessionLogout;

	@FindBy(xpath = "//button[contains(.,'Back to Login') or .//svg[contains(@class,'lucide-arrow-left')]]")
	private WebElement backToLoginButton;

	// =========================================================================
	// 3. POST-LOGIN / NAVIGATION LOCATORS
	// =========================================================================

	@FindBy(xpath = "//h1[normalize-space()='Governance Dashboard' or contains(text(),'Dashboard')]")
	private WebElement dashboardName;

	@FindBy(xpath = "//button[.//*[local-name()='svg' and contains(@class,'lucide-chevron-up')] or contains(@class,'group flex items-center w-full rounded-xl')]")
	private WebElement profileButton;

	// Backward-compatibility alias for tests referencing pofileButton directly
	public WebElement pofileButton;

	@FindBy(xpath = "//button[.//span[contains(text(),'Sign Out') or contains(text(),'Logout') or contains(text(),'Signing out')] or .//*[local-name()='svg' and contains(@class,'lucide-log-out')]]")
	private WebElement logoutButton;

	// Role Switcher in Top Navigation
	@FindBy(xpath = "//div[contains(@class,'relative') and contains(@class,'sm:block')]/button[1]")
	private WebElement roleDropdown;

	@FindBy(xpath = "//div[contains(@class,'absolute') or contains(@class,'rounded-xl')]//button[contains(.,'Super Admin')]")
	private WebElement superAdminRole;

	@FindBy(xpath = "//div[contains(@class,'absolute') or contains(@class,'rounded-xl')]//button[contains(.,'VR Developer')]")
	private WebElement vrDeveloperRole;


	// =========================================================================
	// 4. ACTION METHODS: Sign In Form
	// =========================================================================

	@StepName("Enter User Email Address")
	public void enterUsername(String username) {
		waitUtils.waitForVisibility(emailAddress);
		waitUtils.waitForClickable(emailAddress);
		try {
			emailAddress.clear();
		} catch (Exception e) {
			clickUsingJS(emailAddress);
		}
		emailAddress.sendKeys(username);
	}

	@StepName("Enter User Password")
	public void enterPassword(String passwords) {
		waitUtils.waitForVisibility(password);
		waitUtils.waitForClickable(password);
		try {
			password.clear();
		} catch (Exception e) {
			clickUsingJS(password);
		}
		password.sendKeys(passwords);
	}

	@StepName("Toggle Password Visibility")
	public void togglePasswordVisibility() {
		waitUtils.waitForClickable(togglePasswordVisibilityButton);
		togglePasswordVisibilityButton.click();
	}

	@StepName("Check if Password is Masked")
	public boolean isPasswordMasked() {
		try {
			return "password".equalsIgnoreCase(password.getAttribute("type"));
		} catch (Exception e) {
			return true;
		}
	}

	@StepName("Set Remember Me")
	public void setRememberMe(boolean check) {
		waitUtils.waitForVisibility(rememberMeCheckbox);
		if (rememberMeCheckbox.isSelected() != check) {
			waitUtils.waitForClickable(rememberMeCheckbox);
			rememberMeCheckbox.click();
		}
	}

	@StepName("Click on the Signin Button")
	public void clickLogin() {
		waitUtils.waitForClickable(signinButton);
		try {
			signinButton.click();
		} catch (Exception e) {
			clickUsingJS(signinButton);
		}
		// Conditionally handle session limit if reached without failing regular logins
		handleSessionLimitIfPresent();
	}

	@StepName("Clear Sign In Fields")
	public void clearFields() {
		try {
			emailAddress.clear();
			password.clear();
		} catch (Exception ignored) {
		}
	}

	@StepName("Click Forgot Password Link")
	public void clickForgotPassword() {
		waitUtils.waitForClickable(forgotPasswordLink);
		forgotPasswordLink.click();
	}

	@StepName("Get Inline Error Message")
	public String getErrorMessage() {
		try {
			waitUtils.waitForVisibility(errorMessageBanner);
			return errorMessageBanner.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Check if Error Message is Displayed")
	public boolean isErrorMessageDisplayed() {
		return isElementPresent(errorMessageBanner);
	}

	@StepName("Get Logout Reason Message")
	public String getLogoutReasonMessage() {
		try {
			waitUtils.waitForVisibility(logoutReasonBanner);
			return logoutReasonBanner.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}


	// =========================================================================
	// 5. ACTION METHODS: Session Limit Management
	// =========================================================================

	@StepName("Check if Session Limit Reached Screen is Displayed")
	public boolean isSessionLimitReached() {
		try {
			if (driver.getCurrentUrl() != null && driver.getCurrentUrl().contains("/session-selection")) {
				return true;
			}
			By sessionLimitBy = By.xpath("//h1[normalize-space()='Session Limit Reached']");
			List<WebElement> headings = driver.findElements(sessionLimitBy);
			return !headings.isEmpty() && headings.get(0).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Handle Session Limit if Reached")
	public void handleSessionLimitIfPresent() {
		try {
			if (isSessionLimitReached()) {
				waitUtils.waitForClickable(sessionLogout);
				sessionLogout.click();
			}
		} catch (Exception ignored) {
		}
	}

	@StepName("Get Active Sessions Count Badge")
	public String getActiveSessionsCount() {
		try {
			waitUtils.waitForVisibility(activeSessionsCountBadge);
			return activeSessionsCountBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Click Back to Login from Session Limit Screen")
	public void clickBackToLoginFromSessionLimit() {
		waitUtils.waitForClickable(backToLoginButton);
		backToLoginButton.click();
		waitUtils.waitForVisibility(emailAddress);
	}


	// =========================================================================
	// 6. ACTION METHODS: Post-Login Verification & Profile
	// =========================================================================

	@StepName("Verify Governance Dashboard is Loaded")
	public void dashboardName() {
		waitUtils.waitUntil(d -> {
			// Handle concurrent session limit screen if it appears asynchronously
			if (isSessionLimitReached()) {
				try {
					waitUtils.waitForClickable(sessionLogout);
					sessionLogout.click();
				} catch (Exception ignored) {
				}
			}
			// Check if any inline error banner appeared
			if (isElementPresent(errorMessageBanner)) {
				String msg = errorMessageBanner.getText().trim();
				if (!msg.isEmpty()) {
					throw new IllegalStateException("Login failed with error: " + msg);
				}
			}
			return isElementPresent(dashboardName);
		});
		waitUtils.waitForVisibility(dashboardName);
	}

	@StepName("Check if Dashboard is Loaded")
	public boolean isDashboardLoaded() {
		try {
			waitUtils.waitForVisibility(dashboardName);
			return dashboardName.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click on the Profile Button")
	public void clickProfile() {
		waitUtils.waitForClickable(profileButton);
		try {
			profileButton.click();
		} catch (Exception e) {
			clickUsingJS(profileButton);
		}
	}

	@StepName("Click on the Logout Button")
	public void clickLogOut() {
		waitUtils.waitForClickable(logoutButton);
		try {
			logoutButton.click();
		} catch (Exception e) {
			clickUsingJS(logoutButton);
		}
		waitUtils.waitForVisibility(emailAddress);
	}


	// =========================================================================
	// 7. ACTION METHODS: Role Switching
	// =========================================================================

	@StepName("Switch to Super Admin Role")
	public void switchToSuperAdmin() {
		waitUtils.waitForClickable(roleDropdown);
		roleDropdown.click();
		waitUtils.waitForClickable(superAdminRole);
		superAdminRole.click();
	}

	@StepName("Switch to VR Developer Role")
	public void switchToVRDeveloper() {
		waitUtils.waitForClickable(roleDropdown);
		roleDropdown.click();
		waitUtils.waitForClickable(vrDeveloperRole);
		vrDeveloperRole.click();
	}

	// Helper to safely check element presence without throwing
	private boolean isElementPresent(WebElement element) {
		try {
			return element != null && element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
