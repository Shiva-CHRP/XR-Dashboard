package superadmin.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ToastResponse;

/**
 * Enterprise Test Suite for Super Admin Catalogue, Curriculum & Governance:
 * 
 * 1. Catalogue & Governance Navigation (Module, Curriculum, License, MDM, Health, Audit Log)
 * 2. Super Admin Assessments CRUD (Create, Search, Delete with Sonner Toast validation)
 * 3. Super Admin Question Bank CRUD (Create, Search, Delete with Sonner Toast validation)
 * 4. Offline Portal Releases Stream Tab Switching & Release Inspection
 * 5. Super Admin Support Tickets Status Workflow & Conversation Inspection
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Superadmin", "Regression"})
public class CatalogueAndGovernanceTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void cataloguePrerequisite() {
		loginAsSuperAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Catalogue", description = "Navigate to Module Catalogue", priority = "High")
	public void navigate_To_Module_Catalogue() {
		moduleCatalogue.clickModuleCatalogue();
		Assert.assertTrue(moduleCatalogue.isPageLoaded(), "Module Catalogue page should be loaded");
	}

	@Test(priority = 2)
	@TestInfo(module = "Catalogue", description = "Navigate to Curriculum Catalogue", priority = "High")
	public void navigate_To_Curriculum_Catalogue() {
		curriculumCatalogue.clickCurriculumCatalogue();
		Assert.assertTrue(curriculumCatalogue.isPageLoaded(), "Curriculum Catalogue page should be loaded");
	}

	@Test(priority = 3)
	@TestInfo(module = "Curriculum Assessments CRUD", description = "Assessments CRUD: Create Assessment, Search/Read, and Delete with Sonner Toast Verification", priority = "Critical")
	public void verify_Assessments_CRUD_Workflow() {
		assessments.clickAssessments();
		Assert.assertTrue(assessments.isPageLoaded(), "Assessments page should be loaded");

		String dynamicAssessmentName = "Auto_Assessment_" + System.currentTimeMillis();

		// 1. Create Assessment
		assessments.clickCreateAssessment();
		Assert.assertTrue(assessments.isFormPageLoaded(), "Create Assessment form should be loaded");

		ToastResponse createToast = assessments.createAssessmentWithToast(
				dynamicAssessmentName,
				"Automated assessment for safety procedures",
				true,
				null
		);

		if (createToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(createToast.getType()) || createToast.getMessage().toLowerCase().contains("created") || createToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm assessment creation: " + createToast.getMessage());
		}

		// 2. Search & Read Assessment
		assessments.searchAssessments(dynamicAssessmentName);
		boolean present = assessments.isAssessmentPresent(dynamicAssessmentName);
		Assert.assertTrue(present, "Created assessment should appear in search results: " + dynamicAssessmentName);

		// 3. Delete Assessment
		assessments.triggerDeleteAssessment(dynamicAssessmentName);
		ToastResponse deleteToast = assessments.confirmDeleteAssessmentWithToast();
		if (deleteToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(deleteToast.getType()) || deleteToast.getMessage().toLowerCase().contains("deleted") || deleteToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm assessment deletion: " + deleteToast.getMessage());
		}

		assessments.clearAllFilters();
	}

	@Test(priority = 4)
	@TestInfo(module = "Question Bank CRUD", description = "Question Bank CRUD: Create Question, Search/Read, and Delete with Sonner Toast Verification", priority = "Critical")
	public void verify_Question_Bank_CRUD_Workflow() {
		questionBank.clickQuestionBank();
		Assert.assertTrue(questionBank.isPageLoaded(), "Question Bank page should be loaded");

		String dynamicQuestionText = "Is safety equipment mandatory during plant walkthrough? " + System.currentTimeMillis();

		// 1. Create Question
		questionBank.clickCreateQuestion();
		Assert.assertTrue(questionBank.isFormPageLoaded(), "Create Question form should be loaded");

		ToastResponse createToast = questionBank.createQuestionWithToast(
				dynamicQuestionText,
				"General Safety",
				"Yes, always mandatory",
				"No, optional",
				"Only on weekends",
				"Never required",
				"A",
				"PPE is mandatory at all times on the industrial floor."
		);

		if (createToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(createToast.getType()) || createToast.getMessage().toLowerCase().contains("created") || createToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm question creation: " + createToast.getMessage());
		}

		// 2. Search & Read Question
		questionBank.searchQuestions(dynamicQuestionText);
		boolean present = questionBank.isQuestionPresent(dynamicQuestionText);
		Assert.assertTrue(present, "Created question should appear in search results: " + dynamicQuestionText);

		// 3. Delete Question
		questionBank.triggerDeleteQuestion(dynamicQuestionText);
		ToastResponse deleteToast = questionBank.confirmDeleteQuestionWithToast();
		if (deleteToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(deleteToast.getType()) || deleteToast.getMessage().toLowerCase().contains("deleted") || deleteToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm question deletion: " + deleteToast.getMessage());
		}

		questionBank.clearAllFilters();
	}

	@Test(priority = 5)
	@TestInfo(module = "Governance", description = "Navigate to License Management", priority = "High")
	public void navigate_To_License() {
		license.clickLicense();
		Assert.assertTrue(license.isPageLoaded(), "License page should be loaded");
	}

	@Test(priority = 6)
	@TestInfo(module = "Governance", description = "Navigate to MDM Devices Management", priority = "High")
	public void navigate_To_MDM_Devices() {
		mdmDevices.clickMDMDevices();
		Assert.assertTrue(mdmDevices.isPageLoaded(), "MDM Devices page should be loaded");
	}

	@Test(priority = 7)
	@TestInfo(module = "Governance", description = "Navigate to System Health", priority = "Medium")
	public void navigate_To_System_Health() {
		systemHealth.clickSystemHealth();
		Assert.assertTrue(systemHealth.isPageLoaded(), "System Health page should be loaded");
	}

	@Test(priority = 8)
	@TestInfo(module = "Governance", description = "Navigate to Audit Log", priority = "Medium")
	public void navigate_To_Audit_Log() {
		auditLog.clickAuditLog();
		Assert.assertTrue(auditLog.isPageLoaded(), "Audit Log page should be loaded");
	}

	@Test(priority = 9)
	@TestInfo(module = "Governance", description = "Navigate to Offline Portal Releases, verify stream tabs and release notes modal", priority = "High")
	public void verify_Offline_Releases_Streams_And_Notes() {
		offlinePortalRelease.clickOfflinePortalRelease();
		Assert.assertTrue(offlinePortalRelease.isPageLoaded(), "Offline Portal Releases page should be loaded");

		offlinePortalRelease.switchToApkTab();
		Assert.assertTrue(offlinePortalRelease.isPageLoaded(), "Releases page should remain loaded on APK tab");

		offlinePortalRelease.switchToExeTab();
		Assert.assertTrue(offlinePortalRelease.isPageLoaded(), "Releases page should remain loaded on EXE tab");

		offlinePortalRelease.openUploadReleaseModal();
		Assert.assertTrue(offlinePortalRelease.isUploadModalDisplayed(), "Upload Release modal should be displayed");
		offlinePortalRelease.closeUploadModal();

		if (offlinePortalRelease.openFirstReleaseNotes()) {
			Assert.assertTrue(offlinePortalRelease.isReleaseNotesModalDisplayed(), "Release notes modal should be displayed");
			offlinePortalRelease.closeReleaseNotesModal();
		}
	}

	@Test(priority = 10)
	@TestInfo(module = "Governance", description = "Support Tickets: Active/Resolved Scope, Detail View and Sub-tabs Verification", priority = "High")
	public void verify_Support_Tickets_SuperAdmin_Workflow() {
		support.clickSupport();
		Assert.assertTrue(support.isPageLoaded(), "Support Tickets page should be loaded");

		support.clickResolvedTab();
		support.clickActiveTab();

		if (support.openFirstTicketDetail()) {
			Assert.assertTrue(support.isDetailPageLoaded(), "Ticket detail page should be loaded");
			support.clickDetailsSubTab();
			support.clickIdentitySubTab();
			support.clickDeviceSubTab();
			support.clickBackToSupport();
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDownCatalogueAndGovernanceTest() {
		try {
			loginPage.clickProfile();
			loginPage.clickLogOut();
		} catch (Exception e) {
			// Best-effort cleanup prior to driver quit
		}
	}
}
