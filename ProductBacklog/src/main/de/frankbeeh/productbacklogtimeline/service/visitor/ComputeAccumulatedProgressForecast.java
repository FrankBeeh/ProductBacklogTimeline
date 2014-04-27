package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class ComputeAccumulatedProgressForecast implements SprintDataVisitor {
    private Double accumulatedProgressForecast;
    private final String historyName;

    public ComputeAccumulatedProgressForecast(String historyName) {
        this.historyName = historyName;
    }

    @Override
    public void reset() {
        accumulatedProgressForecast = null;
    }

    @Override
    public void visit(SprintData sprintData) {
        final Double accumulatedEffortDone = sprintData.getAccumulatedEffortDone();
        final Double progressForecast = sprintData.getProgressForecastBasedOnHistory(historyName);
        if (accumulatedEffortDone == null) {
            if (progressForecast != null) {
                accumulatedProgressForecast = addProgressForecast(progressForecast.doubleValue());
                sprintData.setAccumulatedProgressForecastBasedOnHistory(historyName, accumulatedProgressForecast);
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
