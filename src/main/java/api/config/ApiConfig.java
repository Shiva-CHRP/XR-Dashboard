package api.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * ApiConfig - Centralized configuration reader for microservices test automation.
 * Supports priority resolution:
 * 1. System Properties (-Dapi.gateway.url=...)
 * 2. OS Environment Variables (API_GATEWAY_URL=...)
 * 3. api-test-config.properties
 * 4. Hardcoded staging server defaults
 */
public class ApiConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = ApiConfig.class.getClassLoader().getResourceAsStream("api/config/api-test-config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            System.err.println("Notice: Could not load api-test-config.properties from classpath, falling back to defaults.");
        }
    }

    public static String get(String key, String defaultValue) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.trim().isEmpty()) {
            return sysProp.trim();
        }
        String envKey = key.replace('.', '_').toUpperCase();
        String envVar = System.getenv(envKey);
        if (envVar != null && !envVar.trim().isEmpty()) {
            return envVar.trim();
        }
        return props.getProperty(key, defaultValue);
    }

    public static String get(String key) {
        return get(key, "");
    }

    // Microservice Base URLs
    public static String getGatewayUrl() {
        return get("api.gateway.url", "http://13.127.17.90:5000");
    }

    public static String getIdentityUrl() {
        return get("api.identity.url", "http://13.127.17.90:5001");
    }

    public static String getUserUrl() {
        return get("api.user.url", "http://13.127.17.90:5002");
    }

    public static String getLicenseUrl() {
        return get("api.license.url", "http://13.127.17.90:5003");
    }

    public static String getClientUrl() {
        return get("api.client.url", "http://13.127.17.90:5004");
    }

    public static String getNotificationUrl() {
        return get("api.notification.url", "http://13.127.17.90:5006");
    }

    public static String getCloudSyncUrl() {
        return get("api.cloudsync.url", "http://13.127.17.90:5007");
    }

    public static String getOfflineUrl() {
        return get("api.offline.url", "http://13.127.17.90:5008");
    }

    // Credentials & Keys
    public static String getAdminEmail() {
        return get("api.admin.email", "testadmin@vems.com");
    }

    public static String getAdminPassword() {
        return get("api.admin.password", "Test@1234");
    }

    public static String getAdminKey() {
        return get("api.admin.key", "236879trsdxcvkuytreiu76543445wedghytr2212345tyuhgvcb");
    }

    public static String getAllowedOrigin() {
        return get("api.allowed.origin", "https://superadmin-ui-staging.xrdashboard.com");
    }
}
