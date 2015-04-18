package de.frankbeeh.productbacklogtimeline.data;

import java.text.DecimalFormat;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;

public class ProductBacklogComparisonItem {
    private final ProductBacklogItem productBacklogItem;
    private final ProductBacklogItem referenceProductBacklogItem;

    public ProductBacklogComparisonItem(ProductBacklogItem productBacklogItem, ProductBacklogItem referenceProductBacklogItem) {
        this.productBacklogItem = productBacklogItem;
        this.referenceProductBacklogItem = referenceProductBacklogItem;
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

    public Double getEstimate() {
        return productBacklogItem.getEstimate();
    }

    public String getSprint() {
        return productBacklogItem.getSprint();
    }

    public Integer getRank() {
        return productBacklogItem.getRank();
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

    public String getAccumulatedEstimateDescription() {
        final StringBuilder stringBuilder = new StringBuilder();
        final Double accumulatedEstimate = getAccumulatedEstimate();
        if (accumulatedEstimate == null) {
            return null;
        }
        final String formattedEstimate = accumulatedEstimate.toString();
        stringBuilder.append(formattedEstimate);
        if (referenceProductBacklogItem != null && referenceProductBacklogItem.getAccumulatedEstimate() != null) {
            final double accumulatedEstimateDifference = accumulatedEstimate - referenceProductBacklogItem.getAccumulatedEstimate();
            if (accumulatedEstimateDifference != 0) {
                final String formattedDifference = "(" + new DecimalFormat("+0.0;-0.0").format(accumulatedEstimateDifference) + ")";
                int count = formattedDifference.length() - formattedEstimate.length() + 1;
                if (count > 0) {
                    stringBuilder.insert(0, Strings.repeat(" ", count));
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append("\n").append(Strings.repeat(" ", 3 - count));
                }
                stringBuilder.append(formattedDifference);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
