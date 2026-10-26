package api.filter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import superadmin.reports.ExtentTestManager;

/**
 * ExtentRestAssuredFilter - Captures and formats RestAssured HTTP Request and Response details
 * directly into the active ExtentReports node.
 */
public class ExtentRestAssuredFilter implements Filter {

    private static final ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final int MAX_BODY_LENGTH = 15000;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        long startTime = System.currentTimeMillis();
        Response response = ctx.next(requestSpec, responseSpec);
        long duration = System.currentTimeMillis() - startTime;

        try {
            logToExtentReport(requestSpec, response, duration);
        } catch (Throwable t) {
            // Ensure logging failures never break test execution
            System.err.println("[ExtentRestAssuredFilter] Failed to log API details: " + t.getMessage());
        }

        return response;
    }

    private void logToExtentReport(FilterableRequestSpecification requestSpec, Response response, long duration) {
        ExtentTest test = ExtentTestManager.getTest();
        if (test == null) {
            return;
        }

        String method = requestSpec.getMethod();
        String uri = requestSpec.getURI();
        int statusCode = response.getStatusCode();
        String statusLine = response.getStatusLine();

        // 1. Color-coded Status Label Badge
        ExtentColor badgeColor = resolveBadgeColor(statusCode);
        String labelText = String.format("%s %s  ➔  [%d %s | %d ms]",
                method, uri, statusCode, statusLine, duration);
        test.info(MarkupHelper.createLabel(labelText, badgeColor));

        // 2. Request Details (Headers & Payload)
        Object requestBody = requestSpec.getBody();
        if (requestBody != null) {
            String formattedReqBody = formatPayload(requestBody);
            if (formattedReqBody != null && !formattedReqBody.trim().isEmpty()) {
                test.info("<span style='font-weight:600; font-size:12px; color:#94a3b8;'>REQUEST BODY (" + method + "):</span>");
                test.info(MarkupHelper.createCodeBlock(formattedReqBody, CodeLanguage.JSON));
            }
        }

        // 3. Response Details
        String responseBody = response.getBody() != null ? response.getBody().asString() : "";
        if (responseBody != null && !responseBody.trim().isEmpty()) {
            String trimmedBody = responseBody.length() > MAX_BODY_LENGTH
                    ? responseBody.substring(0, MAX_BODY_LENGTH) + "\n... [Response truncated - exceeds " + MAX_BODY_LENGTH + " chars]"
                    : responseBody;

            test.info("<span style='font-weight:600; font-size:12px; color:#94a3b8;'>RESPONSE BODY (HTTP " + statusCode + "):</span>");
            if (isJson(trimmedBody)) {
                try {
                    String prettyJson = response.getBody().asPrettyString();
                    test.info(MarkupHelper.createCodeBlock(prettyJson, CodeLanguage.JSON));
                } catch (Exception ex) {
                    test.info(MarkupHelper.createCodeBlock(trimmedBody));
                }
            } else {
                test.info(MarkupHelper.createCodeBlock(trimmedBody));
            }
        } else {
            test.info("<span style='color:#64748b; font-style:italic;'>No Response Body Returned</span>");
        }
    }

    private ExtentColor resolveBadgeColor(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return ExtentColor.GREEN;
        } else if (statusCode >= 300 && statusCode < 400) {
            return ExtentColor.BLUE;
        } else if (statusCode >= 400 && statusCode < 500) {
            return ExtentColor.ORANGE;
        } else if (statusCode >= 500) {
            return ExtentColor.RED;
        }
        return ExtentColor.GREY;
    }

    private String formatPayload(Object body) {
        if (body == null) return null;
        try {
            if (body instanceof String) {
                String str = ((String) body).trim();
                if (isJson(str)) {
                    Object jsonNode = MAPPER.readValue(str, Object.class);
                    return MAPPER.writeValueAsString(jsonNode);
                }
                return str;
            }
            return MAPPER.writeValueAsString(body);
        } catch (Exception e) {
            return String.valueOf(body);
        }
    }

    private boolean isJson(String str) {
        if (str == null) return false;
        String trimmed = str.trim();
        return (trimmed.startsWith("{") && trimmed.endsWith("}")) ||
               (trimmed.startsWith("[") && trimmed.endsWith("]"));
    }
}
