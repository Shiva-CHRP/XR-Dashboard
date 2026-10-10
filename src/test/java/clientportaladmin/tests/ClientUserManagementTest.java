package clientportaladmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;

@Listeners(Listener.class)
@Test(groups = {"E2E", "Clientportal", "Regression"})
public class ClientUserManagementTest extends ClientBaseTest {

	@BeforeClass(alwaysRun = true)
	public void userManagementPrerequisite() {
		loginAsClientAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Client User Management", description = "Navigate to Employees Section", priority = "High")
	public void testNavigateToEmployees() {
		users.clickEmployees();
	}

	@Test(priority = 2)
	@TestInfo(module = "Client User Management", description = "Navigate to Department Master", priority = "Medium")
	public void testNavigateToDepartmentMaster() {
		departmentMaster.clickDepartmentMaster();
	}

	@Test(priority = 3)
	@TestInfo(module = "Client User Management", description = "Navigate to Designation Master", priority = "Medium")
	public void testNavigateToDesignationMaster() {
		designationMaster.clickDesignationMaster();
	}

	@Test(priority = 4)
	@TestInfo(module = "Client User Management", description = "Navigate to Contractor Master", priority = "Medium")
	public void testNavigateToContractorMaster() {
		contractorMaster.clickContractorMaster();
	}
}
