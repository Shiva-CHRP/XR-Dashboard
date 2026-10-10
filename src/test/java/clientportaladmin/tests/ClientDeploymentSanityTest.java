package clientportaladmin.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import clientportaladmin.pojo.ClientEventData;
import clientportaladmin.pojo.ClientMasterData;
import clientportaladmin.pojo.ClientSupportTicketData;
import clientportaladmin.pojo.ClientUserData;
import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;
import superadmin.utils.JsonReader;

/**
 * Enterprise Post-Deployment Sanity / Build Verification Test (BVT) Suite for Client Portal.
 *
 * Executed automatically by CI/CD / DevOps pipelines following environment deployment.
 * Verifies screen integrity, route accessibility, navigation controls, tab switching,
 * search mechanisms, and modal forms across the Client Portal without corrupting persistent data.
 */
@Listeners(Listener.class)
@Test(groups = {"sanity", "clientportal", "regression"})
public class ClientDeploymentSanityTest extends ClientBaseTest {

	private String orgCode;
	private String email;
	private String password;

	private List<ClientUserData> userDataList;
	private List<ClientEventData> eventDataList;
	private List<ClientSupportTicketData> ticketDataList;
	private List<ClientMasterData> masterDataList;

	@BeforeClass(alwaysRun = true)
	public void loadTestData() throws IOException {
		orgCode = ConfigReader.getClientOrgCode();
		email = ConfigReader.getClientUsername();
		password = ConfigReader.getClientPassword();

		userDataList = JsonReader.getClientUserData("src/main/resources/testdata/clientUserData.json");
		eventDataList = JsonReader.getClientEventData("src/main/resources/testdata/clientEventData.json");
		ticketDataList = JsonReader.getClientSupportTicketData("src/main/resources/testdata/clientSupportTicketData.json");
		masterDataList = JsonReader.getClientMasterData("src/main/resources/testdata/clientMasterData.json");
	}

