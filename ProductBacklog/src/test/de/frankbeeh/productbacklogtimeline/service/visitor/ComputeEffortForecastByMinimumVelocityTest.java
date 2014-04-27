package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeEffortForecastByMinimumVelocityTest extends ComputeEffortForecastBaseTest {
    @Override
    protected double getExpectedVelocity(final double... velocities) {
        double minimumVelocity = Double.MAX_VALUE;
        for (final double velocity : velocities) {
            minimumVelocity = Math.min(minimumVelocity, velocity);
        }
        return minimumVelocity;
    }

    @Override
    public ComputeProgressForecastByHistory createVisitor() {
        return new ComputeProgressForecastByMinimumVelocity("");
    }
}
