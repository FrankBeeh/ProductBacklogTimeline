package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.SPRINT;
import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.VELOCITY_FORECAST;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;

import org.jooq.Record7;
import org.jooq.Result;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.DateConverter;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link VelocityForecast} into the Database.
 * </ul>
 */
public class VelocityForecastMapper extends BaseMapper {
    public VelocityForecastMapper(Connection connection) {
        super(connection);
    }

    public void insert(LocalDateTime productTimestampId, VelocityForecast velocityForecast) {
        for (Sprint sprintData : velocityForecast.getSprints()) {
            insertSprint(sprintData);
            insertRelation(productTimestampId, sprintData);
        }
    }

    public VelocityForecast get(LocalDateTime productTimestampId) {
        final VelocityForecast velocityForecast = new VelocityForecast();
        final Result<Record7<String, Date, Date, Double, Double, Double, Double>> result = getDslContext().select(SPRINT.NAME, SPRINT.START, SPRINT.END, SPRINT.CAPACITY_FORECAST,
                SPRINT.EFFORT_FORECAST, SPRINT.CAPACITY_DONE, SPRINT.EFFORT_DONE).from(VELOCITY_FORECAST).join(SPRINT).on(
                VELOCITY_FORECAST.SPRINT_END.eq(SPRINT.END).and(VELOCITY_FORECAST.SPRINT_HASH.eq(SPRINT.HASH))).where(VELOCITY_FORECAST.PT_ID.eq(DateConverter.getTimestamp(productTimestampId))).fetch();
        for (Record7<String, Date, Date, Double, Double, Double, Double> record : result) {
            Sprint sprint = new Sprint(record.getValue(SPRINT.NAME), DateConverter.getLocalDate(record.getValue(SPRINT.START)), DateConverter.getLocalDate(record.getValue(SPRINT.END)),
                    record.getValue(SPRINT.CAPACITY_FORECAST), record.getValue(SPRINT.EFFORT_FORECAST), record.getValue(SPRINT.CAPACITY_DONE), record.getValue(SPRINT.EFFORT_DONE));
            velocityForecast.addItem(sprint);
        }
        return velocityForecast;
    }
    
    private void insertSprint(Sprint sprintData) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (sprintNotYetInserted(sprintData)) {
            getDslContext().insertInto(SPRINT, SPRINT.HASH, SPRINT.NAME, SPRINT.START, SPRINT.END, SPRINT.CAPACITY_FORECAST, SPRINT.EFFORT_FORECAST, SPRINT.CAPACITY_DONE, SPRINT.EFFORT_DONE).values(
                    sprintData.getHash(), sprintData.getName(), DateConverter.getSqlDate(sprintData.getStartDate()), DateConverter.getSqlDate(sprintData.getEndDate()),
                    sprintData.getCapacityForecast(), sprintData.getEffortForecast(), sprintData.getCapacityDone(), sprintData.getEffortDone()).execute();
        }
    }

    private boolean sprintNotYetInserted(Sprint sprintData) {
        return getDslContext().select(SPRINT.HASH).from(SPRINT).where(SPRINT.HASH.eq(sprintData.getHash()).and(SPRINT.END.eq(DateConverter.getSqlDate(sprintData.getEndDate())))).fetchOne() == null;
    }
    
    private void insertRelation(LocalDateTime productTimestampId, Sprint sprintData) {
        getDslContext().insertInto(VELOCITY_FORECAST, VELOCITY_FORECAST.PT_ID, VELOCITY_FORECAST.SPRINT_END, VELOCITY_FORECAST.SPRINT_HASH).values(DateConverter.getTimestamp(productTimestampId),
                DateConverter.getSqlDate(sprintData.getEndDate()), sprintData.getHash()).execute();
    }
}
