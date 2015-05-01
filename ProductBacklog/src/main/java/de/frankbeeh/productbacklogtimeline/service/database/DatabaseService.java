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
    private static final String CREATE_PBL_TABLE = "CREATE TABLE IF NOT EXISTS PBL (RF_ID TIMESTAMP NOT NULL, PBI_ID CHAR(20) NOT NULL, PBI_HASH CHAR(40) NOT NULL, CONSTRAINT PK_PBI FOREIGN KEY (PBI_ID, PBI_HASH) REFERENCES PBI(PBI_ID, PBI_HASH), CONSTRAINT PK_RF FOREIGN KEY (RF_ID) REFERENCES RELEASE_FORECAST(ID))";
    private static final String CREATE_RELEASE_FORECAST_TABLE = "CREATE TABLE IF NOT EXISTS RELEASE_FORECAST (ID TIMESTAMP NOT NULL, NAME VARCHAR(100) NOT NULL, CONSTRAINT PB_RF PRIMARY KEY (ID))";

    private final Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public void createTables() throws ClassNotFoundException, SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_RELEASE_FORECAST_TABLE);
            statement.executeUpdate(CREATE_PBI_TABLE);
            statement.executeUpdate(CREATE_PBL_TABLE);
        }
    }
}
