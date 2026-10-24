package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GatewayApiClient {

    private final String baseUrl;

    public GatewayApiClient() {
        this.baseUrl = ApiConfig.getGatewayUrl();
    }

    public Response getHealth() {
        return given(RequestSpecFactory.unauthenticatedSpec())
                .baseUri(baseUrl)
            .when()
                .get(ApiEndpoints.GATEWAY_HEALTH);
    }

    public Response getCorsOptions(String endpoint, String origin) {
        return given(RequestSpecFactory.unauthenticatedSpec())
                .baseUri(baseUrl)
                .header("Origin", origin)
            .when()
                .options(endpoint);
    }

    public Response enableMaintenanceMode() {
        return given(RequestSpecFactory.adminKeySpec())
                .baseUri(baseUrl)
            .when()
                .post(ApiEndpoints.GATEWAY_MAINTENANCE_ENABLE);
    }

    public Response disableMaintenanceMode() {
        return given(RequestSpecFactory.adminKeySpec())
                .baseUri(baseUrl)
            .when()
                .post(ApiEndpoints.GATEWAY_MAINTENANCE_DISABLE);
    }

    public Response callWithCustomToken(String endpoint, String customToken) {
        return given(RequestSpecFactory.unauthenticatedSpec())
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + customToken)
            .when()
                .get(endpoint);
    }
}
