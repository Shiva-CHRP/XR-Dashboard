package superadmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;
import superadmin.utils.ConfigReader;

/**
 * Enterprise Form Validation & Modal Dirty State Edge Case Suite for Super Admin.
 * Validates mandatory field constraints and clean modal dismissal without polluting persistent data.
 */
@Listeners(Listener.class)
@Test(groups = {"regression", "superadmin"})
public class SuperAdminFormValidationEdgeTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void formValidationPrerequisite() {
		loginAsSuperAdmin();
	}

	@BeforeMethod(alwaysRun = true)
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
	}

	@Test(priority = 1)
	@TestInfo(module = "Form Validation Edge Cases", description = "Verify Add Organization wizard blocks submission when mandatory fields are omitted", priority = "High")
	public void verify_Add_Organization_Blank_Mandatory_Fields() {
		organizationPage.clickOrganizations();
		organizationPage.clickAddOrganization();

		try {
			organizationPage.clickWizardNext();
		} catch (Exception ignored) {
		}

		softAssert.assertTrue(organizationPage.isAddOrganizationModalDisplayed(), "Modal should remain open and prevent progression when mandatory fields are omitted");
		organizationPage.cancelOrganizationCreation();
		softAssert.assertAll();
	}

	@Test(priority = 3, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Form Validation Edge Cases", description = "Verify Add Organization Modal Cancel cleanly dismisses dirty state", priority = "High")
	public void verify_Add_Organization_Modal_Cancel_Clears_State() {
		organizationPage.clickAddOrganization();
		organizationPage.enterOrganizationName("Draft Temp Dirty Org");
		organizationPage.enterAdminFullName("Draft Temp Admin");
		organizationPage.cancelOrganizationCreation();

		softAssert.assertFalse(organizationPage.isAddOrganizationModalDisplayed(), "Modal should be cleanly dismissed after clicking Cancel");
		softAssert.assertAll();
	}
}
