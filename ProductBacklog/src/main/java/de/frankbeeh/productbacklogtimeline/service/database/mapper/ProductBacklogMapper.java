package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBI;
import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBL;

import java.sql.Connection;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

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

    public void insert(int releaseForecastId, ProductBacklog productBacklog) {
        for (ProductBacklogItem item : productBacklog.getItems()) {
            productBacklogItemMapper.insert(item);
            if (notYetInserted(releaseForecastId, item)) {
                getDslContext().insertInto(PBL, PBL.ID, PBL.PBI_ID, PBL.HASH).values(releaseForecastId, item.getId(), item.getHash()).execute();
            }
        }
    }

    public ProductBacklog get(int releaseForecastId) {
        final ProductBacklog productBacklog = new ProductBacklog();
        final List<ProductBacklogItem> itemDataList = getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(
                PBL.join(PBI).on(PBL.PBI_ID.eq(PBI.ID).and(PBL.HASH.eq(PBI.HASH)))).where(PBL.ID.eq(releaseForecastId)).fetch().into(ProductBacklogItem.class);
        for (ProductBacklogItem itemData : itemDataList) {
            productBacklog.addItem(itemData);
        }
        return productBacklog;
    }

    private boolean notYetInserted(int releaseForecastId, ProductBacklogItem productBacklogItem) {
        return getDslContext().select(PBL.ID).from(PBL).where(PBL.ID.eq(releaseForecastId).and(PBL.PBI_ID.eq(productBacklogItem.getId()).and(PBL.HASH.eq(productBacklogItem.getHash())))).fetchOne() == null;
    }
}
