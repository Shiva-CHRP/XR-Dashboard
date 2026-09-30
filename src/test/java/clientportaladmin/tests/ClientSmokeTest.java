package clientportaladmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class ClientSmokeTest extends ClientBaseTest {

	String orgCode = ConfigReader.getClientOrgCode();
	String email = ConfigReader.getClientUsername();
	String password = ConfigReader.getClientPassword();

	@Test(priority = 1)
	@TestInfo(module = "Client Login", description = "Login to Client Portal with Org Code, Email and Password", priority = "Critical")
	public void login_To_Client_Application() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
	}

	@Test(priority = 2, dependsOnMethods = {"login_To_Client_Application"})
	@TestInfo(module = "Client Navigation", description = "Navigate to Client Overview Dashboard", priority = "High")
	public void navigate_To_Overview() {
		overview.clickOverview();
	}

	@Test(priority = 3, dependsOnMethods = {"login_To_Client_Application"})
	@TestInfo(module = "Client Navigation", description = "Navigate to Curriculum Section", priority = "High")
	public void navigate_To_Curriculum() {
		curriculum.clickCurriculum();
	}

	@Test(priority = 4, dependsOnMethods = {"login_To_Client_Application"})
	@TestInfo(module = "Client Navigation", description = "Navigate to Client Settings", priority = "Medium")
	public void navigate_To_Settings() {
		settings.clickSettings();
	}
}
