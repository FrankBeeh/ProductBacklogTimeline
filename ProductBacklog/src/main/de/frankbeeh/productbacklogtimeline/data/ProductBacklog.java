package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.criteria.Criteria;
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
        this(new JiraProductBacklogSortingStrategy(), new AccumulateEstimate(), new ForecastSprintOfCompletion(Sprints.AVERAGE_VELOCITY_FORECAST), new ForecastSprintOfCompletion(
                Sprints.MINIMUM_VELOCITY_FORECAST), new ForecastSprintOfCompletion(Sprints.MAXIMUM_VELOCITY_FORECAST));
    }

    // Visible for testing
    ProductBacklog(ProductBacklogSortingStrategy sortingStrategy, ProductBacklogItemVisitor... visitorMocks) {
        this.sortingStrategy = sortingStrategy;
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
        sortingStrategy.sortProductBacklog(this, sprints);
        for (final ProductBacklogItemVisitor visitor : visitors) {
            visitor.reset();
            for (final ProductBacklogItem item : items) {
                visitor.visit(item, sprints);
            }
        }
    }

    public List<ProductBacklogItem> getMatchingProductBacklogItems(Criteria criteria) {
        final List<ProductBacklogItem> matchingProductBacklogItems = new ArrayList<ProductBacklogItem>();
        for (final ProductBacklogItem productBacklogItem : items) {
            if (criteria.isMatching(productBacklogItem)) {
                matchingProductBacklogItems.add(productBacklogItem);
            }
        }
        return matchingProductBacklogItems;
    }

    public boolean containsId(String id) {
        for (final ProductBacklogItem item : items) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
