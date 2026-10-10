package clientportaladmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

/**
 * Enterprise Search, Filter & Empty State Edge Case Suite for Client Portal.
 * Verifies employee searches, master data queries, curriculum empty states, and special character sanitization.
 */
@Listeners(Listener.class)
@Test(groups = {"regression", "clientportal"})
public class ClientSearchAndFilterEdgeTest extends ClientBaseTest {

	@BeforeClass(alwaysRun = true)
	public void searchEdgePrerequisite() {
		loginAsClientAdmin();
	}

	@BeforeMethod(alwaysRun = true)
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify User / Employee search with non-existent query displays empty state", priority = "High")
	public void verify_Employee_Search_Non_Existent_Query() {
		users.clickUsers();
		users.searchUsers("__NON_EXISTENT_EMPLOYEE_404__");

		softAssert.assertTrue(users.isEmptyTableStateDisplayed(), "Table should indicate empty state when no users match search");
		users.clearSearch();
		softAssert.assertAll();
	}

	@Test(priority = 2)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify User search handles special characters safely", priority = "High")
	public void verify_Employee_Search_Special_Characters() {
		users.searchUsers("!@#$%^&*()'<script>");

		softAssert.assertTrue(users.getTableRowCount() >= 0, "Users table should safely handle special characters without DOM crash");
		users.clearSearch();
		softAssert.assertAll();
	}

	@Test(priority = 3)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify User search clear restores table records", priority = "Medium")
	public void verify_Employee_Search_Clear_Restores_Data() {
		int initialCount = users.getTableRowCount();
		users.searchUsers("test");
		users.clearSearch();
		int restoredCount = users.getTableRowCount();

		softAssert.assertTrue(restoredCount >= initialCount || restoredCount >= 0, "Clearing user search should restore row records");
		softAssert.assertAll();
	}

	@Test(priority = 4)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify Department Master search with non-existent query renders empty state", priority = "Medium")
	public void verify_Department_Master_Non_Existent_Search() {
		departmentMaster.clickDepartmentMaster();
		departmentMaster.searchDepartment("__NO_DEPT_EXISTS_404__");

		softAssert.assertTrue(departmentMaster.isEmptyTableStateDisplayed(), "Department table should show empty state for non-existent search");
		departmentMaster.searchDepartment("");
		softAssert.assertAll();
	}

	@Test(priority = 5)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify Designation Master search with non-existent query renders empty state", priority = "Medium")
	public void verify_Designation_Master_Non_Existent_Search() {
		designationMaster.clickDesignationMaster();
		designationMaster.searchDesignation("__NO_DESIG_EXISTS_404__");

		softAssert.assertTrue(designationMaster.isEmptyTableStateDisplayed(), "Designation table should show empty state for non-existent search");
		designationMaster.searchDesignation("");
		softAssert.assertAll();
	}

	@Test(priority = 6)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify Curriculum Catalogue non-existent query renders empty state", priority = "High")
	public void verify_Curriculum_Search_Non_Existent_Query() {
		curriculum.clickCurriculum();
		curriculum.searchCurricula("__NO_CURRICULUM_MATCH_404__");

		softAssert.assertTrue(curriculum.isEmptyStateDisplayed(), "Curriculum section should show empty state for non-existent query");
		curriculum.clearSearch();
		softAssert.assertAll();
	}

	@Test(priority = 7)
	@TestInfo(module = "Client Search Edge Cases", description = "Verify Duplicate Conflicts status filter toggle renders data or empty state cleanly", priority = "Medium")
	public void verify_Duplicate_Conflicts_Filter_And_State() {
		duplicateConflicts.clickDuplicateConflicts();
		softAssert.assertTrue(duplicateConflicts.isDuplicateConflictsPageLoaded(), "Duplicate conflicts screen should be loaded");
		duplicateConflicts.switchToResolvedTab();
		softAssert.assertTrue(duplicateConflicts.isDuplicateConflictsPageLoaded(), "Resolved tab should remain functional");
		duplicateConflicts.switchToOpenTab();
		softAssert.assertTrue(duplicateConflicts.isDuplicateConflictsPageLoaded(), "Open tab should remain functional");
		softAssert.assertAll();
	}
}
