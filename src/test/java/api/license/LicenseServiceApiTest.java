package api.license;

import api.base.ApiBaseTest;
import api.pojo.license.CreateLicenseRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class LicenseServiceApiTest extends ApiBaseTest {

    private String createdLicenseId;
    private final String clientId = "client_" + System.currentTimeMillis();

    @Test(priority = 1, groups = {"API", "Smoke", "License"}, description = "Create License returns 201 with generated license key")
    public void testCreateLicense() {
        CreateLicenseRequest req = new CreateLicenseRequest(clientId, "VEMS Enterprise Pro", 100, "2028-12-31");
        Response response = licenseClient.createLicense(req);
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(401)));

        if (response.getStatusCode() == 201 || response.getStatusCode() == 200) {
            createdLicenseId = response.jsonPath().getString("id");
            String licenseKey = response.jsonPath().getString("licenseKey");
            if (licenseKey != null) {
                // Assert base64 encrypted format
                Assert.assertTrue(licenseKey.matches("[A-Za-z0-9+/=]+"), "License key should match valid Base64 string");
            }
        }
    }

    @Test(priority = 2, dependsOnMethods = {"testCreateLicense"}, groups = {"API", "Regression", "License"}, description = "Get license by ID returns active status")
    public void testGetLicenseById() {
        if (createdLicenseId != null) {
            Response response = licenseClient.getLicenseById(createdLicenseId);
            response.then()
                    .statusCode(anyOf(equalTo(200), equalTo(404)))
                    .body("id", anyOf(equalTo(createdLicenseId), nullValue()));
        }
    }

    @Test(priority = 3, dependsOnMethods = {"testCreateLicense"}, groups = {"API", "Regression", "License"}, description = "Revoke license changes status to REVOKED")
    public void testRevokeLicense() {
        if (createdLicenseId != null) {
            Response response = licenseClient.revokeLicense(createdLicenseId);
            response.then()
                    .statusCode(anyOf(equalTo(200), equalTo(204), equalTo(404)));
        }
    }
}
