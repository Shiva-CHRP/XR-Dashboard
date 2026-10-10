package superadmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;

@Listeners(Listener.class)
@Test(groups = {"e2e", "superadmin", "regression"})
public class DeveloperPortalTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void developerPortalPrerequisite() {
		loginAsSuperAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Developer Portal", description = "Switch to VR Developer Role", priority = "High")
	public void switch_To_VR_Developer_Role() {
		loginPage.switchToVRDeveloper();
	}

	@Test(priority = 2, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Developer Portal", description = "Navigate to Developer Dashboard", priority = "High")
	public void navigate_To_Developer_Dashboard() {
		developerDashboard.clickDeveloperDashboard();
	}

	@Test(priority = 3, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Developer Portal", description = "Navigate to Developer Organisations", priority = "High")
	public void navigate_To_Developer_Organisations() {
		developerOrganisations.clickDeveloperOrganisations();
	}

	@Test(priority = 4, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Developer Portal", description = "Navigate to Developer Profile", priority = "Medium")
	public void navigate_To_Developer_Profile() {
		developerProfile.clickDeveloperProfile();
	}

	@Test(priority = 5, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Developer Portal", description = "Switch back to Super Admin Role", priority = "High")
	public void switch_Back_To_SuperAdmin_Role() {
		loginPage.switchToSuperAdmin();
		loginPage.dashboardName();
	}

	@Test(priority = 6, dependsOnMethods = {"switch_Back_To_SuperAdmin_Role"})
	@TestInfo(module = "Developer Portal", description = "Navigate to Superadmin Developers List", priority = "Medium")
	public void navigate_To_Superadmin_Developers_List() {
		developers.clickDevelopers();
	}

	@Test(priority = 7)
	@TestInfo(module = "Developer Portal", description = "Logout from Application after Developer tests", priority = "Low")
	public void logout_After_Developer_Tests() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
	}
}
