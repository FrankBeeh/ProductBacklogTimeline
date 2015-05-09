package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.SPRINT;
import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.VELOCITY_FORECAST;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;

import org.jooq.Record7;
import org.jooq.Result;

import de.frankbeeh.productbacklogtimeline.domain.SprintData;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.ConvertUtility;

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
        for (SprintData sprintData : velocityForecast.getSprints()) {
            insertSprint(sprintData);
            insertRelation(productTimestampId, sprintData);
        }
    }

    public VelocityForecast get(LocalDateTime productTimestampId) {
        final VelocityForecast velocityForecast = new VelocityForecast();
        final Result<Record7<String, Date, Date, Double, Double, Double, Double>> result = getDslContext().select(SPRINT.NAME, SPRINT.START, SPRINT.END, SPRINT.CAPACITY_FORECAST,
                SPRINT.EFFORT_FORECAST, SPRINT.CAPACITY_DONE, SPRINT.EFFORT_DONE).from(VELOCITY_FORECAST).join(SPRINT).on(
                VELOCITY_FORECAST.SPRINT_END.eq(SPRINT.END).and(VELOCITY_FORECAST.SPRINT_HASH.eq(SPRINT.HASH))).where(VELOCITY_FORECAST.PT_ID.eq(ConvertUtility.getTimestamp(productTimestampId))).fetch();
        for (Record7<String, Date, Date, Double, Double, Double, Double> record : result) {
            SprintData sprint = new SprintData(record.getValue(SPRINT.NAME), ConvertUtility.getLocalDate(record.getValue(SPRINT.START)), ConvertUtility.getLocalDate(record.getValue(SPRINT.END)),
                    record.getValue(SPRINT.CAPACITY_FORECAST), record.getValue(SPRINT.EFFORT_FORECAST), record.getValue(SPRINT.CAPACITY_DONE), record.getValue(SPRINT.EFFORT_DONE));
            velocityForecast.addItem(sprint);
        }
        return velocityForecast;
    }
    
    private void insertSprint(SprintData sprintData) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (sprintNotYetInserted(sprintData)) {
            getDslContext().insertInto(SPRINT, SPRINT.HASH, SPRINT.NAME, SPRINT.START, SPRINT.END, SPRINT.CAPACITY_FORECAST, SPRINT.EFFORT_FORECAST, SPRINT.CAPACITY_DONE, SPRINT.EFFORT_DONE).values(
                    sprintData.getHash(), sprintData.getName(), ConvertUtility.getSqlDate(sprintData.getStartDate()), ConvertUtility.getSqlDate(sprintData.getEndDate()),
                    sprintData.getCapacityForecast(), sprintData.getEffortForecast(), sprintData.getCapacityDone(), sprintData.getEffortDone()).execute();
        }
    }

    private boolean sprintNotYetInserted(SprintData sprintData) {
        return getDslContext().select(SPRINT.HASH).from(SPRINT).where(SPRINT.HASH.eq(sprintData.getHash()).and(SPRINT.END.eq(ConvertUtility.getSqlDate(sprintData.getEndDate())))).fetchOne() == null;
    }
    
    private void insertRelation(LocalDateTime productTimestampId, SprintData sprintData) {
        getDslContext().insertInto(VELOCITY_FORECAST, VELOCITY_FORECAST.PT_ID, VELOCITY_FORECAST.SPRINT_END, VELOCITY_FORECAST.SPRINT_HASH).values(ConvertUtility.getTimestamp(productTimestampId),
                ConvertUtility.getSqlDate(sprintData.getEndDate()), sprintData.getHash()).execute();
    }
}
