package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public abstract class ComputeEffortForecastBaseTest {

    private static final double EFFORT_FORECAST_1 = 7d;
    private static final double CAPACITY_FORECAST_1 = 11d;
    private static final double VELOCITY_FORECAST_1 = EFFORT_FORECAST_1 / CAPACITY_FORECAST_1;
    private static final double EFFORT_DONE_1 = 8d;
    private static final double CAPACITY_DONE_1 = 13d;
    private static final double VELOCITY_DONE_1 = EFFORT_DONE_1 / CAPACITY_DONE_1;

    private static final double CAPACITY_FORECAST_2 = 10d;
    private static final double EFFORT_FORECAST_2 = 5d;
    private static final double VELOCITY_FORECAST_2 = EFFORT_FORECAST_2 / CAPACITY_FORECAST_2;
    private static final double CAPACITY_DONE_2 = 6d;
    private static final double EFFORT_DONE_2 = 2d;
    private static final double VELOCITY_DONE_2 = EFFORT_DONE_2 / CAPACITY_DONE_2;

    private static final double EFFORT_FORECAST_3 = 11d;
    private static final double CAPACITY_FORECAST_3 = 14d;
    private static final double VELOCITY_FORECAST_3 = EFFORT_FORECAST_3 / CAPACITY_FORECAST_3;
    private static final double EFFORT_DONE_3 = 12d;
    private static final double CAPACITY_DONE_3 = 9d;
    private static final double VELOCITY_DONE_3 = EFFORT_DONE_3 / CAPACITY_DONE_3;
    private ComputeEffortForecastByHistory visitor;

    public ComputeEffortForecastBaseTest() {
        super();
    }

    protected abstract double getExpectedVelocity(final double... velocities);

    protected abstract ComputeEffortForecastByHistory createVisitor();

    @Test
    public void visitFirst_capacityForecastMissing() {
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitFirst_effortForecastMissing() {
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_1, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitFirst_capacityDoneMissing() {
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_1 * getExpectedVelocity(VELOCITY_FORECAST_1), sprintData);
    }

    @Test
    public void visitFirst_effortDoneMissing() {
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_1 * getExpectedVelocity(VELOCITY_FORECAST_1), sprintData);
    }

    @Test
    public void visitFirst_capacityAndEffortDone() {
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_1 * getExpectedVelocity(VELOCITY_DONE_1), sprintData);
    }

    @Test
    public void visitSecond_capacityForecastMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitSecond_effortForecastMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_2, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_2 * getExpectedVelocity(VELOCITY_DONE_1), sprintData);
    }

    @Test
    public void visitSecond_capacityDoneMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_2 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_FORECAST_2), sprintData);
    }

    @Test
    public void visitSecond_effortDoneMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_2 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_FORECAST_2), sprintData);
    }

    @Test
    public void visitSecond_capacityAndEffortDone() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_2 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2), sprintData);
    }

    @Test
    public void visitThird_capacityForecastMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final SprintData sprintData = new SprintData(null, null, null, null, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitThird_effortForecastMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_3, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2), sprintData);
    }

    @Test
    public void visitThird_capacityDoneMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_FORECAST_3), sprintData);
    }

    @Test
    public void visitThird_effortDoneMissing() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_FORECAST_3), sprintData);
    }

    @Test
    public void visitThird_capacityAndEffortDone() {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3, EFFORT_DONE_3);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_DONE_3), sprintData);
    }

    @Test
    public void reset() throws Exception {
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.reset();
        visitor.visit(new SprintData(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final SprintData sprintData = new SprintData(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3, EFFORT_DONE_3);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_3 * getExpectedVelocity(VELOCITY_DONE_2, VELOCITY_DONE_3), sprintData);
    }

    @Before
    public void setUp() {
        visitor = createVisitor();
    }

    private void assertEqualsEffortForecastBasedOnHistory(Double expectedEffortForecast, final SprintData sprintData) {
        assertEquals(expectedEffortForecast, sprintData.getEffortForecastBasedOnHistory(visitor.getHistoryForecastName()));
    }

}