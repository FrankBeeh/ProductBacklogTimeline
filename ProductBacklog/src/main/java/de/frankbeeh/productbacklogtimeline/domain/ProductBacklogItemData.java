package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductBacklogItemData {

    private final String id;
    private final String title;
    private final String description;
    private Double estimate;
    private final State state;
    private final String sprint;
    private final String rank;
    private final String plannedRelease;

    public ProductBacklogItemData(String id, String title, String description, Double estimate, State state, String sprint, String rank, String plannedRelease) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimate = estimate;
        this.state = state;
        this.sprint = sprint;
        this.rank = rank;
        this.plannedRelease = plannedRelease;
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

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public State getState() {
        return state;
    }

    public String getSprint() {
        return sprint;
    }

    public String getRank() {
        return rank;
    }
    
    public String getPlannedRelease() {
        return plannedRelease;
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}