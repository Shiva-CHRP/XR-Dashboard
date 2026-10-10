package superadmin.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;
import superadmin.utils.ToastResponse;

/**
 * Enterprise Negative Authentication & Security Test Suite for Super Admin.
 * Validates error toasts, inline banners, input masking, and unauthorized route guards.
 */
@Listeners(Listener.class)
@Test(groups = {"regression", "superadmin"})
public class SuperAdminAuthNegativeTest extends BaseTest {

	private String validUsername;

	@BeforeMethod(alwaysRun = true)
	public void initData() {
		validUsername = ConfigReader.getUsername();
		softAssert = new SoftAssert();
		if (!driver.getCurrentUrl().contains("/login")) {
			driver.get(ConfigReader.getUrl());
		}
		loginPage.clearFields();
	}

	@Test(priority = 1)
	@TestInfo(module = "Auth Negative", description = "Verify Login with Valid Email and Invalid Password triggers error notification", priority = "Critical")
	public void verify_Login_With_Invalid_Password() {
		loginPage.clearFields();
		loginPage.enterUsername(validUsername);
		loginPage.enterPassword("WrongPassword@999!");
		loginPage.clickLogin();

		boolean hasErrorBanner = loginPage.isErrorMessageDisplayed();
		boolean hasErrorToast = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasErrorToast = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("invalid") || toast.getMessage().toLowerCase().contains("credential");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasErrorBanner || hasErrorToast, "An error toast or error banner should appear for invalid password");
		softAssert.assertTrue(driver.getCurrentUrl().contains("/login") || !driver.getCurrentUrl().contains("/super-admin/governance"), "User must not be logged in");
		loginPage.clearFields();
		softAssert.assertAll();
	}

	@Test(priority = 2)
	@TestInfo(module = "Auth Negative", description = "Verify Login with Unregistered Email triggers error notification", priority = "Critical")
	public void verify_Login_With_Unregistered_Email() {
		driver.get(ConfigReader.getUrl());
		loginPage.clearFields();
		loginPage.enterUsername("nonexistent_admin_9999@chrp-india.com");
		loginPage.enterPassword("RandomPass@12345");
		loginPage.clickLogin();

		boolean hasErrorBanner = loginPage.isErrorMessageDisplayed();
		boolean hasErrorToast = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasErrorToast = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("invalid") || toast.getMessage().toLowerCase().contains("not found");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasErrorBanner || hasErrorToast || driver.getCurrentUrl().contains("/login"), "An error toast, banner, or remaining on login should result from unregistered email");
		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "User must stay on login page");
		loginPage.clearFields();
		softAssert.assertAll();
	}

	@Test(priority = 3)
	@TestInfo(module = "Auth Negative", description = "Verify Blank Credentials submission does not navigate away", priority = "High")
	public void verify_Login_With_Blank_Credentials() {
		loginPage.clearFields();
		loginPage.clickLogin();

		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "Application must remain on /login when credentials are blank");
		softAssert.assertAll();
	}

	@Test(priority = 4)
	@TestInfo(module = "Auth Negative", description = "Verify Password Visibility Toggle unmasks and re-masks password input", priority = "Medium")
	public void verify_Password_Visibility_Toggle() {
		loginPage.clearFields();
		loginPage.enterPassword("SecretPass@123");

		softAssert.assertTrue(loginPage.isPasswordMasked(), "Password field should initially be masked (type='password')");

		loginPage.togglePasswordVisibility();
		softAssert.assertFalse(loginPage.isPasswordMasked(), "Password field should be unmasked (type='text') after clicking eye toggle");

		loginPage.togglePasswordVisibility();
		softAssert.assertTrue(loginPage.isPasswordMasked(), "Password field should return to masked state after toggling again");

		loginPage.clearFields();
		softAssert.assertAll();
	}

	@Test(priority = 5)
	@TestInfo(module = "Auth Negative", description = "Verify Direct Protected Route Access without Session redirects to /login", priority = "High")
	public void verify_Unauthorized_Direct_Route_Access() {
		driver.get(ConfigReader.getUrl() + "/super-admin/organizations");
		boolean onLogin = waitUtils.waitForUrlContains("login", 5);

		softAssert.assertTrue(onLogin || driver.getCurrentUrl().contains("/login"), "Unauthenticated direct access to /super-admin/organizations must redirect to /login");
		softAssert.assertAll();
	}
}
