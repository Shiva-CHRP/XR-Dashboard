package superadmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.*;

import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
@Test(groups = {"Smoke", "Superadmin", "Regression"})
public class SmokeTest extends BaseTest {
	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();

	@Test(priority = 1)
	@TestInfo(module = "Login", description = "Login using valid Email and Password", priority = "Critical")
	public void login_With_Valid_Credintials() throws InterruptedException {
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

	@Test(priority = 2)
	@TestInfo(module = "Login", description = "Logout from the Application", priority = "Critical")
	public void logout_from_Application() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
		// Thread.sleep(5000);
	}

}
