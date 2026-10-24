package api.auth;

/**
 * TokenManager - ThreadLocal and static utilities for managing API tokens.
 */
public class TokenManager {

    private static final ThreadLocal<String> threadLocalToken = new ThreadLocal<>();

    public static void setCustomToken(String token) {
        threadLocalToken.set(token);
    }

    public static String getToken() {
        String token = threadLocalToken.get();
        if (token != null && !token.isEmpty()) {
            return token;
        }
        return AuthHelper.getValidJwtToken();
    }

    public static void clearCustomToken() {
        threadLocalToken.remove();
    }
}
