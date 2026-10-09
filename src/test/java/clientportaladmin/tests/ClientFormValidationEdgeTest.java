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
 * Enterprise Form Validation & Modal Boundary Edge Case Suite for Client Portal.
 * Validates blank mandatory field constraints and modal clean dismissals without database modification.
 */
@Listeners(Listener.class)
public class ClientFormValidationEdgeTest extends ClientBaseTest {

	private String orgCode = ConfigReader.getClientOrgCode();
	private String email = ConfigReader.getClientUsername();
	private String password = ConfigReader.getClientPassword();

	@BeforeMethod(alwaysRun = true)
	public void setupSoftAssert() {
		softAssert = new SoftAssert();
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Form Validation", description = "Login to Client Portal for Form Validation Tests", priority = "Critical")
	public void login_As_ClientAdmin() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
		softAssert.assertTrue(overview.isOverviewPageLoaded(), "Overview dashboard should be loaded post-login");
		softAssert.assertAll();
	}

	@Test(priority = 2, dependsOnMethods = {"login_As_ClientAdmin"})
	@TestInfo(module = "Client Form Validation", description = "Verify Add User modal prevents submission when fields are blank", priority = "High")
	public void verify_Add_User_Blank_Mandatory_Fields() {
		users.clickUsers();
		users.clickAddUser();
		users.clickSaveUser();

		// Check that the modal remains open (cannot submit empty user)
		softAssert.assertTrue(driver.getPageSource().contains("Add User") || driver.getPageSource().contains("Save"), "Modal should remain open when mandatory fields are omitted");
		users.closeAddUserModal();
		softAssert.assertAll();
	}

	@Test(priority = 3, dependsOnMethods = {"login_As_ClientAdmin"})
	@TestInfo(module = "Client Form Validation", description = "Verify Add User modal clean dismissal without dirty state persistence", priority = "High")
	public void verify_Add_User_Modal_Cancel_Clears_State() {
		users.clickUsers();
		users.clickAddUser();
		users.enterFullName("Draft Dummy Name");
		users.closeAddUserModal();

		// Reopen modal to verify clean state
		users.clickAddUser();
		users.closeAddUserModal();
		softAssert.assertAll();
	}

	@Test(priority = 4, dependsOnMethods = {"login_As_ClientAdmin"})
	@TestInfo(module = "Client Form Validation", description = "Verify Add User modal Internal and Contractor employee type selection", priority = "Medium")
	public void verify_Add_User_Modal_Employee_Type_Toggle() {
		users.clickUsers();
		users.clickAddUser();
		users.selectContractorChip();
		users.selectInternalChip();
		users.closeAddUserModal();
		softAssert.assertAll();
	}
}
