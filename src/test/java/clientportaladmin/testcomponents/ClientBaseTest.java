package clientportaladmin.testcomponents;

import java.io.IOException;

import org.testng.annotations.BeforeClass;

import superadmin.testcomponents.BaseTest;

public class ClientBaseTest extends BaseTest {

	@BeforeClass(alwaysRun = true)
	@Override
	public void setup() throws IOException, InterruptedException {
		initializeEnvironment();
		launchClientPortal();
	}
}
