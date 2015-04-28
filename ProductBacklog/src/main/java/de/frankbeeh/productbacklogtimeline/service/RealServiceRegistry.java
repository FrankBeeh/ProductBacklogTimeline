package de.frankbeeh.productbacklogtimeline.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.frankbeeh.productbacklogtimeline.service.database.DatabaseService;
import de.frankbeeh.productbacklogtimeline.service.database.ProductBacklogItemService;

public class RealServiceRegistry extends ServiceRegistry {
	private static final String PRODUCT_TIMELINE_DB = "productTimeline.db";

	public RealServiceRegistry() {
		Connection connection = initConnection();
		registerService(ProductBacklogItemService.class, new ProductBacklogItemService(connection));
	}

	private static Connection getConnection(String databaseName)
			throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		final String connectionString = "jdbc:sqlite:" + databaseName;
		return DriverManager.getConnection(connectionString);
	}

	private static Connection initConnection() {
		Connection connection;
		try {
			connection = getConnection(PRODUCT_TIMELINE_DB);
			new DatabaseService(connection).createTables();
		} catch (ClassNotFoundException | SQLException exception) {
			throw new RuntimeException(exception);
		}
		return connection;
	}
}
