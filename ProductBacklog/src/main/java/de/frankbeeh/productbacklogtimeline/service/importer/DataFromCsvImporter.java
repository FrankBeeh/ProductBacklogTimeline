package de.frankbeeh.productbacklogtimeline.service.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.DateConverter;

public abstract class DataFromCsvImporter<T> {

    private static final String RESOLUTION_FIXED = "Fixed";
    private static final String RESOLUTION_UNRESOLVED = "Unresolved";
    private static final String RESOLUTION_WONT_FIX = "Won't Fix";
    private static final String RESOLUTION_DUPLICATE = "Duplicate";

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.UK);
    private static final char SPLIT_BY = ';';

    private static final Map<String, State> stateMap = new HashMap<String, State>();

    private Map<String, Integer> mapColumnNameToColumnIndex;
    private String[] values;

    static{
        stateMap.put(RESOLUTION_FIXED, State.Done);
        stateMap.put(RESOLUTION_UNRESOLVED, State.Todo);
        stateMap.put(RESOLUTION_WONT_FIX, State.Canceled);
        stateMap.put(RESOLUTION_DUPLICATE, State.Canceled);
    }

    protected DataFromCsvImporter() {
    }

    protected abstract T createContainer();

    protected abstract void addItem(final T container);

    public final T importData(Reader reader) throws IOException {
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

    protected final Double getDouble(String columnName) {
        final String value = getString(columnName);
        if (value.isEmpty()) {
            return null;
        }
        try {
            return NUMBER_FORMAT.parse(value).doubleValue();
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    protected Integer getInteger(String columnName) {
        final String value = getString(columnName);
        if (value.isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }

    protected final LocalDate getLocalDate(String columnName) {
        final String value = getString(columnName);
        if (value.isEmpty()) {
            return null;
        }
        return DateConverter.parseLocalDate(value);
    }

    protected final State getState(String columnName) {
        return parseState(getString(columnName));
    }

	public static State parseState(final String string) {
		return stateMap.get(string);
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