package de.frankbeeh.productbacklogtimeline.data;

import java.util.LinkedList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.sort.JiraProductBacklogSortingStrategy;
import de.frankbeeh.productbacklogtimeline.service.sort.ProductBacklogSortingStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;
import de.frankbeeh.productbacklogtimeline.service.visitor.ForecastSprintOfCompletion;
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
    private final ProductBacklogSortingStrategy sortingStrategy;

    public ProductBacklog() {
        this(new JiraProductBacklogSortingStrategy(), new AccumulateEstimate(), new ForecastSprintOfCompletion(VelocityForecast.AVERAGE_VELOCITY_FORECAST), new ForecastSprintOfCompletion(
                VelocityForecast.MINIMUM_VELOCITY_FORECAST), new ForecastSprintOfCompletion(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
    }

    @VisibleForTesting
    ProductBacklog(ProductBacklogSortingStrategy sortingStrategy, ProductBacklogItemVisitor... visitorMocks) {
        this.sortingStrategy = sortingStrategy;
        this.visitors = visitorMocks;
        this.items = new LinkedList<ProductBacklogItem>();
    }

    @VisibleForTesting
    public ProductBacklog(List<ProductBacklogItem> items) {
        this();
        items = new LinkedList<ProductBacklogItem>(items);
    }

    public void addItem(ProductBacklogItem productBacklogItem) {
        items.add(productBacklogItem);
    }

    public List<ProductBacklogItem> getItems() {
        return items;
    }

    public void updateAllItems(VelocityForecast selectedVelocityForecast) {
        sortingStrategy.sortProductBacklog(this, selectedVelocityForecast);
        for (final ProductBacklogItemVisitor visitor : visitors) {
            visitor.reset();
            for (final ProductBacklogItem item : items) {
                visitor.visit(item, selectedVelocityForecast);
            }
        }
    }

    public boolean containsId(String id) {
        for (final ProductBacklogItem item : items) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public ProductBacklogItem getItemById(String id) {
        for (final ProductBacklogItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public Double getTotalEffort() {
        if (items.isEmpty()) {
            return 0.0;
        }
        return items.getLast().getAccumulatedEstimate();
    }
}
