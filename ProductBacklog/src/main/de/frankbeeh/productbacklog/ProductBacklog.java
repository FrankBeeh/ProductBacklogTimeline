package de.frankbeeh.productbacklog;

import java.util.LinkedList;

public class ProductBacklog {

    private final LinkedList<ProductBacklogItem> items;

    public ProductBacklog() {
        items = new LinkedList<ProductBacklogItem>();
    }

    public int size() {
        return items.size();
    }

    public void addItem(ProductBacklogItem productBacklogItem) {
        items.add(productBacklogItem);
    }

    public ProductBacklogItem getItem(int index) {
        return items.get(index);
    }
}
