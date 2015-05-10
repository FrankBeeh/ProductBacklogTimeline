package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class ProductBacklogItemIdIsEqual implements ReleaseCriteria {

    private final String id;

    public ProductBacklogItemIdIsEqual(String id) {
        this.id = id;
    }

    @Override
    public boolean isMatching(ProductBacklogItem productBacklogItem) {
        return id.equals(productBacklogItem.getId());
    }

    @Override
    public String toString() {
        return "idOfPBI=" + id;
    }
}
