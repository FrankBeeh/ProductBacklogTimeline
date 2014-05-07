package de.frankbeeh.productbacklogtimeline.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ChangeEstimate extends ProductBacklogChange {
    private final Double changedEstimate;

    public ChangeEstimate(String id, Double changedEstimate) {
        super(id);
        this.changedEstimate = changedEstimate;
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToId(productBacklogItems).previous().setEstimate(changedEstimate);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
