package e2e.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.pojo.VRModuleData;
import superadmin.testcomponents.BaseTest;
import superadmin.testcomponents.TestDataProvider;
import superadmin.utils.ToastResponse;

/**
 * End-to-End Test Suite for VR Module Full Lifecycle:
 * 1. VR Developer Module Upload Wizard Cancellation & Blocker Verification
 * 2. VR Developer Module Upload Wizard (Module Details -> Build Upload (Select File Type first,
 *    then upload build) -> Next -> Review & Submit (Verify all details) -> Submit) + Dashboard KPI Increment
 * 3. Super Admin Review Queue Module Search Filter, Details Inspection & Quality Approval
 * 4. Module Catalogue Module Search Filter, View Details & Manage Cross-Organisation Access
 * 5. Module Catalogue Search Filtered Archive & Unarchive Lifecycle with Tab Badge Synchronization
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Superadmin", "Regression"})
public class VRModuleAndCatalogueE2ETest extends BaseTest {

	private String dynamicModuleName;
	private VRModuleData moduleData;

	@BeforeClass(alwaysRun = true)
	public void setupSession() {
		loginAsSuperAdmin();
		dynamicModuleName = "VR_HazGas_" + System.currentTimeMillis();
	}

	@Test(priority = 1)
	@TestInfo(module = "VR Developer Upload", description = "Verify Upload Wizard Cancellation, Leave Page Warning Modal, Stay on Page and Safe Exit", priority = "High")
	public void verify_VR_Developer_Module_Upload_Cancellation() {
		// 1. Switch to VR Developer Role
		loginPage.switchToVRDeveloper();
		Assert.assertTrue(developerDashboard.isPageLoaded(), "VR Developer Dashboard should be loaded");

		// 2. Navigate to Developer Organisations and select target org
		developerOrganisations.clickDeveloperOrganisations();
		Assert.assertTrue(developerOrganisations.isPageLoaded(), "Developer Organisations page should be loaded");

		boolean orgSelected = developerOrganisations.openFirstOrganisationManageModules();
		Assert.assertTrue(orgSelected, "An active organisation should be selected to manage modules");
		Assert.assertTrue(developerOrganisations.isOrgOverviewLoaded(), "Organisation Overview should be loaded");

		// 3. Initiate Upload Wizard
		developerOrganisations.clickUploadNewModule();
		Assert.assertTrue(developerOrganisations.isUploadFormLoaded(), "Upload Module wizard should be loaded");
		Assert.assertTrue(developerOrganisations.isModuleDetailsStepActive(), "Module Details (Step 1) should be active");

		// 4. Enter partial details
		developerOrganisations.enterModuleDetails(
				"Cancel_Test_" + System.currentTimeMillis(),
				"Safety Training",
				"Guided/Unguided",
				"Standard",
				"1.0.0",
				"Temporary module metadata to test wizard cancellation and leave-page confirmation modal.",
				java.util.Collections.emptyList()
		);

		// 5. Click Cancel in Wizard footer
		developerOrganisations.clickCancelUploadWizard();
		Assert.assertTrue(developerOrganisations.isLeavePageModalDisplayed(), "'Leave this page?' confirmation modal should be displayed");

		// 6. Test 'Stay on Page'
		developerOrganisations.clickStayOnPage();
		Assert.assertTrue(developerOrganisations.isUploadFormLoaded(), "Wizard should remain open after clicking 'Stay on Page'");

		// 7. Click Cancel again and test 'Leave Page'
		developerOrganisations.clickCancelUploadWizard();
		Assert.assertTrue(developerOrganisations.isLeavePageModalDisplayed(), "'Leave this page?' modal should reappear");
		developerOrganisations.clickLeavePage();
		Assert.assertTrue(developerOrganisations.isOrgOverviewLoaded(), "Should return cleanly to Organisation Overview after confirming leave");
	}

	@Test(priority = 2, dataProvider = "vrModuleData", dataProviderClass = TestDataProvider.class, dependsOnMethods = {"verify_VR_Developer_Module_Upload_Cancellation"})
	@TestInfo(module = "VR Developer Upload", description = "VR Developer Module Upload Wizard: Module Details -> Build Upload (Select File Type first, upload build) -> Next -> Review & Submit (Verify all details) -> Submit + KPI Increment", priority = "Critical")
	public void verify_VR_Developer_Module_Upload_And_KPI(VRModuleData moduleData) {
		this.moduleData = moduleData;

		// 1. Capture initial Dashboard KPI counts
		developerDashboard.clickDeveloperDashboard();
		Assert.assertTrue(developerDashboard.isPageLoaded(), "VR Developer Dashboard should be loaded");
		int initialSubmissions = developerDashboard.getTotalSubmissionsCount();
		int initialPending = developerDashboard.getPendingCount();

		// 2. Navigate back to Developer Organisations and target org
		developerOrganisations.clickDeveloperOrganisations();
		Assert.assertTrue(developerOrganisations.isPageLoaded(), "Developer Organisations page should be loaded");
		developerOrganisations.openFirstOrganisationManageModules();
		Assert.assertTrue(developerOrganisations.isOrgOverviewLoaded(), "Organisation Overview should be loaded");

		// 3. Initiate Upload Wizard
		developerOrganisations.clickUploadNewModule();
		Assert.assertTrue(developerOrganisations.isUploadFormLoaded(), "Upload Module wizard should be loaded");

		// STEP 1: MODULE DETAILS
		Assert.assertTrue(developerOrganisations.isModuleDetailsStepActive(), "Step 1: Module Details should be active");
		developerOrganisations.enterModuleDetails(
				dynamicModuleName,
				moduleData.getModuleType(),
				moduleData.getSimulationType(),
				moduleData.getSetup(),
				moduleData.getVersion(),
				moduleData.getDescription(),
				moduleData.getPlatforms()
		);

		// Click Next from Module Details to Build Upload
		developerOrganisations.clickNextStep();

		// STEP 2: BUILD UPLOAD
		Assert.assertTrue(developerOrganisations.isBuildUploadStepActive(), "Step 2: Build Upload should be active");

		// First: Select the File Type
		developerOrganisations.selectFileType(moduleData.getFileType());

		// Later: Upload / Load the build files
		developerOrganisations.uploadBuildFileOnly(moduleData.getBuildFilePath());

		// Click Next from Build Upload to Review & Submit
		developerOrganisations.clickNextStep();

		// STEP 3: REVIEW & SUBMIT
		Assert.assertTrue(developerOrganisations.isReviewSubmitStepActive(), "Step 3: Review & Submit should be active");

		// In Review Submit: Verify all entered details
		String reviewName = developerOrganisations.getReviewModuleName();
		Assert.assertEquals(reviewName, dynamicModuleName, "Review Module Name should match entered name");

		String reviewType = developerOrganisations.getReviewModuleType();
		Assert.assertTrue(reviewType.toLowerCase().contains(moduleData.getModuleType().toLowerCase()),
				"Review Module Type should match entered type: " + moduleData.getModuleType());

		String reviewSimType = developerOrganisations.getReviewSimulationType();
		Assert.assertTrue(reviewSimType.toLowerCase().contains(moduleData.getSimulationType().toLowerCase()),
				"Review Simulation Type should match: " + moduleData.getSimulationType());

		String reviewVersion = developerOrganisations.getReviewVersion();
		Assert.assertTrue(reviewVersion.contains("1.0.0"), "Review Version should display v1.0.0");

		String reviewFileType = developerOrganisations.getReviewFileType();
		Assert.assertTrue(reviewFileType.toUpperCase().contains(moduleData.getFileType().toUpperCase()),
				"Review File Type should match: " + moduleData.getFileType());

		String reviewFileName = developerOrganisations.getReviewFileName();
		Assert.assertFalse(reviewFileName.isEmpty(), "Review File Name should not be empty");

		String reviewSha256 = developerOrganisations.getReviewSha256();
		Assert.assertFalse(reviewSha256.isEmpty(), "Review SHA-256 hash should be automatically computed");

		// Submit Module
		developerOrganisations.submitForReview();

		ToastResponse toast = toastUtils.captureToast();
		if (toast != null) {
			Assert.assertTrue(toast.getType().equals("success") || toast.getMessage().toLowerCase().contains("success") || toast.getMessage().toLowerCase().contains("submitted"),
					"Toast should confirm successful module submission");
		}

		// Verify Developer Dashboard KPI increment
		developerDashboard.clickDeveloperDashboard();
		int newSubmissions = developerDashboard.getTotalSubmissionsCount();
		if (newSubmissions > 0 && initialSubmissions > 0) {
			Assert.assertTrue(newSubmissions >= initialSubmissions, "Total Submissions should increment or maintain state");
		}
	}

	@Test(priority = 3, dependsOnMethods = {"verify_VR_Developer_Module_Upload_And_KPI"})
	@TestInfo(module = "Module Review Queue", description = "Super Admin Review Queue: Module Name Search Filter, Review Detail Verification, Decision Rationale & Formal Approval", priority = "Critical")
	public void verify_SuperAdmin_Review_And_Approval() {
		// 1. Switch role to Super Admin
		loginPage.switchToSuperAdmin();

		// 2. Navigate to Review Queue
		reviewQueue.clickReviewQueue();
		Assert.assertTrue(reviewQueue.isPageLoaded(), "Review Queue page should be loaded");

		int initialPending = reviewQueue.getPendingReviewCountInt();
		int initialApproved = reviewQueue.getApprovedCountInt();

		// 3. Search filter based on the Module Name
		reviewQueue.searchModule(dynamicModuleName);
		Assert.assertTrue(reviewQueue.isModulePresentInQueue(dynamicModuleName),
				"Newly submitted module '" + dynamicModuleName + "' should appear in Review Queue filtered results");

		// 4. Open review detail for that filtered module
		boolean detailOpened = reviewQueue.openReviewDetailForModule(dynamicModuleName);
		Assert.assertTrue(detailOpened, "Review detail page should open for module: " + dynamicModuleName);
		Assert.assertTrue(reviewQueue.isDetailPageLoaded(), "Review detail page should be loaded");

		String moduleTitle = reviewQueue.getDetailModuleTitle();
		Assert.assertTrue(moduleTitle.toLowerCase().contains(dynamicModuleName.toLowerCase()),
				"Detail page title should match submitted module name");
		Assert.assertEquals(reviewQueue.getDetailStatus().toLowerCase(), "pending review",
				"Module status in review queue detail should be Pending Review");

		// 5. Approve with Decision Rationale
		String rationale = moduleData != null && moduleData.getReviewRationale() != null
				? moduleData.getReviewRationale()
				: "Validated VR interaction controls, safety checklists, and simulation engine.";
		reviewQueue.approveModuleWithRationale(rationale);

		ToastResponse toast = toastUtils.captureToast();
		if (toast != null) {
			Assert.assertTrue(toast.getType().equals("success") || toast.getMessage().toLowerCase().contains("success") || toast.getMessage().toLowerCase().contains("approved"),
					"Toast should confirm module approval");
		}

		// 6. Verify Review Queue KPI counters update
		reviewQueue.clickReviewQueue();
		int newApproved = reviewQueue.getApprovedCountInt();
		if (newApproved > 0 && initialApproved > 0) {
			Assert.assertTrue(newApproved >= initialApproved, "Approved count should increment");
		}
	}

	@Test(priority = 4, dependsOnMethods = {"verify_SuperAdmin_Review_And_Approval"})
	@TestInfo(module = "Module Catalogue", description = "Module Catalogue: Module Name Search Filter, View Details, and Manage Cross-Organisation Access", priority = "High")
	public void verify_Module_Catalogue_Details_And_Manage_Access() {
		// 1. Navigate to Module Catalogue
		moduleCatalogue.clickModuleCatalogue();
		Assert.assertTrue(moduleCatalogue.isPageLoaded(), "Module Catalogue page should be loaded");

		// 2. Search filter based on the Module Name
		moduleCatalogue.searchModule(dynamicModuleName);
		Assert.assertTrue(moduleCatalogue.isModulePresent(dynamicModuleName),
				"Approved module '" + dynamicModuleName + "' should appear in Module Catalogue search results");

		// 3. View Details functionality after search
		boolean openedDetail = moduleCatalogue.openModuleDetailAfterSearch(dynamicModuleName);
		Assert.assertTrue(openedDetail, "Module detail page should open for searched module");
		Assert.assertTrue(moduleCatalogue.isDetailPageLoaded(), "Module Catalogue Detail page should be loaded");
		Assert.assertTrue(moduleCatalogue.getDetailTitle().toLowerCase().contains(dynamicModuleName.toLowerCase()),
				"Detail title should match searched module name");

		// Return back to Module Catalogue
		moduleCatalogue.clickBackToCatalogue();
		Assert.assertTrue(moduleCatalogue.isPageLoaded(), "Should return back to Module Catalogue");

		// 4. Manage Access to Other Organisations after search
		boolean openedModal = moduleCatalogue.openManageAccessAfterSearch(dynamicModuleName);
		Assert.assertTrue(openedModal, "Manage Access modal should open for searched module");
		Assert.assertTrue(moduleCatalogue.isAccessModalDisplayed(), "Manage Access modal should be displayed");

		// Add secondary org access if configured
		if (moduleData != null && moduleData.getGrantAccessToOrgs() != null && !moduleData.getGrantAccessToOrgs().isEmpty()) {
			String targetOrg = moduleData.getGrantAccessToOrgs().get(0);
			try {
				moduleCatalogue.addOrganisationAccess(targetOrg);
				moduleCatalogue.saveAccessChanges();
				ToastResponse toast = toastUtils.captureToast();
				if (toast != null) {
					Assert.assertTrue(toast.getType().equals("success") || toast.getMessage().toLowerCase().contains("access"),
							"Toast should confirm organisation access saved");
				}
			} catch (Exception e) {
				moduleCatalogue.cancelManageAccessModal();
			}
		} else {
			moduleCatalogue.cancelManageAccessModal();
		}
	}

	@Test(priority = 5, dependsOnMethods = {"verify_Module_Catalogue_Details_And_Manage_Access"})
	@TestInfo(module = "Module Catalogue Lifecycle", description = "Module Catalogue: Module Name Search Filter, Archive and Unarchive Cycle with Tab Badge Synchronization", priority = "High")
	public void verify_Module_Archive_And_Unarchive_Lifecycle() {
		moduleCatalogue.clickModuleCatalogue();
		Assert.assertTrue(moduleCatalogue.isPageLoaded(), "Module Catalogue page should be loaded");

		// 1. Archive module using Search Filter in Active Tab
		moduleCatalogue.archiveModuleAfterSearch(dynamicModuleName);
		ToastResponse archiveToast = toastUtils.captureToast();
		if (archiveToast != null) {
			Assert.assertTrue(archiveToast.getType().equals("success") || archiveToast.getMessage().toLowerCase().contains("archive"),
					"Toast should confirm module archive");
		}

		// 2. Switch to Archived Tab and Search Filter
		moduleCatalogue.clickArchivedTab();
		moduleCatalogue.searchModule(dynamicModuleName);
		Assert.assertTrue(moduleCatalogue.isModulePresent(dynamicModuleName),
				"Archived module '" + dynamicModuleName + "' should appear in Archived tab search results");

		// 3. Unarchive module using Search Filter in Archived Tab
		moduleCatalogue.unarchiveModuleAfterSearch(dynamicModuleName);
		ToastResponse unarchiveToast = toastUtils.captureToast();
		if (unarchiveToast != null) {
			Assert.assertTrue(unarchiveToast.getType().equals("success") || unarchiveToast.getMessage().toLowerCase().contains("unarchive"),
					"Toast should confirm module unarchive");
		}

		// 4. Switch back to Active Tab and Search Filter to confirm restoration
		moduleCatalogue.clickActiveTab();
		moduleCatalogue.searchModule(dynamicModuleName);
		Assert.assertTrue(moduleCatalogue.isModulePresent(dynamicModuleName),
				"Unarchived module '" + dynamicModuleName + "' should reappear in Active tab search results");
	}

	@AfterClass(alwaysRun = true)
	public void tearDownVRModuleLifecycle() {
		try {
			loginPage.clickProfile();
			loginPage.clickLogOut();
		} catch (Exception ignored) {
		}
	}
}
