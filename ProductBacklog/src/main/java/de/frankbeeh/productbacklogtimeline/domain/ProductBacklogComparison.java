package de.frankbeeh.productbacklogtimeline.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link ProductBacklog}s.
 * </ul>
 */
public class ProductBacklogComparison extends Comparison<ProductBacklog, ProductBacklogItem, ProductBacklogItemComparison> {
    public ProductBacklogComparison() {
        setSelected(new ProductBacklog());
    }

    public List<ProductBacklogItemComparison> getMatchingProductBacklogItems(ReleaseCriteria criteria) {
        final List<ProductBacklogItemComparison> matchingProductBacklogItems = new ArrayList<ProductBacklogItemComparison>();
        for (final ProductBacklogItemComparison comparisons : getComparisons()) {
            if (criteria.isMatching(comparisons)) {
                matchingProductBacklogItems.add(comparisons);
            }
        }
        return matchingProductBacklogItems;
    }

    @VisibleForTesting
    ProductBacklogComparison(ProductBacklogItemComparison... productBacklogComparisonItems) {
        getComparisons().addAll(Arrays.asList(productBacklogComparisonItems));
    }

    @Override
    protected ProductBacklogItemComparison createComparisonWithSelf(ProductBacklogItem productBacklogItem) {
        return new ProductBacklogItemComparison(productBacklogItem);
    }

    @Override
    protected ProductBacklogItemComparison createComparison(ProductBacklogItem productBacklogItem) {
        return new ProductBacklogItemComparison(productBacklogItem, getReference().getItemById(productBacklogItem.getId()));
    }

    @Override
    protected List<ProductBacklogItem> getItems() {
        return getSelected().getItems();
    }
}
