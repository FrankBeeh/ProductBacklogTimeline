package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Responsibility:
 * <ul>
 * <li>Creates the not yet existing tables in the DB.
 * </ul>
 */
public class DatabaseService {
    private static final String CREATE_PBI_TABLE = "CREATE TABLE IF NOT EXISTS PBI (ID CHAR(20) NOT NULL, HASH CHAR(40) NOT NULL, TITLE TEXT, DESCRIPTION TEXT, ESTIMATE DOUBLE, STATE TEXT NOT NULL, SPRINT TEXT, RANK TEXT, PLANNED_RELEASE TEXT, CONSTRAINT PK_PBI PRIMARY KEY (ID, HASH))";
    private static final String CREATE_PBL_TABLE = "CREATE TABLE IF NOT EXISTS PBL (ID INT NOT NULL, PBI_ID CHAR(20) NOT NULL, HASH CHAR(40) NOT NULL, CONSTRAINT PK_PBI FOREIGN KEY (PBI_ID, HASH) REFERENCES PBI(PBI_ID, HASH))";

    private final Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public void createTables() throws ClassNotFoundException, SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_PBI_TABLE);
            statement.executeUpdate(CREATE_PBL_TABLE);
        }
    }
}
