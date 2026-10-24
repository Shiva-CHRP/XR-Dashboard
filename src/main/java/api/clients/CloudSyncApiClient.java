package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CloudSyncApiClient {

    private final String baseUrl;

    public CloudSyncApiClient() {
        this.baseUrl = ApiConfig.getCloudSyncUrl();
    }

    public Response triggerSync() {
        return given(RequestSpecFactory.adminKeySpec())
                .baseUri(baseUrl)
            .when()
                .post(ApiEndpoints.CLOUDSYNC_TRIGGER);
    }

    public Response getSyncStatus() {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
            .when()
                .get(ApiEndpoints.CLOUDSYNC_STATUS);
    }
}
