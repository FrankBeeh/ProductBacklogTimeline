package de.frankbeeh.productbacklogtimeline.data;

import java.util.List;

public class DeleteProductBacklogItem extends ProductBacklogChange {
    public DeleteProductBacklogItem(String referenceId) {
        super(referenceId);
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToReferenceId(productBacklogItems).remove();
    }
}
