package clientportalmanager.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
@Test(groups = {"Smoke", "Clientportal", "Regression"})
public class ManagerSmokeTest extends ClientBaseTest {

	String orgCode = ConfigReader.getClientOrgCode();
	String email = ConfigReader.getClientManagerUsername();
	String password = ConfigReader.getClientManagerPassword();

	@Test(priority = 1)
	@TestInfo(module = "Client Manager", description = "Login to Client Portal as Manager", priority = "Critical")
	public void login_As_Manager() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
		managerOverview.isManagerOverviewPageLoaded();
	}

	@Test(priority = 2, dependsOnMethods = {"login_As_Manager"})
	@TestInfo(module = "Client Manager", description = "Navigate to Manager Overview", priority = "High")
	public void navigate_To_Manager_Overview() {
		managerOverview.clickManagerOverview();
	}

	@Test(priority = 3, dependsOnMethods = {"login_As_Manager"})
	@TestInfo(module = "Client Manager", description = "Navigate to Assign Curriculums", priority = "High")
	public void navigate_To_Assign_Curriculums() {
		managerAssignCurriculums.clickManagerAssignCurriculums();
	}

	@Test(priority = 4, dependsOnMethods = {"login_As_Manager"})
	@TestInfo(module = "Client Manager", description = "Navigate to Assign Modules", priority = "High")
	public void navigate_To_Assign_Modules() {
		managerAssignModules.clickManagerAssignModules();
	}

	@Test(priority = 5, dependsOnMethods = {"login_As_Manager"})
	@TestInfo(module = "Client Manager", description = "Navigate to Certificates", priority = "Medium")
	public void navigate_To_Certificates() {
		managerCertificates.clickManagerCertificates();
	}

	@Test(priority = 6, dependsOnMethods = {"login_As_Manager"})
	@TestInfo(module = "Client Manager", description = "Navigate to My Events", priority = "Medium")
	public void navigate_To_My_Events() {
		myEvents.clickManagerMyEvents();
	}

	@Test(priority = 7, dependsOnMethods = {"login_As_Manager"})
	@TestInfo(module = "Client Manager", description = "Navigate to Manager Settings", priority = "Low")
	public void navigate_To_Manager_Settings() {
		managerSettings.clickManagerSettings();
	}
}
