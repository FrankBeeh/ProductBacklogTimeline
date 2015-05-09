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
public class ProductBacklogComparison {
    private ProductBacklog referenceProductBacklog;
    private ProductBacklog selectedProductBacklog;
    private final List<ProductBacklogComparisonItem> productBacklogComparisonItems = new ArrayList<ProductBacklogComparisonItem>();

    @VisibleForTesting
    ProductBacklogComparison(ProductBacklogComparisonItem... productBacklogComparisonItems) {
        this.productBacklogComparisonItems.addAll(Arrays.asList(productBacklogComparisonItems));
    }

    public void updateAllItems() {
        productBacklogComparisonItems.clear();
        for (ProductBacklogItem productBacklogItem : selectedProductBacklog.getItems()) {
            if (referenceProductBacklog == null) {
                productBacklogComparisonItems.add(new ProductBacklogComparisonItem(productBacklogItem));
            } else {
                final ProductBacklogItem referenceProductBacklogItem = referenceProductBacklog.getItemById(productBacklogItem.getId());
                productBacklogComparisonItems.add(new ProductBacklogComparisonItem(productBacklogItem, referenceProductBacklogItem));
            }
        }
    }

    public void setReferenceProductBacklog(ProductBacklog referenceProductBacklog) {
        this.referenceProductBacklog = referenceProductBacklog;
    }

    public void setSelectedProductBacklog(ProductBacklog selectedProductBacklog) {
        this.selectedProductBacklog = selectedProductBacklog;
    }

    public List<ProductBacklogComparisonItem> getItems() {
        return productBacklogComparisonItems;
    }

    public List<ProductBacklogComparisonItem> getMatchingProductBacklogItems(ReleaseCriteria criteria) {
        final List<ProductBacklogComparisonItem> matchingProductBacklogItems = new ArrayList<ProductBacklogComparisonItem>();
        for (final ProductBacklogComparisonItem productBacklogComparisonItem : productBacklogComparisonItems) {
            if (criteria.isMatching(productBacklogComparisonItem)) {
                matchingProductBacklogItems.add(productBacklogComparisonItem);
            }
        }
        return matchingProductBacklogItems;
    }
}
