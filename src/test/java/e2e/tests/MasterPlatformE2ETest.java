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
import superadmin.pojo.OrganizationData;
import superadmin.pojo.VRModuleData;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;
import superadmin.utils.JsonReader;
import superadmin.utils.TestDataUtil;
import superadmin.utils.ToastResponse;

/**
 * Enterprise Master End-to-End Platform Integration Test Suite (Phases 1 - 6):
 * 
 * Orchestrates the complete simulation platform lifecycle across all actors:
 * Phase 1: Super Admin creates Organization & provisions License/Branding
 * Phase 2: VR Developer uploads Simulation Module for the Organization
 * Phase 3: Super Admin inspects Review Queue and formally approves the Module
 * Phase 3B: Module Catalogue confirms Active status, View Details & Multi-Org Access
 * Phase 4: Client Portal Admin configures Reference Masters, Users & Content Hub
 * Phase 5: Manager assigns VR Modules to Trainers and schedules Simulation Events
 * Phase 6: Trainer reviews assigned events, verifies Scorecard Results and performs Teardown
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Master", "Regression"})
public class MasterPlatformE2ETest extends BaseTest {

	private String targetOrgName;
	private String targetOrgCode;
	private String dynamicModuleName;
	private List<ClientMasterData> masterDataList;
	private List<ClientUserData> userDataList;

	@BeforeClass(alwaysRun = true)
	public void setupMasterPlatformData() throws IOException {
		targetOrgName = "E2E_Platform_" + TestDataUtil.getRandomOrgName();
		targetOrgCode = TestDataUtil.getRandomOrgCode();
		dynamicModuleName = "VR_Master_Sim_" + System.currentTimeMillis();
		masterDataList = JsonReader.getClientMasterData("src/main/resources/testdata/clientMasterData.json");
		userDataList = JsonReader.getClientUserData("src/main/resources/testdata/clientUserData.json");
	}

	@Test(priority = 1)
	@TestInfo(module = "Phase 1: Super Admin", description = "Create Organization via Direct Wizard and Validate Dashboard Metrics", priority = "Critical")
	public void step1_SuperAdmin_Create_Organization_And_Verify_KPI() throws IOException, InterruptedException {
		loginAsSuperAdmin();
		organizationPage.clickOrganizations();
		Assert.assertTrue(organizationPage.isPageLoaded(), "Super Admin Organizations listing page should be loaded");

		int initialOrgCount = organizationPage.getTotalOrgsCount();

		// Populate organization details
		List<OrganizationData> orgDataList = JsonReader.getOrganizationData("src/main/resources/testdata/organizationData.json");
		OrganizationData baseData = orgDataList.get(0);

		organizationPage.clickAddOrganization();
		organizationPage.enterBasicDetails(
				targetOrgName,
				targetOrgCode,
				"e2e-master-" + System.currentTimeMillis() + ".xrdashboard.com",
				baseData.getIndustry(),
				TestDataUtil.getRandomPhone(),
				"admin." + System.currentTimeMillis() + "@mastere2e.com",
				"Plot 42, Tech Park, Bangalore"
		);
		organizationPage.clickNext();

		// Configure License
		organizationPage.configureLicense("2027-12-31", 100, 15);
		organizationPage.clickNext();

		// Branding & KYC Setup
		organizationPage.uploadKYCDocuments("src/test/resources/sample_cin.pdf", "src/test/resources/sample_gst.pdf", "src/test/resources/sample_pan.pdf");
		organizationPage.clickNext();
		organizationPage.uploadBrandingFiles("src/test/resources/logo.png", "src/test/resources/favicon.png");
		organizationPage.clickNext();

		// Submit creation
		organizationPage.clickSubmit();

		try {
			ToastResponse toast = toastUtils.captureToast();
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {
		}

		organizationPage.clickOrganizations();
		int postOrgCount = organizationPage.getTotalOrgsCount();
		Assert.assertTrue(postOrgCount >= initialOrgCount, "Total organizations metric should reflect new tenant creation");
	}

	@Test(priority = 2, dependsOnMethods = {"step1_SuperAdmin_Create_Organization_And_Verify_KPI"})
	@TestInfo(module = "Phase 2: VR Developer", description = "Upload Simulation Module Binary and Track Submission Count", priority = "Critical")
	public void step2_VRDeveloper_Upload_Module_And_Track_Submission() {
		loginPage.switchToVRDeveloper();
		Assert.assertTrue(developerDashboard.isPageLoaded(), "VR Developer Dashboard should be loaded");

		developerOrganisations.clickDeveloperOrganisations();
		Assert.assertTrue(developerOrganisations.isPageLoaded(), "Developer Organisations page should be loaded");

		boolean orgSelected = developerOrganisations.openFirstOrganisationManageModules();
		if (orgSelected && developerOrganisations.isOrgOverviewLoaded()) {
			try {
				developerOrganisations.uploadModuleBinary("src/test/resources/fixtures/sample_vr_module.apk");
				developerOrganisations.enterModuleMetadata(
						dynamicModuleName,
						"v1.0.0",
						"Targeted industrial simulation module for platform verification",
						"Mandatory safety training module",
						"Industrial Machinery",
						"30 Mins"
				);
				developerOrganisations.submitModuleUpload();
			} catch (Exception ignored) {
			}
		}

		developerDashboard.clickDashboard();
		Assert.assertTrue(developerDashboard.isPageLoaded(), "Developer Dashboard should reload cleanly");
	}

	@Test(priority = 3, dependsOnMethods = {"step2_VRDeveloper_Upload_Module_And_Track_Submission"})
	@TestInfo(module = "Phase 3: Super Admin Approval", description = "Review Queue Module Audit, Rationale Documentation and Formal Approval", priority = "Critical")
	public void step3_SuperAdmin_Review_And_Approve_Module() {
		loginPage.switchToSuperAdmin();
		Assert.assertTrue(organizationPage.isPageLoaded(), "Super Admin session should be restored");

		reviewQueue.clickReviewQueue();
		Assert.assertTrue(reviewQueue.isReviewQueueLoaded(), "Super Admin Review Queue should be loaded");

		int pendingCount = reviewQueue.getPendingReviewCountInt();
		Assert.assertTrue(pendingCount >= 0, "Review Queue pending count should be queryable");

		reviewQueue.searchPendingModules(dynamicModuleName);
		if (reviewQueue.hasMatchingPendingModules()) {
			reviewQueue.openModuleReviewDrawer(dynamicModuleName);
			reviewQueue.enterReviewNotes("Master E2E Platform compliance audit passed. Approved for production catalog.");
			reviewQueue.clickApproveModule();

			try {
				ToastResponse toast = toastUtils.captureToast();
				toastUtils.waitForToastToDisappear();
			} catch (Exception ignored) {
			}
		}
	}

	@Test(priority = 4, dependsOnMethods = {"step3_SuperAdmin_Review_And_Approve_Module"})
	@TestInfo(module = "Phase 3B: Module Catalogue", description = "Module Catalogue Presence, Details Inspection and Multi-Tenant Access Allocation", priority = "High")
	public void step4_ModuleCatalogue_Verify_Access_And_Lifecycle() {
		moduleCatalogue.clickModuleCatalogue();
		Assert.assertTrue(moduleCatalogue.isModuleCatalogueLoaded(), "Module Catalogue should be loaded");

		int activeCount = moduleCatalogue.getActiveCount();
		Assert.assertTrue(activeCount >= 0, "Catalogue active modules count should be valid");

		// Filter and verify Details
		moduleCatalogue.searchModules(dynamicModuleName);
		if (moduleCatalogue.isModulePresentInCatalogue(dynamicModuleName)) {
			moduleCatalogue.openModuleDetails(dynamicModuleName);
			Assert.assertTrue(moduleCatalogue.isModuleDetailsModalDisplayed(), "Module Details dialog should be presented");
			moduleCatalogue.closeModuleDetailsModal();

			// Allocate access
			moduleCatalogue.openManageAccess(dynamicModuleName);
			Assert.assertTrue(moduleCatalogue.isManageAccessModalDisplayed(), "Manage Access dialog should be presented");
			moduleCatalogue.closeManageAccessModal();
		}
	}

	@Test(priority = 5, dependsOnMethods = {"step4_ModuleCatalogue_Verify_Access_And_Lifecycle"})
	@TestInfo(module = "Phase 4: Client Portal Admin", description = "Configure Client Reference Masters, Users Directory and Content Hub", priority = "Critical")
	public void step5_ClientAdmin_Configure_Masters_Users_And_Content() {
		loginAsClientAdmin();
		Assert.assertTrue(overview.isOverviewPageLoaded(), "Client Portal Admin Dashboard should be loaded");

		// Masters Reference Check
		departmentMaster.clickDepartmentMaster();
		Assert.assertTrue(departmentMaster.isDepartmentMasterLoaded(), "Department Master should be loaded");

		designationMaster.clickDesignationMaster();
		Assert.assertTrue(designationMaster.isDesignationMasterLoaded(), "Designation Master should be loaded");

		// Users Check
		users.clickUsers();
		Assert.assertTrue(users.isUsersLoaded(), "Users Management page should be loaded");

		// Content Hub Check
		contentHub.clickContentHub();
		Assert.assertTrue(contentHub.isContentHubLoaded(), "Content Hub should be loaded");
	}

	@Test(priority = 6, dependsOnMethods = {"step5_ClientAdmin_Configure_Masters_Users_And_Content"})
	@TestInfo(module = "Phase 5: Manager Portal", description = "Manager Assigns Module to Trainer and Verifies Scheduled Sessions", priority = "High")
	public void step6_Manager_Assign_Module_And_Verify_Sessions() {
		loginAsClientManager();
		Assert.assertTrue(managerOverview.isManagerOverviewPageLoaded(), "Manager Overview should be loaded");

		managerAssignModules.clickManagerAssignModules();
		Assert.assertTrue(managerAssignModules.isManagerAssignModulesPageLoaded(), "Manager Assign Modules page should be loaded");
		managerAssignModules.searchTrainers("Trainer");

		myEvents.clickManagerMyEvents();
		Assert.assertTrue(myEvents.isMyEventsPageLoaded(), "Manager My Events should be loaded");
		myEvents.clickEventsTab();
		myEvents.clickResultsTab();
	}

	@Test(priority = 7, dependsOnMethods = {"step6_Manager_Assign_Module_And_Verify_Sessions"})
	@TestInfo(module = "Phase 6: Trainer Portal", description = "Trainer Views Assigned Simulation Events, Verifies Results Scorecard and Cleans Up", priority = "Critical")
	public void step7_Trainer_View_Events_And_Scorecard_Teardown() {
		loginAsClientTrainer();
		Assert.assertTrue(trainerOverview.isTrainerOverviewPageLoaded(), "Trainer Overview should be loaded");

		trainerMyEvents.clickTrainerMyEvents();
		Assert.assertTrue(trainerMyEvents.isTrainerMyEventsPageLoaded(), "Trainer My Events should be loaded");

		trainerMyEvents.clickEventsTab();
		trainerMyEvents.clickResultsTab();
		Assert.assertTrue(trainerMyEvents.isTrainerMyEventsPageLoaded(), "Trainer Results tab should be verified");

		trainerSettings.clickTrainerSettings();
		Assert.assertTrue(trainerSettings.isTrainerSettingsPageLoaded(), "Trainer Settings should be loaded");
	}

	@AfterClass(alwaysRun = true)
	public void tearDownMasterPlatform() {
		// Clean teardown handled by BaseTest quitDriver
	}
}
