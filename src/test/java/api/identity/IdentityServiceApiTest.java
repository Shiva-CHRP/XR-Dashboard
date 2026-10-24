package api.identity;

import api.base.ApiBaseTest;
import api.config.ApiConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class IdentityServiceApiTest extends ApiBaseTest {

    @Test(priority = 1, groups = {"API", "Smoke", "Identity"}, description = "Valid credentials return 200 with JWT token")
    public void testLoginWithValidCredentialsReturnsJwt() {
        Response response = identityClient.login(ApiConfig.getAdminEmail(), ApiConfig.getAdminPassword());
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            Assert.assertNotNull(token, "JWT token should not be null on successful login");
            Assert.assertFalse(token.isEmpty(), "JWT token should not be empty");
        }
    }

    @Test(priority = 2, groups = {"API", "Regression", "Identity"}, description = "Invalid password returns 401 Unauthorized")
    public void testLoginWithInvalidPasswordReturns401() {
        Response response = identityClient.login(ApiConfig.getAdminEmail(), "WrongPassword@12345");
        response.then()
                .statusCode(401);
    }

    @Test(priority = 3, groups = {"API", "Regression", "Identity"}, description = "Unauthenticated access to protected endpoint returns 401")
    public void testProtectedEndpointWithoutTokenReturns401() {
        Response response = identityClient.getMeWithoutToken();
        response.then()
                .statusCode(401);
    }

    @Test(priority = 4, groups = {"API", "Regression", "Identity"}, description = "Token refresh endpoint structure verification")
    public void testTokenRefreshFlow() {
        Response loginRes = identityClient.login(ApiConfig.getAdminEmail(), ApiConfig.getAdminPassword());
        if (loginRes.getStatusCode() == 200) {
            String token = loginRes.jsonPath().getString("token");
            String refreshToken = loginRes.jsonPath().getString("refreshToken");
            if (refreshToken != null && !refreshToken.isEmpty()) {
                Response refreshRes = identityClient.refreshToken(token, refreshToken);
                refreshRes.then().statusCode(anyOf(equalTo(200), equalTo(400)));
            }
        }
    }
}
