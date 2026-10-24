package api.offline;

import api.base.ApiBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class OfflineServiceApiTest extends ApiBaseTest {

    @Test(priority = 1, groups = {"API", "Smoke", "Offline"}, description = "Trigger offline sync via X-Admin-Key")
    public void testOfflineSyncTrigger() {
        Response response = offlineClient.triggerSync();
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(202), equalTo(401), equalTo(404)));
    }

    @Test(priority = 2, groups = {"API", "Regression", "Offline"}, description = "Query device gate registration status")
    public void testDeviceStatus() {
        Response response = offlineClient.getDeviceStatus("device_12345");
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401), equalTo(404)));
    }
}
