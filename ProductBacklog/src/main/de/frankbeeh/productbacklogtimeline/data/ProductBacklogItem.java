package de.frankbeeh.productbacklogtimeline.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductBacklogItem {

    private final String id;
    private final String title;
    private final String description;
    private Double estimate;
    private final State state;
    private final Map<String, String> completionForecast;
    private final String sprint;
    private final Integer rank;
    private Double accumulatedEstimate;

    public ProductBacklogItem(String id, String title, String description, Double estimate, State state, String sprint, Integer rank) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimate = estimate;
        this.state = state;
        this.sprint = sprint;
        this.rank = rank;
        this.completionForecast = new HashMap<String, String>();
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

    public void setAccumulatedEstimate(Double accumulatedEstimate) {
        this.accumulatedEstimate = accumulatedEstimate;
    }

    public Double getAccumulatedEstimate() {
        return accumulatedEstimate;
    }

    public String getCompletionForecast(String progressForecastName) {
        return completionForecast.get(progressForecastName);
    }

    public void setCompletionForecast(String progressForecastName, SprintData sprintData) {
        if (sprintData == null) {
            completionForecast.put(progressForecastName, null);
        } else {
            completionForecast.put(progressForecastName, sprintData.getDescription());
        }
    }

    public String getSprint() {
        return sprint;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
