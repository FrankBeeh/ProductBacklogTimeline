package de.frankbeeh.productbacklog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class ProductBacklogFromCsvReader {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.getDefault());
    private static final String NAME_OF_ID_COLUMN = "Id";
    private static final String NAME_OF_TITLE_COLUMN = "Title";
    private static final String NAME_OF_DESCRIPTION_COLUMN = "Summary";
    private static final String NAME_OF_ESTIMATE_COLUMN = "Estimate";
    private static final char SPLIT_BY = ';';

    public ProductBacklog readProductBacklog(Reader reader) throws IOException, ParseException {
        final ProductBacklog productBacklog = new ProductBacklog();
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final CSVReader csvReader = new CSVReader(bufferedReader, SPLIT_BY);
        try {
            final String[] columnNames = csvReader.readNext();
            if (columnNames != null) {
                final Map<String, Integer> mapColumnNameToColumnIndex = mapColumnNameToColumnIndex(columnNames);
                String[] values;
                while ((values = csvReader.readNext()) != null) {
                    final String title = parseTitle(mapColumnNameToColumnIndex, values);
                    final String id = parseId(mapColumnNameToColumnIndex, values);
                    final String description = parseDescription(mapColumnNameToColumnIndex, values);
                    final Double estimate = parseEstimate(mapColumnNameToColumnIndex, values);
                    productBacklog.addItem(new ProductBacklogItem(id, title, description, estimate));
                }
            }
        } finally {
            csvReader.close();
        }
        return productBacklog;
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
        return NUMBER_FORMAT.parse(estimate).doubleValue();
    }

    private Map<String, Integer> mapColumnNameToColumnIndex(String[] columnNames) {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            final String columnName = columnNames[columnIndex];
            map.put(columnName, columnIndex);
        }
        return map;
    }
}
