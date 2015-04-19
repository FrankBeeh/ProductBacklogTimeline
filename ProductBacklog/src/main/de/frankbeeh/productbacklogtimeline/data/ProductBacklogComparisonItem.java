package de.frankbeeh.productbacklogtimeline.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

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

    public Double getAccumulatedEstimate() {
        return productBacklogItem.getAccumulatedEstimate();
    }

    public String getComparedSprint() {
        return formatDifference(productBacklogItem.getSprint(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getSprint());
    }

    public String getComparedTitle() {
        return formatDifference(productBacklogItem.getTitle(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getTitle());
    }

    public String getComparedDescription() {
        return formatDifference(productBacklogItem.getDescription(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getDescription());
    }

    public String getComparedCompletionForecast(String progressForecastName) {
        final SprintData sprintData = productBacklogItem.getCompletionForecast(progressForecastName);
        if (sprintData == null) {
            return null;
        } else {
            final SprintData referenceCompletionForecast = referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getCompletionForecast(progressForecastName);
            return sprintData.getComparedForecast(referenceCompletionForecast);
        }
    }

    public String getComparedState() {
        return formatDifference(productBacklogItem.getState() == null ? null : productBacklogItem.getState().toString(),
                (referenceProductBacklogItem == null || referenceProductBacklogItem.getState() == null) ? null : referenceProductBacklogItem.getState().toString());
    }

    public String getComparedEstimate() {
        return formatDifference(productBacklogItem.getEstimate(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getEstimate());
    }

    public String getComparedAccumulatedEstimate() {
        return formatDifference(productBacklogItem.getAccumulatedEstimate(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getAccumulatedEstimate());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private String formatDifference(final String value, String referenceValue) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value.toString());
        if (referenceValue != null && !value.equals(referenceValue)) {
            stringBuilder.append("\n(").append(referenceValue).append(")");
        }
        return stringBuilder.toString();
    }

    private String formatDifference(final Double value, Double referenceValue) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = value.toString();
        stringBuilder.append(formattedEstimate);
        if (referenceValue != null) {
            final double accumulatedEstimateDifference = value - referenceValue;
            if (accumulatedEstimateDifference != 0) {
                final String formattedDifference = "(" + FormatUtility.formatDifferenceDouble(accumulatedEstimateDifference) + ")";
                rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
            }
        }
        return stringBuilder.toString();
    }

    private void rigthAllign(final StringBuilder stringBuilder, final String formattedEstimate, final String formattedDifference) {
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
