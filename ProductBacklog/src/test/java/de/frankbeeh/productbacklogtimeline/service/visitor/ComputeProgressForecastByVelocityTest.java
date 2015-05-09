package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.same;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;

@RunWith(EasyMockRunner.class)
public class ComputeProgressForecastByVelocityTest extends EasyMockSupport {

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
    private ComputeProgressForecastByVelocity visitor;

    @Mock
    private SprintDataVisitor computeAccumulatedProgressForecastMock;

    public ComputeProgressForecastByVelocityTest() {
        super();
    }

    @Test
    public void visitFirst_capacityForecastMissing() {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitFirst_effortForecastMissing() {
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_1, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitFirst_capacityDoneMissing() {
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_1 * getExpectedVelocity(VELOCITY_FORECAST_1), sprintData);
    }

    @Test
    public void visitFirst_effortDoneMissing() {
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_1 * getExpectedVelocity(VELOCITY_FORECAST_1), sprintData);
    }

    @Test
    public void visitFirst_capacityAndEffortDone() {
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_1 * getExpectedVelocity(VELOCITY_DONE_1), sprintData);
    }

    @Test
    public void visitSecond_capacityForecastMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitSecond_effortForecastMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_2, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_2 * getExpectedVelocity(VELOCITY_DONE_1), sprintData);
    }

    @Test
    public void visitSecond_capacityDoneMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_2 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_FORECAST_2), sprintData);
    }

    @Test
    public void visitSecond_effortDoneMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_2 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_FORECAST_2), sprintData);
    }

    @Test
    public void visitSecond_capacityAndEffortDone() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_2 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2), sprintData);
    }

    @Test
    public void visitThird_capacityForecastMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(null, sprintData);
    }

    @Test
    public void visitThird_effortForecastMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_3, null, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2), sprintData);
    }

    @Test
    public void visitThird_capacityDoneMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, null, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_FORECAST_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_FORECAST_3), sprintData);
    }

    @Test
    public void visitThird_effortDoneMissing() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3, null);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_FORECAST_3), sprintData);
    }

    @Test
    public void visitThird_capacityAndEffortDone() {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3, EFFORT_DONE_3);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_3 * getExpectedVelocity(VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_DONE_3), sprintData);
    }

    @Test
    public void reset() throws Exception {
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1, EFFORT_DONE_1));
        visitor.reset();
        visitor.visit(new Sprint(null, null, null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2, EFFORT_DONE_2));
        final Sprint sprintData = new Sprint(null, null, null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3, EFFORT_DONE_3);
        visitor.visit(sprintData);
        assertEqualsEffortForecastBasedOnHistory(CAPACITY_DONE_3 * getExpectedVelocity(VELOCITY_DONE_2, VELOCITY_DONE_3), sprintData);
    }

    @Test
    public void reset_callsComputeAccumulatedProcessForecastVisitor() throws Exception {
        visitor.setComputeAccumulatedProcessForecastVisitor(computeAccumulatedProgressForecastMock);

        computeAccumulatedProgressForecastMock.reset();
        replayAll();
        visitor.reset();
        verifyAll();
    }

    @Test
    public void visit_callsComputeAccumulatedProcessForecastVisitor() throws Exception {
        final Sprint sprintData = new Sprint(null, null, null, null, null, null, null);
        visitor.setComputeAccumulatedProcessForecastVisitor(computeAccumulatedProgressForecastMock);

        computeAccumulatedProgressForecastMock.visit(same(sprintData));
        replayAll();
        visitor.visit(sprintData);
        verifyAll();
    }

    @Before
    public void setUp() {
        visitor = createVisitor();
    }

    private void assertEqualsEffortForecastBasedOnHistory(Double expectedProgressForecast, final Sprint sprintData) {
        assertEquals(round(expectedProgressForecast), sprintData.getProgressForecastBasedOnHistory(""));
    }

    private Double round(Double value) {
        if (value == null) {
            return null;
        }
        return Math.round(value * 10.0) / 10.0;
    }

    protected double getExpectedVelocity(final double... velocities) {
        double averageVelocity = 0d;
        for (final double velocity : velocities) {
            averageVelocity += velocity;
        }
        return averageVelocity / velocities.length;
    }

    public ComputeProgressForecastByVelocity createVisitor() {
        return new ComputeProgressForecastByVelocity("", new ComputeAverageVelocityStrategy());
    }
}