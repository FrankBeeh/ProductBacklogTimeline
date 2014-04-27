package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ComputeMinimumVelocityStrategyTest {
    private static final double GREATER_VELOCITY = 2d;
    private static final double SMALLER_VELOCITY = 1d;

    private ComputeMinimumVelocityStrategy strategy;

    @Test
    public void computeVelocity_velocityOfThisSprintIsSmaller() {
        assertEquals(SMALLER_VELOCITY, strategy.computeVelocity(SMALLER_VELOCITY, GREATER_VELOCITY, 1));
    }

    @Test
    public void computeVelocity_velocityOfThisSprintIsGreater() {
        assertEquals(SMALLER_VELOCITY, strategy.computeVelocity(GREATER_VELOCITY, SMALLER_VELOCITY, 1));
    }

    @Before
    public void setUp() {
        strategy = new ComputeMinimumVelocityStrategy();
    }
}
