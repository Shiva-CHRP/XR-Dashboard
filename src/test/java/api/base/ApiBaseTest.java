package api.base;

import api.auth.AuthHelper;
import api.auth.TokenManager;
import api.clients.*;
import api.config.ApiConfig;
import api.spec.RequestSpecFactory;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * ApiBaseTest - Base test class for all VEMS Backend Microservices API tests.
 */
public abstract class ApiBaseTest {

    protected GatewayApiClient gatewayClient;
    protected IdentityApiClient identityClient;
    protected UserApiClient userClient;
    protected LicenseApiClient licenseClient;
    protected ClientApiClient clientApiClient;
    protected NotificationApiClient notificationClient;
    protected CloudSyncApiClient cloudSyncClient;
    protected OfflineApiClient offlineClient;

    @BeforeSuite(alwaysRun = true)
    public void globalApiSuiteSetup() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @BeforeClass(alwaysRun = true)
    public void setupApiClients() {
        this.gatewayClient = new GatewayApiClient();
        this.identityClient = new IdentityApiClient();
        this.userClient = new UserApiClient();
        this.licenseClient = new LicenseApiClient();
        this.clientApiClient = new ClientApiClient();
        this.notificationClient = new NotificationApiClient();
        this.cloudSyncClient = new CloudSyncApiClient();
        this.offlineClient = new OfflineApiClient();
    }

    /**
     * Authenticated RequestSpec with JWT Bearer Token.
     */
    protected RequestSpecification auth() {
        return RequestSpecFactory.authenticatedSpec();
    }

    /**
     * Service-to-Service RequestSpec with X-Admin-Key.
     */
    protected RequestSpecification adminKey() {
        return RequestSpecFactory.adminKeySpec();
    }

    /**
     * Unauthenticated RequestSpec.
     */
    protected RequestSpecification unauth() {
        return RequestSpecFactory.unauthenticatedSpec();
    }

    /**
     * MultiPart RequestSpec for S3 file uploads.
     */
    protected RequestSpecification multiPart() {
        return RequestSpecFactory.multiPartSpec();
    }
}
