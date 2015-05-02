package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class SprintDataTest {

    private static final String SPRINT_NAME_1 = "Sprint name 1";
    private static final String SPRINT_NAME_2 = "Sprint name 2";
    private static final String FORECAST_NAME = "forecast name";
    private static final Double PROGRESS_FORECAST = 10d;
    private static final Double ACCUMULATED_EFFORT_DONE = 5d;

    @Test
    public void setProgressForecastBasedOnHistory_isRounding() throws Exception {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setProgressForecastBasedOnHistory(FORECAST_NAME, 1.23d);
        assertEquals(Double.valueOf(1.2d), sprintData.getProgressForecastBasedOnHistory(FORECAST_NAME));
    }

    @Test
    public void setAccumulatedProgressForecastBasedOnHistory_isRounding() throws Exception {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, 2.59d);
        assertEquals(Double.valueOf(2.6d), sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getAccumulatedEffortDoneOrProgressForcast_none() {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, null);
        assertNull(sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getAccumulatedEffortDoneOrProgressForcast_effortDone() {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(ACCUMULATED_EFFORT_DONE);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, PROGRESS_FORECAST);
        assertEquals(ACCUMULATED_EFFORT_DONE, sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getAccumulatedEffortDoneOrProgressForcast_progressForecast() {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, PROGRESS_FORECAST);
        assertEquals(PROGRESS_FORECAST, sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
    }

    @Test
    public void getDescription_withEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n" + endDate, sprintData.getComparedForecast(null));
    }

    @Test
    public void getDescription_noEndDate() throws Exception {
        final SprintData sprintData = newSprintDataWithName(SPRINT_NAME_1);
        assertEquals(SPRINT_NAME_1, sprintData.getComparedForecast(null));
    }

    @Test
    public void getDescription_sameReferenceSprint() throws Exception {
        final String endDate = "01.02.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n" + endDate, sprintData.getComparedForecast(sprintData));
    }

    @Test
    public void getDescription_laterReferenceEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final String referenceEndDate = "02.02.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        final SprintData referenceSprintData = new SprintData(SPRINT_NAME_2, null, FormatUtility.parseLocalDate(referenceEndDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + endDate + "\n(-1d)", sprintData.getComparedForecast(referenceSprintData));
    }

    @Test
    public void getDescription_earlierReferenceEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final String referenceEndDate = "01.01.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        final SprintData referenceSprintData = new SprintData(SPRINT_NAME_2, null, FormatUtility.parseLocalDate(referenceEndDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + endDate + "\n(+31d)", sprintData.getComparedForecast(referenceSprintData));
    }

    @Test
    public void hashName() {
        assertEquals(newSprintDataWithName(SPRINT_NAME_1).getHash(), newSprintDataWithName(SPRINT_NAME_1).getHash());
        assertNotEquals(newSprintDataWithName(SPRINT_NAME_1).getHash(), newSprintDataWithName(SPRINT_NAME_2).getHash());
    }

    @Test
    public void hashStartDate() {
        assertEquals(newSprintDataWithStartDate(LocalDate.now()).getHash(), newSprintDataWithStartDate(LocalDate.now()).getHash());
        assertNotEquals(newSprintDataWithStartDate(LocalDate.now()).getHash(), newSprintDataWithStartDate(LocalDate.now().plusDays(1)).getHash());
    }

    @Test
    public void hashEndDate() {
        assertEquals(newSprintDataWithEndDate(LocalDate.now()).getHash(), newSprintDataWithEndDate(LocalDate.now()).getHash());
        assertNotEquals(newSprintDataWithEndDate(LocalDate.now()).getHash(), newSprintDataWithEndDate(LocalDate.now().plusDays(1)).getHash());
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

    private SprintData newSprintDataWithName(String name) {
        return new SprintData(name, null, null, null, null, null, null);
    }

    private SprintData newSprintDataWithStartDate(LocalDate startDate) {
        return new SprintData(null, startDate, null, null, null, null, null);
    }

    private SprintData newSprintDataWithEndDate(LocalDate endDate) {
        return new SprintData(null, null, endDate, null, null, null, null);
    }

    private SprintData newSprintDataWithCapacityForecast(double capacityForecast) {
        return new SprintData(null, null, null, capacityForecast, null, null, null);
    }

    private SprintData newSprintDataWithEffortForecast(double effortForecast) {
        return new SprintData(null, null, null, null, effortForecast, null, null);
    }

    private SprintData newSprintDataWithCapacityDone(double capacityDone) {
        return new SprintData(null, null, null, null, null, capacityDone, null);
    }

    private SprintData newSprintDataWithEffortDone(double effortDone) {
        return new SprintData(null, null, null, null, null, null, effortDone);
    }
}
