package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeProgressForecastByMinimumVelocity extends ComputeProgressForecastByHistory {

    public static final String HISTORY_FORECAST_NAME = "Min. Vel.";

    public ComputeProgressForecastByMinimumVelocity() {
        super(HISTORY_FORECAST_NAME);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return Math.min(velocityOfThisSprint, historicVelocity);
    }
}
