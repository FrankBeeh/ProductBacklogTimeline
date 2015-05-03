package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.RELEASE_FORECAST;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.service.ConvertUtility;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ProductTimestamp} into the Database.
 * </ul>
 */
public class ProductTimestampMapper extends BaseMapper {
    private final ProductBacklogMapper productBacklogMapper;

    public ProductTimestampMapper(Connection connection) {
        super(connection);
        productBacklogMapper = new ProductBacklogMapper(connection);
    }

    public void insert(ProductTimestamp productTimestamp) {
        productBacklogMapper.insert(productTimestamp.getDateTime(), productTimestamp.getProductBacklog());
        getDslContext().insertInto(RELEASE_FORECAST, RELEASE_FORECAST.ID, RELEASE_FORECAST.NAME).values(ConvertUtility.getTimestamp(productTimestamp.getDateTime()), productTimestamp.getName()).execute();
    }

    public ProductTimestamp get(LocalDateTime locatDateTime) {
        final Record2<Timestamp, String> record = getDslContext().select(RELEASE_FORECAST.ID, RELEASE_FORECAST.NAME).from(RELEASE_FORECAST).where(
                RELEASE_FORECAST.ID.eq(ConvertUtility.getTimestamp(locatDateTime))).fetchOne();
        final LocalDateTime localDateTime = ConvertUtility.getLocalDateTime(record.getValue(RELEASE_FORECAST.ID));
        return new ProductTimestamp(localDateTime, record.getValue(RELEASE_FORECAST.NAME), productBacklogMapper.get(locatDateTime), null, null);
    }

    public List<LocalDateTime> getAllIds() {
        final Result<Record1<Timestamp>> results = getDslContext().select(RELEASE_FORECAST.ID).from(RELEASE_FORECAST).orderBy(RELEASE_FORECAST.ID).fetch();
        final List<LocalDateTime> ids = new ArrayList<LocalDateTime>();
        for (Record1<Timestamp> record : results) {
            ids.add(ConvertUtility.getLocalDateTime(record.getValue(RELEASE_FORECAST.ID)));
        }
        return ids;
    }
}
