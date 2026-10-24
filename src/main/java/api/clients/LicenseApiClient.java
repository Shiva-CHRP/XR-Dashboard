package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.pojo.license.CreateLicenseRequest;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LicenseApiClient {

    private final String baseUrl;

    public LicenseApiClient() {
        this.baseUrl = ApiConfig.getLicenseUrl();
    }

    public Response createLicense(CreateLicenseRequest request) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .body(request)
            .when()
                .post(ApiEndpoints.LICENSES);
    }

    public Response getLicenseById(String licenseId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .pathParam("id", licenseId)
            .when()
                .get(ApiEndpoints.LICENSES_ID);
    }

    public Response revokeLicense(String licenseId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .pathParam("id", licenseId)
            .when()
                .patch(ApiEndpoints.LICENSES_REVOKE);
    }

    public Response getLicensesByClientId(String clientId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .queryParam("clientId", clientId)
            .when()
                .get(ApiEndpoints.LICENSES);
    }
}
