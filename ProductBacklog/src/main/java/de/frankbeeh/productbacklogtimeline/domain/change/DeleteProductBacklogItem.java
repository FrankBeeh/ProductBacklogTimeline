package de.frankbeeh.productbacklogtimeline.domain.change;

import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class DeleteProductBacklogItem extends ProductBacklogChange {
    public DeleteProductBacklogItem(String referenceId) {
        super(referenceId);
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToReferenceId(productBacklogItems).remove();
    }
}
