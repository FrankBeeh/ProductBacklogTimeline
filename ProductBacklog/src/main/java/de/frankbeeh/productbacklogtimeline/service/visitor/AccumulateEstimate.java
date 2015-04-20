package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
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

    public void visit(ProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast) {
        accumulatedEstimate += productBacklogItem.getCleanedEstimate();
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
    }
}
