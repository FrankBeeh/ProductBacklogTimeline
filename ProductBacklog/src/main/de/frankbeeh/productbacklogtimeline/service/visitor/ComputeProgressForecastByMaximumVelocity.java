package de.frankbeeh.productbacklogtimeline.service.visitor;

public class ComputeProgressForecastByMaximumVelocity extends ComputeProgressForecastByHistory {
    public ComputeProgressForecastByMaximumVelocity(String progressForecastName) {
        super(progressForecastName);
    }

    @Override
    protected double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount) {
        return Math.max(velocityOfThisSprint, historicVelocity);
    }
}
