package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * Responsibility:
 * <ul>
 * <li>Provide the {@link DSLContext} for accessing the Database.
 * </ul>
 */
public class BaseMapper {
    private final Connection connection;

    public BaseMapper(Connection connection) {
        this.connection = connection;
    }

    protected DSLContext getDslContext() {
        return DSL.using(connection, SQLDialect.SQLITE);
    }
}