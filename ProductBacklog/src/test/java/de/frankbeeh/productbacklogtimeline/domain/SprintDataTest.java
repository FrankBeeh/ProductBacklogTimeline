package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class SprintDataTest {
    private static final LocalDate DATE_1 = LocalDate.of(2001, Month.JANUARY, 1);
    private static final LocalDate DATE_2 = LocalDate.of(2002, Month.FEBRUARY, 2);
    private static final String SPRINT_NAME_1 = "Sprint name 1";
    private static final String SPRINT_NAME_2 = "Sprint name 2";
    private static final String FORECAST_NAME = "forecast name";
    private static final Double PROGRESS_FORECAST = 10d;
    private static final Double ACCUMULATED_EFFORT_DONE = 5d;

    @Test
    public void setProgressForecastBasedOnHistory_isRounding() throws Exception {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        sprintData.setProgressForecastBasedOnHistory(FORECAST_NAME, 1.23d);
        assertEquals(Double.valueOf(1.2d), sprintData.getProgressForecastBasedOnHistory(FORECAST_NAME));
    }

    @Test
    public void setAccumulatedProgressForecastBasedOnHistory_isRounding() throws Exception {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, 2.59d);
        assertEquals(Double.valueOf(2.6d), sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getAccumulatedEffortDoneOrProgressForcast_none() {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, null);
        assertNull(sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getAccumulatedEffortDoneOrProgressForcast_effortDone() {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(ACCUMULATED_EFFORT_DONE);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, PROGRESS_FORECAST);
        assertEquals(ACCUMULATED_EFFORT_DONE, sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getAccumulatedEffortDoneOrProgressForcast_progressForecast() {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, PROGRESS_FORECAST);
        assertEquals(PROGRESS_FORECAST, sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void hashName() {
        assertEquals(newSprintDataWithName(SPRINT_NAME_1).getHash(), newSprintDataWithName(SPRINT_NAME_1).getHash());
        assertNotEquals(newSprintDataWithName(SPRINT_NAME_1).getHash(), newSprintDataWithName(SPRINT_NAME_2).getHash());
    }

    @Test
    public void hashStartDate() {
        assertEquals(newSprintDataWithStartDate(DATE_1).getHash(), newSprintDataWithStartDate(DATE_1).getHash());
        assertNotEquals(newSprintDataWithStartDate(DATE_1).getHash(), newSprintDataWithStartDate(DATE_2).getHash());
    }

    @Test
    public void hashEndDate() {
        assertEquals(newSprintDataWithEndDate(DATE_1).getHash(), newSprintDataWithEndDate(DATE_1).getHash());
        assertNotEquals(newSprintDataWithEndDate(DATE_1).getHash(), newSprintDataWithEndDate(DATE_2).getHash());
    }

    @Test
    public void hashCapacityForecast() {
        assertEquals(newSprintDataWithCapacityForecast(1d).getHash(), newSprintDataWithCapacityForecast(1d).getHash());
        assertNotEquals(newSprintDataWithCapacityForecast(1d).getHash(), newSprintDataWithCapacityForecast(2d).getHash());
    }

    @Test
    public void hashEffortForecast() {
        assertEquals(newSprintDataWithEffortForecast(1d).getHash(), newSprintDataWithEffortForecast(1d).getHash());
        assertNotEquals(newSprintDataWithEffortForecast(1d).getHash(), newSprintDataWithEffortForecast(2d).getHash());
    }

    @Test
    public void hashCapacityDone() {
        assertEquals(newSprintDataWithCapacityDone(1d).getHash(), newSprintDataWithCapacityDone(1d).getHash());
        assertNotEquals(newSprintDataWithCapacityDone(1d).getHash(), newSprintDataWithCapacityDone(2d).getHash());
    }

    @Test
    public void hashEffortDone() {
        assertEquals(newSprintDataWithEffortDone(1d).getHash(), newSprintDataWithEffortDone(1d).getHash());
        assertNotEquals(newSprintDataWithEffortDone(1d).getHash(), newSprintDataWithEffortDone(2d).getHash());
    }

    @Test
    public void hashSameValueOfDifferentProperties() {
        assertNotEquals(newSprintDataWithStartDate(DATE_1).getHash(), newSprintDataWithEndDate(DATE_1).getHash());
        assertNotEquals(newSprintDataWithCapacityDone(1d).getHash(), newSprintDataWithCapacityForecast(1d).getHash());
        assertNotEquals(newSprintDataWithEffortDone(1d).getHash(), newSprintDataWithEffortForecast(1d).getHash());
    }

    private Sprint newSprintDataWithName(String name) {
        return new Sprint(name, null, null, null, null, null, null);
    }

    private Sprint newSprintDataWithStartDate(LocalDate startDate) {
        return new Sprint(null, startDate, null, null, null, null, null);
    }

    private Sprint newSprintDataWithEndDate(LocalDate endDate) {
        return new Sprint(null, null, endDate, null, null, null, null);
    }

    private Sprint newSprintDataWithCapacityForecast(double capacityForecast) {
        return new Sprint(null, null, null, capacityForecast, null, null, null);
    }

    private Sprint newSprintDataWithEffortForecast(double effortForecast) {
        return new Sprint(null, null, null, null, effortForecast, null, null);
    }

    private Sprint newSprintDataWithCapacityDone(double capacityDone) {
        return new Sprint(null, null, null, null, null, capacityDone, null);
    }

    private Sprint newSprintDataWithEffortDone(double effortDone) {
        return new Sprint(null, null, null, null, null, null, effortDone);
    }
}
