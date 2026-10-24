package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.pojo.auth.LoginRequest;
import api.pojo.auth.RefreshTokenRequest;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IdentityApiClient {

    private final String baseUrl;

    public IdentityApiClient() {
        this.baseUrl = ApiConfig.getIdentityUrl();
    }

    public Response login(String email, String password) {
        LoginRequest req = new LoginRequest(email, password);
        return given(RequestSpecFactory.unauthenticatedSpec())
                .baseUri(baseUrl)
                .body(req)
            .when()
                .post(ApiEndpoints.AUTH_LOGIN);
    }

    public Response refreshToken(String token, String refreshToken) {
        RefreshTokenRequest req = new RefreshTokenRequest(token, refreshToken);
        return given(RequestSpecFactory.unauthenticatedSpec())
                .baseUri(baseUrl)
                .body(req)
            .when()
                .post(ApiEndpoints.AUTH_REFRESH);
    }

    public Response getMe() {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
            .when()
                .get(ApiEndpoints.AUTH_ME);
    }

    public Response getMeWithoutToken() {
        return given(RequestSpecFactory.unauthenticatedSpec())
                .baseUri(baseUrl)
            .when()
                .get(ApiEndpoints.AUTH_ME);
    }
}
