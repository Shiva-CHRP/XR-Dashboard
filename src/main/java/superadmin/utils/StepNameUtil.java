package superadmin.utils;

import java.lang.reflect.Method;

import superadmin.annotations.StepName;

public class StepNameUtil {
	private static final ThreadLocal<String> dynamicStep = new ThreadLocal<>();

    public static void setStepName(String stepName) {
        dynamicStep.set(stepName);
    }

    public static void clearStepName() {
        dynamicStep.remove();
    }

    public static String getStepName() {

        // Return dynamic step if available
        if (dynamicStep.get() != null) {
            return dynamicStep.get();
        }

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        try {

            for (StackTraceElement element : stack) {

                String className = element.getClassName();

                if (className.contains("pageobjects")) {

                    Class<?> clazz = Class.forName(className);

                    for (Method method : clazz.getDeclaredMethods()) {

                        if (method.getName().equals(element.getMethodName())
                                && method.isAnnotationPresent(StepName.class)) {

                            return cleanStepName(method.getAnnotation(StepName.class).value());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Executing Step";
    }

    private static String cleanStepName(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.replaceAll(":\\s*\\{[0-9]+\\}", "")
                  .replaceAll("\\{[0-9]+\\}", "")
                  .trim();
    }
}
