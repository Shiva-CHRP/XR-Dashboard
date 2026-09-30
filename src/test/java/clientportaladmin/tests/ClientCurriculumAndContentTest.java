package clientportaladmin.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;
import superadmin.utils.ConfigReader;

@Listeners(Listener.class)
public class ClientCurriculumAndContentTest extends ClientBaseTest {

	String orgCode = ConfigReader.getClientOrgCode();
	String email = ConfigReader.getClientUsername();
	String password = ConfigReader.getClientPassword();

	@Test(priority = 1)
	@TestInfo(module = "Client Curriculum & Content", description = "Login to Client Portal for Curriculum & Content tests", priority = "Critical")
	public void login_For_Curriculum_And_Content() throws InterruptedException {
		clientLogin.loginToClient(orgCode, email, password);
	}

	@Test(priority = 2, dependsOnMethods = {"login_For_Curriculum_And_Content"})
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Curriculum Section", priority = "High")
	public void testNavigateToCurriculum() {
		curriculum.clickCurriculum();
	}

	@Test(priority = 3, dependsOnMethods = {"login_For_Curriculum_And_Content"})
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Assign Curriculums Section", priority = "High")
	public void testNavigateToAssignCurriculums() {
		assignCurriculums.clickAssignCurriculums();
	}

	@Test(priority = 4, dependsOnMethods = {"login_For_Curriculum_And_Content"})
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Assign Modules Section", priority = "High")
	public void testNavigateToAssignModules() {
		assignModules.clickAssignModules();
	}

	@Test(priority = 5, dependsOnMethods = {"login_For_Curriculum_And_Content"})
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Content Hub Section", priority = "Medium")
	public void testNavigateToContentHub() {
		contentHub.clickContentHub();
	}

	@Test(priority = 6, dependsOnMethods = {"login_For_Curriculum_And_Content"})
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Certificate Templates Section", priority = "Medium")
	public void testNavigateToCertificateTemplates() {
		certificateTemplates.clickCertificateTemplates();
	}
}
