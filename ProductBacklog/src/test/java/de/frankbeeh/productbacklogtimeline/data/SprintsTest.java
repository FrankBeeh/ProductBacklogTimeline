package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

@RunWith(EasyMockRunner.class)
public class SprintsTest extends EasyMockSupport {

    private static final String FORECAST_NAME = "forecast name";
    private static final double PROGRESS_FORECAST_SPRINT_1 = 5d;
    private static final double PROGRESS_FORECAST_SPRINT_2 = 10d;

    @Mock(name = "visitorMock1")
    private SprintDataVisitor visitorMock1;
    @Mock(name = "visitorMock2")
    private SprintDataVisitor visitorMock2;

    private SprintData sprint1;
    private SprintData sprint2;

    private VelocityForecast velocityForecast;

    @Test
    public void getSortIndex() throws Exception {
        assertEquals(1, velocityForecast.getSortIndex(sprint1.getName()));
        assertEquals(2, velocityForecast.getSortIndex(sprint2.getName()));
        assertEquals(2, velocityForecast.getSortIndex(sprint1.getName() + ", " + sprint2.getName()));
        assertEquals(Integer.MAX_VALUE, velocityForecast.getSortIndex("other name"));
    }

    @Test
    public void updateAllSprints() {
        velocityForecast = new VelocityForecast(visitorMock1, visitorMock2);
        velocityForecast.addItem(sprint1);
        velocityForecast.addItem(sprint2);
        visitorMock1.reset();
        visitorMock1.visit(sprint1);
        visitorMock1.visit(sprint2);
        visitorMock2.reset();
        visitorMock2.visit(sprint1);
        visitorMock2.visit(sprint2);
        replayAll();
        velocityForecast.updateForecast();
        verifyAll();
    }

    @Test
    public void visit_effortSmallerThanProgressForecastOfSprint1() {
        assertEquals(sprint1, velocityForecast.getCompletionSprintForecast(FORECAST_NAME, PROGRESS_FORECAST_SPRINT_1 - 1));
    }

    @Test
    public void visit_effortEqualToProgressForecastOfSprint1() {
        assertEquals(sprint1, velocityForecast.getCompletionSprintForecast(FORECAST_NAME, PROGRESS_FORECAST_SPRINT_1));
    }

    @Test
    public void visit_effortGreaterThanProgressForecastOfSprint1() {
        assertEquals(sprint2, velocityForecast.getCompletionSprintForecast(FORECAST_NAME, PROGRESS_FORECAST_SPRINT_1 + 1));
    }

    @Test
    public void effortGreaterThanProgressForecastOfLastSprint() {
        assertEquals(null, velocityForecast.getCompletionSprintForecast(FORECAST_NAME, PROGRESS_FORECAST_SPRINT_2 + 1));
    }

    @Before
    public void setUp() {
        velocityForecast = new VelocityForecast();
        sprint1 = createSprintData("Sprint 1", PROGRESS_FORECAST_SPRINT_1, null);
        velocityForecast.addItem(sprint1);
        sprint2 = createSprintData("Sprint 2", null, PROGRESS_FORECAST_SPRINT_2);
        velocityForecast.addItem(sprint2);
    }

    private SprintData createSprintData(String sprintName, Double accumulatedEffortDone, Double progressForecast) {
        final SprintData sprintData = new SprintData(sprintName, null, null, null, null, null, null);
        sprintData.setAccumulatedEffortDone(accumulatedEffortDone);
        sprintData.setAccumulatedProgressForecastBasedOnHistory(FORECAST_NAME, progressForecast);
        return sprintData;
    }
}
