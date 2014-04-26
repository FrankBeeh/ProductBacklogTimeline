package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeEffortForecastByMaximumVelocityTest extends ComputeEffortForecastBaseTest {
    @Override
    protected double getExpectedVelocity(final double... velocities) {
        double minimumVelocity = 0d;
        for (final double velocity : velocities) {
            minimumVelocity = Math.max(minimumVelocity, velocity);
        }
        return minimumVelocity;
    }

    @Override
    public ComputeEffortForecastByHistory createVisitor() {
        return new ComputeEffortForecastByMaximumVelocity();
    }
}
