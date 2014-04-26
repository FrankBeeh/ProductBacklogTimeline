package de.frankbeeh.productbacklogtimeline.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public abstract class DataFromCsvImporter<T> {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.getDefault());
    private static final char SPLIT_BY = ';';

    protected abstract T createContainer();

    protected abstract void addItem(final T container, final Map<String, Integer> mapColumnNameToColumnIndex, String[] values) throws ParseException;

    public T importProductBacklog(Reader reader) throws IOException, ParseException {
        final T container = createContainer();
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final CSVReader csvReader = new CSVReader(bufferedReader, SPLIT_BY);
        try {
            final String[] columnNames = csvReader.readNext();
            if (columnNames != null) {
                final Map<String, Integer> mapColumnNameToColumnIndex = mapColumnNameToColumnIndex(columnNames);
                String[] values;
                while ((values = csvReader.readNext()) != null) {
                    addItem(container, mapColumnNameToColumnIndex, values);
                }
            }
        } finally {
            csvReader.close();
        }
        return container;
    }

    protected double toDoubleValue(final String value) throws ParseException {
        return NUMBER_FORMAT.parse(value).doubleValue();
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