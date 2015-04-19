package de.frankbeeh.productbacklogtimeline.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductBacklogItem {
    private final Map<String, SprintData> completionForecast;
    private Double accumulatedEstimate;
    private final ProductBacklogItemData data;

    public ProductBacklogItem(String id, String title, String description, Double estimate, State state, String sprint, String rank) {
        this.data = new ProductBacklogItemData(id, title, description, estimate, state, sprint, rank);
        this.completionForecast = new HashMap<String, SprintData>();
    }

    public void setAccumulatedEstimate(Double accumulatedEstimate) {
        this.accumulatedEstimate = accumulatedEstimate;
    }

    public Double getAccumulatedEstimate() {
        return accumulatedEstimate;
    }

    public SprintData getCompletionForecast(String progressForecastName) {
        return completionForecast.get(progressForecastName);
    }

    public void setCompletionForecast(String progressForecastName, SprintData sprintData) {
        completionForecast.put(progressForecastName, sprintData);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    public String getId() {
        return data.getId();
    }

    public String getTitle() {
        return data.getTitle();
    }

    public String getDescription() {
        return data.getDescription();
    }

    public Double getEstimate() {
        return data.getEstimate();
    }

    public void setEstimate(Double estimate) {
        data.setEstimate(estimate);
    }

    public State getState() {
        return data.getState();
    }

    public String getSprint() {
        return data.getSprint();
    }

    public String getRank() {
        return data.getRank();
    }
}
