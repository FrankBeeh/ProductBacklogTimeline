package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataBase {
    private final String databaseName;
    private Connection connection;

    public TestDataBase(String databaseName) {
        this.databaseName = databaseName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void recreateTables() throws Exception {
        connection = getConnection(databaseName);
        dropTables(connection);
        createTables();
    }

    public void close() throws SQLException {
        connection.close();
    }
    
    private void dropTables(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS PRODUCT_TIMESTAMP");
            statement.executeUpdate("DROP TABLE IF EXISTS PBL");
            statement.executeUpdate("DROP TABLE IF EXISTS PBI");
            statement.executeUpdate("DROP TABLE IF EXISTS VELOCITY_FORECAST");
            statement.executeUpdate("DROP TABLE IF EXISTS SPRINT");
            statement.executeUpdate("DROP TABLE IF EXISTS RELEASES");
            statement.executeUpdate("DROP TABLE IF EXISTS RELEASE");
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
