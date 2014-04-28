package de.frankbeeh.productbacklogtimeline.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductBacklogItem {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private final String id;
    private final String title;
    private final String description;
    private final Double estimate;
    private final State state;
    private final Map<String, String> completionForecast;
    private Double accumulatedEstimate;

    public ProductBacklogItem(String id, String title, String description, Double estimate, State state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimate = estimate;
        this.state = state;
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
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sprintData.getName());
            final Date endDate = sprintData.getEndDate();
            if (endDate != null) {
                stringBuilder.append("\n");
                stringBuilder.append(DATE_FORMAT.format(endDate));
            }
            completionForecast.put(progressForecastName, stringBuilder.toString());
        }
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

}
