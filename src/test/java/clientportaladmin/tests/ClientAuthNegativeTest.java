package clientportaladmin.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;
import superadmin.utils.ToastResponse;

/**
 * Enterprise Negative Authentication & Multi-Tenant Security Suite for Client Portal.
 * Validates Org Code validation, invalid credentials, change organization flow, and route guards.
 */
@Listeners(Listener.class)
public class ClientAuthNegativeTest extends ClientBaseTest {

	private String validOrgCode;
	private String validEmail;

	@BeforeMethod(alwaysRun = true)
	public void initData() {
		validOrgCode = ConfigReader.getClientOrgCode();
		validEmail = ConfigReader.getClientUsername();
		softAssert = new SoftAssert();
		if (!driver.getCurrentUrl().contains("/login")) {
			driver.get(ConfigReader.getClientUrl());
		}
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Auth Negative", description = "Verify Step 1 submission with Invalid Organisation Code displays error", priority = "Critical")
	public void verify_Step1_Invalid_Organisation_Code() {
		clientLogin.clearOrganisationCode();
		clientLogin.enterOrganisationCode("NON-EXISTENT-ORG-9999");
		clientLogin.clickContinue();

		boolean hasErrorToast = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasErrorToast = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("not found") || toast.getMessage().toLowerCase().contains("invalid");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasErrorToast || clientLogin.isOrganisationCodeFieldDisplayed(), "Invalid org code should trigger an error toast or remain on Step 1");
		clientLogin.clearOrganisationCode();
		softAssert.assertAll();
	}

	@Test(priority = 2)
	@TestInfo(module = "Client Auth Negative", description = "Verify Step 1 submission with Blank Organisation Code is blocked", priority = "High")
	public void verify_Step1_Blank_Organisation_Code() {
		clientLogin.clearOrganisationCode();
		clientLogin.clickContinue();

		softAssert.assertTrue(clientLogin.isOrganisationCodeFieldDisplayed(), "Application should remain on Step 1 when organisation code is blank");
		softAssert.assertAll();
	}

	@Test(priority = 3)
	@TestInfo(module = "Client Auth Negative", description = "Verify Step 2 with Valid Org Code and Invalid Password triggers error toast", priority = "Critical")
	public void verify_Step2_Invalid_Password() {
		driver.get(ConfigReader.getClientUrl());
		if (clientLogin.isOrganisationCodeFieldDisplayed()) {
			clientLogin.enterOrganisationCode(validOrgCode);
			clientLogin.clickContinue();
		}

		softAssert.assertTrue(clientLogin.isEmailFieldDisplayed(), "Email field should be visible on Step 2");
		clientLogin.enterEmailAddress(validEmail);
		clientLogin.enterPassword("WrongClientPassword@999!");
		clientLogin.clickSignIn();

		boolean hasErrorToast = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasErrorToast = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("invalid");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasErrorToast || clientLogin.isEmailFieldDisplayed(), "Invalid password should trigger an error toast or remain on login with email visible");
		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "User must remain on login page");
		clientLogin.clearCredentials();
		softAssert.assertAll();
	}

	@Test(priority = 4)
	@TestInfo(module = "Client Auth Negative", description = "Verify Step 2 with Unregistered Email triggers error toast", priority = "Critical")
	public void verify_Step2_Unregistered_Email() {
		driver.get(ConfigReader.getClientUrl());
		if (clientLogin.isOrganisationCodeFieldDisplayed()) {
			clientLogin.enterOrganisationCode(validOrgCode);
			clientLogin.clickContinue();
		}

		clientLogin.clearCredentials();
		clientLogin.enterEmailAddress("nonexistent_user_9999@testcorp.com");
		clientLogin.enterPassword("SomePassword@123");
		clientLogin.clickSignIn();

		boolean hasErrorToast = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasErrorToast = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("invalid") || toast.getMessage().toLowerCase().contains("not found");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasErrorToast || clientLogin.isEmailFieldDisplayed(), "Unregistered user email should trigger error notification or remain on login");
		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "User must remain on login page");
		clientLogin.clearCredentials();
		softAssert.assertAll();
	}

	@Test(priority = 5)
	@TestInfo(module = "Client Auth Negative", description = "Verify Change Organisation button resets form to Step 1", priority = "Medium")
	public void verify_Step2_Change_Organisation_Flow() {
		if (!clientLogin.isOrganisationCodeFieldDisplayed()) {
			clientLogin.clickChangeOrganisation();
		}

		softAssert.assertTrue(clientLogin.isOrganisationCodeFieldDisplayed(), "Clicking Change Org must navigate back to Organisation Code input");
		softAssert.assertAll();
	}

	@Test(priority = 6)
	@TestInfo(module = "Client Auth Negative", description = "Verify Unauthorized Direct Route Access redirects to /login", priority = "High")
	public void verify_Unauthorized_Direct_Route_Access() {
		driver.get(ConfigReader.getClientUrl() + "/admin/dashboard");
		boolean onLogin = waitUtils.waitForUrlContains("login", 5);

		softAssert.assertTrue(onLogin || driver.getCurrentUrl().contains("/login"), "Direct unauthenticated access to /admin/dashboard must redirect to login");
		softAssert.assertAll();
	}
}
