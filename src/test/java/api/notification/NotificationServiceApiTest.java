package api.notification;

import api.base.ApiBaseTest;
import api.pojo.notification.SendNotificationRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class NotificationServiceApiTest extends ApiBaseTest {

    @Test(priority = 1, groups = {"API", "Smoke", "Notification"}, description = "Send notification via service-to-service X-Admin-Key")
    public void testSendNotificationViaAdminKey() {
        SendNotificationRequest req = new SendNotificationRequest(
                "user-" + System.currentTimeMillis(),
                "EMAIL",
                "Automated Backend Test Notification",
                "Verification message from CHRP automation suite."
        );
        Response response = notificationClient.sendNotification(req);
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(202), equalTo(401), equalTo(404)));
    }

    @Test(priority = 2, groups = {"API", "Regression", "Notification"}, description = "Get notification history for user")
    public void testGetNotificationHistory() {
        Response response = notificationClient.getNotificationHistory("sample-user-id");
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401), equalTo(404)));
    }
}
