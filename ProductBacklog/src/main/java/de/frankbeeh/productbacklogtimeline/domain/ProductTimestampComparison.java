package de.frankbeeh.productbacklogtimeline.domain;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link ProductTimestamp}s.
 * </ul>
 */
public class ProductTimestampComparison {
    private ProductBacklogComparison productBacklogComparison =new ProductBacklogComparison();

    public void setSelectedTimestamp(ProductTimestamp selectedProductTimestamp) {
        productBacklogComparison.setSelectedProductBacklog(selectedProductTimestamp.getProductBacklog());
    }

    public void setReferenceTimestamp(ProductTimestamp referenceProductTimestamp) {
        productBacklogComparison.setReferenceProductBacklog(referenceProductTimestamp.getProductBacklog());
    }

    public ProductBacklogComparison getProductBacklogComparision() {
        return productBacklogComparison;
    }
}
