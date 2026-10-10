package clientportaladmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;

@Listeners(Listener.class)
@Test(groups = {"E2E", "Clientportal", "Regression"})
public class ClientReportsAndSyncTest extends ClientBaseTest {

	@BeforeClass(alwaysRun = true)
	public void reportsAndSyncPrerequisite() {
		loginAsClientAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Reports & Analytics", priority = "High")
	public void testNavigateToReports() {
		reportsAnalytics.clickReports();
	}

	@Test(priority = 2)
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Synchronization Section", priority = "High")
	public void testNavigateToSynchronization() {
		synchronization.clickSynchronization();
	}

	@Test(priority = 3)
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Recovery Center", priority = "Medium")
	public void testNavigateToRecoveryCenter() {
		recoveryCenter.clickRecoveryCenter();
	}

	@Test(priority = 4)
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Events Sessions Section", priority = "Medium")
	public void testNavigateToEventsSessions() {
		events.clickSessions();
	}

	@Test(priority = 5)
	@TestInfo(module = "Client Reports & Sync", description = "Navigate to Duplicate Conflicts Section and verify tabs", priority = "High")
	public void testNavigateToDuplicateConflicts() {
		duplicateConflicts.clickDuplicateConflicts();
		duplicateConflicts.switchToResolvedTab();
		duplicateConflicts.switchToOpenTab();
	}
}
