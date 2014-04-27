package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeProgressForecastByMinimumVelocity extends ComputeProgressForecastByHistory {
    public ComputeProgressForecastByMinimumVelocity(String progressForecastName) {
        super(progressForecastName);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return Math.min(velocityOfThisSprint, historicVelocity);
    }
}
