package api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

/**
 * ResponseSpecFactory - Factory for pre-configured RestAssured ResponseSpecifications.
 */
public class ResponseSpecFactory {

    private ResponseSpecFactory() {}

    public static ResponseSpecification success200Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L))
                .build();
    }

    public static ResponseSpecification created201Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L))
                .build();
    }

    public static ResponseSpecification unauthorized401Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }

    public static ResponseSpecification conflict409Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .build();
    }
}
