package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBI;

import java.sql.Connection;

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

    public void insert(ProductBacklogItemData productBacklogItemData) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (notYetInserted(productBacklogItemData)) {
            getDslContext().insertInto(PBI, PBI.HASH, PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).values(productBacklogItemData.getHash(),
                    productBacklogItemData.getId(), productBacklogItemData.getTitle(), productBacklogItemData.getDescription(), productBacklogItemData.getEstimate(),
                    productBacklogItemData.getState().toString(), productBacklogItemData.getSprint(), productBacklogItemData.getRank(), productBacklogItemData.getPlannedRelease()).execute();
        }
    }

    private boolean notYetInserted(ProductBacklogItemData productBacklogItemData) {
        return getDslContext().select(PBI.HASH).from(PBI).where(PBI.ID.eq(productBacklogItemData.getId()).and(PBI.HASH.eq(productBacklogItemData.getHash()))).fetchOne() == null;
    }

    public ProductBacklogItemData get(String id, String hash) {
        return getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(PBI).where(PBI.HASH.eq(hash)).and(PBI.ID.eq(id)).fetchOne().into(
                ProductBacklogItemData.class);
    }
}
