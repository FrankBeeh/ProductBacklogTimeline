package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

	private static final String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS PBI (PBI_ID INTEGER PRIMARY KEY NOT NULL, ID CHAR(20) NOT NULL, TITLE TEXT, DESCRIPTION TEXT, ESTIMATE DOUBLE, STATE TEXT NOT NULL, SPRINT TEXT, RANK TEXT, PLANNED_RELEASE TEXT)";
	private final Connection connection;

	public DatabaseService(Connection connection) {
		this.connection = connection;
	}

	public void createTables()
			throws ClassNotFoundException, SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(CREATE_ITEM_TABLE);
		}
	}
}