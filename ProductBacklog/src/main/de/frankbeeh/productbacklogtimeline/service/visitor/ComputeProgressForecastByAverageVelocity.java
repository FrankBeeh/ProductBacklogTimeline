package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeProgressForecastByAverageVelocity extends ComputeProgressForecastByHistory {
    public ComputeProgressForecastByAverageVelocity(String progressForecastName) {
        super(progressForecastName);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return (historicVelocity * (clientCount - 1) + velocityOfThisSprint) / clientCount;
    }
}
