package de.frankbeeh.productbacklogtimeline.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class FormatUtilityTest {
    @Test
    public void parseLocalDate() {
        assertEquals(LocalDate.of(2015, Month.JANUARY, 1), FormatUtility.parseLocalDate("01.01.2015"));
        assertEquals(LocalDate.of(2015, Month.JANUARY, 1), FormatUtility.parseLocalDate("01.01.15"));
    }
}
