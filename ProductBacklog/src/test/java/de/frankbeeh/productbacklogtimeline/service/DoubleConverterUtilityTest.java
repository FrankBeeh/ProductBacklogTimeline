package de.frankbeeh.productbacklogtimeline.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DoubleConverterUtilityTest {

    private DoubleConverterUtility doubleConverterUtility;

    @Before
    public void setUp() {
        doubleConverterUtility = new DoubleConverterUtility();
    }

    @Test
    public void formatDouble() throws Exception {
        final String expectedResult = "4.0";
        final String actualResult = doubleConverterUtility.format(4.0D);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatEmptyDouble() throws Exception {
        final String expectedResult = "";
        final String actualResult = doubleConverterUtility.format(null);

        assertEquals(expectedResult, actualResult);
    }

}
