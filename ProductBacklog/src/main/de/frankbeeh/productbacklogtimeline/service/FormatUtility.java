package de.frankbeeh.productbacklogtimeline.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtility {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Date parseDate(String value) throws ParseException {
        return DATE_FORMAT.parse(value);
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

}
