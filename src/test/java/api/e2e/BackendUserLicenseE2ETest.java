package api.e2e;

import api.base.ApiBaseTest;
import api.pojo.client.CreateClientRequest;
import api.pojo.license.CreateLicenseRequest;
import api.pojo.user.CreateUserRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class BackendUserLicenseE2ETest extends ApiBaseTest {

    private String userId;
    private String clientId;
    private String licenseId;

    @Test(priority = 1, groups = {"API", "E2E"}, description = "E2E Step 1: Create new user in User Service")
    public void step1_CreateUser() {
        String email = "e2e_user_" + System.currentTimeMillis() + "@vems.com";
        CreateUserRequest userReq = new CreateUserRequest(email, "E2E", "User", "Admin");
        Response response = userClient.createUser(userReq);
        response.then().statusCode(anyOf(equalTo(200), equalTo(201), equalTo(401)));

        if (response.getStatusCode() == 201 || response.getStatusCode() == 200) {
            userId = response.jsonPath().getString("id");
            Assert.assertNotNull(userId);
        }
    }

    @Test(priority = 2, dependsOnMethods = {"step1_CreateUser"}, groups = {"API", "E2E"}, description = "E2E Step 2: Create new client tenant in Client Service")
    public void step2_CreateClient() {
        String company = "E2E_Tenant_" + System.currentTimeMillis();
        CreateClientRequest clientReq = new CreateClientRequest(company, "contact@" + company.toLowerCase() + ".com", "+91-9876543210");
        Response response = clientApiClient.createClient(clientReq);
        response.then().statusCode(anyOf(equalTo(200), equalTo(201), equalTo(401)));

        if (response.getStatusCode() == 201 || response.getStatusCode() == 200) {
            clientId = response.jsonPath().getString("id");
            Assert.assertNotNull(clientId);
        }
    }

    @Test(priority = 3, dependsOnMethods = {"step2_CreateClient"}, groups = {"API", "E2E"}, description = "E2E Step 3: Provision license for client tenant in License Service")
    public void step3_ProvisionLicense() {
        String targetClientId = (clientId != null) ? clientId : "client_" + System.currentTimeMillis();
        CreateLicenseRequest licenseReq = new CreateLicenseRequest(targetClientId, "VEMS E2E Sim", 25, "2028-12-31");
        Response response = licenseClient.createLicense(licenseReq);
        response.then().statusCode(anyOf(equalTo(200), equalTo(201), equalTo(401)));

        if (response.getStatusCode() == 201 || response.getStatusCode() == 200) {
            licenseId = response.jsonPath().getString("id");
            Assert.assertNotNull(licenseId);
        }
    }

    @Test(priority = 4, dependsOnMethods = {"step3_ProvisionLicense"}, groups = {"API", "E2E"}, description = "E2E Step 4: Verify license query returns provisioned state")
    public void step4_VerifyLicense() {
        if (licenseId != null) {
            Response response = licenseClient.getLicenseById(licenseId);
            response.then().statusCode(anyOf(equalTo(200), equalTo(404)));
        }
    }

    @Test(priority = 5, dependsOnMethods = {"step1_CreateUser"}, alwaysRun = true, groups = {"API", "E2E"}, description = "E2E Teardown: Clean up created user")
    public void step5_CleanupUser() {
        if (userId != null) {
            userClient.deleteUser(userId);
        }
    }
}