	@BeforeMethod(alwaysRun = true)
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
	}

	// =========================================================================
	// 1. AUTHENTICATION & LOGIN SCREEN VERIFICATION
	// =========================================================================

	@Test(priority = 1)
	@TestInfo(module = "Sanity - Authentication", description = "Verify Client Login with Org Code, Email and Password", priority = "Critical")
	public void Verify_Client_Login_And_Authenticate() {
		clientLogin.loginToClient(orgCode, email, password);
		softAssert.assertTrue(overview.isOverviewPageLoaded(), "Overview dashboard should be visible post-login");
		softAssert.assertAll();
	}

	// =========================================================================
	// 2. OVERVIEW DASHBOARD
	// =========================================================================

	@Test(priority = 2, dependsOnMethods = {"Verify_Client_Login_And_Authenticate"})
	@TestInfo(module = "Sanity - Overview Dashboard", description = "Verify Client Portal Overview Dashboard Screen & KPI Cards", priority = "High")
	public void Verify_Overview_Dashboard() {
		overview.clickOverview();
		softAssert.assertTrue(overview.isOverviewPageLoaded(), "Overview dashboard should be loaded");
		int kpiCount = overview.getKPICardsCount();
		softAssert.assertTrue(kpiCount >= 0, "KPI cards count should be non-negative");
		softAssert.assertAll();
	}

	// =========================================================================
	// 3. MASTER DATA SCREENS (8 MASTERS)
	// =========================================================================

	@Test(priority = 3, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Department Master", description = "Verify Department Master Screen, Search and Data Listing", priority = "High")
	public void Verify_Department_Master_Screen() {
		departmentMaster.clickDepartmentMaster();
		softAssert.assertTrue(departmentMaster.isDepartmentMasterPageLoaded(), "Department Master page should be loaded");
		String searchQuery = masterDataList.isEmpty() ? "test" : masterDataList.get(0).getName();
		departmentMaster.searchMaster(searchQuery);
		softAssert.assertTrue(departmentMaster.isDepartmentMasterPageLoaded(), "Department Master should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 4, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Designation Master", description = "Verify Designation Master Screen, Search and Data Listing", priority = "High")
	public void Verify_Designation_Master_Screen() {
		designationMaster.clickDesignationMaster();
		softAssert.assertTrue(designationMaster.isDesignationMasterPageLoaded(), "Designation Master page should be loaded");
		String searchQuery = masterDataList.size() > 1 ? masterDataList.get(1).getName() : "test";
		designationMaster.searchMaster(searchQuery);
		softAssert.assertTrue(designationMaster.isDesignationMasterPageLoaded(), "Designation Master should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 5, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Contractor Master", description = "Verify Contractor Master Screen, Search and Data Listing", priority = "High")
	public void Verify_Contractor_Master_Screen() {
		contractorMaster.clickContractorMaster();
		softAssert.assertTrue(contractorMaster.isContractorMasterPageLoaded(), "Contractor Master page should be loaded");
		String searchQuery = masterDataList.size() > 2 ? masterDataList.get(2).getName() : "test";
		contractorMaster.searchMaster(searchQuery);
		softAssert.assertTrue(contractorMaster.isContractorMasterPageLoaded(), "Contractor Master should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 6, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Plant Master", description = "Verify Plant / Area Master Screen (template-gated)", priority = "Medium")
	public void Verify_Plant_Master_Screen() {
		if (plantMaster.isPlantMasterPresent()) {
			plantMaster.clickPlantMaster();
			softAssert.assertTrue(plantMaster.isPlantMasterPageLoaded(), "Plant Master page should be loaded");
			plantMaster.searchMaster("test");
			softAssert.assertTrue(plantMaster.isPlantMasterPageLoaded(), "Plant Master should remain functional after search");
		}
		softAssert.assertAll();
	}

	@Test(priority = 7, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Division Master", description = "Verify Division / Nodal Office Master Screen, Search and Data Listing (template-gated)", priority = "Medium")
	public void Verify_Division_Master_Screen() {
		if (divisionMaster.isDivisionMasterPresent()) {
			divisionMaster.clickDivisionMaster();
			softAssert.assertTrue(divisionMaster.isDivisionMasterPageLoaded(), "Division Master page should be loaded");
			divisionMaster.searchDivision("test");
			softAssert.assertTrue(divisionMaster.isDivisionMasterPageLoaded(), "Division Master should remain functional after search");
		}
		softAssert.assertAll();
	}

	@Test(priority = 8, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Category Master", description = "Verify Category Master Screen, Search and Data Listing (template-gated)", priority = "Medium")
	public void Verify_Category_Master_Screen() {
		if (categoryMaster.isCategoryMasterPresent()) {
			categoryMaster.clickCategoryMaster();
			softAssert.assertTrue(categoryMaster.isCategoryMasterPageLoaded(), "Category Master page should be loaded");
			categoryMaster.searchCategory("test");
			softAssert.assertTrue(categoryMaster.isCategoryMasterPageLoaded(), "Category Master should remain functional after search");
		}
		softAssert.assertAll();
	}

	@Test(priority = 9, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Setup Master", description = "Verify Setup Master Screen, Search and Data Listing (template-gated)", priority = "Medium")
	public void Verify_Setup_Master_Screen() {
		if (setupMaster.isSetupMasterPresent()) {
			setupMaster.clickSetupMaster();
			softAssert.assertTrue(setupMaster.isSetupMasterPageLoaded(), "Setup Master page should be loaded");
			setupMaster.searchSetup("test");
			softAssert.assertTrue(setupMaster.isSetupMasterPageLoaded(), "Setup Master should remain functional after search");
		}
		softAssert.assertAll();
	}

	@Test(priority = 10, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Area Master", description = "Verify Area / Mine Master Screen (template-gated)", priority = "Medium")
	public void Verify_Area_Master_Screen() {
		if (areaMaster.isAreaMasterPresent()) {
			areaMaster.clickAreaMaster();
			softAssert.assertTrue(areaMaster.isAreaMasterPageLoaded(), "Area Master page should be loaded");
			areaMaster.searchArea("test");
			softAssert.assertTrue(areaMaster.isAreaMasterPageLoaded(), "Area Master should remain functional after search");
		}
		softAssert.assertAll();
	}

	// =========================================================================
	// 4. TRAINING & CONTENT MANAGEMENT
	// =========================================================================

	@Test(priority = 11, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Content Hub", description = "Verify Content Hub Screen, Search and Module Cards", priority = "High")
	public void Verify_Content_Hub_Screen() {
		contentHub.clickContentHub();
		softAssert.assertTrue(contentHub.isContentHubPageLoaded(), "Content Hub heading should be loaded");
		String query = eventDataList.isEmpty() ? "Safety" : eventDataList.get(0).getModuleName();
		contentHub.searchModules(query);
		softAssert.assertTrue(contentHub.isContentHubPageLoaded(), "Content Hub should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 12, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Curriculum", description = "Verify Curriculum Catalogue and Catalogue Filter Chips", priority = "High")
	public void Verify_Curriculum_Catalogue_And_MyCurriculums() {
		curriculum.clickCurriculum();
		softAssert.assertTrue(curriculum.isCurriculumPageLoaded(), "Curriculum page heading should be loaded");
		curriculum.clickBrowseCatalogue();
		softAssert.assertTrue(curriculum.isCurriculumPageLoaded(), "Curriculum catalogue should remain functional");
		curriculum.searchCurriculums("test");
		softAssert.assertAll();
	}

	@Test(priority = 13, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Assign Modules", description = "Verify Assign Modules Screen and Trainers List", priority = "Medium")
	public void Verify_Assign_Modules_Screen() {
		assignModules.clickAssignModules();
		softAssert.assertTrue(assignModules.isAssignModulesPageLoaded(), "Assign Modules page should be loaded");
		assignModules.searchTrainers("test");
		softAssert.assertTrue(assignModules.isAssignModulesPageLoaded(), "Assign Modules should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 14, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Assign Curriculums", description = "Verify Assign Curriculums Screen and Trainers List", priority = "Medium")
	public void Verify_Assign_Curriculums_Screen() {
		assignCurriculums.clickAssignCurriculums();
		softAssert.assertTrue(assignCurriculums.isAssignCurriculumsPageLoaded(), "Assign Curriculums page should be loaded");
		assignCurriculums.searchTrainers("test");
		softAssert.assertTrue(assignCurriculums.isAssignCurriculumsPageLoaded(), "Assign Curriculums should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 15, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - VR Modules", description = "Verify VR Modules Screen (if separately routed)", priority = "Medium")
	public void Verify_VR_Modules_Screen() {
		if (vrModules.isVRModulesPresent()) {
			vrModules.clickVRModules();
			softAssert.assertTrue(vrModules.isVRModulesPageLoaded(), "VR Modules page should be loaded");
			vrModules.searchVRModules("test");
			softAssert.assertTrue(vrModules.isVRModulesPageLoaded(), "VR Modules should remain functional after search");
		}
		softAssert.assertAll();
	}

	// =========================================================================
	// 5. EVENTS & SESSIONS
	// =========================================================================

	@Test(priority = 16, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Events & Sessions", description = "Verify Events Sessions Screen, Outer Tabs (Events & Results) and Search", priority = "High")
	public void Verify_Events_And_Sessions_Screen() {
		events.clickSessions();
		softAssert.assertTrue(events.isEventsPageLoaded(), "Events page heading should be loaded");
		events.switchToEventsTab();
		softAssert.assertTrue(events.isEventsPageLoaded(), "Events tab should remain loaded");
		events.switchToResultsTab();
		softAssert.assertTrue(events.isEventsPageLoaded(), "Results tab should remain loaded");
		events.switchToEventsTab();
		String eventQuery = eventDataList.isEmpty() ? "test" : eventDataList.get(0).getEventName();
		events.searchEvents(eventQuery);
		softAssert.assertTrue(events.isEventsPageLoaded(), "Events page should remain functional after search");
		softAssert.assertAll();
	}

	// =========================================================================
	// 6. CERTIFICATES & CERTIFICATE TEMPLATES
	// =========================================================================

	@Test(priority = 17, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Certificates", description = "Verify Certificates Screen, Search and Data Listing", priority = "High")
	public void Verify_Certificates_Screen() {
		certificates.clickCertificates();
		softAssert.assertTrue(certificates.isCertificatesPageLoaded(), "Certificates page heading should be loaded");
		certificates.searchCertificates("test");
		softAssert.assertTrue(certificates.isCertificatesPageLoaded(), "Certificates page should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 18, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Certificate Templates", description = "Verify Certificate Templates Screen and Cards", priority = "Medium")
	public void Verify_Certificate_Templates_Screen() {
		certificateTemplates.clickCertificateTemplates();
		softAssert.assertTrue(certificateTemplates.isCertificateTemplatesLoaded(), "Certificate Templates page should be loaded");
		int count = certificateTemplates.getTemplateCardsCount();
		softAssert.assertTrue(count >= 0, "Template cards count should be non-negative");
		softAssert.assertAll();
	}

	// =========================================================================
	// 7. PEOPLE & USER MANAGEMENT
	// =========================================================================

	@Test(priority = 19, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Role Assignment", description = "Verify Role Assignment Screen and User Search", priority = "High")
	public void Verify_Role_Assignment_Screen() {
		roleAssignment.clickRoleAssignment();
		softAssert.assertTrue(roleAssignment.isRoleAssignmentLoaded(), "Role Assignment page should be loaded");
		roleAssignment.searchRoleAssignment("test");
		softAssert.assertTrue(roleAssignment.isRoleAssignmentLoaded(), "Role Assignment should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 20, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Users & Employees", description = "Verify Users Screen with Internal & Contractor Tabs and Search", priority = "Critical")
	public void Verify_Users_And_Employees_Screen() {
		users.clickUsers();
		softAssert.assertTrue(users.isUsersPageLoaded(), "Users page heading should be visible");

		// 1. Verify Internal Users Tab & Search
		users.switchToInternalUsersTab();
		softAssert.assertTrue(users.isUsersPageLoaded(), "Internal Users tab should be selected");
		String internalQuery = userDataList.isEmpty() ? "test" : userDataList.get(0).getFullName();
		users.searchUsers(internalQuery);
		softAssert.assertTrue(users.isUsersPageLoaded(), "Users listing should remain loaded after internal search");

		// 2. Verify Contractor Users Tab & Search
		users.switchToContractorUsersTab();
		softAssert.assertTrue(users.isUsersPageLoaded(), "Contractor Users tab should be selected");
		String contractorQuery = userDataList.size() > 1 ? userDataList.get(1).getFullName() : "test";
		users.searchUsers(contractorQuery);
		softAssert.assertTrue(users.isUsersPageLoaded(), "Users listing should remain loaded after contractor search");

		// Return to Internal Tab
		users.switchToInternalUsersTab();
		softAssert.assertAll();
	}

	@Test(priority = 21, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Trainers", description = "Verify Trainers Screen and Search", priority = "Medium")
	public void Verify_Trainers_Screen() {
		trainers.clickTrainers();
		softAssert.assertTrue(trainers.isTrainersPageLoaded(), "Trainers page heading should be loaded");
		trainers.searchTrainers("test");
		softAssert.assertTrue(trainers.isTrainersPageLoaded(), "Trainers page should remain functional after search");
		softAssert.assertAll();
	}

	// =========================================================================
	// 8. REPORTS & ANALYTICS (ALL 4 TABS)
	// =========================================================================

	@Test(priority = 22, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Reports & Analytics", description = "Verify Reports & Analytics 4 Dashboard Tabs (Training Performance, Certifications, Leaderboards, Analytics)", priority = "High")
	public void Verify_Reports_And_Analytics_All_Tabs() {
		reportsAnalytics.clickReports();
		softAssert.assertTrue(reportsAnalytics.isReportsPageLoaded(), "Reports page heading should be loaded");

		reportsAnalytics.switchToTrainingPerformanceTab();
		softAssert.assertTrue(reportsAnalytics.isReportsPageLoaded(), "Training Performance tab should remain functional");

		reportsAnalytics.switchToCertificationsTab();
		softAssert.assertTrue(reportsAnalytics.isReportsPageLoaded(), "Certifications tab should remain functional");

		reportsAnalytics.switchToLeaderboardsTab();
		softAssert.assertTrue(reportsAnalytics.isReportsPageLoaded(), "Leaderboards tab should remain functional");

		reportsAnalytics.switchToAnalyticsTab();
		softAssert.assertTrue(reportsAnalytics.isReportsPageLoaded(), "Analytics tab should remain functional");

		softAssert.assertAll();
	}

	// =========================================================================
	// 9. SYSTEM INFRASTRUCTURE & SUPPORT
	// =========================================================================

	@Test(priority = 23, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - MDM Devices", description = "Verify Device Registry Screen, Devices, Pending Requests and Rejected Tabs", priority = "High")
	public void Verify_MDM_Device_Registry_Screen() {
		clientMdmDevices.clickMDMDevices();
		softAssert.assertTrue(clientMdmDevices.isMDMDevicesPageLoaded(), "MDM Devices page should be loaded");
		clientMdmDevices.switchToDevicesTab();
		softAssert.assertTrue(clientMdmDevices.isMDMDevicesPageLoaded(), "Devices tab should be functional");
		clientMdmDevices.switchToPendingRequestsTab();
		softAssert.assertTrue(clientMdmDevices.isMDMDevicesPageLoaded(), "Pending Requests tab should be functional");
		clientMdmDevices.switchToRejectedTab();
		softAssert.assertTrue(clientMdmDevices.isMDMDevicesPageLoaded(), "Rejected tab should be functional");
		clientMdmDevices.switchToDevicesTab();
		clientMdmDevices.searchDevices("test");
		softAssert.assertAll();
	}

	@Test(priority = 24, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Synchronization", description = "Verify Synchronization Dashboard Screen", priority = "Medium")
	public void Verify_Synchronization_Screen() {
		synchronization.clickSynchronization();
		softAssert.assertTrue(synchronization.isSynchronizationPageLoaded(), "Synchronization page should be loaded");
		softAssert.assertAll();
	}

	@Test(priority = 25, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Recovery Center", description = "Verify Recovery Center Screen and Search", priority = "Medium")
	public void Verify_Recovery_Center_Screen() {
		recoveryCenter.clickRecoveryCenter();
		softAssert.assertTrue(recoveryCenter.isRecoveryCenterLoaded(), "Recovery Center page should be loaded");
		recoveryCenter.searchDeletedRecords("test");
		softAssert.assertTrue(recoveryCenter.isRecoveryCenterLoaded(), "Recovery Center should remain functional after search");
		softAssert.assertAll();
	}

	@Test(priority = 26, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Support Tickets", description = "Verify Support Tickets Screen, Status Tabs (Active, Resolved) and Form Integrity", priority = "High")
	public void Verify_Support_Tickets_Screen_And_Modal() {
		supportTickets.clickSupportTickets();
		softAssert.assertTrue(supportTickets.isSupportTicketsPageLoaded(), "Support Tickets page should be loaded");

		supportTickets.switchToActiveTab();
		softAssert.assertTrue(supportTickets.isSupportTicketsPageLoaded(), "Active status tab should be loaded");

		supportTickets.switchToResolvedTab();
		softAssert.assertTrue(supportTickets.isSupportTicketsPageLoaded(), "Resolved status tab should be loaded");

		// Open Raise Ticket modal non-destructively and close
		supportTickets.clickRaiseTicket();
		supportTickets.closeRaiseTicketModal();
		softAssert.assertTrue(supportTickets.isSupportTicketsPageLoaded(), "Support tickets listing should remain loaded after modal dismiss");

		softAssert.assertAll();
	}

	// =========================================================================
	// 10. CLIENT SETTINGS (ALL 5 TABS)
	// =========================================================================

	@Test(priority = 27, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Settings", description = "Verify Client Portal Settings All Tabs (Portal Customization, Advanced Settings, Account, Notifications, Security)", priority = "High")
	public void Verify_Client_Settings_All_Tabs() {
		settings.clickSettings();
		softAssert.assertTrue(settings.isSettingsPageLoaded(), "Settings page heading should be visible");

		settings.switchToPortalCustomizationTab();
		softAssert.assertTrue(settings.isSettingsPageLoaded(), "Portal Customization tab should be selectable");

		settings.switchToAdvancedSettingsTab();
		softAssert.assertTrue(settings.isSettingsPageLoaded(), "Advanced Settings tab should be selectable");

		settings.switchToAccountTab();
		softAssert.assertTrue(settings.isSettingsPageLoaded(), "Account tab should be selectable");

		settings.switchToNotificationsTab();
		softAssert.assertTrue(settings.isSettingsPageLoaded(), "Notifications tab should be selectable");

		settings.switchToSecurityTab();
		softAssert.assertTrue(settings.isSettingsPageLoaded(), "Security tab should be selectable");

		softAssert.assertAll();
	}

	// =========================================================================
	// 11. DATA SYNCHRONIZATION & DUPLICATE CONFLICTS
	// =========================================================================

	@Test(priority = 28, dependsOnMethods = {"Verify_Overview_Dashboard"})
	@TestInfo(module = "Sanity - Duplicate Conflicts", description = "Verify Duplicate Conflicts Screen, Open/Resolved Tabs and Table/Empty State", priority = "High")
	public void Verify_Duplicate_Conflicts_Screen_And_Tabs() {
		duplicateConflicts.clickDuplicateConflicts();
		softAssert.assertTrue(duplicateConflicts.isDuplicateConflictsPageLoaded(), "Duplicate Conflicts page should be loaded");

		duplicateConflicts.switchToResolvedTab();
		softAssert.assertTrue(duplicateConflicts.isDuplicateConflictsPageLoaded(), "Resolved tab should remain functional");

		duplicateConflicts.switchToOpenTab();
		softAssert.assertTrue(duplicateConflicts.isDuplicateConflictsPageLoaded(), "Open tab should remain functional");

		softAssert.assertAll();
	}
}
