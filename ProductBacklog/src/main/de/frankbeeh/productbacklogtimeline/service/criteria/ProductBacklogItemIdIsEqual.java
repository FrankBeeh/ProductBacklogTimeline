package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogItemIdIsEqual implements Criteria {

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
        return "id=" + id;
    }
}
