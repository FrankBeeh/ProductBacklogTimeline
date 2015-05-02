package de.frankbeeh.productbacklogtimeline.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConvertUtility {
    public static Timestamp getTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static Date getSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate getLocalDate(Date date) {
        return date.toLocalDate();
    }
}
