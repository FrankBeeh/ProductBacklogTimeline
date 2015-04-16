package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

public class ForecastSprintOfCompletion implements ProductBacklogItemVisitor {

    private final String progressForecastName;

    public ForecastSprintOfCompletion(String progressForecastName) {
        this.progressForecastName = progressForecastName;
    }

    @Override
    public void reset() {
        // Nothing to do.
    }

    @Override
    public void visit(ProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast, ProductBacklog referenceProductBacklog, VelocityForecast referenceVelocityForecast) {
        final SprintData completionSprintForecast = selectedVelocityForecast.getCompletionSprintForecast(progressForecastName, productBacklogItem.getAccumulatedEstimate());
        final String id = productBacklogItem.getId();
        SprintData referenceCompletionSprintForecast = null;
        final ProductBacklogItem referenceItem = referenceProductBacklog.getItemById(id);
        if (referenceItem != null) {
            final Double referenceAccumulatedEstimate = referenceItem.getAccumulatedEstimate();
            referenceCompletionSprintForecast = referenceVelocityForecast.getCompletionSprintForecast(progressForecastName, referenceAccumulatedEstimate);
        }
        productBacklogItem.setCompletionForecast(progressForecastName, completionSprintForecast, referenceCompletionSprintForecast);
    }
}
