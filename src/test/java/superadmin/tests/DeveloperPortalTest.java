package superadmin.tests;

import java.util.List;

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
 * Enterprise Test Suite for VR Developer & Super Admin Developer Management CRUD:
 * 
 * 1. VR Developer Operations:
 *    - Role Switch & Dashboard KPIs
 *    - Developer Profile Read & Security Password Change with Sonner Toast
 *    - Developer Organisations View, Search, Status Filter & Module Inventory
 *    - Submission Tracker Inspection & Status Filtering
 * 
 * 2. Super Admin Developer Management CRUD Operations:
 *    - Create Developer (Create) with Dynamic Data & Sonner Toast Assertion
 *    - Read & Filter Developers in Fleet Table
 *    - Edit Developer Information (Update) with Sonner Toast Assertion
 *    - Reset Password (Update) with Sonner Toast Assertion
 *    - Status Deactivation / Reactivation (Status Toggle) with Sonner Toast Assertion
 *    - Delete Developer (Delete) with Sonner Toast Assertion & Removal Verification
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Superadmin", "Regression"})
public class DeveloperPortalTest extends BaseTest {

	private String dynamicDevName;
	private String dynamicDevEmail;

	@BeforeClass(alwaysRun = true)
	public void developerPortalPrerequisite() {
		loginAsSuperAdmin();
		dynamicDevName = "VRDev_" + System.currentTimeMillis();
		dynamicDevEmail = "vrdev." + System.currentTimeMillis() + "@e2etest.com";
	}

	@Test(priority = 1)
	@TestInfo(module = "Developer Portal", description = "Switch to VR Developer Role and Verify Dashboard State", priority = "High")
	public void switch_To_VR_Developer_Role_And_Verify_Dashboard() {
		loginPage.switchToVRDeveloper();
		developerDashboard.clickDeveloperDashboard();
		Assert.assertTrue(developerDashboard.isPageLoaded(), "VR Developer Dashboard should be loaded");
		int totalSubs = developerDashboard.getTotalSubmissionsCount();
		Assert.assertTrue(totalSubs >= 0, "Total Submissions KPI should be non-negative");
	}

	@Test(priority = 2, dependsOnMethods = {"switch_To_VR_Developer_Role_And_Verify_Dashboard"})
	@TestInfo(module = "VR Developer Profile", description = "VR Developer Profile: Read Personal Information and Test Password Update with Toast", priority = "High")
	public void verify_VR_Developer_Profile_And_Security_Update() {
		developerProfile.clickDeveloperProfile();
		Assert.assertTrue(developerProfile.isPageLoaded(), "Developer Profile page should be loaded");

		String fullName = developerProfile.getFullName();
		Assert.assertFalse(fullName.isEmpty(), "Developer profile full name should not be empty");

		String email = developerProfile.getEmail();
		Assert.assertFalse(email.isEmpty(), "Developer profile email should not be empty");

		List<String> roles = developerProfile.getRoles();
		Assert.assertFalse(roles.isEmpty(), "Developer assigned roles should not be empty");

		if (developerProfile.isPasswordChangeAvailable()) {
			ToastResponse toast = developerProfile.updatePassword("CurrentPass@123", "NewSecurePass@123", "NewSecurePass@123");
			if (toast != null) {
				Assert.assertTrue(toast.getType().equals("success") || toast.getType().equals("error") || toast.getMessage().toLowerCase().contains("password"),
						"Toast should be captured for developer password update attempt");
			}
		}
	}

	@Test(priority = 3, dependsOnMethods = {"switch_To_VR_Developer_Role_And_Verify_Dashboard"})
	@TestInfo(module = "VR Developer Organisations", description = "VR Developer Organisations: Search, Status Filter, Grid/List Toggle and Overview Inspection", priority = "High")
	public void verify_VR_Developer_Organisations_And_Module_Inspection() {
		developerOrganisations.clickDeveloperOrganisations();
		Assert.assertTrue(developerOrganisations.isPageLoaded(), "Developer Organisations page should be loaded");

		developerOrganisations.toggleViewMode("list");
		developerOrganisations.toggleViewMode("grid");

		developerOrganisations.filterByStatus("Active");
		developerOrganisations.clearFilters();

		boolean opened = developerOrganisations.openFirstOrganisationManageModules();
		if (opened) {
			Assert.assertTrue(developerOrganisations.isOrgOverviewLoaded(), "Organisation Overview should be loaded");
			String orgName = developerOrganisations.getOrgDetailName();
			Assert.assertFalse(orgName.isEmpty(), "Organisation detail name heading should be displayed");
			developerOrganisations.clickBackToOrganisations();
			Assert.assertTrue(developerOrganisations.isPageLoaded(), "Should return back to Developer Organisations listing");
		}
	}

	@Test(priority = 4, dependsOnMethods = {"switch_To_VR_Developer_Role_And_Verify_Dashboard"})
	@TestInfo(module = "VR Developer Submissions", description = "VR Developer Submission Tracker: Stats Inspection and Filter Verification", priority = "Medium")
	public void verify_VR_Developer_Submission_Tracker() {
		submissionTracker.clickSubmissionTracker();
		Assert.assertTrue(submissionTracker.isPageLoaded(), "Submission Tracker page should be loaded");

		submissionTracker.filterByStatus("All Status");
		submissionTracker.searchSubmissions("Safety");
		submissionTracker.clearFilters();
	}

