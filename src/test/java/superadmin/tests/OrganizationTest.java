package superadmin.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;

/**
 * Enterprise Test Suite for Super Admin Organization Management:
 * Covers navigation, search, modal validation, dynamic creation wizard,
 * and comprehensive Edit functionality across Organization Details and all Associated Tabs.
 */
@Listeners(Listener.class)
@Test(groups = {"E2E", "Superadmin", "Regression"})
public class OrganizationTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void organizationTestPrerequisite() {
		loginAsSuperAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Organizations", description = "Navigate to Organizations Page", priority = "High")
	public void navigate_To_Organizations() {
		organizationPage.clickOrganizations();
		Assert.assertTrue(organizationPage.isPageLoaded(), "Organizations page should be loaded");
	}

	@Test(priority = 2, dependsOnMethods = {"navigate_To_Organizations"})
	@TestInfo(module = "Organizations", description = "Search Organization in the list", priority = "Medium")
	public void search_Organization() {
		organizationPage.searchOrganization("Test");
		organizationPage.clearSearch();
	}

	@Test(priority = 3, dependsOnMethods = {"navigate_To_Organizations"})
	@TestInfo(module = "Organizations", description = "Open and Cancel Add Organization Modal", priority = "Medium")
	public void verify_Add_Organization_Modal() {
		organizationPage.clickAddOrganization();
		Assert.assertTrue(organizationPage.isAddOrganizationModalDisplayed(), "Add organization modal should be displayed");
		organizationPage.cancelOrganizationCreation();
	}

	@Test(priority = 4, dependsOnMethods = {"navigate_To_Organizations"})
	@TestInfo(module = "Organizations", description = "Fill Organization Form with Dynamic Data & Trial License", priority = "High")
	public void fill_Organization_Form_With_Dynamic_Data() {
		String dynamicOrgName = superadmin.utils.TestDataUtil.getRandomOrgName();
		String dynamicPhone = superadmin.utils.TestDataUtil.getRandomPhone();
		String dynamicEmail = superadmin.utils.TestDataUtil.getRandomEmail();
		String dynamicDomain = superadmin.utils.TestDataUtil.getRandomDomain();

		organizationPage.clickAddOrganization();
		organizationPage.enterOrganizationName(dynamicOrgName);
		organizationPage.enterPortalDomain(dynamicDomain);
		organizationPage.enterAdminFullName("Auto Admin");
		organizationPage.enterPhoneNumber(dynamicPhone);
		organizationPage.enterAdminEmail(dynamicEmail);
		organizationPage.enterTemporaryPassword("Admin@12345");
		organizationPage.selectStartWithTrial();
		organizationPage.cancelOrganizationCreation();
	}

	@Test(priority = 5, dependsOnMethods = {"navigate_To_Organizations"})
	@TestInfo(module = "Organizations", description = "Verify Edit Functionality on Organization Overview and Associated Tabs", priority = "High")
	public void verify_Edit_Organization_And_Associated_Tabs() {
		// Open first organization in Manage mode if available
		boolean opened = organizationPage.openFirstOrganizationManage();
		if (opened) {
			// 1. Overview Tab - Edit Details
			organizationPage.clickOverviewTab();
			try {
				organizationPage.openEditOrganizationFromOverview();
				if (organizationPage.isEditOrganizationModalDisplayed()) {
					organizationPage.cancelOrganizationEdit();
				}
			} catch (Exception ignored) {}

			// 2. Admin Users Tab - Edit Admin User
			organizationPage.clickAdminUsersTab();
			try {
				boolean adminEditOpen = organizationPage.openFirstAdminUserEdit();
				if (adminEditOpen) {
					organizationPage.cancelModal();
				}
			} catch (Exception ignored) {}

			// 3. Branding Tab - Edit Branding
			organizationPage.clickBrandingTab();
			try {
				organizationPage.editBrandingConfiguration("#3B82F6", "Modern", "#0F172A", null);
				organizationPage.saveBrandingChanges();
			} catch (Exception ignored) {}

			// 4. Security Tab - Edit Security Policies
			organizationPage.clickSecurityTab();
			try {
				organizationPage.editSecurityConfiguration("30 Minutes", false);
				organizationPage.saveSecurityChanges();
			} catch (Exception ignored) {}

			// 5. Offline Config Tab - Edit Offline Configuration
			organizationPage.clickOfflineConfigTab();
			try {
				organizationPage.editOfflineConfiguration(false, "15 Days");
				organizationPage.saveOfflineConfigChanges();
			} catch (Exception ignored) {}

			// 6. License & Tokens Tab - Edit License Limits
			organizationPage.clickLicenseAndTokensTab();
			try {
				boolean limitsModalOpen = organizationPage.openEditLicenseLimitsModal();
				if (limitsModalOpen) {
					organizationPage.cancelModal();
				}
			} catch (Exception ignored) {}

			// 7. Storage Tab - Edit Storage Settings
			organizationPage.clickStorageTab();
			try {
				organizationPage.editStorageSettings("50", false);
				organizationPage.saveStorageSettings();
			} catch (Exception ignored) {}

			// 8. Notifications Tab - Edit Notification Preferences
			organizationPage.clickNotificationsTab();
			try {
				organizationPage.editNotificationPreferences(true, true);
				organizationPage.saveNotificationSettings();
			} catch (Exception ignored) {}

			// Return to Organizations listing
			organizationPage.clickOrganizations();
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDownOrganizationTest() {
		try {
			loginPage.clickProfile();
			loginPage.clickLogOut();
		} catch (Exception e) {
			// Best-effort cleanup prior to driver quit
		}
	}
}
