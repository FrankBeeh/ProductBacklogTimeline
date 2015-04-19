package de.frankbeeh.productbacklogtimeline.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

public class Release {
    private final String name;
    private final ReleaseCriteria criteria;
    private final Map<String, String> completionForecast = new HashMap<String, String>();
    private Double accumulatedEstimate;

    public Release(String name, ReleaseCriteria criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    public String getName() {
        return name;
    }

    public ReleaseCriteria getCriteria() {
        return criteria;
    }

    public Double getAccumulatedEstimate() {
        return accumulatedEstimate;
    }

    public void setAccumulatedEstimate(Double accumulatedEstimate) {
        this.accumulatedEstimate = accumulatedEstimate;
    }

    public String getCompletionForecast(String progressForecastName) {
        return completionForecast.get(progressForecastName);
    }

    public void setCompletionForecast(String progressForecastName, String completionForecast) {
        this.completionForecast.put(progressForecastName, completionForecast);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
