package de.frankbeeh.productbacklogtimeline.data;

import java.util.LinkedList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;
import de.frankbeeh.productbacklogtimeline.service.visitor.ProductBacklogItemVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Contain all items of the product backlog.
 * <li>Allow visitors to visit all items.
 * </ul>
 */
public class ProductBacklog {

    private final LinkedList<ProductBacklogItem> items;
    private final ProductBacklogItemVisitor[] visitors;

    public ProductBacklog() {
        this(new AccumulateEstimate());
    }

    // Visible for testing
    ProductBacklog(ProductBacklogItemVisitor... visitorMocks) {
        this.visitors = visitorMocks;
        this.items = new LinkedList<ProductBacklogItem>();
    }

    public void addItem(ProductBacklogItem productBacklogItem) {
        items.add(productBacklogItem);
    }

    public List<ProductBacklogItem> getItems() {
        return items;
    }

    public void visitAllItems(Sprints sprints) {
        for (final ProductBacklogItemVisitor visitor : visitors) {
            visitor.reset();
            for (final ProductBacklogItem item : items) {
                visitor.visit(item, sprints);
            }
        }
    }
}
