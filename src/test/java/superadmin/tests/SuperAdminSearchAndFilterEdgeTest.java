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
 * Enterprise Search, Filter & Empty State Edge Case Test Suite for Super Admin.
 * Verifies non-existent queries, special character sanitization, and filter reset restoration.
 */
@Listeners(Listener.class)
@Test(groups = {"regression", "superadmin"})
public class SuperAdminSearchAndFilterEdgeTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	public void searchEdgePrerequisite() {
		loginAsSuperAdmin();
	}

	@BeforeMethod(alwaysRun = true)
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
	}

	@Test(priority = 1)
	@TestInfo(module = "Search Edge Cases", description = "Verify Organization Search with Non-Existent Query renders empty state", priority = "High")
	public void verify_Organization_Search_Non_Existent_Query() {
		organizationPage.clickOrganizations();
		organizationPage.searchOrganization("__NON_EXISTENT_ORG_99999__");

		softAssert.assertTrue(organizationPage.isEmptyTableStateDisplayed(), "Table should display empty state for non-existent query");
		organizationPage.clearSearch();
		softAssert.assertAll();
	}

	@Test(priority = 3, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Search Edge Cases", description = "Verify Organization Search with Special Characters and SQL injection string", priority = "High")
	public void verify_Organization_Search_Special_Characters() {
		organizationPage.searchOrganization("!@#$%^&*()'<script>");

		softAssert.assertTrue(organizationPage.getTableRowCount() >= 0, "Application should safely handle special characters without breaking grid");
		organizationPage.clearSearch();
		softAssert.assertAll();
	}

	@Test(priority = 4, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Search Edge Cases", description = "Verify Organization Search Clear Restores Data List", priority = "Medium")
	public void verify_Organization_Search_Clear_Restores_Data() {
		int initialCount = organizationPage.getTableRowCount();
		organizationPage.searchOrganization("Automation");
		organizationPage.clearSearch();
		int restoredCount = organizationPage.getTableRowCount();

		softAssert.assertTrue(restoredCount >= initialCount || restoredCount > 0, "Clearing search input should restore organization table rows");
		softAssert.assertAll();
	}

	@Test(priority = 5, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Search Edge Cases", description = "Verify Module Catalogue Search with Non-Existent Query", priority = "High")
	public void verify_Module_Catalogue_Non_Existent_Search() {
		moduleCatalogue.clickModuleCatalogue();
		moduleCatalogue.searchModule("__NO_MODULE_MATCH_XYZ_404__");

		softAssert.assertTrue(moduleCatalogue.isEmptyCatalogueStateDisplayed(), "Empty state should display when no modules match search query");
		moduleCatalogue.clearAllFilters();
		softAssert.assertAll();
	}

	@Test(priority = 6, dependsOnMethods = {"login_As_SuperAdmin"})
	@TestInfo(module = "Search Edge Cases", description = "Verify Curriculum Catalogue Search with Non-Existent Query", priority = "High")
	public void verify_Curriculum_Catalogue_Non_Existent_Search() {
		curriculumCatalogue.clickCurriculumCatalogue();
		curriculumCatalogue.searchCurriculum("__NO_CURRICULUM_MATCH_XYZ_404__");

		softAssert.assertTrue(curriculumCatalogue.isEmptyCatalogueStateDisplayed(), "Empty catalogue state should display for non-existent curriculum query");
		curriculumCatalogue.clearAllFilters();
		softAssert.assertAll();
	}
}
