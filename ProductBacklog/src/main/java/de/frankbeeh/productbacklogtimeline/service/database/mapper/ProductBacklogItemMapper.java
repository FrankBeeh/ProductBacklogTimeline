package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBI;

import java.sql.Connection;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemData;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ProductBacklogItemData} into the Database.
 * </ul>
 */
public class ProductBacklogItemMapper extends BaseMapper {
    public ProductBacklogItemMapper(Connection connection) {
        super(connection);
    }

    public void insert(ProductBacklogItem productBacklogItem) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (notYetInserted(productBacklogItem)) {
            getDslContext().insertInto(PBI, PBI.HASH, PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).values(productBacklogItem.getHash(),
                    productBacklogItem.getId(), productBacklogItem.getTitle(), productBacklogItem.getDescription(), productBacklogItem.getEstimate(), productBacklogItem.getState().toString(),
                    productBacklogItem.getSprint(), productBacklogItem.getRank(), productBacklogItem.getPlannedRelease()).execute();
        }
    }

    private boolean notYetInserted(ProductBacklogItem productBacklogItem) {
        return getDslContext().select(PBI.HASH).from(PBI).where(PBI.ID.eq(productBacklogItem.getId()).and(PBI.HASH.eq(productBacklogItem.getHash()))).fetchOne() == null;
    }

    public ProductBacklogItem get(String id, String hash) {
        return getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(PBI).where(PBI.HASH.eq(hash)).and(PBI.ID.eq(id)).fetchOne().into(
                ProductBacklogItem.class);
    }
}
