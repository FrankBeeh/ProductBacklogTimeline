package de.frankbeeh.productbacklogtimeline.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtility {
    private static final DecimalFormat DIFFERENCE_DOUBLE_FORMAT = new DecimalFormat("+0.0;-0.0");
    private static final DecimalFormat DIFFERENCE_LONG_FORMAT = new DecimalFormat("+0;-0");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm.ss");
    private static final DateTimeFormatter DATE_TFORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String formatDifferenceLong(long value) {
        return DIFFERENCE_LONG_FORMAT.format(value);
    }
    
    public static String formatDifferenceDouble(final double value) {
        return DIFFERENCE_DOUBLE_FORMAT.format(value);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_TFORMATTER);
    }

    public static LocalDate parseLocalDate(String value) {
        return LocalDate.parse(value, DATE_TFORMATTER);
    }
}
