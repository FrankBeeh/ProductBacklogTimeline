package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the progress forecast using <b>velocity</b> by visiting {@link SprintData}.
 * <li>Delegate the computation of the velocity to {@link ComputeVelocityStrategy}.
 * </ul>
 */
public class ComputeProgressForecastByVelocity implements SprintDataVisitor {
    private final String progressForecastName;
    private final ComputeVelocityStrategy computeVelocityStrategy;
    private SprintDataVisitor computeAccumulatedProgressForecast;

    private int clientsVisitedCount;
    private Double historicVelocity;

    public ComputeProgressForecastByVelocity(String progressForecastName, ComputeVelocityStrategy computeVelocityStrategy) {
        this.progressForecastName = progressForecastName;
        this.computeAccumulatedProgressForecast = new ComputeAccumulatedProgressForecast(progressForecastName);
        this.computeVelocityStrategy = computeVelocityStrategy;
        reset();
    }

    @Override
    public final void reset() {
        historicVelocity = null;
        clientsVisitedCount = 0;
        computeAccumulatedProgressForecast.reset();
    }

    @Override
    public void visit(SprintData sprintData) {
        clientsVisitedCount++;
        final Double velocityOfThisSprint = computeVelocityOfThisSprint(sprintData);
        sprintData.setProgressForecastBasedOnHistory(progressForecastName, computeProgressForecastOfThisSprint(sprintData, velocityOfThisSprint));
        computeAccumulatedProgressForecast.visit(sprintData);
    }

    private Double getResultingVelocity(Double velocityOfThisSprint) {
        if (historicVelocity == null) {
            return velocityOfThisSprint;
        }
        if (velocityOfThisSprint == null) {
            return historicVelocity;
        }
        return computeVelocityStrategy.computeVelocity(velocityOfThisSprint, historicVelocity, clientsVisitedCount);
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
        return velocity.doubleValue() * capacity.doubleValue();
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

    // Visible for testing
    void setComputeAccumulatedProcessForecastVisitor(SprintDataVisitor computeAccumulatedProgressForecastMock) {
        computeAccumulatedProgressForecast = computeAccumulatedProgressForecastMock;
    }
}
