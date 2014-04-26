package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeEffortForecastByAverageVelocity extends ComputeEffortForecastByHistory {

    public static final String HISTORY_FORECAST_NAME = "Average Velocity";

    public ComputeEffortForecastByAverageVelocity() {
        super(HISTORY_FORECAST_NAME);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return (historicVelocity * (clientCount - 1) + velocityOfThisSprint) / clientCount;
    }
}
