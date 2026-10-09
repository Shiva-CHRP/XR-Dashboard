package clientportaladmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class ClientReportsAndSyncTest extends ClientBaseTest {

	String orgCode = ConfigReader.getClientOrgCode();
	String email = ConfigReader.getClientUsername();
	String password = ConfigReader.getClientPassword();

	@Test(priority = 1)
	@TestInfo(module = "Client Reports & Sync", description = "Login to Client Portal for Reports & Sync tests", priority = "Critical")
	public void login_For_Reports_And_Sync() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
	}

	@Test(priority = 2, dependsOnMethods = {"login_For_Reports_And_Sync"})
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Reports & Analytics", priority = "High")
	public void testNavigateToReports() {
		reportsAnalytics.clickReports();
	}

	@Test(priority = 3, dependsOnMethods = {"login_For_Reports_And_Sync"})
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Synchronization Section", priority = "High")
	public void testNavigateToSynchronization() {
		synchronization.clickSynchronization();
	}

	@Test(priority = 4, dependsOnMethods = {"login_For_Reports_And_Sync"})
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Recovery Center", priority = "Medium")
	public void testNavigateToRecoveryCenter() {
		recoveryCenter.clickRecoveryCenter();
	}

	@Test(priority = 5, dependsOnMethods = {"login_For_Reports_And_Sync"})
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Events Sessions Section", priority = "Medium")
	public void testNavigateToEventsSessions() {
		events.clickSessions();
	}

	@Test(priority = 6, dependsOnMethods = {"login_For_Reports_And_Sync"})
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Duplicate Conflicts Section and verify tabs", priority = "High")
	public void testNavigateToDuplicateConflicts() {
		duplicateConflicts.clickDuplicateConflicts();
		duplicateConflicts.switchToResolvedTab();
		duplicateConflicts.switchToOpenTab();
	}
}
