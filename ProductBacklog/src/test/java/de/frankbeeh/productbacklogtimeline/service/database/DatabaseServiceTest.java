package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;

public class DatabaseServiceTest {
	private static final String TEST_DB = "build/test.db";
	private Connection connection;
	
	protected Connection getConnection(){
		return connection;
	}

	@Before
	public void setUpTables() throws Exception {
		connection = getConnection(TEST_DB);
		dropTables();
		createTables();
	}

	@After
	public void tearDownConnection() throws Exception {
		connection.close();
	}
	
	private void dropTables() throws Exception {
		try (Statement statement = connection.createStatement()) {
		    statement.executeUpdate("DROP TABLE IF EXISTS PBL");
			statement.executeUpdate("DROP TABLE IF EXISTS PBI");
		}
	}

	private void createTables() throws Exception {
		final DatabaseService service = new DatabaseService(connection);
		service.createTables();
	}
	
	private static Connection getConnection(String databaseName)
			throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String connectionString = "jdbc:sqlite:" + databaseName;
		return DriverManager.getConnection(connectionString);
	}
}
