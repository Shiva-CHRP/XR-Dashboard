package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OfflineApiClient {

    private final String baseUrl;

    public OfflineApiClient() {
        this.baseUrl = ApiConfig.getOfflineUrl();
    }

    public Response triggerSync() {
        return given(RequestSpecFactory.adminKeySpec())
                .baseUri(baseUrl)
            .when()
                .post(ApiEndpoints.OFFLINE_SYNC_TRIGGER);
    }

    public Response getDeviceStatus(String deviceId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .queryParam("deviceId", deviceId)
            .when()
                .get(ApiEndpoints.OFFLINE_DEVICE_STATUS);
    }
}
