package de.frankbeeh.productbacklogtimeline.domain.change;

import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class ChangeEstimateOfProductBacklogItem extends ProductBacklogChange {
    private final Double changedEstimate;

    public ChangeEstimateOfProductBacklogItem(String referenceId, Double changedEstimate) {
        super(referenceId);
        this.changedEstimate = changedEstimate;
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToReferenceId(productBacklogItems).previous().setEstimate(changedEstimate);
    }
}
