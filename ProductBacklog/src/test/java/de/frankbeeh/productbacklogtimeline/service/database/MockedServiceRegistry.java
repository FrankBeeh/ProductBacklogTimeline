package de.frankbeeh.productbacklogtimeline.service.database;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;

import de.frankbeeh.productbacklogtimeline.service.ServiceRegistry;

public class MockedServiceRegistry extends ServiceRegistry {
	private static final List<Class<ReleaseForecastService>> MOCKED_SERVICE_CLASSES = Arrays.asList(ReleaseForecastService.class);

	public MockedServiceRegistry() {
		for (Class<?> serviceClass : MOCKED_SERVICE_CLASSES) {
			registerMockService(serviceClass);
		}
	}

	public void resetAllMocks() {
		for (Class<?> serviceClass : MOCKED_SERVICE_CLASSES) {
			reset(getService(serviceClass));
		}
	}
	
	public void replayAllMocks() {
		for (Class<?> serviceClass : MOCKED_SERVICE_CLASSES) {
			replay(getService(serviceClass));
		}
	}

	public void verifyAllMocks() {
		for (Class<?> serviceClass : MOCKED_SERVICE_CLASSES) {
			verify(getService(serviceClass));
		}
	}
	
	private <T> void registerMockService(Class<T> serviceClass) {
		registerService(serviceClass, EasyMock.createMock(
				serviceClass.getSimpleName() + "Mock", serviceClass));
	}
}
