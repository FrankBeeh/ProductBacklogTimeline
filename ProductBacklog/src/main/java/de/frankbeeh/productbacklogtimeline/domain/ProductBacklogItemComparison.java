package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

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
        return formatTextualDifference(productBacklogItem.getPlannedRelease(), referenceProductBacklogItem.getPlannedRelease());
    }

    public String getComparedJiraSprint() {
        return formatTextualDifference(productBacklogItem.getJiraSprint(), referenceProductBacklogItem.getJiraSprint());
    }

    public String getComparedTitle() {
        return formatTextualDifference(productBacklogItem.getTitle(), referenceProductBacklogItem.getTitle());
    }

    public String getComparedDescription() {
        return formatTextualDifference(productBacklogItem.getDescription(), referenceProductBacklogItem.getDescription());
    }

    public String getComparedCompletionForecast(String progressForecastName) {
        final Sprint sprintData = productBacklogItem.getCompletionForecast(progressForecastName);
        if (sprintData == null) {
            return null;
        } else {
            return sprintData.getComparedForecast(referenceProductBacklogItem.getCompletionForecast(progressForecastName));
        }
    }

    public String getComparedState() {
        return formatTextualDifference(productBacklogItem.getState() == null ? null : productBacklogItem.getState().toString(), referenceProductBacklogItem.getState() == null ? null
                : referenceProductBacklogItem.getState().toString());
    }

    public String getComparedEstimate() {
        return formatDoubleDifference(productBacklogItem.getEstimate(), referenceProductBacklogItem.getEstimate());
    }

    public String getComparedAccumulatedEstimate() {
        return formatDoubleDifference(productBacklogItem.getAccumulatedEstimate(), referenceProductBacklogItem.getAccumulatedEstimate());
    }

    public String getComparedProductBacklogRank() {
        return formatProductBacklogRankDifference(productBacklogItem.getProductBacklogRank(), referenceProductBacklogItem.getProductBacklogRank());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private String formatTextualDifference(final String value, String referenceValue) {
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

    private String formatProductBacklogRankDifference(Integer rank, Integer referenceRank) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = rank.toString();
        stringBuilder.append(formattedEstimate);
        if (referenceRank != null) {
            final Integer difference = rank - referenceRank;
            if (difference != 0) {
                final String formattedDifference = "(" + FormatUtility.formatDifferenceLong(difference) + ")";
                rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
            }
        } else {
            rigthAllign(stringBuilder, formattedEstimate, "(NEW)");
        }
        return stringBuilder.toString();
    }

    private String formatDoubleDifference(final Double value, Double referenceValue) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = value.toString();
        stringBuilder.append(formattedEstimate);
        if (referenceValue != null) {
            final Double difference = value - referenceValue;
            if (difference != 0) {
                final String formattedDifference = "(" + FormatUtility.formatDifferenceDouble(difference) + ")";
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
