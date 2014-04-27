package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeProgressForecastByAverageVelocity extends ComputeProgressForecastByHistory {

    public static final String HISTORY_FORECAST_NAME = "Avg. Vel.";

    public ComputeProgressForecastByAverageVelocity() {
        super(HISTORY_FORECAST_NAME);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return (historicVelocity * (clientCount - 1) + velocityOfThisSprint) / clientCount;
    }
}
