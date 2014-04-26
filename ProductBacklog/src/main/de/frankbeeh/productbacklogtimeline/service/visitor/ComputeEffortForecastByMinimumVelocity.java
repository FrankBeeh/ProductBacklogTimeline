package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeEffortForecastByMinimumVelocity extends ComputeEffortForecastByHistory {

    public static final String HISTORY_FORECAST_NAME = "Minimum Velocity";

    public ComputeEffortForecastByMinimumVelocity() {
        super(HISTORY_FORECAST_NAME);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return Math.min(velocityOfThisSprint, historicVelocity);
    }
}
