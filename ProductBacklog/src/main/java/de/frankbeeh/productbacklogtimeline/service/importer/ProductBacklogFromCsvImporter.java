package de.frankbeeh.productbacklogtimeline.service.importer;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class ProductBacklogFromCsvImporter extends DataFromCsvImporter<ProductBacklog> {

    private static final String NAME_OF_RELEASE_COLUMN = "Fix Version/s";
    private static final String NAME_OF_ID_COLUMN = "Key";
    private static final String NAME_OF_TITLE_COLUMN = "Summary";
    private static final String NAME_OF_DESCRIPTION_COLUMN = "Description";
    private static final String NAME_OF_ESTIMATE_COLUMN = "Story Points";
    private static final String NAME_OF_RESOLUTION_COLUMN = "Resolution";
    private static final String NAME_OF_SPRINT_COLUMN = "Sprint";
    private static final String NAME_OF_RANK_COLUMN = "Rank";

    @Override
    public void addItem(final ProductBacklog productBacklog) {
        final String id = getString(NAME_OF_ID_COLUMN);
        final String title = getString(NAME_OF_TITLE_COLUMN);
        final String description = getString(NAME_OF_DESCRIPTION_COLUMN);
        final Double estimate = getDouble(NAME_OF_ESTIMATE_COLUMN);
        final State state = getState(NAME_OF_RESOLUTION_COLUMN);
        final String sprint = getString(NAME_OF_SPRINT_COLUMN);
        final String rank = getString(NAME_OF_RANK_COLUMN);
        final String plannedRelease = getString(NAME_OF_RELEASE_COLUMN);
        productBacklog.addItem(new ProductBacklogItem(id, title, description, estimate, state, sprint, rank, plannedRelease));
    }

    @Override
    public ProductBacklog createContainer() {
        return new ProductBacklog();
    }
}
