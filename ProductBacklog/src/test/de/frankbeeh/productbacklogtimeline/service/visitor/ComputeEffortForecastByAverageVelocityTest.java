package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeEffortForecastByAverageVelocityTest extends ComputeEffortForecastBaseTest {
    @Override
    protected double getExpectedVelocity(final double... velocities) {
        double averageVelocity = 0d;
        for (final double velocity : velocities) {
            averageVelocity += velocity;
        }
        return averageVelocity / velocities.length;
    }

    @Override
    public ComputeProgressForecastByHistory createVisitor() {
        return new ComputeProgressForecastByAverageVelocity("");
    }
}
