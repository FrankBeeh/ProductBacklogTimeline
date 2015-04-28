package de.frankbeeh.productbacklogtimeline.service;


public class ServiceLocator {
	private static ServiceRegistry registry;
	
	public static void init(ServiceRegistry serviceRegistry){
		registry = serviceRegistry;
	}

	public static <T> T getService(Class<T> serviceClass) {
		return (T) registry.getService(serviceClass);
	}
}
