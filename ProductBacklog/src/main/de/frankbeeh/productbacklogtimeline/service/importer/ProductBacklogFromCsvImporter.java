package de.frankbeeh.productbacklogtimeline.service.importer;

import java.text.ParseException;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.State;

public class ProductBacklogFromCsvImporter extends DataFromCsvImporter<ProductBacklog> {

    private static final String NAME_OF_ID_COLUMN = "Key";
    private static final String NAME_OF_TITLE_COLUMN = "Summary";
    private static final String NAME_OF_DESCRIPTION_COLUMN = "Description";
    private static final String NAME_OF_ESTIMATE_COLUMN = "Story Points";
    private static final String NAME_OF_STATE_COLUMN = "Status";

    @Override
    public void addItem(final ProductBacklog productBacklog) throws ParseException {
        final String id = getString(NAME_OF_ID_COLUMN);
        final String title = getString(NAME_OF_TITLE_COLUMN);
        final String description = getString(NAME_OF_DESCRIPTION_COLUMN);
        final Double estimate = getDouble(NAME_OF_ESTIMATE_COLUMN);
        final State state = getState(NAME_OF_STATE_COLUMN);
        productBacklog.addItem(new ProductBacklogItem(id, title, description, estimate, state));
    }

    @Override
    public ProductBacklog createContainer() {
        return new ProductBacklog();
    }
}
