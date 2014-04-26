package de.frankbeeh.productbacklogtimeline;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductBacklogItem {

    private final String id;
    private final String title;
    private final String description;
    private final Double estimate;

    public ProductBacklogItem(String id, String title, String description, Double estimate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimate = estimate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getEstimate() {
        return estimate;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

}
