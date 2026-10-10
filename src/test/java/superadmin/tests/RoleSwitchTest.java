package superadmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;

@Listeners(Listener.class)
@Test(groups = {"smoke", "superadmin", "regression"})
public class RoleSwitchTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void roleSwitchPrerequisite() {
		loginAsSuperAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Role Switch", description = "Switch to VR Developer Role", priority = "High")
	public void switch_To_VR_Developer_Role() {
		loginPage.switchToVRDeveloper();
	}

	@Test(priority = 2, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Role Switch", description = "Access Developer Dashboard", priority = "Medium")
	public void access_Developer_Dashboard() {
		developerDashboard.clickDeveloperDashboard();
	}

	@Test(priority = 3, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Role Switch", description = "Switch back to Super Admin Role", priority = "High")
	public void switch_Back_To_SuperAdmin_Role() {
		loginPage.switchToSuperAdmin();
		loginPage.dashboardName();
	}

	@Test(priority = 4)
	@TestInfo(module = "Role Switch", description = "Logout from Application", priority = "Low")
	public void logout_From_Application() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
	}
}
