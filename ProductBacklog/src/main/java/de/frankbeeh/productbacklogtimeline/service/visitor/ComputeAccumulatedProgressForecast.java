package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;

public class ComputeAccumulatedProgressForecast implements SprintDataVisitor {
    private Double accumulatedProgressForecast;
    private final String progressForecastName;

    public ComputeAccumulatedProgressForecast(String progressForecastName) {
        this.progressForecastName = progressForecastName;
    }

    @Override
    public void reset() {
        accumulatedProgressForecast = null;
    }

    @Override
    public void visit(Sprint sprintData) {
        final Double accumulatedEffortDone = sprintData.getAccumulatedEffortDone();
        final Double progressForecast = sprintData.getProgressForecastBasedOnHistory(progressForecastName);
        if (accumulatedEffortDone == null) {
            if (progressForecast != null) {
                accumulatedProgressForecast = addProgressForecast(progressForecast.doubleValue());
                sprintData.setAccumulatedProgressForecastBasedOnHistory(progressForecastName, accumulatedProgressForecast);
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
