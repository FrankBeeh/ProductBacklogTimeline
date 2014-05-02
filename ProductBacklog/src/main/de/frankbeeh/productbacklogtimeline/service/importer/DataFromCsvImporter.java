package de.frankbeeh.productbacklogtimeline.service.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import de.frankbeeh.productbacklogtimeline.data.State;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public abstract class DataFromCsvImporter<T> {

    private static final String CANCELED_STATE = "Canceled";
    private static final String DONE_STATE = "Done";
    private static final String IN_PROGRESS_STATE = "In Progress";
    private static final String TODO_STATE = "Todo";

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.UK);
    private static final char SPLIT_BY = ';';

    private final Map<String, State> stateMap;

    private Map<String, Integer> mapColumnNameToColumnIndex;
    private String[] values;

    protected DataFromCsvImporter() {
        stateMap = new HashMap<String, State>();
        stateMap.put(TODO_STATE, State.Todo);
        stateMap.put(IN_PROGRESS_STATE, State.InProgress);
        stateMap.put(DONE_STATE, State.Done);
        stateMap.put(CANCELED_STATE, State.Canceled);
    }

    protected abstract T createContainer();

    protected abstract void addItem(final T container) throws ParseException;

    public final T importData(Reader reader) throws IOException, ParseException {
        final T container = createContainer();
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final CSVReader csvReader = new CSVReader(bufferedReader, SPLIT_BY);
        try {
            final String[] columnNames = csvReader.readNext();
            if (columnNames != null) {
                mapColumnNameToColumnIndex = mapColumnNameToColumnIndex(columnNames);
                while ((values = csvReader.readNext()) != null) {
                    addItem(container);
                }
            }
        } finally {
            csvReader.close();
        }
        return container;
    }

    protected final String getString(String columnName) {
        final Integer columnIndex = mapColumnNameToColumnIndex.get(columnName);
        if (columnIndex == null) {
            throw new RuntimeException("Column '" + columnName + "' missing!");
        }
        return values[columnIndex];
    }

    protected final Double getDouble(String columnName) throws ParseException {
        final String value = getString(columnName);
        if (value.isEmpty()) {
            return null;
        }
        return NUMBER_FORMAT.parse(value).doubleValue();
    }

    protected final Date getDate(String columnName) throws ParseException {
        final String value = getString(columnName);
        if (value.isEmpty()) {
            return null;
        }
        return FormatUtility.parseDate(value);
    }

    protected final State getState(String columnName) {
        return stateMap.get(getString(columnName));
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