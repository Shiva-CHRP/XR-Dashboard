package api.auth;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.pojo.auth.LoginRequest;
import api.pojo.auth.LoginResponse;
import api.spec.RequestSpecFactory;

import static io.restassured.RestAssured.given;

/**
 * AuthHelper - Thread-safe acquisition and caching of JWT tokens from Identity Service.
 */
public class AuthHelper {

    private static volatile String cachedToken = null;
    private static volatile long tokenExpiryMillis = 0;

    private AuthHelper() {}

    /**
     * Retrieves a valid cached JWT token or requests a new one from Identity Service.
     */
    public static synchronized String getValidJwtToken() {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiryMillis) {
            return cachedToken;
        }

        LoginRequest loginReq = new LoginRequest(ApiConfig.getAdminEmail(), ApiConfig.getAdminPassword());

        try {
            LoginResponse response = given(RequestSpecFactory.unauthenticatedSpec())
                    .baseUri(ApiConfig.getIdentityUrl())
                    .body(loginReq)
                .when()
                    .post(ApiEndpoints.AUTH_LOGIN)
                .then()
                    .statusCode(200)
                    .extract().as(LoginResponse.class);

            cachedToken = response.getToken();
            // Cache token for 1400 minutes (TTL is 1440 min)
            tokenExpiryMillis = System.currentTimeMillis() + (1400L * 60L * 1000L);
            return cachedToken;
        } catch (Exception e) {
            // Fallback attempt via Gateway if direct Identity port is behind gateway
            try {
                LoginResponse response = given(RequestSpecFactory.unauthenticatedSpec())
                        .baseUri(ApiConfig.getGatewayUrl())
                        .body(loginReq)
                    .when()
                        .post(ApiEndpoints.AUTH_LOGIN)
                    .then()
                        .statusCode(200)
                        .extract().as(LoginResponse.class);

                cachedToken = response.getToken();
                tokenExpiryMillis = System.currentTimeMillis() + (1400L * 60L * 1000L);
                return cachedToken;
            } catch (Exception ex) {
                throw new RuntimeException("Failed to acquire JWT token from Identity Service (:5001) or Gateway (:5000): " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Clears the cached token so the next call forces a new authentication handshake.
     */
    public static synchronized void invalidateToken() {
        cachedToken = null;
        tokenExpiryMillis = 0;
    }
}
