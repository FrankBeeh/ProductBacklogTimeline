package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public class ForecastCompletionSprint implements ProductBacklogItemVisitor {

    private final String progressForecastName;

    public ForecastCompletionSprint(String progressForecastName) {
        this.progressForecastName = progressForecastName;
    }

    @Override
    public void reset() {
        // Nothing to do.
    }

    @Override
    public void visit(ProductBacklogItem productBacklogItem, Sprints sprints) {
        final SprintData completionSprintForecast = sprints.getCompletionSprintForecast(progressForecastName, productBacklogItem.getAccumulatedEstimate());
        productBacklogItem.setCompletionForecast(progressForecastName, completionSprintForecast);
    }
}
