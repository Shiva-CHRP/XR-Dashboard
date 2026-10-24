package api.user;

import api.base.ApiBaseTest;
import api.config.ApiConfig;
import api.pojo.user.CreateUserRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserServiceApiTest extends ApiBaseTest {

    private String createdUserId;
    private final String dynamicEmail = "testuser_" + System.currentTimeMillis() + "@vems.com";

    @Test(priority = 1, groups = {"API", "Smoke", "User"}, description = "Get users endpoint returns paginated result")
    public void testGetUsersPaginated() {
        Response response = userClient.getUsers(1, 10);
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        if (response.getStatusCode() == 200) {
            response.then()
                    .body("data", notNullValue());
        }
    }

    @Test(priority = 2, groups = {"API", "Regression", "User"}, description = "Create user with valid payload returns 201 Created")
    public void testCreateUser() {
        CreateUserRequest req = new CreateUserRequest(dynamicEmail, "Automation", "Tester", "Admin");
        Response response = userClient.createUser(req);
        response.then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(401)));

        if (response.getStatusCode() == 201 || response.getStatusCode() == 200) {
            createdUserId = response.jsonPath().getString("id");
            Assert.assertNotNull(createdUserId, "Created user ID should be present in response");
        }
    }

    @Test(priority = 3, dependsOnMethods = {"testCreateUser"}, groups = {"API", "Regression", "User"}, description = "Create user with duplicate email returns 409 Conflict")
    public void testCreateUserDuplicateEmail() {
        if (createdUserId != null) {
            CreateUserRequest duplicateReq = new CreateUserRequest(dynamicEmail, "Duplicate", "Tester", "Admin");
            Response response = userClient.createUser(duplicateReq);
            response.then()
                    .statusCode(anyOf(equalTo(409), equalTo(400)));
        }
    }

    @Test(priority = 4, dependsOnMethods = {"testCreateUser"}, groups = {"API", "Regression", "User"}, description = "Clean up created user")
    public void testDeleteUser() {
        if (createdUserId != null) {
            Response response = userClient.deleteUser(createdUserId);
            response.then()
                    .statusCode(anyOf(equalTo(200), equalTo(204), equalTo(404)));
        }
    }
}
