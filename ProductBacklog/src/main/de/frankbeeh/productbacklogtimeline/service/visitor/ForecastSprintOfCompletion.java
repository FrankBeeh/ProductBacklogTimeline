package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Forecast the sprint for completing a {@link ProductBacklogItem}.
 * <li>Compute the difference to the sprint forecast by the referenced {@link ReleaseForecast}.
 * </ul>
 */
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
    public void visit(ProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast) {
        final SprintData completionSprintForecast = selectedVelocityForecast.getCompletionSprintForecast(progressForecastName, productBacklogItem.getAccumulatedEstimate());
        productBacklogItem.setCompletionForecast(progressForecastName, completionSprintForecast);
    }
}
