package de.frankbeeh.productbacklogtimeline.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FormatUtility {
    private static final DecimalFormat DIFFERENCE_DOUBLE_FORMAT = new DecimalFormat("+0.0;-0.0");
    private static final DecimalFormat DIFFERENCE_LONG_FORMAT = new DecimalFormat("+0;-0");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm.ss");

    public static Date parseDate(String value) {
        try {
            return DATE_FORMAT.parse(value);
        } catch (ParseException parseException) {
            throw new IllegalArgumentException(parseException);
        }
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

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        final DateTimeFormatter ofPattern = DATE_TIME_FORMATTER;
        return dateTime.format(ofPattern);
    }
}
