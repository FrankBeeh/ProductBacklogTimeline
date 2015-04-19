package de.frankbeeh.productbacklogtimeline.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtility {

    private static final DecimalFormat DIFFERENCE_DOUBLE_FORMAT = new DecimalFormat("+0.0;-0.0");
    private static final DecimalFormat DIFFERENCE_LONG_FORMAT = new DecimalFormat("+0;-0");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Date parseDate(String value) throws ParseException {
        return DATE_FORMAT.parse(value);
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
    
    public static String formatDifferenceLong(long value) {
        return DIFFERENCE_LONG_FORMAT.format(value);
    }
    
    public static String formatDifferenceDouble(final double value) {
        return DIFFERENCE_DOUBLE_FORMAT.format(value);
    }
}
