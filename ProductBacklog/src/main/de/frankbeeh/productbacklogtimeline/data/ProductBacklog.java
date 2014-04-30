package de.frankbeeh.productbacklogtimeline.data;

import java.util.LinkedList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.criteria.Criteria;
import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;
import de.frankbeeh.productbacklogtimeline.service.visitor.ForecastCompletionSprint;
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
        this(new AccumulateEstimate(), new ForecastCompletionSprint(Sprints.AVERAGE_VELOCITY_FORECAST), new ForecastCompletionSprint(Sprints.MINIMUM_VELOCITY_FORECAST), new ForecastCompletionSprint(
                Sprints.MAXIMUM_VELOCITY_FORECAST));
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

    public void updateAllItems(Sprints sprints) {
        for (final ProductBacklogItemVisitor visitor : visitors) {
            visitor.reset();
            for (final ProductBacklogItem item : items) {
                visitor.visit(item, sprints);
            }
        }
    }

    public Double getAccumulatedEstimate(Criteria criteria) {
        Double accumulatedEstimate = null;
        for (final ProductBacklogItem item : items) {
            if (criteria.isMatching(item)) {
                accumulatedEstimate = item.getAccumulatedEstimate();
            }
        }
        return accumulatedEstimate;
    }
}
