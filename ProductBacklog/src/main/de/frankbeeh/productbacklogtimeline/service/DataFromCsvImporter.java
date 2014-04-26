package de.frankbeeh.productbacklogtimeline.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public abstract class DataFromCsvImporter<T> {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.getDefault());
    private static final char SPLIT_BY = ';';
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private Map<String, Integer> mapColumnNameToColumnIndex;
    private String[] values;

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
        return values[mapColumnNameToColumnIndex.get(columnName)];
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
        return DATE_FORMAT.parse(value);
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