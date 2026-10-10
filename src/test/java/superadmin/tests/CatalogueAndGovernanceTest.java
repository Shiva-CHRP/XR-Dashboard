package superadmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;

@Listeners(Listener.class)
@Test(groups = {"e2e", "superadmin", "regression"})
public class CatalogueAndGovernanceTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void cataloguePrerequisite() {
		loginAsSuperAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Catalogue", description = "Navigate to Module Catalogue", priority = "High")
	public void navigate_To_Module_Catalogue() {
		moduleCatalogue.clickModuleCatalogue();
	}

	@Test(priority = 2)
	@TestInfo(module = "Catalogue", description = "Navigate to Curriculum Catalogue", priority = "High")
	public void navigate_To_Curriculum_Catalogue() {
		curriculumCatalogue.clickCurriculumCatalogue();
	}

	@Test(priority = 3)
	@TestInfo(module = "Governance", description = "Navigate to License Management", priority = "High")
	public void navigate_To_License() {
		license.clickLicense();
	}

	@Test(priority = 4)
	@TestInfo(module = "Governance", description = "Navigate to MDM Devices Management", priority = "High")
	public void navigate_To_MDM_Devices() {
		mdmDevices.clickMDMDevices();
	}

	@Test(priority = 5)
	@TestInfo(module = "Governance", description = "Navigate to System Health", priority = "Medium")
	public void navigate_To_System_Health() {
		systemHealth.clickSystemHealth();
	}

	@Test(priority = 6)
	@TestInfo(module = "Governance", description = "Navigate to Audit Log", priority = "Medium")
	public void navigate_To_Audit_Log() {
		auditLog.clickAuditLog();
	}

	@Test(priority = 7)
	@TestInfo(module = "Governance", description = "Navigate to Offline Portal Releases and verify stream tabs", priority = "High")
	public void navigate_To_Offline_Releases() {
		offlinePortalRelease.clickOfflinePortalRelease();
		offlinePortalRelease.switchToApkTab();
		offlinePortalRelease.switchToExeTab();
	}

	@Test(priority = 8)
	@TestInfo(module = "Governance", description = "Logout after Catalogue and Governance tests", priority = "Low")
	public void logout_After_Tests() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
	}
}
