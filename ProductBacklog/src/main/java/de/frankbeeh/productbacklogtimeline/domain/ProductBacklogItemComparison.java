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

    public ComparedValue getComparedPlannedRelease() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getPlannedRelease(), referenceProductBacklogItem.getPlannedRelease());
    }

    public ComparedValue getComparedJiraSprint() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getJiraSprint(), referenceProductBacklogItem.getJiraSprint());
    }

    public ComparedValue getComparedTitle() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getTitle(), referenceProductBacklogItem.getTitle());
    }

    public ComparedValue getComparedDescription() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getDescription(), referenceProductBacklogItem.getDescription());
    }

    public ComparedValue getComparedCompletionForecast(String progressForecastName) {
        return DifferenceFormatter.formatSprintDifference(productBacklogItem.getCompletionForecast(progressForecastName), referenceProductBacklogItem.getCompletionForecast(progressForecastName));
    }

    public ComparedValue getComparedState() {
        return DifferenceFormatter.formatStateDifference(productBacklogItem.getState(), referenceProductBacklogItem.getState());
    }

    public ComparedValue getComparedEstimate() {
        return DifferenceFormatter.formatDoubleDifference(productBacklogItem.getEstimate(), referenceProductBacklogItem.getEstimate(), true);
    }

    public ComparedValue getComparedAccumulatedEstimate() {
        return DifferenceFormatter.formatDoubleDifference(productBacklogItem.getAccumulatedEstimate(), referenceProductBacklogItem.getAccumulatedEstimate(), true);
    }

    public ComparedValue getComparedProductBacklogRank() {
        return DifferenceFormatter.formatProductBacklogRankDifference(productBacklogItem.getProductBacklogRank(), referenceProductBacklogItem.getProductBacklogRank());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
