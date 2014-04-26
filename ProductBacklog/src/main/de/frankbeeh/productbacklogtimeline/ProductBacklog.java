package de.frankbeeh.productbacklogtimeline;

import java.util.LinkedList;
import java.util.List;

public class ProductBacklog {

    private final LinkedList<ProductBacklogItem> items;

    public ProductBacklog() {
        items = new LinkedList<ProductBacklogItem>();
    }

    public void addItem(ProductBacklogItem productBacklogItem) {
        items.add(productBacklogItem);
    }

    public List<ProductBacklogItem> getItems() {
        return items;
    }
}
