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

	// FIXME how to get the unique id back?
	public void insert(ProductBacklogItemData productBacklogItemData) {
		getDslContext()
				.insertInto(PBI, PBI.ID, PBI.TITLE, PBI.DESCRIPTION,
						PBI.ESTIMATE, PBI.STATE, PBI.SPRINT, PBI.RANK,
						PBI.PLANNED_RELEASE)
				.values(productBacklogItemData.getId(),
						productBacklogItemData.getTitle(),
						productBacklogItemData.getDescription(),
						productBacklogItemData.getEstimate(),
						productBacklogItemData.getState().toString(),
						productBacklogItemData.getSprint(),
						productBacklogItemData.getRank(),
						productBacklogItemData.getPlannedRelease()).execute();
	}

	public ProductBacklogItemData get(String id) {
		return getDslContext()
				.select(PBI.ID, PBI.TITLE, PBI.DESCRIPTION, PBI.ESTIMATE,
						PBI.STATE, PBI.SPRINT, PBI.RANK, PBI.PLANNED_RELEASE)
				.from(PBI).where(PBI.ID.eq(id)).fetchOne()
				.into(ProductBacklogItemData.class);
	}

	private DSLContext getDslContext() {
		return DSL.using(connection, SQLDialect.SQLITE);
	}
}
