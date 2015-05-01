package de.frankbeeh.productbacklogtimeline.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ConvertUtility {
    public static Timestamp getTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
