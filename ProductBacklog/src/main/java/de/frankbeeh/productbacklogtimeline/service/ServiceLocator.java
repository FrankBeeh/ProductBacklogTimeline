package de.frankbeeh.productbacklogtimeline.service;

/**
 * Responsibility:
 * <ul>
 * <li>Returns registered services by their class ({@link ServiceRegistry}).
 * <li>Closes the {@link ServiceRegistry}.
 * </ul>
 */
public class ServiceLocator {
    private static ServiceRegistry registry;

    public static void init(ServiceRegistry serviceRegistry) {
        registry = serviceRegistry;
    }

    public static <T> T getService(Class<T> serviceClass) {
        return (T) registry.getService(serviceClass);
    }

    public static void close() throws Exception {
        registry.close();
        registry = null;
    }
}
