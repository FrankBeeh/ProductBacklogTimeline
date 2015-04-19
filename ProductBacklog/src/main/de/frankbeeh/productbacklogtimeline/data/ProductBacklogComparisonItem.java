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

    // FIXME Write unit tests
    public String getComparedCompletionForecast(String progressForecastName) {
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

    public String getComparedState() {
        if (getState() == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getState().toString());
        if (referenceProductBacklogItem != null) {
            final State referenceState = referenceProductBacklogItem.getState();
            if (referenceState != null && !getState().equals(referenceState)) {
                stringBuilder.append("\n(").append(referenceState).append(")");
            }
        }
        return stringBuilder.toString();
    }

    public String getComparedEstimate() {
        return formatDifference(getEstimate(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getEstimate());
    }

    public String getComparedAccumulatedEstimate() {
        return formatDifference(getAccumulatedEstimate(), referenceProductBacklogItem == null ? null : referenceProductBacklogItem.getAccumulatedEstimate());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
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
