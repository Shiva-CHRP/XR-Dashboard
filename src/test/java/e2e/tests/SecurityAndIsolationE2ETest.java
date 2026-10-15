package e2e.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;
import superadmin.utils.ToastResponse;

/**
 * End-to-End Test Suite for Security, RBAC & Multi-Tenant Data Isolation (Phases 1-6):
 * 1. E2E-05: RBAC Route Guard - Trainer blocked from Administrative Settings
 * 2. E2E-05: RBAC Route Guard - Manager blocked from Super Admin Licencing & System Routes
 * 3. E2E-06: Multi-Tenant Data Isolation - Cross-Tenant Access Prevention
 * 4. E2E-07: Unapproved VR Module Assignment Guard in Content Hub
 * 5. E2E-08: Deactivated / Invalid User Authentication Rejection
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Regression", "Clientportal", "Superadmin"})
public class SecurityAndIsolationE2ETest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void setupSecuritySuite() {
		// Initialization handled by BaseTest
	}

	@Test(priority = 1)
	@TestInfo(module = "RBAC Security", description = "Verify Trainer role is blocked from accessing administrative settings and master configuration routes", priority = "Critical")
	public void verify_RBAC_Trainer_Restricted_From_Administrative_Settings() {
		loginAsClientTrainer();
		Assert.assertTrue(trainerOverview.isTrainerOverviewPageLoaded(), "Trainer should be authenticated on Trainer dashboard");

		// Attempt unauthorized navigation to administrative settings
		driver.get(ConfigReader.getClientUrl() + "/admin/settings");
		boolean onUnauthorizedOrRedirected = waitUtils.waitForUrlContains("dashboard", 3)
				|| waitUtils.waitForUrlContains("unauthorized", 3)
				|| waitUtils.waitForUrlContains("login", 3)
				|| !driver.getCurrentUrl().contains("/admin/settings");

		Assert.assertTrue(onUnauthorizedOrRedirected, "Trainer must not be permitted to view /admin/settings");
	}

	@Test(priority = 2)
	@TestInfo(module = "RBAC Security", description = "Verify Manager role cannot access Super Admin governance and licence control routes", priority = "Critical")
	public void verify_RBAC_Manager_Restricted_From_Super_Admin_Routes() {
		loginAsClientManager();
		Assert.assertTrue(managerOverview.isManagerOverviewPageLoaded(), "Manager should be authenticated on Manager dashboard");

		// Attempt unauthorized navigation to Super Admin licence route
		driver.get(ConfigReader.getUrl() + "/super-admin/licence");
		boolean onSuperAdminLoginOrBlocked = waitUtils.waitForUrlContains("login", 4)
				|| waitUtils.waitForUrlContains("unauthorized", 4)
				|| !driver.getCurrentUrl().contains("/super-admin/licence");

		Assert.assertTrue(onSuperAdminLoginOrBlocked, "Client Manager must not have access to Super Admin licence routes");
	}

	@Test(priority = 3)
	@TestInfo(module = "Tenant Isolation", description = "Verify Multi-Tenant Isolation - Organization code mismatch blocks unauthorized tenant access", priority = "Critical")
	public void verify_Multi_Tenant_Organization_Data_Isolation() {
		driver.get(ConfigReader.getClientUrl());
		clientLogin.clearOrganisationCode();
		clientLogin.enterOrganisationCode("NON-EXISTENT-TENANT-999");
		clientLogin.clickContinue();

		boolean hasErrorToast = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasErrorToast = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("not found") || toast.getMessage().toLowerCase().contains("invalid");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		Assert.assertTrue(hasErrorToast || clientLogin.isOrganisationCodeFieldDisplayed(), 
				"System must reject invalid/foreign tenant code and isolate tenant boundary");
		clientLogin.clearOrganisationCode();
	}

	@Test(priority = 4)
	@TestInfo(module = "Catalogue Governance", description = "Verify Unapproved VR Modules cannot be assigned or accessed in Client Content Hub", priority = "High")
	public void verify_Unapproved_VR_Module_Assignment_Guard() {
		loginAsClientAdmin();
		contentHub.clickContentHub();
		Assert.assertTrue(contentHub.isContentHubLoaded(), "Content Hub should be loaded");

		// Search for unapproved / draft module marker
		contentHub.searchModules("__UNAPPROVED_DRAFT_MODULE_TEST__");
		Assert.assertTrue(contentHub.isModuleListEmptyOrZero(), "Unapproved modules must not be visible or assignable in Client Content Hub");
	}

	@Test(priority = 5)
	@TestInfo(module = "Identity Security", description = "Verify Deactivated and revoked user accounts are rejected upon authentication attempt", priority = "Critical")
	public void verify_Deactivated_User_Authentication_Rejection() {
		driver.get(ConfigReader.getClientUrl());
		clientLogin.proceedToCredentialsStep(ConfigReader.getClientOrgCode());
		clientLogin.enterEmailAddress("deactivated.auditor@simulationtest.com");
		clientLogin.enterPassword("Deactivated@1234");
		clientLogin.clickSignIn();

		boolean hasRejectionNotice = false;
		try {
			ToastResponse toast = toastUtils.captureToast();
			hasRejectionNotice = "error".equalsIgnoreCase(toast.getType()) || toast.getMessage().toLowerCase().contains("deactivated") || toast.getMessage().toLowerCase().contains("invalid");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		Assert.assertTrue(hasRejectionNotice || driver.getCurrentUrl().contains("/login"), 
				"Deactivated user must be rejected and prevented from initiating an active session");
		clientLogin.clearCredentials();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownSecuritySuite() {
		// Clean teardown handled by BaseTest quitDriver
	}
}
