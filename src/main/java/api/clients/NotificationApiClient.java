package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.pojo.notification.SendNotificationRequest;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class NotificationApiClient {

    private final String baseUrl;

    public NotificationApiClient() {
        this.baseUrl = ApiConfig.getNotificationUrl();
    }

    public Response sendNotification(SendNotificationRequest request) {
        return given(RequestSpecFactory.adminKeySpec())
                .baseUri(baseUrl)
                .body(request)
            .when()
                .post(ApiEndpoints.NOTIFICATIONS_SEND);
    }

    public Response getNotificationHistory(String userId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .queryParam("userId", userId)
            .when()
                .get(ApiEndpoints.NOTIFICATIONS_HISTORY);
    }
}
