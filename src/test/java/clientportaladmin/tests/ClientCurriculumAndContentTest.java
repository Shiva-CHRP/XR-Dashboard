package clientportaladmin.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import clientportaladmin.testcomponents.ClientBaseTest;
import superadmin.annotations.TestInfo;
import superadmin.listener.Listener;

@Listeners(Listener.class)
@Test(groups = {"E2E", "Clientportal", "Regression"})
public class ClientCurriculumAndContentTest extends ClientBaseTest {

	@BeforeClass(alwaysRun = true)
	public void curriculumPrerequisite() {
		loginAsClientAdmin();
	}

	@Test(priority = 1)
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Curriculum Section", priority = "High")
	public void testNavigateToCurriculum() {
		curriculum.clickCurriculum();
	}

	@Test(priority = 2)
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Assign Curriculums Section", priority = "High")
	public void testNavigateToAssignCurriculums() {
		assignCurriculums.clickAssignCurriculums();
	}

	@Test(priority = 3)
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Assign Modules Section", priority = "High")
	public void testNavigateToAssignModules() {
		assignModules.clickAssignModules();
	}

	@Test(priority = 4)
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Content Hub Section", priority = "Medium")
	public void testNavigateToContentHub() {
		contentHub.clickContentHub();
	}

	@Test(priority = 5)
	@TestInfo(module = "Client Curriculum & Content", description = "Navigate to Certificate Templates Section", priority = "Medium")
	public void testNavigateToCertificateTemplates() {
		certificateTemplates.clickCertificateTemplates();
	}
}
