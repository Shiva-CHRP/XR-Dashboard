package clientportaltrainer.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class TrainerSmokeTest extends ClientBaseTest {

	String orgCode = ConfigReader.getClientOrgCode();
	String email = ConfigReader.getClientTrainerUsername();
	String password = ConfigReader.getClientTrainerPassword();

	@Test(priority = 1)
	@TestInfo(module = "Client Trainer", description = "Login to Client Portal as Trainer", priority = "Critical")
	public void login_As_Trainer() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
		trainerOverview.isTrainerOverviewPageLoaded();
	}

	@Test(priority = 2, dependsOnMethods = {"login_As_Trainer"})
	@TestInfo(module = "Client Trainer", description = "Navigate to Trainer Overview", priority = "High")
	public void navigate_To_Trainer_Overview() {
		trainerOverview.clickTrainerOverview();
	}

	@Test(priority = 3, dependsOnMethods = {"login_As_Trainer"})
	@TestInfo(module = "Client Trainer", description = "Navigate to Trainer My Events", priority = "High")
	public void navigate_To_Trainer_My_Events() {
		trainerMyEvents.clickTrainerMyEvents();
	}

	@Test(priority = 4, dependsOnMethods = {"login_As_Trainer"})
	@TestInfo(module = "Client Trainer", description = "Navigate to Trainer Settings", priority = "Medium")
	public void navigate_To_Trainer_Settings() {
		trainerSettings.clickTrainerSettings();
	}
}
