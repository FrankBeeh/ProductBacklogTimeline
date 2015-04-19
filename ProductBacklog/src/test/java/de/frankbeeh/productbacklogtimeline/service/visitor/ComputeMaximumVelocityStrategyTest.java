package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ComputeMaximumVelocityStrategyTest {
    private static final double GREATER_VELOCITY = 2d;
    private static final double SMALLER_VELOCITY = 1d;

    private ComputeMaximumVelocityStrategy strategy;

    @Test
    public void computeVelocity_velocityOfThisSprintIsSmaller() {
        assertEquals(GREATER_VELOCITY, strategy.computeVelocity(SMALLER_VELOCITY, GREATER_VELOCITY, 1), 1e-10);
    }

    @Test
    public void computeVelocity_velocityOfThisSprintIsGreater() {
        assertEquals(GREATER_VELOCITY, strategy.computeVelocity(GREATER_VELOCITY, SMALLER_VELOCITY, 1), 1e-10);
    }

    @Before
    public void setUp() {
        strategy = new ComputeMaximumVelocityStrategy();
    }
}
