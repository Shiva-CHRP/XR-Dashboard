package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.pojo.user.CreateUserRequest;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private final String baseUrl;

    public UserApiClient() {
        this.baseUrl = ApiConfig.getUserUrl();
    }

    public Response getUsers(int page, int pageSize) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
            .when()
                .get(ApiEndpoints.USERS);
    }

    public Response createUser(CreateUserRequest request) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .body(request)
            .when()
                .post(ApiEndpoints.USERS);
    }

    public Response getUserById(String userId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .pathParam("id", userId)
            .when()
                .get(ApiEndpoints.USERS_ID);
    }

    public Response deleteUser(String userId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .pathParam("id", userId)
            .when()
                .delete(ApiEndpoints.USERS_ID);
    }

    public Response uploadAvatar(String userId, File imageFile) {
        return given(RequestSpecFactory.multiPartSpec())
                .baseUri(baseUrl)
                .pathParam("id", userId)
                .multiPart("file", imageFile, "image/png")
            .when()
                .post(ApiEndpoints.USERS_AVATAR);
    }
}
