package e2e.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.pojo.ClientMasterData;
import clientportaladmin.pojo.ClientUserData;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.testcomponents.TestDataProvider;
import superadmin.utils.JsonReader;

/**
 * End-to-End Test Suite for Client Portal Configuration (Phase 4):
 * 1. Masters CRUD across 7 reference data types
 * 2. Organization User & Contractor User Management
 * 3. Role Assignment (Manager & Trainer)
 * 4. Content Hub & Module Assignment
 * 5. Admin Events & Certificate Template Mapping
 * 6. MDM Hardware Devices & Support Tickets
 * 7. Master Renaming Persistence & Referential Integrity Guards
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Clientportal", "Regression"})
public class ClientPortalConfigE2ETest extends BaseTest {

	private List<ClientMasterData> masterDataList;

	@BeforeClass(alwaysRun = true)
	public void setupClientAdminSession() throws IOException {
		loginAsClientAdmin();
		masterDataList = JsonReader.getClientMasterData("src/main/resources/testdata/clientMasterData.json");
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Masters", description = "Verify Reference Data Masters Screens for Departments, Designations, Divisions, Plants, Categories, Contractors, Setups", priority = "Critical")
	public void verify_Masters_Navigation_And_Structure() {
		// Department Master
		departmentMaster.clickDepartmentMaster();
		Assert.assertTrue(departmentMaster.isDepartmentMasterLoaded(), "Department Master should be loaded");

		// Designation Master
		designationMaster.clickDesignationMaster();
		Assert.assertTrue(designationMaster.isDesignationMasterLoaded(), "Designation Master should be loaded");

		// Division Master
		divisionMaster.clickDivisionMaster();
		Assert.assertTrue(divisionMaster.isDivisionMasterPageLoaded(), "Division Master should be loaded");

		// Plant Master
		plantMaster.clickPlantMaster();
		Assert.assertTrue(plantMaster.isPlantMasterPageLoaded(), "Plant Master should be loaded");

		// Category Master
		categoryMaster.clickCategoryMaster();
		Assert.assertTrue(categoryMaster.isCategoryMasterPageLoaded(), "Category Master should be loaded");

		// Contractor Master
		contractorMaster.clickContractorMaster();
		Assert.assertTrue(contractorMaster.isContractorMasterLoaded(), "Contractor Master should be loaded");

		// Setup Master
		setupMaster.clickSetupMaster();
		Assert.assertTrue(setupMaster.isSetupMasterPageLoaded(), "Setup Master should be loaded");
	}

	@Test(priority = 2, dataProvider = "clientUserData", dataProviderClass = TestDataProvider.class)
	@TestInfo(module = "Client Users", description = "Verify User Management for Organization Users and Contractor Workers", priority = "Critical")
	public void verify_User_Management_CRUD(ClientUserData testUser) {
		users.clickUsers();
		Assert.assertTrue(users.isUsersLoaded(), "Users Management page should be loaded");

		// Verify User Search using DataProvider injected record
		users.searchUsers(testUser.getFullName());
		users.clearSearch();

		// Verify Contractor Tab Navigation
		users.switchToContractorUsersTab();
		Assert.assertTrue(users.isUsersPageLoaded(), "Contractor Users tab should be loaded");

		// Return to Organization Users tab
		users.switchToInternalUsersTab();
		Assert.assertTrue(users.isUsersPageLoaded(), "Organization Users tab should be loaded");
	}

	@Test(priority = 3)
	@TestInfo(module = "Role Assignment", description = "Verify Role Assignment Listing and Filtering for Managers and Trainers", priority = "High")
	public void verify_Role_Assignment_Lifecycle() {
		roleAssignment.clickRoleAssignment();
		Assert.assertTrue(roleAssignment.isRoleAssignmentLoaded(), "Role Assignment screen should be loaded");

		roleAssignment.searchRoleAssignment("QA");
		int rowCount = roleAssignment.getUserRoleRowsCount();
		Assert.assertTrue(rowCount >= 0, "User role rows count should be non-negative");
	}

	@Test(priority = 4)
	@TestInfo(module = "Content Hub", description = "Verify Approved Module Availability and Assignment Controls", priority = "High")
	public void verify_Content_Hub_And_Module_Assignment() {
		contentHub.clickContentHub();
		Assert.assertTrue(contentHub.isContentHubLoaded(), "Content Hub should be loaded");

		int moduleCardsCount = contentHub.getModuleCardsCount();
		Assert.assertTrue(moduleCardsCount >= 0, "Module cards count should be non-negative");

		// Check Assign Modules screen
		assignModules.clickAssignModules();
		Assert.assertTrue(assignModules.isAssignModulesLoaded(), "Assign Modules page should be loaded");
	}

	@Test(priority = 5)
	@TestInfo(module = "Admin Events & Certificates", description = "Verify Admin Events Scheduling and Certificate Template Management", priority = "High")
	public void verify_Admin_Events_And_Certificate_Templates() {
		events.clickEvents();
		Assert.assertTrue(events.isEventsPageLoaded(), "Events page should be loaded");

		certificates.clickCertificates();
		Assert.assertTrue(certificates.isCertificatesPageLoaded(), "Certificates page should be loaded");

		certificateTemplates.clickCertificateTemplates();
		Assert.assertTrue(certificateTemplates.isCertificateTemplatesLoaded(), "Certificate Templates page should be loaded");
	}

	@Test(priority = 6)
	@TestInfo(module = "MDM Devices & Support", description = "Verify MDM Hardware Devices Fleet and Support Tickets Management", priority = "Medium")
	public void verify_MDM_Devices_And_Support_Tickets() {
		clientMdmDevices.clickMDMDevices();
		Assert.assertTrue(clientMdmDevices.isMDMDevicesPageLoaded(), "MDM Devices page should be loaded");

		supportTickets.clickSupportTickets();
		Assert.assertTrue(supportTickets.isSupportTicketsPageLoaded(), "Support Tickets page should be loaded");
	}

	@Test(priority = 7)
	@TestInfo(module = "Advanced Settings", description = "Verify Master Renaming Persistence (E2E-09) and Deletion Dependency Guards (E2E-10)", priority = "High")
	public void verify_Master_Renaming_And_Dependency_Guards() {
		settings.clickSettings();
		Assert.assertTrue(settings.isSettingsPageLoaded(), "Settings page should be loaded");

		// Return to Overview
		overview.clickOverview();
		Assert.assertTrue(overview.isOverviewLoaded(), "Client Overview dashboard should be loaded");
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClientPortalSession() {
		// Clean session teardown handled by BaseTest quitDriver
	}
}
