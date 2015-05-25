package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBI;
import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBL;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Record2;
import org.jooq.Result;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.service.DateConverter;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ProductBacklog} into the Database.
 * </ul>
 */
public class ProductBacklogMapper extends BaseMapper {

    private static final Condition JOIN_CONDITION = PBL.PBI_ID.eq(PBI.ID).and(PBL.PBI_HASH.eq(PBI.HASH));

    public ProductBacklogMapper(Connection connection) {
        super(connection);
    }

    public void insert(LocalDateTime productTimestampId, ProductBacklog productBacklog) {
        for (ProductBacklogItem item : productBacklog.getItems()) {
            insertItem(item);
            insertRelation(productTimestampId, item);
        }
    }

    public void delete(LocalDateTime productTimestampId) {
        deleteRelations(productTimestampId);
        // FIXME Is there a way to use it directly in the deleteFrom?
        final Result<Record2<String, String>> records = getDslContext().select(PBI.ID, PBI.HASH).from(PBI.leftOuterJoin(PBL).on(JOIN_CONDITION)).where(PBL.PBI_ID.isNull()).fetch();
        for (Record2<String, String> record : records) {
            getDslContext().deleteFrom(PBI).where(pbiPrimaryKeyIs(record.getValue(PBI.ID), record.getValue(PBI.HASH))).execute();
        }
    }

    public ProductBacklog get(LocalDateTime productTimestampId) {
        final ProductBacklog productBacklog = new ProductBacklog();
        final List<ProductBacklogItem> itemDataList = getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(
                PBL.join(PBI).on(JOIN_CONDITION)).where(PBL.PT_ID.eq(DateConverter.getTimestamp(productTimestampId))).fetch().into(ProductBacklogItem.class);
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
                    productBacklogItem.getJiraSprint(), productBacklogItem.getJiraRank(), productBacklogItem.getPlannedRelease()).execute();
        }
    }

    private boolean itemNotYetInserted(ProductBacklogItem productBacklogItem) {
        return getDslContext().select(PBI.HASH).from(PBI).where(pbiPrimaryKeyIs(productBacklogItem.getId(), productBacklogItem.getHash())).fetchOne() == null;
    }

    private void insertRelation(LocalDateTime productTimestampId, ProductBacklogItem item) {
        getDslContext().insertInto(PBL, PBL.PT_ID, PBL.PBI_ID, PBL.PBI_HASH).values(DateConverter.getTimestamp(productTimestampId), item.getId(), item.getHash()).execute();
    }

    private void deleteRelations(LocalDateTime productTimestampId) {
        getDslContext().deleteFrom(PBL).where(PBL.PT_ID.eq(DateConverter.getTimestamp(productTimestampId))).execute();
    }
    
    private Condition pbiPrimaryKeyIs(final String id, final String hash) {
        return PBI.ID.eq(id).and(PBI.HASH.eq(hash));
    }
}
