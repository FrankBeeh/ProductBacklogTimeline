package de.frankbeeh.productbacklogtimeline.domain;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link ProductTimestamp}s.
 * </ul>
 */
public class ProductTimestampComparison {
    private ProductBacklogComparison productBacklogComparison = new ProductBacklogComparison();
    private VelocityForecastComparison velocityForecastComparison = new VelocityForecastComparison();

    public void setSelectedTimestamp(ProductTimestamp selectedProductTimestamp) {
        productBacklogComparison.setSelected(selectedProductTimestamp.getProductBacklog());
        velocityForecastComparison.setSelected(selectedProductTimestamp.getVelocityForecast());
    }

    public void setReferenceTimestamp(ProductTimestamp referenceProductTimestamp) {
        productBacklogComparison.setReference(referenceProductTimestamp.getProductBacklog());
        velocityForecastComparison.setReference(referenceProductTimestamp.getVelocityForecast());
    }

    public ProductBacklogComparison getProductBacklogComparision() {
        return productBacklogComparison;
    }

    public VelocityForecastComparison getVelocityForecastComparison() {
        return velocityForecastComparison;
    }
}
