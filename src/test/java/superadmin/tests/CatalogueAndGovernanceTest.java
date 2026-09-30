package superadmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class CatalogueAndGovernanceTest extends BaseTest {

	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();

	@Test(priority = 1)
	@TestInfo(module = "Governance", description = "Login as Superadmin for Catalogue and Governance tests", priority = "Critical")
	public void login_For_Catalogue_And_Governance() throws InterruptedException {
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

	@Test(priority = 2, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Catalogue", description = "Navigate to Module Catalogue", priority = "High")
	public void navigate_To_Module_Catalogue() {
		moduleCatalogue.clickModuleCatalogue();
	}

	@Test(priority = 3, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Catalogue", description = "Navigate to Curriculum Catalogue", priority = "High")
	public void navigate_To_Curriculum_Catalogue() {
		curriculumCatalogue.clickCurriculumCatalogue();
	}

	@Test(priority = 4, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Governance", description = "Navigate to License Management", priority = "High")
	public void navigate_To_License() {
		license.clickLicense();
	}

	@Test(priority = 5, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Governance", description = "Navigate to MDM Devices Management", priority = "High")
	public void navigate_To_MDM_Devices() {
		mdmDevices.clickMDMDevices();
	}

	@Test(priority = 6, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Governance", description = "Navigate to System Health", priority = "Medium")
	public void navigate_To_System_Health() {
		systemHealth.clickSystemHealth();
	}

	@Test(priority = 7, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Governance", description = "Navigate to Audit Log", priority = "Medium")
	public void navigate_To_Audit_Log() {
		auditLog.clickAuditLog();
	}

	@Test(priority = 8, dependsOnMethods = {"login_For_Catalogue_And_Governance"})
	@TestInfo(module = "Governance", description = "Logout after Catalogue and Governance tests", priority = "Low")
	public void logout_After_Tests() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
	}
}
