package de.frankbeeh.productbacklogtimeline.data;

import java.util.List;
import java.util.ListIterator;

public abstract class ProductBacklogChange {
    private final String id;

    public ProductBacklogChange(String id) {
        this.id = id;
    }

    public abstract void applyTo(List<ProductBacklogItem> productBacklogItems);

    protected ListIterator<ProductBacklogItem> iterateToId(List<ProductBacklogItem> productBacklogItems) {
        final ListIterator<ProductBacklogItem> iterator = productBacklogItems.listIterator();
        if (id == null) {
            return iterator;
        }
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return iterator;
            }
        }
        throw new IllegalArgumentException("Id '" + id + "' not found!");
    }
}
