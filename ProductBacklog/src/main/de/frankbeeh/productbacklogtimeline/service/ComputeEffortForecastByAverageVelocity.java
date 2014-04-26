package de.frankbeeh.productbacklogtimeline.service;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class ComputeEffortForecastByAverageVelocity extends SprintDataVisitor {

    private static final String HISTORY_FORECAST_NAME = "Average Velocity";

    private int clientCount;
    private Double historicVelocity;

    public ComputeEffortForecastByAverageVelocity() {
        reset();
    }

    public void reset() {
        historicVelocity = null;
        clientCount = 0;
    }

    public String getHistoryForecastName() {
        return HISTORY_FORECAST_NAME;
    }

    @Override
    public void visit(SprintData sprintData) {
        clientCount++;
        final Double velocityOfThisSprint = computeVelocityOfThisSprint(sprintData);
        sprintData.setEffortForecastBasedOnHistory(HISTORY_FORECAST_NAME, computeEffortForecast(sprintData, velocityOfThisSprint));
    }

    private Double computeResultingVelocity(Double velocityOfThisSprint) {
        if (historicVelocity == null) {
            return velocityOfThisSprint;
        }
        if (velocityOfThisSprint == null) {
            return historicVelocity;
        }
        return (historicVelocity.doubleValue() * (clientCount - 1) + velocityOfThisSprint.doubleValue()) / clientCount;
    }

    private Double computeEffortForecast(SprintData sprintData, Double velocityOfThisSprint) {
        historicVelocity = computeResultingVelocity(velocityOfThisSprint);
        Double effortForecast = null;
        if (historicVelocity != null) {
            effortForecast = computeEffortForecast(historicVelocity, sprintData.getCapacityDone());
            if (effortForecast == null) {
                effortForecast = computeEffortForecast(historicVelocity, sprintData.getCapacityForecast());
            }
        }
        return effortForecast;
    }

    private Double computeEffortForecast(Double velocity, Double capacity) {
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
}
