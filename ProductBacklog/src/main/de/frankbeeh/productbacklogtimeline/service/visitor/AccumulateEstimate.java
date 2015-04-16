package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.State;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the accumulated estimate for completing a {@link ProductBacklogItem}.
 * </ul>
 */
public class AccumulateEstimate implements ProductBacklogItemVisitor {

    private Double accumulatedEstimate;

    public AccumulateEstimate() {
        reset();
    }

    public void reset() {
        accumulatedEstimate = 0d;
    }

    public void visit(ProductBacklogItem productBacklogItem, ProductBacklog referenceProductBacklog, VelocityForecast velocityForecast) {
        final Double estimateOfThisItem = productBacklogItem.getEstimate();
        if (estimateOfThisItem != null && !State.Canceled.equals(productBacklogItem.getState())) {
            accumulatedEstimate += estimateOfThisItem;
        }
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
    }
}
