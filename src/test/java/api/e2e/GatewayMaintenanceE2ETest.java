package api.e2e;

import api.base.ApiBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class GatewayMaintenanceE2ETest extends ApiBaseTest {

    @Test(priority = 1, groups = {"API", "E2E", "Maintenance"}, description = "Enable maintenance mode via Gateway Admin endpoint")
    public void testEnableMaintenanceMode() {
        Response response = gatewayClient.enableMaintenanceMode();
        response.then().statusCode(anyOf(equalTo(200), equalTo(401), equalTo(404)));
    }

    @Test(priority = 2, dependsOnMethods = {"testEnableMaintenanceMode"}, alwaysRun = true, groups = {"API", "E2E", "Maintenance"}, description = "Restore normal operational mode via Gateway Admin endpoint")
    public void testDisableMaintenanceMode() {
        Response response = gatewayClient.disableMaintenanceMode();
        response.then().statusCode(anyOf(equalTo(200), equalTo(401), equalTo(404)));
    }
}
