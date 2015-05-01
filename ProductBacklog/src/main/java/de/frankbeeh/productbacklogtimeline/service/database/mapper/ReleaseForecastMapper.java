package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.RELEASE_FORECAST;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.jooq.Record2;

import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.service.ConvertUtility;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ReleaseForecast} into the Database.
 * </ul>
 */
public class ReleaseForecastMapper extends BaseMapper {
    private final ProductBacklogMapper productBacklogMapper;

    public ReleaseForecastMapper(Connection connection) {
        super(connection);
        productBacklogMapper = new ProductBacklogMapper(connection);
    }

    public void insert(ReleaseForecast releaseForecast) {
        productBacklogMapper.insert(releaseForecast.getDateTime(), releaseForecast.getProductBacklog());
        getDslContext().insertInto(RELEASE_FORECAST, RELEASE_FORECAST.ID, RELEASE_FORECAST.NAME).values(ConvertUtility.getTimestamp(releaseForecast.getDateTime()), releaseForecast.getName()).execute();
    }

    public ReleaseForecast get(LocalDateTime locatDateTime) {
        final Record2<Timestamp, String> record = getDslContext().select(RELEASE_FORECAST.ID, RELEASE_FORECAST.NAME).from(RELEASE_FORECAST).where(RELEASE_FORECAST.ID.eq(ConvertUtility.getTimestamp(locatDateTime))).fetchOne();
        final LocalDateTime localDateTime = ConvertUtility.getLocalDateTime(record.getValue(RELEASE_FORECAST.ID));
        return new ReleaseForecast(localDateTime, record.getValue(RELEASE_FORECAST.NAME), productBacklogMapper.get(locatDateTime), null, null);
    }
}
