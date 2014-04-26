package de.frankbeeh.productbacklogtimeline.service;

import java.text.ParseException;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogFromCsvImporter extends DataFromCsvImporter<ProductBacklog> {

    private static final String NAME_OF_ID_COLUMN = "Id";
    private static final String NAME_OF_TITLE_COLUMN = "Title";
    private static final String NAME_OF_DESCRIPTION_COLUMN = "Summary";
    private static final String NAME_OF_ESTIMATE_COLUMN = "Estimate";

    @Override
    public void addItem(final ProductBacklog productBacklog) throws ParseException {
        final String title = getString(NAME_OF_TITLE_COLUMN);
        final String id = getString(NAME_OF_ID_COLUMN);
        final String description = getString(NAME_OF_DESCRIPTION_COLUMN);
        final Double estimate = getDouble(NAME_OF_ESTIMATE_COLUMN);
        productBacklog.addItem(new ProductBacklogItem(id, title, description, estimate));
    }

    @Override
    public ProductBacklog createContainer() {
        return new ProductBacklog();
    }
}
