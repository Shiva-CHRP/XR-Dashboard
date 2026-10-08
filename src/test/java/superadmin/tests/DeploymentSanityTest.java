package superadmin.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.pageobjects.OrgApplications;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;

/**
 * Enterprise Post-Deployment Sanity / Build Verification Test (BVT) Suite.
 *
 * Executed automatically by CI/CD / DevOps pipelines following environment deployment.
 * Verifies screen integrity, route accessibility, navigation controls, and form fields
 * across Super Admin and VR Developer roles without corrupting or mutating persistent data.
 */
@Listeners(Listener.class)
public class DeploymentSanityTest extends BaseTest {

	private String username;
	private String password;

	@BeforeMethod
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
		username = ConfigReader.getUsername();
		password = ConfigReader.getPassword();
	}

	// =========================================================================
	// 1. AUTHENTICATION & LOGIN SCREEN VERIFICATION
	// =========================================================================

	@Test(priority = 1)
	@TestInfo(module = "Sanity - Authentication", description = "Verify Login Screen Fields and Authenticate", priority = "Critical")
	public void Verify_Login_Screen_And_Authenticate() {
		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "Login URL should contain '/login'");
		softAssert.assertTrue(loginPage.isPasswordMasked(), "Password field should initially be masked");

		username = ConfigReader.getUsername();
		password = ConfigReader.getPassword();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.setRememberMe(true);
		loginPage.clickLogin();

		// Verify Governance Dashboard renders post-authentication
		loginPage.dashboardName();
		softAssert.assertTrue(loginPage.isDashboardLoaded(), "Governance Dashboard should be visible post-login");
		softAssert.assertAll();
	}

	// =========================================================================
	// 2. GOVERNANCE DASHBOARD
	// =========================================================================

	@Test(priority = 2, dependsOnMethods = {"Verify_Login_Screen_And_Authenticate"})
	@TestInfo(module = "Sanity - Governance Dashboard", description = "Verify Governance Dashboard Screen & Layout", priority = "High")
	public void Verify_Governance_Dashboard_Screen() {
		softAssert.assertTrue(loginPage.isDashboardLoaded(), "Governance Dashboard h1 heading should be loaded");
		softAssert.assertTrue(driver.getCurrentUrl().contains("/super-admin"), "Current URL should be in /super-admin context");
		softAssert.assertAll();
	}

	// =========================================================================
	// 3. ORGANIZATIONS MANAGEMENT
	// =========================================================================

	@Test(priority = 3, dependsOnMethods = {"Verify_Governance_Dashboard_Screen"})
	@TestInfo(module = "Sanity - Organizations", description = "Verify Organizations Screen, Search & Create Modal", priority = "High")
	public void Verify_Organizations_Screen_And_Fields() {
		organizationPage.clickOrganizations();
		softAssert.assertTrue(organizationPage.isPageLoaded(), "Organizations page URL should contain /super-admin/organizations");
		organizationPage.searchOrganization("test");
		softAssert.assertTrue(organizationPage.isPageLoaded(), "Organizations page should remain functional after searching");
		organizationPage.clearSearch();

		// Verify Create Organization Wizard Modal and cancel non-destructively
		organizationPage.clickAddOrganization();
		organizationPage.cancelOrganizationCreation();
		softAssert.assertTrue(organizationPage.isPageLoaded(), "Organizations listing should remain loaded after cancelling creation");
		softAssert.assertAll();
	}

	// =========================================================================
	// 4. MANAGE ORGANIZATION - ALL TABS TRAVERSAL & ACTIONS
	// =========================================================================

	@Test(priority = 4, dependsOnMethods = {"Verify_Organizations_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Organization Manage Tabs", description = "Verify All 10 Manage Organization Tabs (Overview, Admin Users, Branding, Security, Offline Config, License & Devices, Storage, Notifications, Activity Logs, Assigned Modules) & Modal Actions", priority = "High")
	public void Verify_Manage_Organization_All_Tabs() {
		organizationPage.clickOrganizations();
		boolean opened = organizationPage.openFirstOrganizationManage();
		Assert.assertTrue(opened, "Existing organization must be opened from the table into Manage mode");
			// 1. Overview Tab
			organizationPage.clickOverviewTab();
			softAssert.assertTrue(organizationPage.isOverviewTabLoaded(), "Overview tab details should be loaded");

			// 2. Admin Users Tab + Create Admin Modal
			organizationPage.clickAdminUsersTab();
			softAssert.assertTrue(organizationPage.isAdminUsersTabLoaded(), "Admin Users tab controls should be loaded");
			organizationPage.openCreateAdminModal();
			organizationPage.closeCreateAdminModal();

			// 3. Branding Tab
			organizationPage.clickBrandingTab();
			softAssert.assertTrue(organizationPage.isBrandingTabLoaded(), "Branding tab styling options should be loaded");

			// 4. Security Tab
			organizationPage.clickSecurityTab();
			softAssert.assertTrue(organizationPage.isSecurityTabLoaded(), "Security tab policy options should be loaded");

			// 5. Offline Config Tab
			organizationPage.clickOfflineConfigTab();
			softAssert.assertTrue(organizationPage.isOfflineConfigTabLoaded(), "Offline Config tab controls should be loaded");

			// 6. License & Devices Tab (including sub-tabs)
			organizationPage.clickLicenseAndTokensTab();
			softAssert.assertTrue(organizationPage.isLicenseAndTokensTabLoaded(), "License & Devices tab should be loaded");
			organizationPage.clickDevicesTab();
			organizationPage.clickBillingTab();
			organizationPage.clickLicenceTab();

			// 7. Storage Tab
			organizationPage.clickStorageTab();
			softAssert.assertTrue(organizationPage.isStorageTabLoaded(), "Storage tab quota breakdown should be loaded");

			// 8. Notifications Tab
			organizationPage.clickNotificationsTab();
			softAssert.assertTrue(organizationPage.isNotificationsTabLoaded(), "Notifications tab settings should be loaded");

			// 9. Activity Logs Tab
			organizationPage.clickActivityLogsTab();
			softAssert.assertTrue(organizationPage.isActivityLogsTabLoaded(), "Activity Logs tab table/filters should be loaded");

			// 10. Assigned Modules Tab + Assign Modules Modal
			organizationPage.clickAssignedModulesTab();
			softAssert.assertTrue(organizationPage.isAssignedModulesTabLoaded(), "Assigned Modules tab table/controls should be loaded");
			organizationPage.openAssignModulesModal();
			organizationPage.closeAssignModulesModal();

			// Clean navigation back to Organizations listing
			organizationPage.clickBackToOrganizations();
			softAssert.assertTrue(organizationPage.isPageLoaded(), "Should return back to Organizations listing");
		softAssert.assertAll();
	}

	// =========================================================================
	// 5. ORGANISATION APPLICATIONS (PROSPECTIVE CLIENT REGISTRATIONS)
	// =========================================================================

	@Test(priority = 5, dependsOnMethods = {"Verify_Manage_Organization_All_Tabs"})
	@TestInfo(module = "Sanity - Applications", description = "Verify Organisation Applications Screen, Public Link, Tabs, Search & Review Modal", priority = "High")
	public void Verify_Org_Applications_Screen_And_Fields() {
		orgApplications.clickApplications();
		softAssert.assertTrue(orgApplications.isPageLoaded(), "Organisation Applications screen heading and route should be loaded");

		// 1. Verify Client Application Public Registration Link Card
		String linkVal = orgApplications.getApplicationLinkValue();
		softAssert.assertTrue(linkVal != null && linkVal.contains("/register-organisation"), "Client application link should contain register-organisation route");
		orgApplications.clickCopyLink();

		// 2. Verify StatCard Tabs Filtering
		orgApplications.filterByTab("Pending review");
		orgApplications.filterByTab("Approved");
		orgApplications.filterByTab("Rejected");
		orgApplications.filterByTab("All applications");

		// 3. Search and Clear non-destructively
		orgApplications.searchApplications("test");
		orgApplications.clearSearch();

		// 4. Inspect Review Detail Modal non-destructively if any application is present
		boolean openedModal = orgApplications.viewFirstApplicationIfAvailable();
		if (openedModal) {
			orgApplications.closeDetailModalIfOpen();
		}

		// 5. Refresh Action
		orgApplications.clickRefresh();
		softAssert.assertTrue(orgApplications.isPageLoaded(), "Organisation Applications listing should remain loaded after refresh");
		softAssert.assertAll();
	}

	// =========================================================================
	// 6. DEVELOPERS FLEET
	// =========================================================================

	@Test(priority = 6, dependsOnMethods = {"Verify_Org_Applications_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Developers", description = "Verify Developers Fleet Screen, Search, Create, View & Edit Modals", priority = "High")
	public void Verify_Developers_Screen_And_Fields() {
		developers.clickDevelopers();
		softAssert.assertTrue(developers.isPageLoaded(), "Developers screen heading should be visible");

		// 0. KPI Stat Cards Validation
		int totalDevs = developers.getTotalDevelopersCount();
		softAssert.assertTrue(totalDevs >= 0, "Total developers KPI count should be non-negative");

		// 1. Search and Filtering
		developers.searchDevelopers("test");
		softAssert.assertTrue(developers.isPageLoaded(), "Developers page should remain functional after searching");
		developers.clearAllFilters();

		// 2. Create Developer Modal Validation
		developers.clickCreateDeveloper();
		softAssert.assertTrue(developers.isDeveloperModalDisplayed(), "Create Developer modal should be displayed");
		developers.cancelDeveloperCreation();
		softAssert.assertTrue(developers.isPageLoaded(), "Developers listing should remain loaded after cancelling creation");

		// 3. View Submissions Modal Validation
		if (developers.openFirstDeveloperSubmissions()) {
			softAssert.assertTrue(developers.isSubmissionsModalDisplayed(), "Submissions modal should be displayed");
			developers.closeSubmissionsModal();
			softAssert.assertTrue(developers.isPageLoaded(), "Developers listing should remain loaded after closing submissions");
		}

		// 4. Edit Developer Modal Validation
		if (developers.openFirstDeveloperEdit()) {
			softAssert.assertTrue(developers.isDeveloperModalDisplayed(), "Edit Developer modal should be displayed");
			developers.cancelDeveloperCreation();
			softAssert.assertTrue(developers.isPageLoaded(), "Developers listing should remain loaded after cancelling edit");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 7. MODULE REVIEW QUEUE
	// =========================================================================

	@Test(priority = 7, dependsOnMethods = {"Verify_Developers_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Review Queue", description = "Verify Module Review Queue Screen & View Detail", priority = "Medium")
	public void Verify_Module_Review_Queue_Screen_And_Filters() {
		reviewQueue.clickReviewQueue();
		softAssert.assertTrue(reviewQueue.isPageLoaded(), "Module Review Queue heading should be visible");

		// View Action: If review rows exist, open detail and return
		if (reviewQueue.openFirstReviewDetail()) {
			softAssert.assertTrue(reviewQueue.isDetailPageLoaded(), "Review detail page should be loaded");
			reviewQueue.clickBackToQueue();
			softAssert.assertTrue(reviewQueue.isPageLoaded(), "Should return back to Review Queue page");
		}
		softAssert.assertAll();
	}

	// =========================================================================
	// 8. MODULE CATALOGUE
	// =========================================================================

	@Test(priority = 8, dependsOnMethods = {"Verify_Module_Review_Queue_Screen_And_Filters"})
	@TestInfo(module = "Sanity - Module Catalogue", description = "Verify Module Catalogue Screen, Views, Manage Access Modal & View Detail", priority = "Medium")
	public void Verify_Module_Catalogue_Screen_And_Fields() {
		moduleCatalogue.clickModuleCatalogue();
		softAssert.assertTrue(moduleCatalogue.isPageLoaded(), "Module Catalogue heading should be visible");

		// 1. View Toggles (List vs Grid)
		moduleCatalogue.clickListView();
		moduleCatalogue.clickGridView();

		// 2. Search & Clear
		moduleCatalogue.searchModule("test");
		moduleCatalogue.clearAllFilters();

		// 3. Manage Access Modal Validation & Clean Dismissal
		if (moduleCatalogue.openFirstManageAccessModal()) {
			softAssert.assertTrue(moduleCatalogue.isAccessModalDisplayed(), "Manage Access modal should be displayed");
			moduleCatalogue.cancelAccessModal();
			softAssert.assertTrue(moduleCatalogue.isPageLoaded(), "Module Catalogue should remain loaded after cancelling access modal");
		}

		// 4. View Module Detail & Return
		if (moduleCatalogue.openFirstModuleDetail()) {
			softAssert.assertTrue(moduleCatalogue.isDetailPageLoaded(), "Module Detail page should be loaded");
			moduleCatalogue.clickBackToCatalogue();
			softAssert.assertTrue(moduleCatalogue.isPageLoaded(), "Module Catalogue should remain loaded after returning from detail");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 9. CURRICULUM CATALOGUE
	// =========================================================================

	@Test(priority = 9, dependsOnMethods = {"Verify_Module_Catalogue_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Curriculum Catalogue", description = "Verify Curriculum Catalogue Screen, Create, View Detail & Edit Form", priority = "High")
	public void Verify_Curriculum_Catalogue_Screen_And_Fields() {
		curriculumCatalogue.clickCurriculumCatalogue();
		softAssert.assertTrue(curriculumCatalogue.isPageLoaded(), "Curriculum Catalogue page should be loaded");

		// 1. Create Curriculum Route & Form Validation
		curriculumCatalogue.clickCreateCurriculum();
		softAssert.assertTrue(curriculumCatalogue.isFormPageLoaded(), "Create Curriculum form page should be loaded");
		curriculumCatalogue.cancelForm();
		softAssert.assertTrue(curriculumCatalogue.isPageLoaded(), "Curriculum Catalogue should be loaded after cancelling form");

		// 2. View Detail & 3. Edit Form Validation
		if (curriculumCatalogue.openFirstCurriculumDetail()) {
			softAssert.assertTrue(curriculumCatalogue.isDetailPageLoaded(), "Curriculum Detail page should be loaded");

			// Test Edit from Detail
			curriculumCatalogue.clickEditFromDetail();
			softAssert.assertTrue(curriculumCatalogue.isFormPageLoaded(), "Edit Curriculum form should be loaded");
			curriculumCatalogue.cancelForm();

			// Return back to Catalogue
			curriculumCatalogue.clickCurriculumCatalogue();
			softAssert.assertTrue(curriculumCatalogue.isPageLoaded(), "Curriculum Catalogue should be loaded");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 10. QUESTION BANK
	// =========================================================================

	@Test(priority = 10, dependsOnMethods = {"Verify_Curriculum_Catalogue_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Question Bank", description = "Verify Question Bank Screen, Create, View Expand & Edit Form", priority = "Medium")
	public void Verify_Question_Bank_Screen_And_Fields() {
		questionBank.clickQuestionBank();
		softAssert.assertTrue(questionBank.isPageLoaded(), "Question Bank page should be loaded");

		// 1. Create Question Form Validation
		questionBank.clickCreateQuestion();
		softAssert.assertTrue(questionBank.isFormPageLoaded(), "Create Question form page should be loaded");
		questionBank.cancelForm();
		softAssert.assertTrue(questionBank.isPageLoaded(), "Question Bank page should be loaded after cancelling form");

		// 2. View / Expand Question Card Validation
		questionBank.expandFirstQuestion();

		// 3. Edit Question Form Validation
		if (questionBank.clickEditFirstQuestion()) {
			softAssert.assertTrue(questionBank.isFormPageLoaded(), "Edit Question form page should be loaded");
			questionBank.cancelForm();
			softAssert.assertTrue(questionBank.isPageLoaded(), "Question Bank page should be loaded after cancelling edit");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 11. ASSESSMENTS
	// =========================================================================

	@Test(priority = 11, dependsOnMethods = {"Verify_Question_Bank_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Assessments", description = "Verify Assessments Screen, Create, View Detail & Edit Form", priority = "Medium")
	public void Verify_Assessments_Screen_And_Fields() {
		assessments.clickAssessments();
		softAssert.assertTrue(assessments.isPageLoaded(), "Assessments page should be loaded");

		// 1. Create Assessment Route & Form Validation
		assessments.clickCreateAssessment();
		softAssert.assertTrue(assessments.isFormPageLoaded(), "Create Assessment form page should be loaded");
		assessments.cancelForm();
		softAssert.assertTrue(assessments.isPageLoaded(), "Assessments page should be loaded after cancelling form");

		// 2. View Detail & 3. Edit Form Validation
		if (assessments.openFirstAssessmentDetail()) {
			softAssert.assertTrue(assessments.isDetailPageLoaded(), "Assessment Detail page should be loaded");

			// Test Edit from Detail
			assessments.clickEditFromDetail();
			softAssert.assertTrue(assessments.isFormPageLoaded(), "Edit Assessment form should be loaded");
			assessments.cancelForm();

			// Return back to Assessments
			assessments.clickAssessments();
			softAssert.assertTrue(assessments.isPageLoaded(), "Assessments page should be loaded");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 12. LICENSE MANAGEMENT
	// =========================================================================

	@Test(priority = 12, dependsOnMethods = {"Verify_Assessments_Screen_And_Fields"})
	@TestInfo(module = "Sanity - License Management", description = "Verify License Management Screen, Search & Password Verification Modal", priority = "High")
	public void Verify_License_Management_Screen_And_Fields() {
		license.clickLicense();
		softAssert.assertTrue(license.isPageLoaded(), "License page heading should be visible");

		license.searchLicense("test");
		license.clearAllFilters();

		// Password Verification Modal Validation & Clean Dismissal
		if (license.openFirstCopyKeyVerificationModal()) {
			softAssert.assertTrue(license.isVerifyModalDisplayed(), "Verify Identity modal should be displayed");
			license.cancelVerifyModal();
			softAssert.assertTrue(license.isPageLoaded(), "License page should remain loaded after cancelling verification modal");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 13. MDM DEVICES
	// =========================================================================

	@Test(priority = 13, dependsOnMethods = {"Verify_License_Management_Screen_And_Fields"})
	@TestInfo(module = "Sanity - MDM Devices", description = "Verify MDM Devices Screen, Sub-Tabs & Edit Device Limit Modal", priority = "Medium")
	public void Verify_MDM_Devices_Screen_And_Fields() {
		mdmDevices.clickMDMDevices();
		softAssert.assertTrue(mdmDevices.isPageLoaded(), "MDM Devices page should be loaded");

		// 1. Edit Device Limit Modal on Organisations tab
		if (mdmDevices.openFirstOrgChangeLimitModal()) {
			softAssert.assertTrue(mdmDevices.isChangeLimitModalDisplayed(), "Change Device Limit modal should be displayed");
			mdmDevices.cancelChangeLimitModal();
			softAssert.assertTrue(mdmDevices.isPageLoaded(), "MDM Devices page should remain loaded after cancelling modal");
		}

		// 2. Sub-Tab Switch: Devices & back to Organisations
		mdmDevices.clickDevicesTab();
		softAssert.assertTrue(mdmDevices.isPageLoaded(), "MDM Devices page should remain functional on Devices tab");
		mdmDevices.clickOrganisationsTab();
		softAssert.assertTrue(mdmDevices.isPageLoaded(), "MDM Devices page should return to Organisations tab");

		softAssert.assertAll();
	}

	// =========================================================================
	// 14. ORGANIZATION SYNC CENTER
	// =========================================================================

	@Test(priority = 14, dependsOnMethods = {"Verify_MDM_Devices_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Organization Sync", description = "Verify Organization Sync Center Screen & Detail View", priority = "Medium")
	public void Verify_Organization_Sync_Center_Screen_And_Fields() {
		organizationSync.clickOrganizationSync();
		softAssert.assertTrue(organizationSync.isPageLoaded(), "Organization Sync Center heading should be visible");

		// View Detail: If organizations exist, navigate into detail and verify all 4 tabs
		if (organizationSync.openFirstOrganizationDetail()) {
			softAssert.assertTrue(organizationSync.isDetailPageLoaded(), "Organization Sync Detail page should be loaded");
			
			// Traverse all 4 detail sub-tabs: Overview, Sync Health, Sync History, Devices
			organizationSync.clickOverviewTab();
			organizationSync.clickSyncHealthTab();
			organizationSync.clickSyncHistoryTab();
			organizationSync.clickDevicesTab();

			organizationSync.clickBackButton();
			softAssert.assertTrue(organizationSync.isPageLoaded(), "Should return back to Organization Sync Center");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 15. OFFLINE PORTAL RELEASES
	// =========================================================================

	@Test(priority = 15, dependsOnMethods = {"Verify_Organization_Sync_Center_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Offline Releases", description = "Verify Offline Portal Releases Screen, Upload Modal & Release Notes", priority = "Medium")
	public void Verify_Offline_Portal_Releases_Screen_And_Fields() {
		offlinePortalRelease.clickOfflinePortalRelease();
		softAssert.assertTrue(offlinePortalRelease.isPageLoaded(), "Offline Portal Releases page should be loaded");

		// 1. Upload Release Modal Validation & Clean Dismissal
		offlinePortalRelease.openUploadReleaseModal();
		softAssert.assertTrue(offlinePortalRelease.isUploadModalDisplayed(), "Upload Release modal should be displayed");
		offlinePortalRelease.closeUploadModal();
		softAssert.assertTrue(offlinePortalRelease.isPageLoaded(), "Offline Releases page should remain loaded after closing modal");

		// 2. Release Notes Inspection Modal if releases exist
		if (offlinePortalRelease.openFirstReleaseNotes()) {
			softAssert.assertTrue(offlinePortalRelease.isReleaseNotesModalDisplayed(), "Release notes modal should be displayed");
			offlinePortalRelease.closeReleaseNotesModal();
			softAssert.assertTrue(offlinePortalRelease.isPageLoaded(), "Offline Releases page should remain loaded after closing notes");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 16. SYSTEM HEALTH
	// =========================================================================

	@Test(priority = 16, dependsOnMethods = {"Verify_Offline_Portal_Releases_Screen_And_Fields"})
	@TestInfo(module = "Sanity - System Health", description = "Verify System Health Screen & Services", priority = "High")
	public void Verify_System_Health_Screen_And_Meters() {
		systemHealth.clickSystemHealth();
		softAssert.assertTrue(systemHealth.isPageLoaded(), "System Health page should be loaded");
		systemHealth.clickRefresh();
		softAssert.assertTrue(systemHealth.isPageLoaded(), "System Health page should remain loaded after refresh");
		softAssert.assertAll();
	}

	// =========================================================================
	// 17. AUDIT LOGS
	// =========================================================================

	@Test(priority = 17, dependsOnMethods = {"Verify_System_Health_Screen_And_Meters"})
	@TestInfo(module = "Sanity - Audit Logs", description = "Verify Audit Logs Screen, Notices Modal & Row Inspection", priority = "Medium")
	public void Verify_Audit_Logs_Screen_And_Filters() {
		auditLog.clickAuditLog();
		softAssert.assertTrue(auditLog.isPageLoaded(), "Audit Log page should be loaded");

		// 1. Regulatory Notices Modal Validation
		auditLog.openNoticesModal();
		softAssert.assertTrue(auditLog.isNoticesModalOpen(), "Regulatory Notices modal should be open");
		auditLog.closeNoticesModal();
		softAssert.assertTrue(auditLog.isPageLoaded(), "Audit Log page should remain loaded after closing modal");

		// 2. Row inspection if logs exist
		if (auditLog.openFirstRowDetail()) {
			softAssert.assertTrue(auditLog.isPageLoaded(), "Audit Log row inspection should expand properly");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 18. SUPPORT CENTER
	// =========================================================================

	@Test(priority = 18, dependsOnMethods = {"Verify_Audit_Logs_Screen_And_Filters"})
	@TestInfo(module = "Sanity - Support Center", description = "Verify Support Center Screen, Tabs & Ticket Detail View", priority = "Medium")
	public void Verify_Support_Center_Screen_And_Fields() {
		support.clickSupport();
		softAssert.assertTrue(support.isPageLoaded(), "Support page should be loaded");

		// 1. Tab Navigation
		support.clickResolvedTab();
		support.clickActiveTab();

		// 2. View Detail: If tickets exist, navigate into detail and verify all tabs
		if (support.openFirstTicketDetail()) {
			softAssert.assertTrue(support.isDetailPageLoaded(), "Support Ticket Detail page should be loaded");
			
			// Traverse ticket detail sub-tabs: Details, Identity, Device
			support.clickDetailsSubTab();
			support.clickIdentitySubTab();
			support.clickDeviceSubTab();

			support.clickBackToSupport();
			softAssert.assertTrue(support.isPageLoaded(), "Should return back to Support Center");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 19. SUPER ADMIN SETTINGS SCREEN
	// =========================================================================

	@Test(priority = 19, dependsOnMethods = {"Verify_Support_Center_Screen_And_Fields"})
	@TestInfo(module = "Sanity - Settings", description = "Verify Super Admin Settings Screen, Brand Assets, Appearance & Navigation Colors", priority = "High")
	public void Verify_Super_Admin_Settings_Screen() {
		superAdminSettings.navigateToSettings();
		softAssert.assertTrue(superAdminSettings.isSettingsPageLoaded(), "Super Admin Settings page should be loaded");
		softAssert.assertTrue(superAdminSettings.isBrandAssetsCardPresent(), "Brand Assets card should be present");
		softAssert.assertTrue(superAdminSettings.isAppearanceCardPresent(), "Appearance card should be present");
		softAssert.assertTrue(superAdminSettings.isSidebarStyleCardPresent(), "Sidebar Style card should be present");
		softAssert.assertTrue(superAdminSettings.isNavigationColorsCardPresent(), "Navigation Colors card should be present");
		softAssert.assertTrue(superAdminSettings.isNotificationsCardPresent(), "Notifications card should be present");
		softAssert.assertTrue(superAdminSettings.isSecurityCardPresent(), "Security card should be present");
		softAssert.assertTrue(superAdminSettings.isGeneralCardPresent(), "General card should be present");
		softAssert.assertAll();
	}

	// =========================================================================
	// 20. ROLE SWITCH TO VR DEVELOPER
	// =========================================================================

	@Test(priority = 20, dependsOnMethods = {"Verify_Super_Admin_Settings_Screen"})
	@TestInfo(module = "Sanity - Role Switch", description = "Switch Role to VR Developer", priority = "Critical")
	public void Verify_Role_Switch_To_VR_Developer() {
		loginPage.switchToVRDeveloper();
		softAssert.assertTrue(developerDashboard.isPageLoaded(), "VR Developer Dashboard should be displayed upon switching role");
		softAssert.assertAll();
	}

	// =========================================================================
	// 21. VR DEVELOPER DASHBOARD
	// =========================================================================

	@Test(priority = 21, dependsOnMethods = {"Verify_Role_Switch_To_VR_Developer"})
	@TestInfo(module = "Sanity - Developer Dashboard", description = "Verify VR Developer Dashboard Screen & Stats", priority = "High")
	public void Verify_VR_Developer_Dashboard_Screen() {
		developerDashboard.clickDeveloperDashboard();
		softAssert.assertTrue(developerDashboard.isPageLoaded(), "VR Developer Dashboard should be loaded");
		softAssert.assertAll();
	}

	// =========================================================================
	// 22. VR DEVELOPER SUBMISSION TRACKER
	// =========================================================================

	@Test(priority = 22, dependsOnMethods = {"Verify_VR_Developer_Dashboard_Screen"})
	@TestInfo(module = "Sanity - Submission Tracker", description = "Verify VR Developer Submission Tracker Screen & View Detail", priority = "Medium")
	public void Verify_VR_Developer_Submission_Tracker_Screen() {
		submissionTracker.clickSubmissionTracker();
		softAssert.assertTrue(submissionTracker.isPageLoaded(), "Submission Tracker page should be loaded");

		// View Detail: If submissions exist, navigate into detail and return
		if (submissionTracker.openFirstSubmissionDetail()) {
			softAssert.assertTrue(submissionTracker.isDetailPageLoaded(), "Submission detail page should be loaded");
			submissionTracker.clickBackToSubmissions();
			softAssert.assertTrue(submissionTracker.isPageLoaded(), "Should return back to Submission Tracker page");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 23. VR DEVELOPER MY ORGANISATIONS
	// =========================================================================

	@Test(priority = 23, dependsOnMethods = {"Verify_VR_Developer_Submission_Tracker_Screen"})
	@TestInfo(module = "Sanity - Developer Organisations", description = "Verify VR Developer My Organisations Screen, View Modes, Manage Modules & Upload Wizard", priority = "Medium")
	public void Verify_VR_Developer_Organisations_Screen() {
		developerOrganisations.clickDeveloperOrganisations();
		softAssert.assertTrue(developerOrganisations.isPageLoaded(), "Developer Organisations page should be loaded");

		// 1. View Toggles (List vs Grid)
		developerOrganisations.clickListView();
		developerOrganisations.clickGridView();

		// 2. Manage Modules Detail & Upload Wizard Route Validation
		if (developerOrganisations.openFirstOrganisationDetail()) {
			softAssert.assertTrue(developerOrganisations.isOrgOverviewLoaded(), "Organisation Overview page should be loaded");

			// Test Upload Route and cancel cleanly
			developerOrganisations.clickUploadNewModule();
			softAssert.assertTrue(developerOrganisations.isUploadFormLoaded(), "Upload Module wizard should be loaded");
			developerOrganisations.cancelModuleUploadWizard();
			softAssert.assertTrue(developerOrganisations.isOrgOverviewLoaded(), "Should return back to Organisation Overview");

			// Return to Organisations list
			developerOrganisations.clickBackToOrganisations();
			softAssert.assertTrue(developerOrganisations.isPageLoaded(), "Should return back to Developer Organisations listing");
		}

		softAssert.assertAll();
	}

	// =========================================================================
	// 24. VR DEVELOPER PROFILE
	// =========================================================================

	@Test(priority = 24, dependsOnMethods = {"Verify_VR_Developer_Organisations_Screen"})
	@TestInfo(module = "Sanity - Developer Profile", description = "Verify VR Developer Profile Screen", priority = "Medium")
	public void Verify_VR_Developer_Profile_Screen() {
		developerProfile.clickDeveloperProfile();
		softAssert.assertTrue(developerProfile.isPageLoaded(), "Developer Profile page should be loaded");
		softAssert.assertAll();
	}

	// =========================================================================
	// 25. SWITCH BACK TO SUPER ADMIN & LOGOUT
	// =========================================================================

	@Test(priority = 25, dependsOnMethods = {"Verify_VR_Developer_Profile_Screen"})
	@TestInfo(module = "Sanity - Logout", description = "Switch Back to Super Admin and Sign Out", priority = "Critical")
	public void Switch_Back_To_Super_Admin_And_Logout() throws InterruptedException {
		loginPage.switchToSuperAdmin();
		loginPage.dashboardName();
		softAssert.assertTrue(loginPage.isDashboardLoaded(), "Governance Dashboard should be active after switching back");

		loginPage.clickProfile();
		loginPage.clickLogOut();
		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "User should be redirected back to /login post-signout");
		softAssert.assertAll();
	}
}
