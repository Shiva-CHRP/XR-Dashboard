package superadmin.testcomponents;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.DataProvider;

import clientportaladmin.pojo.ClientEventData;
import clientportaladmin.pojo.ClientMasterData;
import clientportaladmin.pojo.ClientSupportTicketData;
import clientportaladmin.pojo.ClientUserData;
import clientportaladmin.pojo.MDMDeviceData;
import superadmin.pojo.OrgApplicationData;
import superadmin.pojo.OrganizationData;
import superadmin.pojo.VRModuleData;
import superadmin.utils.JsonReader;

/**
 * Centralized TestNG DataProvider repository for CHRP Simulation Platform.
 * Loads test datasets from JSON files into strongly typed POJO models
 * for data-driven test execution.
 */
public class TestDataProvider {

	@DataProvider(name = "organizationData")
	public static Object[][] getOrganizationData() throws IOException {
		List<OrganizationData> list = JsonReader.getOrganizationData("src/main/resources/testdata/organizationData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}

	@DataProvider(name = "orgApplicationData")
	public static Object[][] getOrgApplicationData() throws IOException {
		List<OrgApplicationData> list = JsonReader.getOrgApplicationData("src/main/resources/testdata/orgApplicationData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}

	@DataProvider(name = "vrModuleData")
	public static Object[][] getVRModuleData() throws IOException {
		List<VRModuleData> list = JsonReader.getVRModuleData("src/main/resources/testdata/vrModuleData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}

	@DataProvider(name = "clientMasterData")
	public static Object[][] getClientMasterData() throws IOException {
		List<ClientMasterData> list = JsonReader.getClientMasterData("src/main/resources/testdata/clientMasterData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}

	@DataProvider(name = "clientUserData")
	public static Object[][] getClientUserData() throws IOException {
		List<ClientUserData> list = JsonReader.getClientUserData("src/main/resources/testdata/clientUserData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}

	@DataProvider(name = "clientEventData")
	public static Object[][] getClientEventData() throws IOException {
		List<ClientEventData> list = JsonReader.getClientEventData("src/main/resources/testdata/clientEventData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}

	@DataProvider(name = "mdmDeviceData")
	public static Object[][] getMdmDeviceData() throws IOException {
		List<MDMDeviceData> list = JsonReader.getMdmDeviceData("src/main/resources/testdata/clientMdmDeviceData.json");
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}
}
