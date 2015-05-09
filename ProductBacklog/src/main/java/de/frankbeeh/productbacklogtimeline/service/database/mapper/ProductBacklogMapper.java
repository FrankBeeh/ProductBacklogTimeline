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

    public ProductBacklogMapper(Connection connection) {
        super(connection);
    }

    public void insert(LocalDateTime productTimestampId, ProductBacklog productBacklog) {
        for (ProductBacklogItem item : productBacklog.getItems()) {
            insertItem(item);
            insertRelation(productTimestampId, item);
        }
    }

    public ProductBacklog get(LocalDateTime productTimestampId) {
        final ProductBacklog productBacklog = new ProductBacklog();
        final List<ProductBacklogItem> itemDataList = getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(
                PBL.join(PBI).on(PBL.PBI_ID.eq(PBI.ID).and(PBL.PBI_HASH.eq(PBI.HASH)))).where(PBL.PT_ID.eq(ConvertUtility.getTimestamp(productTimestampId))).fetch().into(ProductBacklogItem.class);
        for (ProductBacklogItem itemData : itemDataList) {
            productBacklog.addItem(itemData);
        }
        return productBacklog;
    }

    private void insertItem(ProductBacklogItem productBacklogItem) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (itemNotYetInserted(productBacklogItem)) {
            getDslContext().insertInto(PBI, PBI.HASH, PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).values(productBacklogItem.getHash(),
                    productBacklogItem.getId(), productBacklogItem.getTitle(), productBacklogItem.getDescription(), productBacklogItem.getEstimate(), productBacklogItem.getState().toString(),
                    productBacklogItem.getSprint(), productBacklogItem.getRank(), productBacklogItem.getPlannedRelease()).execute();
        }
    }

    private boolean itemNotYetInserted(ProductBacklogItem productBacklogItem) {
        return getDslContext().select(PBI.HASH).from(PBI).where(PBI.ID.eq(productBacklogItem.getId()).and(PBI.HASH.eq(productBacklogItem.getHash()))).fetchOne() == null;
    }

    private void insertRelation(LocalDateTime productTimestampId, ProductBacklogItem item) {
            getDslContext().insertInto(PBL, PBL.PT_ID, PBL.PBI_ID, PBL.PBI_HASH).values(ConvertUtility.getTimestamp(productTimestampId), item.getId(), item.getHash()).execute();
    }
}
