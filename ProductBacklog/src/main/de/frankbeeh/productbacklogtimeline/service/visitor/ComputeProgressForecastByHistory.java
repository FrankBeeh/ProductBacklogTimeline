package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.text.DecimalFormat;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public abstract class ComputeProgressForecastByHistory implements SprintDataVisitor {
    private final String progressForecastName;
    private int clientCount;
    private Double historicVelocity;
    private SprintDataVisitor computeAccumulatedProgressForecast;

    protected ComputeProgressForecastByHistory(String progressForecastName) {
        this.progressForecastName = progressForecastName;
        computeAccumulatedProgressForecast = new ComputeAccumulatedProgressForecast(progressForecastName);
        reset();
    }

    protected abstract double computeResultingVelocity(double velocityOfThisSprint, double historicVelocity, int clientCount);

    public final String getProgressForecastName() {
        return progressForecastName;
    }

    @Override
    public final void reset() {
        historicVelocity = null;
        clientCount = 0;
        computeAccumulatedProgressForecast.reset();
    }

    @Override
    public void visit(SprintData sprintData) {
        clientCount++;
        final Double velocityOfThisSprint = computeVelocityOfThisSprint(sprintData);
        sprintData.setProgressForecastBasedOnHistory(getProgressForecastName(), computeProgressForecastOfThisSprint(sprintData, velocityOfThisSprint));
        computeAccumulatedProgressForecast.visit(sprintData);
    }

    private Double getResultingVelocity(Double velocityOfThisSprint) {
        if (historicVelocity == null) {
            return velocityOfThisSprint;
        }
        if (velocityOfThisSprint == null) {
            return historicVelocity;
        }
        return computeResultingVelocity(velocityOfThisSprint.doubleValue(), historicVelocity.doubleValue(), clientCount);
    }

    private Double computeProgressForecastOfThisSprint(SprintData sprintData, Double velocityOfThisSprint) {
        historicVelocity = getResultingVelocity(velocityOfThisSprint);
        Double effortForecast = null;
        if (historicVelocity != null) {
            effortForecast = computeProgressForecast(historicVelocity, sprintData.getCapacityDone());
            if (effortForecast == null) {
                effortForecast = computeProgressForecast(historicVelocity, sprintData.getCapacityForecast());
            }
        }
        return effortForecast;
    }

    private Double computeProgressForecast(Double velocity, Double capacity) {
        if (capacity == null) {
            return null;
        }
        return round(velocity.doubleValue() * capacity.doubleValue());
    }

    private Double computeVelocityOfThisSprint(SprintData sprintData) {
        Double velocity = null;
        final Double capacityDone = sprintData.getCapacityDone();
        final Double effortDone = sprintData.getEffortDone();
        velocity = computeVelocity(capacityDone, effortDone);
        if (velocity == null) {
            velocity = computeVelocity(sprintData.getCapacityForecast(), sprintData.getEffortForecast());
        }
        return velocity;
    }

    private Double computeVelocity(final Double capacity, final Double effort) {
        if (capacity == null || effort == null) {
            return null;
        }
        return effort.doubleValue() / capacity.doubleValue();
    }

    private Double round(double value) {
        return Double.parseDouble(new DecimalFormat("#.#").format(value));
    }

    // Visible for testing
    void setComputeAccumulatedProcessForecastVisitor(SprintDataVisitor computeAccumulatedProgressForecastMock) {
        computeAccumulatedProgressForecast = computeAccumulatedProgressForecastMock;
    }
}
