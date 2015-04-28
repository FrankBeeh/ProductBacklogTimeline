package de.frankbeeh.productbacklogtimeline.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
	private final Map<Class<Object>, Object> serviceMap = new HashMap<Class<Object>, Object>();

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceClass) {
		return (T) serviceMap.get(serviceClass);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void registerService(Class<T> serviceClass, T service) {
		serviceMap.put((Class<Object>) serviceClass, (Object) service);
	}
}
