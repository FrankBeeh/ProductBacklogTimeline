package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.SPRINT;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import org.jooq.Record7;

import de.frankbeeh.productbacklogtimeline.domain.SprintData;
import de.frankbeeh.productbacklogtimeline.service.ConvertUtility;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link SprintData} into the Database.
 * </ul>
 */
public class SprintDataMapper extends BaseMapper {
    public SprintDataMapper(Connection connection) {
        super(connection);
    }

    public void insert(SprintData sprintData) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (notYetInserted(sprintData)) {
            getDslContext().insertInto(SPRINT, SPRINT.HASH, SPRINT.NAME, SPRINT.START, SPRINT.END, SPRINT.CAPACITY_FORECAST, SPRINT.EFFORT_FORECAST, SPRINT.CAPACITY_DONE, SPRINT.EFFORT_DONE).values(
                    sprintData.getHash(), sprintData.getName(), ConvertUtility.getSqlDate(sprintData.getStartDate()), ConvertUtility.getSqlDate(sprintData.getEndDate()),
                    sprintData.getCapacityForecast(), sprintData.getEffortForecast(), sprintData.getCapacityDone(), sprintData.getEffortDone()).execute();
        }
    }

    public SprintData get(LocalDate endDate, String hash) {
        final Record7<String, Date, Date, Double, Double, Double, Double> record = getDslContext().select(SPRINT.NAME, SPRINT.START, SPRINT.END, SPRINT.CAPACITY_FORECAST, SPRINT.EFFORT_FORECAST,
                SPRINT.CAPACITY_DONE, SPRINT.EFFORT_DONE).from(SPRINT).where(SPRINT.HASH.eq(hash)).and(SPRINT.END.eq(ConvertUtility.getSqlDate(endDate))).fetchOne();
        return new SprintData(record.getValue(SPRINT.NAME), ConvertUtility.getLocalDate(record.getValue(SPRINT.START)), ConvertUtility.getLocalDate(record.getValue(SPRINT.END)),
                record.getValue(SPRINT.CAPACITY_FORECAST), record.getValue(SPRINT.EFFORT_FORECAST), record.getValue(SPRINT.CAPACITY_DONE), record.getValue(SPRINT.EFFORT_DONE));
    }

    private boolean notYetInserted(SprintData sprintData) {
        return getDslContext().select(SPRINT.HASH).from(SPRINT).where(SPRINT.HASH.eq(sprintData.getHash()).and(SPRINT.END.eq(ConvertUtility.getSqlDate(sprintData.getEndDate())))).fetchOne() == null;
    }
}
