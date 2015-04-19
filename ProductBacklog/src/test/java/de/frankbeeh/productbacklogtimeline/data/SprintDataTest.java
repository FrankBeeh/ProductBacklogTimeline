package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

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
        assertEquals(1.2d, sprintData.getProgressForecastBasedOnHistory(FORECAST_NAME));
    }

    @Test
    public void setAccumulatedProgressForecastBasedOnHistory_isRounding() throws Exception {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, 2.59d);
        assertEquals(2.6d, sprintData.getAccumulatedEffortDoneOrProgressForcast(FORECAST_NAME));
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
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseDate(endDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n" + endDate, sprintData.getComparedForecast(null));
    }

    @Test
    public void getDescription_noEndDate() throws Exception {
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, null, null, null, null, null);
        assertEquals(SPRINT_NAME_1, sprintData.getComparedForecast(null));
    }

    @Test
    public void getDescription_sameReferenceSprint() throws Exception {
        final String endDate = "01.02.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseDate(endDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n" + endDate, sprintData.getComparedForecast(sprintData));
    }

    @Test
    public void getDescription_laterReferenceEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final String referenceEndDate = "02.02.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseDate(endDate), null, null, null, null);
        final SprintData referenceSprintData = new SprintData(SPRINT_NAME_2, null, FormatUtility.parseDate(referenceEndDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + endDate + "\n(-1d)", sprintData.getComparedForecast(referenceSprintData));
    }

    @Test
    public void getDescription_earlierReferenceEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final String referenceEndDate = "01.01.2003";
        final SprintData sprintData = new SprintData(SPRINT_NAME_1, null, FormatUtility.parseDate(endDate), null, null, null, null);
        final SprintData referenceSprintData = new SprintData(SPRINT_NAME_2, null, FormatUtility.parseDate(referenceEndDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + endDate + "\n(+31d)", sprintData.getComparedForecast(referenceSprintData));
    }

}
