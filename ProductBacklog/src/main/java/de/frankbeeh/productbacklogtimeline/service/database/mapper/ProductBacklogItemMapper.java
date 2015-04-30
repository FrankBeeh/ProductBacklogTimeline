package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.PBI;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemData;

/**
 * Responsibility:
 * <ul>
 * <li>Maps a {@link ProductBacklogItemData} into the Database.
 * </ul>
 */
public class ProductBacklogItemMapper {
    private final Connection connection;

    public ProductBacklogItemMapper(Connection connection) {
        this.connection = connection;
    }

    public void insert(ProductBacklogItemData productBacklogItemData) {
        getDslContext().insertInto(PBI, PBI.HASH, PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).values(productBacklogItemData.getHash(),
                productBacklogItemData.getId(), productBacklogItemData.getTitle(), productBacklogItemData.getDescription(), productBacklogItemData.getEstimate(),
                productBacklogItemData.getState().toString(), productBacklogItemData.getSprint(), productBacklogItemData.getRank(), productBacklogItemData.getPlannedRelease()).execute();
    }

    public ProductBacklogItemData get(String id, String hash) {
        return getDslContext().select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE).from(PBI).where(PBI.HASH.eq(hash)).and(PBI.ID.eq(id)).fetchOne().into(
                ProductBacklogItemData.class);
    }

    private DSLContext getDslContext() {
        return DSL.using(connection, SQLDialect.SQLITE);
    }
}
