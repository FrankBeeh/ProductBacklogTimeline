package de.frankbeeh.productbacklogtimeline.service.visitor;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class ComputeAccumulatedProgressForecastTest {

    private static final double EFFORT_DONE_1 = 10d;
    private static final double PROGRESS_FORECAST_1 = 5d;
    private static final double PROGRESS_FORECAST_2 = 6d;
    private static final Double PROGRESS_FORECAST_3 = 7d;
    private static final double PROGRESS_FORECAST_5 = 8d;
    private static final String HISTORY_NAME = "history";

    private ComputeAccumulatedProgressForecast visitor;

    @Test
    public void visit() {
        final SprintData sprintData1 = createSprintData(EFFORT_DONE_1, PROGRESS_FORECAST_1);
        visitor.visit(sprintData1);
        assertEquals(null, sprintData1.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final SprintData sprintData2 = createSprintData(null, PROGRESS_FORECAST_2);
        visitor.visit(sprintData2);
        assertEquals(Double.valueOf(EFFORT_DONE_1 + PROGRESS_FORECAST_2), sprintData2.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final SprintData sprintData3 = createSprintData(null, PROGRESS_FORECAST_3);
        visitor.visit(sprintData3);
        assertEquals(Double.valueOf(EFFORT_DONE_1 + PROGRESS_FORECAST_2 + PROGRESS_FORECAST_3), sprintData3.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final SprintData sprintData4 = createSprintData(null, null);
        visitor.visit(sprintData4);
        assertEquals(null, sprintData4.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final SprintData sprintData5 = createSprintData(null, PROGRESS_FORECAST_5);
        visitor.visit(sprintData5);
        assertEquals(Double.valueOf(EFFORT_DONE_1 + PROGRESS_FORECAST_2 + PROGRESS_FORECAST_3 + PROGRESS_FORECAST_5), sprintData5.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));
    }

    @Test
    public void reset() throws Exception {
        final SprintData sprintData1 = createSprintData(EFFORT_DONE_1, PROGRESS_FORECAST_1);
        visitor.visit(sprintData1);
        assertEquals(null, sprintData1.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        visitor.reset();

        final SprintData sprintData2 = createSprintData(null, PROGRESS_FORECAST_2);
        visitor.visit(sprintData2);
        assertEquals(Double.valueOf(PROGRESS_FORECAST_2), sprintData2.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));
    }

    @Before
    public void setUp() {
        visitor = new ComputeAccumulatedProgressForecast(HISTORY_NAME);
    }

    private SprintData createSprintData(Double accumulatedEffortDone, Double progressForecast) {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(accumulatedEffortDone);
        sprintData.setProgressForecastBasedOnHistory(HISTORY_NAME, progressForecast);
        return sprintData;
    }
}
