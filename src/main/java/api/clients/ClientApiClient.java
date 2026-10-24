package api.clients;

import api.config.ApiConfig;
import api.config.ApiEndpoints;
import api.pojo.client.CreateClientRequest;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClientApiClient {

    private final String baseUrl;

    public ClientApiClient() {
        this.baseUrl = ApiConfig.getClientUrl();
    }

    public Response createClient(CreateClientRequest request) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .body(request)
            .when()
                .post(ApiEndpoints.CLIENTS);
    }

    public Response getClients(int page, int size) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
            .when()
                .get(ApiEndpoints.CLIENTS);
    }

    public Response getClientById(String clientId) {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
                .pathParam("id", clientId)
            .when()
                .get(ApiEndpoints.CLIENTS_ID);
    }

    public Response getDepartments() {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
            .when()
                .get(ApiEndpoints.CLIENT_DEPARTMENTS);
    }

    public Response getDesignations() {
        return given(RequestSpecFactory.authenticatedSpec())
                .baseUri(baseUrl)
            .when()
                .get(ApiEndpoints.CLIENT_DESIGNATIONS);
    }
}
