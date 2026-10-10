package superadmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.testcomponents.BaseTest;

@Listeners(Listener.class)
@Test(groups = {"e2e", "superadmin", "regression"})
public class OrganizationTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void organizationTestPrerequisite() {
		loginAsSuperAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Organizations", description = "Navigate to Organizations Page", priority = "High")
	public void navigate_To_Organizations() {
		organizationPage.clickOrganizations();
	}

	@Test(priority = 2, dependsOnMethods = {"navigate_To_Organizations"})
	@TestInfo(module = "Organizations", description = "Search Organization in the list", priority = "Medium")
	public void search_Organization() {
		organizationPage.searchOrganization("Test");
	}

	@Test(priority = 3, dependsOnMethods = {"navigate_To_Organizations"})
	@TestInfo(module = "Organizations", description = "Open and Cancel Add Organization Modal", priority = "Medium")
	public void verify_Add_Organization_Modal() {
		organizationPage.clickAddOrganization();
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

	@Test(priority = 5)
	@TestInfo(module = "Organizations", description = "Logout after Organization tests", priority = "Low")
	public void logout_After_Tests() throws InterruptedException {
		loginPage.clickProfile();
		loginPage.clickLogOut();
	}
}
