package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;

public class ProductBacklogItemIdIsEqual implements ReleaseCriteria {

    private final String id;

    public ProductBacklogItemIdIsEqual(String id) {
        this.id = id;
    }

    @Override
    public boolean isMatching(ProductBacklogItemComparison productBacklogComparisonItem) {
        return id.equals(productBacklogComparisonItem.getId());
    }

    @Override
    public String toString() {
        return "idOfPBI=" + id;
    }
}
