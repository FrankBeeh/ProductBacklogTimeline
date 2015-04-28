package de.frankbeeh.productbacklogtimeline.domain.change;

import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class InsertProductBacklogItemAfterId extends ProductBacklogChange {
    private final ProductBacklogItem productBacklogItem;

    public InsertProductBacklogItemAfterId(String referenceId, ProductBacklogItem productBacklogItem) {
        super(referenceId);
        this.productBacklogItem = productBacklogItem;
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToReferenceId(productBacklogItems).add(productBacklogItem);
    }
}
