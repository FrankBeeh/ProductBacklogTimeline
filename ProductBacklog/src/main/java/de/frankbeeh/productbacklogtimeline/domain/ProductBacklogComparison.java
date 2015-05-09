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
    private final List<ProductBacklogItemComparison> productBacklogComparisonItems = new ArrayList<ProductBacklogItemComparison>();

    @VisibleForTesting
    ProductBacklogComparison(ProductBacklogItemComparison... productBacklogComparisonItems) {
        this.productBacklogComparisonItems.addAll(Arrays.asList(productBacklogComparisonItems));
    }

    public void updateAllItems() {
        productBacklogComparisonItems.clear();
        for (ProductBacklogItem productBacklogItem : selectedProductBacklog.getItems()) {
            if (referenceProductBacklog == null) {
                productBacklogComparisonItems.add(new ProductBacklogItemComparison(productBacklogItem));
            } else {
                final ProductBacklogItem referenceProductBacklogItem = referenceProductBacklog.getItemById(productBacklogItem.getId());
                productBacklogComparisonItems.add(new ProductBacklogItemComparison(productBacklogItem, referenceProductBacklogItem));
            }
        }
    }

    public void setReferenceProductBacklog(ProductBacklog referenceProductBacklog) {
        this.referenceProductBacklog = referenceProductBacklog;
    }

    public void setSelectedProductBacklog(ProductBacklog selectedProductBacklog) {
        this.selectedProductBacklog = selectedProductBacklog;
    }

    public List<ProductBacklogItemComparison> getItems() {
        return productBacklogComparisonItems;
    }

    public List<ProductBacklogItemComparison> getMatchingProductBacklogItems(ReleaseCriteria criteria) {
        final List<ProductBacklogItemComparison> matchingProductBacklogItems = new ArrayList<ProductBacklogItemComparison>();
        for (final ProductBacklogItemComparison productBacklogComparisonItem : productBacklogComparisonItems) {
            if (criteria.isMatching(productBacklogComparisonItem)) {
                matchingProductBacklogItems.add(productBacklogComparisonItem);
            }
        }
        return matchingProductBacklogItems;
    }
}
