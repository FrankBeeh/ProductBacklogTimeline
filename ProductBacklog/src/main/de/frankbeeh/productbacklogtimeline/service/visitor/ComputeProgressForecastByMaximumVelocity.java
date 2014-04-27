package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeProgressForecastByMaximumVelocity extends ComputeProgressForecastByHistory {

    public static final String HISTORY_FORECAST_NAME = "Max. Vel.";

    public ComputeProgressForecastByMaximumVelocity() {
        super(HISTORY_FORECAST_NAME);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return Math.max(velocityOfThisSprint, historicVelocity);
    }
}
