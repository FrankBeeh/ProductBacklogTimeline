package de.frankbeeh.productbacklogtimeline.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    private ProductBacklog selectedProductBacklog = new ProductBacklog();
    private final List<ProductBacklogItemComparison> productBacklogComparisonItems = new ArrayList<ProductBacklogItemComparison>();

    public void setSelectedProductBacklog(ProductBacklog selectedProductBacklog) {
        this.selectedProductBacklog = selectedProductBacklog;
        updateAllItems();
    }

    public void setReferenceProductBacklog(ProductBacklog referenceProductBacklog) {
        this.referenceProductBacklog = referenceProductBacklog;
        updateAllItems();
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

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
    
    private void updateAllItems() {
        productBacklogComparisonItems.clear();
        for (ProductBacklogItem productBacklogItem : selectedProductBacklog.getItems()) {
            if (referenceProductBacklog == null) {
                productBacklogComparisonItems.add(new ProductBacklogItemComparison(productBacklogItem));
            } else {
                productBacklogComparisonItems.add(new ProductBacklogItemComparison(productBacklogItem, referenceProductBacklog.getItemById(productBacklogItem.getId())));
            }
        }
    }

    @VisibleForTesting
    ProductBacklogComparison(ProductBacklogItemComparison... productBacklogComparisonItems) {
        this.productBacklogComparisonItems.addAll(Arrays.asList(productBacklogComparisonItems));
    }
}
