package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparisonItem;

public class ProductBacklogItemIdIsEqual implements ReleaseCriteria {

    private final String id;

    public ProductBacklogItemIdIsEqual(String id) {
        this.id = id;
    }

    @Override
    public boolean isMatching(ProductBacklogComparisonItem productBacklogComparisonItem) {
        return id.equals(productBacklogComparisonItem.getId());
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
