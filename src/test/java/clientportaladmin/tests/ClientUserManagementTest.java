package clientportaladmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class ClientUserManagementTest extends ClientBaseTest {

	String orgCode = ConfigReader.getClientOrgCode();
	String email = ConfigReader.getClientUsername();
	String password = ConfigReader.getClientPassword();

	@Test(priority = 1)
	@TestInfo(module = "Client User Management", description = "Login to Client Portal for User Management tests", priority = "Critical")
	public void login_For_User_Management() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
	}

	@Test(priority = 2, dependsOnMethods = {"login_For_User_Management"})
	@TestInfo(module = "Client User Management", description = "Navigate to Employees Section", priority = "High")
	public void testNavigateToEmployees() {
		users.clickEmployees();
	}

	@Test(priority = 3, dependsOnMethods = {"login_For_User_Management"})
	@TestInfo(module = "Client User Management", description = "Navigate to Department Master", priority = "Medium")
	public void testNavigateToDepartmentMaster() {
		departmentMaster.clickDepartmentMaster();
	}

	@Test(priority = 4, dependsOnMethods = {"login_For_User_Management"})
	@TestInfo(module = "Client User Management", description = "Navigate to Designation Master", priority = "Medium")
	public void testNavigateToDesignationMaster() {
		designationMaster.clickDesignationMaster();
	}

	@Test(priority = 5, dependsOnMethods = {"login_For_User_Management"})
	@TestInfo(module = "Client User Management", description = "Navigate to Contractor Master", priority = "Medium")
	public void testNavigateToContractorMaster() {
		contractorMaster.clickContractorMaster();
	}
}
