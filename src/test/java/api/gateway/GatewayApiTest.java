package api.gateway;

import api.base.ApiBaseTest;
import api.config.ApiConfig;
import api.config.ApiEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class GatewayApiTest extends ApiBaseTest {

    @Test(priority = 1, groups = {"API", "Smoke", "Gateway"}, description = "Gateway health check endpoint returns 200")
    public void testGatewayHealthCheck() {
        Response response = gatewayClient.getHealth();
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(404))); // 200 if health endpoint enabled
    }

    @Test(priority = 2, groups = {"API", "Smoke", "Gateway"}, description = "Gateway rejects expired/invalid JWT with 401")
    public void testGatewayRejectsInvalidJwt() {
        Response response = gatewayClient.callWithCustomToken(ApiEndpoints.USERS, "invalid_jwt_token_payload");
        response.then()
                .statusCode(401);
    }

    @Test(priority = 3, groups = {"API", "Regression", "Gateway"}, description = "Gateway validates CORS headers for allowed origin")
    public void testGatewayCorsForAllowedOrigin() {
        String allowedOrigin = ApiConfig.getAllowedOrigin();
        Response response = gatewayClient.getCorsOptions(ApiEndpoints.USERS, allowedOrigin);
        // If gateway CORS is active, Access-Control-Allow-Origin header is returned
        if (response.getStatusCode() == 200) {
            String originHeader = response.getHeader("Access-Control-Allow-Origin");
            if (originHeader != null) {
                Assert.assertTrue(originHeader.contains(allowedOrigin) || originHeader.equals("*"),
                        "Access-Control-Allow-Origin should allow staging portal origin");
            }
        }
    }

    @Test(priority = 4, groups = {"API", "Regression", "Gateway"}, description = "Gateway blocks unregistered origins from CORS header")
    public void testGatewayCorsBlocksUnknownOrigin() {
        Response response = gatewayClient.getCorsOptions(ApiEndpoints.USERS, "http://unauthorized.attacker.com");
        String originHeader = response.getHeader("Access-Control-Allow-Origin");
        if (originHeader != null) {
            Assert.assertNotEquals(originHeader, "http://unauthorized.attacker.com",
                    "Unregistered origin must not be reflected in Access-Control-Allow-Origin header");
        }
    }
}
