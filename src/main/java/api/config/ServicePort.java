package api.config;

/**
 * ServicePort - Enumeration of all 8 VEMS Microservices with their standard ports.
 */
public enum ServicePort {
    GATEWAY(5000, "gateway", "API Gateway & Routing"),
    IDENTITY(5001, "identity-service", "Identity & JWT Auth Service"),
    USER(5002, "user-service", "User, Developer & Organization Management"),
    LICENSE(5003, "license-service", "Licensing, Tokens & Encryption"),
    CLIENT(5004, "client-service", "Client Portal, Masters & Offline Results"),
    NOTIFICATION(5006, "notification-service", "Email & Push Notification Service"),
    CLOUDSYNC(5007, "cloudsync-service", "Cloud Data Sync Service"),
    OFFLINE(5008, "offline-service", "Offline Simulation & Device Gate Service");

    private final int port;
    private final String containerName;
    private final String description;

    ServicePort(int port, String containerName, String description) {
        this.port = port;
        this.containerName = containerName;
        this.description = description;
    }

    public int getPort() {
        return port;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getDescription() {
        return description;
    }
}
