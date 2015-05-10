package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PRODUCT_TIMESTAMP;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.service.DateConverter;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ProductTimestamp} into the Database.
 * </ul>
 */
public class ProductTimestampMapper extends BaseMapper {
    private final ProductBacklogMapper productBacklogMapper;
    private final VelocityForecastMapper velocityForecastMapper;
    private final ReleasesMapper releasesMapper;

    public ProductTimestampMapper(Connection connection) {
        super(connection);
        productBacklogMapper = new ProductBacklogMapper(connection);
        velocityForecastMapper = new VelocityForecastMapper(connection);
        releasesMapper = new ReleasesMapper(connection);
    }

    public void insert(ProductTimestamp productTimestamp) {
        final LocalDateTime productTimestampId = productTimestamp.getDateTime();
        productBacklogMapper.insert(productTimestampId, productTimestamp.getProductBacklog());
        velocityForecastMapper.insert(productTimestampId, productTimestamp.getVelocityForecast());
        releasesMapper.insert(productTimestampId, productTimestamp.getReleaseForecast());
        insertProductTimestamp(productTimestamp);
    }

    public ProductTimestamp get(LocalDateTime productTimestampId) {
        final Record2<Timestamp, String> record = getDslContext().select(PRODUCT_TIMESTAMP.ID, PRODUCT_TIMESTAMP.NAME).from(PRODUCT_TIMESTAMP).where(
                PRODUCT_TIMESTAMP.ID.eq(DateConverter.getTimestamp(productTimestampId))).fetchOne();
        return new ProductTimestamp(productTimestampId, record.getValue(PRODUCT_TIMESTAMP.NAME), productBacklogMapper.get(productTimestampId),
                velocityForecastMapper.get(productTimestampId), releasesMapper.get(productTimestampId));
    }

    public List<LocalDateTime> getAllIds() {
        final Result<Record1<Timestamp>> results = getDslContext().select(PRODUCT_TIMESTAMP.ID).from(PRODUCT_TIMESTAMP).orderBy(PRODUCT_TIMESTAMP.ID).fetch();
        final List<LocalDateTime> ids = new ArrayList<LocalDateTime>();
        for (Record1<Timestamp> record : results) {
            ids.add(DateConverter.getLocalDateTime(record.getValue(PRODUCT_TIMESTAMP.ID)));
        }
        return ids;
    }

    private void insertProductTimestamp(ProductTimestamp productTimestamp) {
        getDslContext().insertInto(PRODUCT_TIMESTAMP, PRODUCT_TIMESTAMP.ID, PRODUCT_TIMESTAMP.NAME).values(DateConverter.getTimestamp(productTimestamp.getDateTime()), productTimestamp.getName()).execute();
    }
}