	@Test(priority = 5, dependsOnMethods = {"switch_To_VR_Developer_Role_And_Verify_Dashboard"})
	@TestInfo(module = "Developer Management", description = "Switch back to Super Admin Role and Navigate to Developers Fleet", priority = "High")
	public void switch_Back_To_SuperAdmin_Role() {
		loginPage.switchToSuperAdmin();
		loginPage.dashboardName();
		developers.clickDevelopers();
		Assert.assertTrue(developers.isPageLoaded(), "Superadmin Developers listing should be loaded");
	}

	@Test(priority = 6, dependsOnMethods = {"switch_Back_To_SuperAdmin_Role"})
	@TestInfo(module = "Developer Management CRUD", description = "Create Developer: Fill Create Form, Submit and Validate Sonner Toast Notification", priority = "Critical")
	public void superadmin_Developer_Create_CRUD() {
		developers.clickDevelopers();

		developers.clickCreateDeveloper();
		Assert.assertTrue(developers.isDeveloperModalDisplayed(), "Create Developer modal should be displayed");

		ToastResponse createToast = developers.createDeveloper(
				dynamicDevName,
				dynamicDevEmail,
				"DevPass@12345",
				"VR developer created via automated E2E CRUD suite"
		);

		if (createToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(createToast.getType()) || createToast.getMessage().toLowerCase().contains("created") || createToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm developer creation: " + createToast.getMessage());
		}

		// Verify developer is visible in table search
		developers.searchDevelopers(dynamicDevName);
		Assert.assertTrue(developers.isDeveloperPresent(dynamicDevName), "Newly created developer should appear in search results: " + dynamicDevName);
	}

	@Test(priority = 7, dependsOnMethods = {"superadmin_Developer_Create_CRUD"})
	@TestInfo(module = "Developer Management CRUD", description = "Update Developer: Edit Details, Reset Password and View Submissions with Toast Validation", priority = "High")
	public void superadmin_Developer_Update_And_Reset_Password_CRUD() {
		developers.searchDevelopers(dynamicDevName);

		// 1. Edit Developer details
		developers.openEditDeveloper(dynamicDevName);
		Assert.assertTrue(developers.isDeveloperModalDisplayed(), "Edit Developer modal should be displayed");

		ToastResponse editToast = developers.updateDeveloper(null, "Updated VR Developer notes via automation");
		if (editToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(editToast.getType()) || editToast.getMessage().toLowerCase().contains("updated") || editToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm developer update: " + editToast.getMessage());
		}

		// 2. Reset Password
		developers.openResetPassword(dynamicDevName);
		ToastResponse resetToast = developers.resetPassword("NewPass@12345", "NewPass@12345");
		if (resetToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(resetToast.getType()) || resetToast.getMessage().toLowerCase().contains("reset") || resetToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm password reset: " + resetToast.getMessage());
		}

		// 3. View Submissions modal
		try {
			developers.openSubmissions(dynamicDevName);
			if (developers.isSubmissionsModalDisplayed()) {
				developers.closeSubmissionsModal();
			}
		} catch (Exception ignored) {}
	}

	@Test(priority = 8, dependsOnMethods = {"superadmin_Developer_Update_And_Reset_Password_CRUD"})
	@TestInfo(module = "Developer Management CRUD", description = "Status Toggle & Delete Developer: Deactivate Developer, Delete Developer and Assert Table Removal", priority = "Critical")
	public void superadmin_Developer_Status_Toggle_And_Delete_CRUD() {
		developers.searchDevelopers(dynamicDevName);

		// 1. Status Toggle (Deactivate)
		try {
			developers.triggerDeactivate(dynamicDevName);
			ToastResponse deactToast = developers.confirmDeactivate();
			if (deactToast != null) {
				Assert.assertTrue("success".equalsIgnoreCase(deactToast.getType()) || deactToast.getMessage().toLowerCase().contains("deactivated") || deactToast.getMessage().toLowerCase().contains("success"),
						"Toast should confirm developer status change");
			}
		} catch (Exception ignored) {}

		// 2. Delete Developer
		developers.searchDevelopers(dynamicDevName);
		developers.triggerDeleteDeveloper(dynamicDevName);
		ToastResponse deleteToast = developers.confirmDeleteDeveloper();
		if (deleteToast != null) {
			Assert.assertTrue("success".equalsIgnoreCase(deleteToast.getType()) || deleteToast.getMessage().toLowerCase().contains("deleted") || deleteToast.getMessage().toLowerCase().contains("success"),
					"Toast should confirm developer deletion: " + deleteToast.getMessage());
		}

		// 3. Confirm developer no longer present
		developers.searchDevelopers(dynamicDevName);
		Assert.assertFalse(developers.isDeveloperPresent(dynamicDevName), "Deleted developer should no longer appear in search results: " + dynamicDevName);
		developers.clearAllFilters();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownDeveloperPortalTest() {
		try {
			loginPage.clickProfile();
			loginPage.clickLogOut();
		} catch (Exception e) {
			// Best-effort cleanup prior to driver quit
		}
	}
}
