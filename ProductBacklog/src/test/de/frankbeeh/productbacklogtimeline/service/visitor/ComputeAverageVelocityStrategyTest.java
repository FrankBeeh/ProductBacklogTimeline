package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ComputeAverageVelocityStrategyTest {
    private ComputeAverageVelocityStrategy strategy;

    @Test
    public void computeVelocity_secondVisit() {
        final double historicVelocity = 10d;
        final double velocityOfThisSprint = 5d;
        assertEquals((historicVelocity + velocityOfThisSprint) / 2, strategy.computeVelocity(velocityOfThisSprint, historicVelocity, 2));
    }

    @Test
    public void computeVelocity_thirdVisit() {
        setUp();
        final double historicVelocity = 10d;
        final double velocityOfThisSprint = 5d;
        assertEquals((2 * historicVelocity + velocityOfThisSprint) / 3, strategy.computeVelocity(velocityOfThisSprint, historicVelocity, 3));
    }

    @Before
    public void setUp() {
        strategy = new ComputeAverageVelocityStrategy();
    }
}
