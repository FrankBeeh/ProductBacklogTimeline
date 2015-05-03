package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBI;
import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBL;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.service.ConvertUtility;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ProductBacklog} into the Database.
 * </ul>
 */
public class ProductBacklogMapper extends BaseMapper {
    private final ProductBacklogItemMapper productBacklogItemMapper;

    public ProductBacklogMapper(Connection connection) {
        super(connection);
        productBacklogItemMapper = new ProductBacklogItemMapper(connection);
    }

    public void insert(LocalDateTime productTimestampId, ProductBacklog productBacklog) {
        for (ProductBacklogItem item : productBacklog.getItems()) {
            productBacklogItemMapper.insert(item);
            if (notYetInserted(productTimestampId, item)) {
                getDslContext().insertInto(PBL, PBL.RF_ID, PBL.PBI_ID, PBL.PBI_HASH).values(ConvertUtility.getTimestamp(productTimestampId), item.getId(), item.getHash()).execute();
            }
        }
    }

    public ProductBacklog get(LocalDateTime productTimestampId) {
        final ProductBacklog productBacklog = new ProductBacklog();
        final List<ProductBacklogItem> itemDataList = getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(
                PBL.join(PBI).on(PBL.PBI_ID.eq(PBI.ID).and(PBL.PBI_HASH.eq(PBI.HASH)))).where(PBL.RF_ID.eq(ConvertUtility.getTimestamp(productTimestampId))).fetch().into(ProductBacklogItem.class);
        for (ProductBacklogItem itemData : itemDataList) {
            productBacklog.addItem(itemData);
        }
        return productBacklog;
    }

    private boolean notYetInserted(LocalDateTime productTimestampId, ProductBacklogItem productBacklogItem) {
        return getDslContext().select(PBL.RF_ID).from(PBL).where(
                PBL.RF_ID.eq(ConvertUtility.getTimestamp(productTimestampId)).and(PBL.PBI_ID.eq(productBacklogItem.getId()).and(PBL.PBI_HASH.eq(productBacklogItem.getHash())))).fetchOne() == null;
    }
}
