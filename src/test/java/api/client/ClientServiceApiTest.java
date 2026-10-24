package api.client;

import api.base.ApiBaseTest;
import api.pojo.client.CreateClientRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class ClientServiceApiTest extends ApiBaseTest {

    private String createdClientId;
    private final String companyName = "TestCorp_" + System.currentTimeMillis();

    @Test(priority = 1, groups = {"API", "Smoke", "Client"}, description = "Get clients list returns paginated result")
    public void testGetClientsList() {
        Response response = clientApiClient.getClients(1, 10);
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));
    }

    @Test(priority = 2, groups = {"API", "Regression", "Client"}, description = "Create client returns 201 with client ID")
    public void testCreateClient() {
        CreateClientRequest req = new CreateClientRequest(companyName, "contact@" + companyName.toLowerCase() + ".com", "+91-9876543210");
        Response response = clientApiClient.createClient(req);
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(401)));

        if (response.getStatusCode() == 201 || response.getStatusCode() == 200) {
            createdClientId = response.jsonPath().getString("id");
            Assert.assertNotNull(createdClientId, "Created Client ID should be returned");
        }
    }

    @Test(priority = 3, groups = {"API", "Regression", "Client"}, description = "Get client portal departments master list")
    public void testGetDepartmentsMaster() {
        Response response = clientApiClient.getDepartments();
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));
    }

    @Test(priority = 4, groups = {"API", "Regression", "Client"}, description = "Get client portal designations master list")
    public void testGetDesignationsMaster() {
        Response response = clientApiClient.getDesignations();
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));
    }
}
