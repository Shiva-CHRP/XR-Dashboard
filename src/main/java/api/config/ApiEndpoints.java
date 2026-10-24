package api.config;

/**
 * ApiEndpoints - Centralized route constants for all VEMS Microservices.
 */
public final class ApiEndpoints {

    private ApiEndpoints() {}

    // Gateway & Health
    public static final String GATEWAY_HEALTH = "/health";
    public static final String GATEWAY_MAINTENANCE_ENABLE = "/api/admin/maintenance/enable";
    public static final String GATEWAY_MAINTENANCE_DISABLE = "/api/admin/maintenance/disable";

    // Identity Service (:5001)
    public static final String AUTH_LOGIN = "/api/auth/login";
    public static final String AUTH_REFRESH = "/api/auth/refresh";
    public static final String AUTH_ME = "/api/auth/me";
    public static final String AUTH_LOGOUT = "/api/auth/logout";

    // User Service (:5002)
    public static final String USERS = "/api/users";
    public static final String USERS_ID = "/api/users/{id}";
    public static final String USERS_AVATAR = "/api/users/{id}/avatar";
    public static final String DEVELOPERS = "/api/developers";
    public static final String DEVELOPERS_ID = "/api/developers/{id}";
    public static final String ORGANIZATIONS = "/api/organizations";
    public static final String ORGANIZATIONS_ID = "/api/organizations/{id}";
    public static final String ORG_APPLICATIONS = "/api/organization-applications";
    public static final String ASSESSMENTS = "/api/assessments";
    public static final String QUESTION_BANK = "/api/question-bank";

    // License Service (:5003)
    public static final String LICENSES = "/api/licenses";
    public static final String LICENSES_ID = "/api/licenses/{id}";
    public static final String LICENSES_REVOKE = "/api/licenses/{id}/revoke";
    public static final String TOKENS = "/api/tokens";

    // Client Service (:5004)
    public static final String CLIENTS = "/api/clients";
    public static final String CLIENTS_ID = "/api/clients/{id}";
    public static final String CLIENT_DEPARTMENTS = "/api/departments";
    public static final String CLIENT_DESIGNATIONS = "/api/designations";
    public static final String CLIENT_DIVISIONS = "/api/divisions";
    public static final String CLIENT_PLANTS = "/api/plants";
    public static final String CLIENT_AREAS = "/api/areas";
    public static final String CLIENT_CATEGORIES = "/api/categories";
    public static final String CLIENT_PARTNERS = "/api/partners";
    public static final String CLIENT_SETUPS = "/api/setups";
    public static final String CLIENT_TICKETS = "/api/client-tickets";

    // Notification Service (:5006)
    public static final String NOTIFICATIONS_SEND = "/api/notifications/send";
    public static final String NOTIFICATIONS_HISTORY = "/api/notifications";

    // CloudSync Service (:5007)
    public static final String CLOUDSYNC_TRIGGER = "/api/sync/trigger";
    public static final String CLOUDSYNC_STATUS = "/api/sync/status";

    // Offline Service (:5008)
    public static final String OFFLINE_SYNC_TRIGGER = "/api/sync/trigger";
    public static final String OFFLINE_DEVICE_REGISTER = "/api/device-registration";
    public static final String OFFLINE_DEVICE_STATUS = "/api/device-gate/status";
}
