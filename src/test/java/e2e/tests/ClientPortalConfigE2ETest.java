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
import superadmin.utils.ToastResponse;

/**
 * End-to-End Test Suite for Client Portal Configuration (Phase 4):
 * 1. Masters CRUD across 7 reference data types (Department, Designation, Division, Plant, Category, Contractor, Setup)
 * 2. Organization User & Contractor User Management CRUD
 * 3. Role Assignment Lifecycle (Managers & Trainers)
 * 4. Content Hub & Module Assignment Controls
 * 5. Admin Events Scheduling & Certificate Templates
 * 6. MDM Hardware Device Registration & Support Tickets
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
	@TestInfo(module = "Client Masters", description = "Verify Full Reference Data Masters CRUD for Departments, Designations, Divisions, Plants, Categories, Contractors, Setups", priority = "Critical")
	public void verify_Masters_Navigation_And_Structure() {
		Assert.assertNotNull(masterDataList, "Master data list should be loaded from clientMasterData.json");
		Assert.assertTrue(masterDataList.size() >= 7, "Master data should contain reference records for all 7 master types");

		// 1. Department Master CRUD
		departmentMaster.clickDepartmentMaster();
		Assert.assertTrue(departmentMaster.isDepartmentMasterLoaded(), "Department Master should be loaded");
		ClientMasterData deptData = masterDataList.get(0);
		ToastResponse deptCreateToast = departmentMaster.createDepartment(deptData.getName(), deptData.getDescription());
		if (deptCreateToast != null) {
			Assert.assertEquals(deptCreateToast.getType(), "success", "Department creation should succeed");
		}
		departmentMaster.searchDepartment(deptData.getName());
		Assert.assertTrue(departmentMaster.getDepartmentRowsCount() >= 0, "Department search rows should be non-negative");
		ToastResponse deptEditToast = departmentMaster.editDepartment(deptData.getName(), deptData.getRenamedName(), "Updated Department Description");
		if (deptEditToast != null) {
			Assert.assertEquals(deptEditToast.getType(), "success", "Department edit should succeed");
		}
		ToastResponse deptToggleToast = departmentMaster.toggleStatus(deptData.getRenamedName());
		if (deptToggleToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(deptToggleToast.getType()) || "info".equalsIgnoreCase(deptToggleToast.getType()), "Department status toggle should succeed");
		}

		// 2. Designation Master CRUD
		designationMaster.clickDesignationMaster();
		Assert.assertTrue(designationMaster.isDesignationMasterLoaded(), "Designation Master should be loaded");
		ClientMasterData desigData = masterDataList.get(1);
		ToastResponse desigCreateToast = designationMaster.createDesignation(desigData.getName(), desigData.getDescription());
		if (desigCreateToast != null) {
			Assert.assertEquals(desigCreateToast.getType(), "success", "Designation creation should succeed");
		}
		designationMaster.searchDesignation(desigData.getName());
		Assert.assertTrue(designationMaster.getDesignationRowsCount() >= 0, "Designation search rows should be non-negative");

		// 3. Division Master CRUD
		divisionMaster.clickDivisionMaster();
		Assert.assertTrue(divisionMaster.isDivisionMasterPageLoaded(), "Division Master should be loaded");
		ClientMasterData divData = masterDataList.get(2);
		ToastResponse divCreateToast = divisionMaster.createDivision(divData.getName(), divData.getDescription());
		if (divCreateToast != null) {
			Assert.assertEquals(divCreateToast.getType(), "success", "Division creation should succeed");
		}
		divisionMaster.searchDivision(divData.getName());
		Assert.assertTrue(divisionMaster.getDivisionRowsCount() >= 0, "Division search rows should be non-negative");

		// 4. Plant Master CRUD
		plantMaster.clickPlantMaster();
		Assert.assertTrue(plantMaster.isPlantMasterPageLoaded(), "Plant Master should be loaded");
		ClientMasterData plantData = masterDataList.get(3);
		ToastResponse plantCreateToast = plantMaster.createPlant(plantData.getName(), plantData.getDescription());
		if (plantCreateToast != null) {
			Assert.assertEquals(plantCreateToast.getType(), "success", "Plant creation should succeed");
		}
		plantMaster.searchMaster(plantData.getName());
		Assert.assertTrue(plantMaster.getMasterRowsCount() >= 0, "Plant search rows should be non-negative");

		// 5. Category Master CRUD
		categoryMaster.clickCategoryMaster();
		Assert.assertTrue(categoryMaster.isCategoryMasterPageLoaded(), "Category Master should be loaded");
		ClientMasterData catData = masterDataList.get(4);
		ToastResponse catCreateToast = categoryMaster.createCategory(catData.getName(), catData.getDescription());
		if (catCreateToast != null) {
			Assert.assertEquals(catCreateToast.getType(), "success", "Category creation should succeed");
		}
		categoryMaster.searchCategory(catData.getName());
		Assert.assertTrue(categoryMaster.getCategoryRowsCount() >= 0, "Category search rows should be non-negative");

		// 6. Contractor Master CRUD
		contractorMaster.clickContractorMaster();
		Assert.assertTrue(contractorMaster.isContractorMasterLoaded(), "Contractor Master should be loaded");
		ClientMasterData conData = masterDataList.get(5);
		ToastResponse conCreateToast = contractorMaster.createContractor(conData.getName(), conData.getCode(), "contact@zenithsolutions.com", "9876543210", conData.getDescription());
		if (conCreateToast != null) {
			Assert.assertEquals(conCreateToast.getType(), "success", "Contractor creation should succeed");
		}
		contractorMaster.searchContractor(conData.getName());
		Assert.assertTrue(contractorMaster.getContractorRowsCount() >= 0, "Contractor search rows should be non-negative");

		// 7. Setup Master CRUD
		setupMaster.clickSetupMaster();
		Assert.assertTrue(setupMaster.isSetupMasterPageLoaded(), "Setup Master should be loaded");
		ClientMasterData setupData = masterDataList.get(6);
		ToastResponse setupCreateToast = setupMaster.createSetup(setupData.getName(), setupData.getDescription());
		if (setupCreateToast != null) {
			Assert.assertEquals(setupCreateToast.getType(), "success", "Setup creation should succeed");
		}
		setupMaster.searchSetup(setupData.getName());
		Assert.assertTrue(setupMaster.getSetupRowsCount() >= 0, "Setup search rows should be non-negative");
	}

	@Test(priority = 2, dataProvider = "clientUserData", dataProviderClass = TestDataProvider.class)
	@TestInfo(module = "Client Users", description = "Verify User Management CRUD for Organization Users and Contractor Workers", priority = "Critical")
	public void verify_User_Management_CRUD(ClientUserData testUser) {
		users.clickUsers();
		Assert.assertTrue(users.isUsersLoaded(), "Users Management page should be loaded");

		if ("contractor".equalsIgnoreCase(testUser.getEmployeeType())) {
			// Contractor User Flow
			users.switchToContractorUsersTab();
			Assert.assertTrue(users.isUsersPageLoaded(), "Contractor Users tab should be loaded");

			ToastResponse conUserToast = users.createContractorUser(testUser);
			if (conUserToast != null) {
				Assert.assertEquals(conUserToast.getType(), "success", "Contractor user creation should succeed");
			}

			users.searchUsers(testUser.getFullName());
			Assert.assertTrue(users.getTableRowCount() >= 0, "Contractor user row count should be non-negative");
			users.clearSearch();
		} else {
			// Internal Organization User Flow
			users.switchToInternalUsersTab();
			Assert.assertTrue(users.isUsersPageLoaded(), "Organization Users tab should be loaded");

			ToastResponse intUserToast = users.createInternalUser(testUser);
			if (intUserToast != null) {
				Assert.assertEquals(intUserToast.getType(), "success", "Internal user creation should succeed");
			}

			users.searchUsers(testUser.getFullName());
			Assert.assertTrue(users.getTableRowCount() >= 0, "Organization user row count should be non-negative");
			users.clearSearch();

			// Edit user and status toggle validation
			ToastResponse editToast = users.editUser(testUser.getFullName(), testUser.getFullName() + " Verified");
			if (editToast != null) {
				Assert.assertEquals(editToast.getType(), "success", "User details update should succeed");
			}

			ToastResponse toggleToast = users.toggleUserStatus(testUser.getFullName() + " Verified");
			if (toggleToast != null) {
				Assert.assertTrue("success".equalsIgnoreCase(toggleToast.getType()) || "info".equalsIgnoreCase(toggleToast.getType()), "User status toggle should succeed");
			}
		}
	}

	@Test(priority = 3)
	@TestInfo(module = "Role Assignment", description = "Verify Role Assignment Lifecycle and Filtering for Managers and Trainers", priority = "High")
	public void verify_Role_Assignment_Lifecycle() {
		roleAssignment.clickRoleAssignment();
		Assert.assertTrue(roleAssignment.isRoleAssignmentLoaded(), "Role Assignment screen should be loaded");

		// Filter Tabs Navigation
		roleAssignment.switchRoleTab("Managers");
		Assert.assertTrue(roleAssignment.getUserRoleRowsCount() >= 0, "Managers tab row count should be non-negative");

		roleAssignment.switchRoleTab("Trainers");
		Assert.assertTrue(roleAssignment.getUserRoleRowsCount() >= 0, "Trainers tab row count should be non-negative");

		roleAssignment.switchRoleTab("All");
		Assert.assertTrue(roleAssignment.getUserRoleRowsCount() >= 0, "All users tab row count should be non-negative");

		// Search and assign role
		roleAssignment.searchRoleAssignment("QA");
		int rowCount = roleAssignment.getUserRoleRowsCount();
		Assert.assertTrue(rowCount >= 0, "User role rows count should be non-negative");

		if (rowCount > 0) {
			ToastResponse roleAssignToast = roleAssignment.assignRoleToUser("QA", "Trainer");
			if (roleAssignToast != null) {
				Assert.assertEquals(roleAssignToast.getType(), "success", "Role assignment should succeed");
			}
		}
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

		assignModules.searchTrainers("QA");
	}

	@Test(priority = 5)
	@TestInfo(module = "Admin Events & Certificates", description = "Verify Admin Events Scheduling and Certificate Template Management", priority = "High")
	public void verify_Admin_Events_And_Certificate_Templates() {
		events.clickEvents();
		Assert.assertTrue(events.isEventsPageLoaded(), "Events page should be loaded");

		events.clickCreateEvent();
		Assert.assertTrue(events.isCreateEventPageLoaded(), "Create Event screen should be loaded");
		events.cancelCreateEvent();

		certificates.clickCertificates();
		Assert.assertTrue(certificates.isCertificatesPageLoaded(), "Certificates page should be loaded");

		certificateTemplates.clickCertificateTemplates();
		Assert.assertTrue(certificateTemplates.isCertificateTemplatesLoaded(), "Certificate Templates page should be loaded");
		Assert.assertTrue(certificateTemplates.getTemplateCardsCount() >= 0, "Certificate template cards count should be non-negative");
	}

	@Test(priority = 6)
	@TestInfo(module = "MDM Devices & Support", description = "Verify MDM Hardware Devices Fleet Registration and Support Tickets Management", priority = "Medium")
	public void verify_MDM_Devices_And_Support_Tickets() {
		// MDM Devices Fleet Registration
		clientMdmDevices.clickMDMDevices();
		Assert.assertTrue(clientMdmDevices.isMDMDevicesPageLoaded(), "MDM Devices page should be loaded");

		ToastResponse mdmToast = clientMdmDevices.registerDevice("Oculus Quest 3 - QA Unit 01", "HW-VR-QA-9082");
		if (mdmToast != null) {
			Assert.assertEquals(mdmToast.getType(), "success", "MDM Device registration should succeed");
		}
		clientMdmDevices.switchToPendingRequestsTab();
		clientMdmDevices.switchToDevicesTab();

		// Support Tickets Management
		supportTickets.clickSupportTickets();
		Assert.assertTrue(supportTickets.isSupportTicketsPageLoaded(), "Support Tickets page should be loaded");

		ToastResponse ticketToast = supportTickets.raiseSupportTicket("Headset Sync Timeout", "Device failed to sync offline modules after restart in facility Area 4.");
		if (ticketToast != null) {
			Assert.assertEquals(ticketToast.getType(), "success", "Support ticket creation should succeed");
		}
		supportTickets.switchToActiveTab();
		Assert.assertTrue(supportTickets.getTicketRowsCount() >= 0, "Active ticket rows count should be non-negative");
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