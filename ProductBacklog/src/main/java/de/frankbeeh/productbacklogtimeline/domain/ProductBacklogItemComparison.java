package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatter;

/**
 * Responsibility:
 * <ul>
 * <li>Immutable representation of the comparison of two {@link ProductBacklogItem}s.
 * </ul>
 */
public class ProductBacklogItemComparison {
    private final ProductBacklogItem productBacklogItem;
    private final ProductBacklogItem referenceProductBacklogItem;

    public ProductBacklogItemComparison(ProductBacklogItem productBacklogItem) {
        this(productBacklogItem, productBacklogItem);
    }

    public ProductBacklogItemComparison(ProductBacklogItem productBacklogItem, ProductBacklogItem referenceProductBacklogItem) {
        this.productBacklogItem = productBacklogItem;
        if (referenceProductBacklogItem == null) {
            this.referenceProductBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        } else {
            this.referenceProductBacklogItem = referenceProductBacklogItem;
        }
    }

    public String getId() {
        return productBacklogItem.getId();
    }

    public Double getAccumulatedEstimate() {
        return productBacklogItem.getAccumulatedEstimate();
    }

    public String getPlannedRelease() {
        return productBacklogItem.getPlannedRelease();
    }

    public String getComparedPlannedRelease() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getPlannedRelease(), referenceProductBacklogItem.getPlannedRelease());
    }

    public String getComparedJiraSprint() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getJiraSprint(), referenceProductBacklogItem.getJiraSprint());
    }

    public String getComparedTitle() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getTitle(), referenceProductBacklogItem.getTitle());
    }

    public String getComparedDescription() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getDescription(), referenceProductBacklogItem.getDescription());
    }

    public String getComparedCompletionForecast(String progressForecastName) {
        return DifferenceFormatter.formatSprintDifference(productBacklogItem.getCompletionForecast(progressForecastName), referenceProductBacklogItem.getCompletionForecast(progressForecastName));
    }

    public String getComparedState() {
        return DifferenceFormatter.formatStateDifference(productBacklogItem.getState(), referenceProductBacklogItem.getState());
    }

    public String getComparedEstimate() {
        return DifferenceFormatter.formatDoubleDifference(productBacklogItem.getEstimate(), referenceProductBacklogItem.getEstimate());
    }

    public String getComparedAccumulatedEstimate() {
        return DifferenceFormatter.formatDoubleDifference(productBacklogItem.getAccumulatedEstimate(), referenceProductBacklogItem.getAccumulatedEstimate());
    }

    public String getComparedProductBacklogRank() {
        return DifferenceFormatter.formatProductBacklogRankDifference(productBacklogItem.getProductBacklogRank(), referenceProductBacklogItem.getProductBacklogRank());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
