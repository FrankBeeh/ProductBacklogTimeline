package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;

import de.frankbeeh.productbacklogtimeline.service.ServiceRegistry;

public class UITestServiceRegistry extends ServiceRegistry {
	private static final String TEST_DB = "build/uitest.db";
    private final TestDataBase testDataBase;

	public UITestServiceRegistry() throws Exception {
	    testDataBase = new TestDataBase(TEST_DB);
	    testDataBase.recreateTables();
		Connection connection = testDataBase.getConnection();
		registerService(ReleaseForecastService.class, new ReleaseForecastService(connection));
	}
	
	@Override
	public void close() throws Exception {
	    testDataBase.close();
	}
}
