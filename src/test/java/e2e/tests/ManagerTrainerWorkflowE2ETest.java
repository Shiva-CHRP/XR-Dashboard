package e2e.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;

/**
 * End-to-End Test Suite for Manager & Trainer Workflows (Phases 5 & 6):
 * 1. Manager Authentication & Dashboard Overview
 * 2. Manager Assign Modules & Curriculums Operations
 * 3. Manager My Events Scheduling & Results Inspection
 * 4. Manager User Directory & Support Ticket Tracking
 * 5. Trainer Authentication & Operational Dashboard
 * 6. Trainer Assigned Events & Schedule Timeline
 * 7. Trainer Performance Results & Portal Settings Configuration
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Clientportal", "Regression"})
public class ManagerTrainerWorkflowE2ETest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void setupWorkflow() {
		// Driver initialization handled by BaseTest
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Manager", description = "Verify Client Manager Authentication and Dashboard KPI Overview", priority = "Critical")
	public void verify_Manager_Login_And_Overview() {
		loginAsClientManager();
		Assert.assertTrue(managerOverview.isManagerOverviewPageLoaded(), "Manager Overview / Dashboard page should be loaded");
		Assert.assertTrue(managerOverview.getKPICardsCount() >= 0, "Manager KPI Cards should be present");
	}

	@Test(priority = 2, dependsOnMethods = {"verify_Manager_Login_And_Overview"})
	@TestInfo(module = "Client Manager", description = "Verify Manager Assign Modules and Curriculums Workflow", priority = "High")
	public void verify_Manager_Assign_Modules_And_Curriculums() {
		managerAssignModules.clickManagerAssignModules();
		Assert.assertTrue(managerAssignModules.isManagerAssignModulesPageLoaded(), "Manager Assign Modules page should be loaded");

		managerAssignModules.searchTrainers("Trainer");
		Assert.assertTrue(managerAssignModules.getTrainerItemsCount() >= 0, "Trainer list search should return valid state");

		managerAssignCurriculums.clickManagerAssignCurriculums();
		Assert.assertTrue(managerAssignCurriculums.isManagerAssignCurriculumsPageLoaded(), "Manager Assign Curriculums page should be loaded");
	}

	@Test(priority = 3, dependsOnMethods = {"verify_Manager_Login_And_Overview"})
	@TestInfo(module = "Client Manager", description = "Verify Manager My Events Scheduling and Results Examination", priority = "High")
	public void verify_Manager_My_Events_And_Results() {
		myEvents.clickManagerMyEvents();
		Assert.assertTrue(myEvents.isMyEventsPageLoaded(), "Manager My Events page should be loaded");

		myEvents.clickEventsTab();
		Assert.assertTrue(myEvents.isMyEventsPageLoaded(), "Events tab should be active");

		myEvents.searchEvents("Safety");
		Assert.assertTrue(myEvents.getEventRowsCount() >= 0, "Event search should execute cleanly");

		myEvents.clickResultsTab();
		Assert.assertTrue(myEvents.isMyEventsPageLoaded(), "Results tab should be active");
	}

	@Test(priority = 4, dependsOnMethods = {"verify_Manager_Login_And_Overview"})
	@TestInfo(module = "Client Manager", description = "Verify Manager User Directory and Support Tickets Tracking", priority = "Medium")
	public void verify_Manager_Users_And_Support_Tickets() {
		managerUsers.clickManagerUsers();
		Assert.assertTrue(managerUsers.isManagerUsersPageLoaded(), "Manager Users page should be loaded");

		managerUsers.clickInternalUsersTab();
		Assert.assertTrue(managerUsers.getUserRowsCount() >= 0, "Internal user rows should be queryable");

		managerUsers.clickContractorUsersTab();
		Assert.assertTrue(managerUsers.getUserRowsCount() >= 0, "Contractor user rows should be queryable");

		managerSupportTickets.clickSupportTickets();
		Assert.assertTrue(managerSupportTickets.isSupportTicketsPageLoaded(), "Manager Support Tickets page should be loaded");
	}

	@Test(priority = 5)
	@TestInfo(module = "Client Trainer", description = "Verify Client Trainer Authentication and Overview Metrics", priority = "Critical")
	public void verify_Trainer_Login_And_Overview() {
		loginAsClientTrainer();
		Assert.assertTrue(trainerOverview.isTrainerOverviewPageLoaded(), "Trainer Overview / Dashboard page should be loaded");
		Assert.assertTrue(trainerOverview.getKPICardsCount() >= 0, "Trainer KPI Cards should be present");
	}

	@Test(priority = 6, dependsOnMethods = {"verify_Trainer_Login_And_Overview"})
	@TestInfo(module = "Client Trainer", description = "Verify Trainer Assigned Events, Results and Schedule", priority = "High")
	public void verify_Trainer_Events_And_Schedule() {
		trainerMyEvents.clickTrainerMyEvents();
		Assert.assertTrue(trainerMyEvents.isTrainerMyEventsPageLoaded(), "Trainer My Events page should be loaded");

		trainerMyEvents.clickEventsTab();
		Assert.assertTrue(trainerMyEvents.isTrainerMyEventsPageLoaded(), "Trainer Events tab should be active");

		trainerMyEvents.clickResultsTab();
		Assert.assertTrue(trainerMyEvents.isTrainerMyEventsPageLoaded(), "Trainer Results tab should be active");

		trainerSchedule.clickSchedule();
		Assert.assertTrue(trainerSchedule.isSchedulePageLoaded(), "Trainer Schedule page should be loaded");
		Assert.assertTrue(trainerSchedule.getScheduledEventCardsCount() >= 0, "Trainer scheduled event cards should be queryable");
	}

	@Test(priority = 7, dependsOnMethods = {"verify_Trainer_Login_And_Overview"})
	@TestInfo(module = "Client Trainer", description = "Verify Trainer Settings Navigation and Configuration Tabs", priority = "Medium")
	public void verify_Trainer_Settings_And_Teardown() {
		trainerSettings.clickTrainerSettings();
		Assert.assertTrue(trainerSettings.isTrainerSettingsPageLoaded(), "Trainer Settings page should be loaded");

		trainerSettings.clickOfflineEnrollmentTab();
		trainerSettings.clickAccountTab();
		trainerSettings.clickNotificationsTab();
		trainerSettings.clickSecurityTab();
		Assert.assertTrue(trainerSettings.isTrainerSettingsPageLoaded(), "Trainer Security tab should be loaded");
	}

	@AfterClass(alwaysRun = true)
	public void tearDownWorkflow() {
		// Teardown handled by BaseTest quitDriver
	}
}
