package de.frankbeeh.productbacklogtimeline.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductBacklogComparisonItem {
    private final ProductBacklogItem productBacklogItem;
    private final ProductBacklogItem referenceProductBacklogItem;

    public ProductBacklogComparisonItem(ProductBacklogItem productBacklogItem, ProductBacklogItem referenceProductBacklogItem) {
        this.productBacklogItem = productBacklogItem;
        this.referenceProductBacklogItem = referenceProductBacklogItem;
    }

    public String getCompletionForecastDescription(String progressForecastName) {
        final SprintData sprintData = productBacklogItem.getCompletionForecast(progressForecastName);
        if (sprintData == null) {
            return null;
        } else {
            SprintData referenceCompletionForecast = null;
            if (referenceProductBacklogItem != null) {
                referenceCompletionForecast = referenceProductBacklogItem.getCompletionForecast(progressForecastName);
            }
            return sprintData.getDescription(referenceCompletionForecast);
        }
    }

    public String getId() {
        return productBacklogItem.getId();
    }

    public SprintData getCompletionForecast(String progressForecastName) {
        return productBacklogItem.getCompletionForecast(progressForecastName);
    }

    public String getTitle() {
        return productBacklogItem.getTitle();
    }

    public String getDescription() {
        return productBacklogItem.getDescription();
    }

    public State getState() {
        return productBacklogItem.getState();
    }

    public Double getAccumulatedEstimate() {
        return productBacklogItem.getAccumulatedEstimate();
    }

    public String getAccumulatedEstimateDescription() {
        final Double accumulatedEstimate = getAccumulatedEstimate();
        if (accumulatedEstimate == null) {
            return null;
        }
        return accumulatedEstimate.toString();
    }

    public Double getEstimate() {
        return productBacklogItem.getEstimate();
    }

    public String getSprint() {
        return productBacklogItem.getSprint();
    }

    public Integer getRank() {
        return productBacklogItem.getRank();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
