package superadmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class RoleSwitchTest extends BaseTest {

	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();

	@Test(priority = 1)
	@TestInfo(module = "Role Switch", description = "Login as Superadmin", priority = "Critical")
	public void login_As_SuperAdmin() throws InterruptedException {
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

	@Test(priority = 2, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Role Switch", description = "Switch to VR Developer Role", priority = "High")
	public void switch_To_VR_Developer_Role() {
		loginPage.switchToVRDeveloper();
	}

	@Test(priority = 3, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Role Switch", description = "Access Developer Dashboard", priority = "Medium")
	public void access_Developer_Dashboard() {
		developerDashboard.clickDeveloperDashboard();
	}

	@Test(priority = 4, dependsOnMethods = {"switch_To_VR_Developer_Role"})
	@TestInfo(module = "Role Switch", description = "Switch back to Super Admin Role", priority = "High")
	public void switch_Back_To_SuperAdmin_Role() {
		loginPage.switchToSuperAdmin();
		loginPage.dashboardName();
	}

	@Test(priority = 5, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Role Switch", description = "Logout from Application", priority = "Low")
	public void logout_From_Application() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
	}
}
