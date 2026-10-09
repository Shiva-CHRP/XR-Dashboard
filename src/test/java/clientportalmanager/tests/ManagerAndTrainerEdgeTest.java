package clientportalmanager.tests;

import org.testng.annotations.BeforeClass;
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
 * Enterprise Operational & Role Edge Case Suite for Client Manager & Trainer Roles.
 * Validates negative role authentication, operational boundaries, and search filters.
 */
@Listeners(Listener.class)
public class ManagerAndTrainerEdgeTest extends ClientBaseTest {

	private String orgCode = ConfigReader.getClientOrgCode();
	private String clientAdminEmail = ConfigReader.getClientUsername();
	private String clientAdminPassword = ConfigReader.getClientPassword();
	private String managerEmail = ConfigReader.getClientManagerUsername();
	private String managerPassword = ConfigReader.getClientManagerPassword();
	private String trainerEmail = ConfigReader.getClientTrainerUsername();
	private String trainerPassword = ConfigReader.getClientTrainerPassword();

	@BeforeMethod(alwaysRun = true)
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
	}

	@Test(priority = 1)
	@TestInfo(module = "Manager & Trainer Edge Cases", description = "Verify Manager credentials invalid for organization triggers error toast", priority = "High")
	public void verify_Manager_Credentials_Invalid_Org_Authentication() {
		clientLogin.proceedToCredentialsStep(orgCode);
		clientLogin.enterEmailAddress(managerEmail);
		clientLogin.enterPassword("InvalidManagerPassword@999!");
		clientLogin.clickSignIn();

		boolean hasError = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasError = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("incorrect") || toast.getMessage().toLowerCase().contains("invalid");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasError || driver.getCurrentUrl().contains("/login"), "Invalid manager credentials must trigger error toast or remain on login");
		clientLogin.clearCredentials();
		softAssert.assertAll();
	}

	@Test(priority = 2)
	@TestInfo(module = "Manager & Trainer Edge Cases", description = "Verify Trainer credentials invalid for organization triggers error toast", priority = "High")
	public void verify_Trainer_Credentials_Invalid_Org_Authentication() {
		if (!driver.getCurrentUrl().contains("/login")) {
			driver.get(ConfigReader.getClientUrl());
		}
		clientLogin.proceedToCredentialsStep(orgCode);
		clientLogin.enterEmailAddress(trainerEmail);
		clientLogin.enterPassword("InvalidTrainerPassword@999!");
		clientLogin.clickSignIn();

		boolean hasError = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasError = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("incorrect") || toast.getMessage().toLowerCase().contains("invalid");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(hasError || driver.getCurrentUrl().contains("/login"), "Invalid trainer credentials must trigger error toast or remain on login");
		clientLogin.clearCredentials();
		softAssert.assertAll();
	}

	@Test(priority = 3)
	@TestInfo(module = "Manager & Trainer Edge Cases", description = "Login to Client Portal for Operations Testing", priority = "Critical")
	public void login_For_Operations_Testing() throws InterruptedException {
		driver.get(ConfigReader.getClientUrl());
		clientLogin.loginToClient(orgCode, clientAdminEmail, clientAdminPassword);
		clientLogin.handleSessionLimitIfPresent();
		try {
			waitUtils.waitUntil(d -> overview.isOverviewPageLoaded() || !d.findElements(org.openqa.selenium.By.xpath("//aside//button")).isEmpty(), 15);
		} catch (Exception ignored) {
		}
		softAssert.assertTrue(overview.isOverviewPageLoaded(), "Overview dashboard should load post-login");
		softAssert.assertAll();
	}

	@Test(priority = 4, dependsOnMethods = {"login_For_Operations_Testing"})
	@TestInfo(module = "Manager & Trainer Edge Cases", description = "Verify Assign Curriculums search handles non-existent query safely", priority = "High")
	public void verify_Assign_Curriculums_Non_Existent_Search() {
		assignCurriculums.clickAssignCurriculums();
		softAssert.assertTrue(assignCurriculums.isAssignCurriculumsLoaded(), "Assign Curriculums page should be loaded");
		assignCurriculums.searchTrainers("__NO_TRAINER_EXISTS_404__");
		softAssert.assertTrue(assignCurriculums.isAssignCurriculumsLoaded(), "Assign Curriculums page should remain loaded after search");
		assignCurriculums.searchTrainers("");
		softAssert.assertAll();
	}

	@Test(priority = 5, dependsOnMethods = {"login_For_Operations_Testing"})
	@TestInfo(module = "Manager & Trainer Edge Cases", description = "Verify Assign Modules search handles non-existent query safely", priority = "High")
	public void verify_Assign_Modules_Non_Existent_Search() {
		assignModules.clickAssignModules();
		softAssert.assertTrue(assignModules.isAssignModulesLoaded(), "Assign Modules page should be loaded");
		assignModules.searchTrainers("__NO_TRAINER_EXISTS_404__");
		softAssert.assertTrue(assignModules.isAssignModulesLoaded(), "Assign Modules page should remain loaded after search");
		assignModules.searchTrainers("");
		softAssert.assertAll();
	}

	@Test(priority = 6, dependsOnMethods = {"login_For_Operations_Testing"})
	@TestInfo(module = "Manager & Trainer Edge Cases", description = "Verify Sessions search with non-existent query renders safely", priority = "Medium")
	public void verify_Sessions_Non_Existent_Search() {
		events.clickSessions();
		softAssert.assertTrue(events.isEventsPageLoaded(), "Events page should be loaded");
		events.searchEvents("__NO_SESSION_EXISTS_404__");
		softAssert.assertTrue(events.isEventsPageLoaded(), "Events page should remain loaded after search");
		events.searchEvents("");
		softAssert.assertAll();
	}
}
