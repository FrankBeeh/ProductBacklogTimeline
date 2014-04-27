package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class ComputeAccumulatedProgressForecastByAverageVelocity implements SprintDataVisitor {

    private static final String HISTORY_NAME = "Avg. Vel.";
    private Double accumulatedProgressForecast;

    @Override
    public void reset() {
        accumulatedProgressForecast = null;
    }

    @Override
    public void visit(SprintData sprintData) {
        final Double accumulatedEffortDone = sprintData.getAccumulatedEffortDone();
        final Double progressForecast = sprintData.getProgressForecastBasedOnHistory(HISTORY_NAME);
        if (accumulatedEffortDone == null) {
            if (progressForecast != null) {
                accumulatedProgressForecast = addProgressForecast(progressForecast.doubleValue());
                sprintData.setAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME, accumulatedProgressForecast);
            }
        } else {
            accumulatedProgressForecast = accumulatedEffortDone;
        }
    }

    private Double addProgressForecast(double progressForecast) {
        if (accumulatedProgressForecast == null) {
            return progressForecast;
        } else {
            return accumulatedProgressForecast + progressForecast;
        }
    }
}
