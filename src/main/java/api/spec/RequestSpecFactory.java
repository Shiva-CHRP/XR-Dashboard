package api.spec;

import api.auth.TokenManager;
import api.config.ApiConfig;
import api.filter.ExtentRestAssuredFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * RequestSpecFactory - Factory for pre-configured RestAssured RequestSpecifications
 * with centralized ExtentReports HTTP request & response logging.
 */
public class RequestSpecFactory {

    private static final ExtentRestAssuredFilter EXTENT_FILTER = new ExtentRestAssuredFilter();

    private RequestSpecFactory() {}

    /**
     * RequestSpec authenticated with JWT Bearer token.
     */
    public static RequestSpecification authenticatedSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + TokenManager.getToken())
                .setRelaxedHTTPSValidation()
                .addFilter(EXTENT_FILTER)
                .build();
    }

    /**
     * RequestSpec authenticated with service-to-service X-Admin-Key.
     */
    public static RequestSpecification adminKeySpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("X-Admin-Key", ApiConfig.getAdminKey())
                .setRelaxedHTTPSValidation()
                .addFilter(EXTENT_FILTER)
                .build();
    }

    /**
     * Unauthenticated JSON RequestSpec.
     */
    public static RequestSpecification unauthenticatedSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .addFilter(EXTENT_FILTER)
                .build();
    }

    /**
     * MultiPart RequestSpec for S3 file uploads.
     */
    public static RequestSpecification multiPartSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.MULTIPART)
                .addHeader("Authorization", "Bearer " + TokenManager.getToken())
                .setRelaxedHTTPSValidation()
                .addFilter(EXTENT_FILTER)
                .build();
    }
}
