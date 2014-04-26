package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeEffortForecastByMaximumVelocity extends ComputeEffortForecastByHistory {

    public static final String HISTORY_FORECAST_NAME = "Maximum Velocity";

    public ComputeEffortForecastByMaximumVelocity() {
        super(HISTORY_FORECAST_NAME);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return Math.max(velocityOfThisSprint, historicVelocity);
    }
}
