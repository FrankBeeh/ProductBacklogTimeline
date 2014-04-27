package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

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

    public void visit(ProductBacklogItem productBacklogItem) {
        final Double estimateOfThisItem = productBacklogItem.getEstimate();
        if (estimateOfThisItem != null) {
            accumulatedEstimate += estimateOfThisItem;
        }
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
    }
}
