package de.frankbeeh.productbacklogtimeline.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsibility:
 * <ul>
 * <li>Central registry for all used services.
 * </ul>
 */
public abstract class ServiceRegistry {
    private final Map<Class<Object>, Object> serviceMap = new HashMap<Class<Object>, Object>();

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        return (T) serviceMap.get(serviceClass);
    }

    @SuppressWarnings("unchecked")
    protected <T> void registerService(Class<T> serviceClass, T service) {
        serviceMap.put((Class<Object>) serviceClass, (Object) service);
    }

    public abstract void close() throws Exception;
}
