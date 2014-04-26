package de.frankbeeh.productbacklogtimeline.service;

import java.text.ParseException;
import java.util.Map;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogFromCsvImporter extends DataFromCsvImporter<ProductBacklog> {

    private static final String NAME_OF_ID_COLUMN = "Id";
    private static final String NAME_OF_TITLE_COLUMN = "Title";
    private static final String NAME_OF_DESCRIPTION_COLUMN = "Summary";
    private static final String NAME_OF_ESTIMATE_COLUMN = "Estimate";

    @Override
    public void addItem(final ProductBacklog productBacklog, final Map<String, Integer> mapColumnNameToColumnIndex, String[] values) throws ParseException {
        final String title = parseTitle(mapColumnNameToColumnIndex, values);
        final String id = parseId(mapColumnNameToColumnIndex, values);
        final String description = parseDescription(mapColumnNameToColumnIndex, values);
        final Double estimate = parseEstimate(mapColumnNameToColumnIndex, values);
        productBacklog.addItem(new ProductBacklogItem(id, title, description, estimate));
    }

    @Override
    public ProductBacklog createContainer() {
        return new ProductBacklog();
    }

    private String parseId(final Map<String, Integer> mapColumnNameToColumnIndex, final String[] values) {
        return values[mapColumnNameToColumnIndex.get(NAME_OF_ID_COLUMN)];
    }

    private String parseTitle(final Map<String, Integer> mapColumnNameToColumnIndex, final String[] values) {
        return values[mapColumnNameToColumnIndex.get(NAME_OF_TITLE_COLUMN)];
    }

    private String parseDescription(final Map<String, Integer> mapColumnNameToColumnIndex, final String[] values) {
        return values[mapColumnNameToColumnIndex.get(NAME_OF_DESCRIPTION_COLUMN)];
    }

    private Double parseEstimate(final Map<String, Integer> mapColumnNameToColumnIndex, final String[] values) throws ParseException {
        final String estimate = values[mapColumnNameToColumnIndex.get(NAME_OF_ESTIMATE_COLUMN)];
        if (estimate.isEmpty()) {
            return null;
        }
        return toDoubleValue(estimate);
    }
}
