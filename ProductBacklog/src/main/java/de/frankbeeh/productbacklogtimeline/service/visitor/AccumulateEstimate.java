package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

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
        if (State.Canceled.equals(productBacklogItem.getState())) {
            productBacklogItem.setAccumulatedEstimate(null);
        } else {
            accumulatedEstimate += productBacklogItem.getCleanedEstimate();
            productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        }
    }
}
