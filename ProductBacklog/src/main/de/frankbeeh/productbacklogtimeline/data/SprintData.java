package de.frankbeeh.productbacklogtimeline.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

public class SprintData {
    private final String name;
    private final Date startDate;
    private final Date endDate;
    private final Double capacityForecast;
    private final Double effortForecast;
    private final Double capacityDone;
    private final Double effortDone;

    private final Map<String, Double> progressForecastBasedOnHistory;
    private final Map<String, Double> accumulatedProgressForecastBasedOnHistory;
    private Double accumulatedEffortDone;

    public SprintData(String name, Date startDate, Date endDate, Double capacityForecast, Double effortForecast, Double capacityDone, Double effortDone) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacityForecast = capacityForecast;
        this.effortForecast = effortForecast;
        this.capacityDone = capacityDone;
        this.effortDone = effortDone;
        this.accumulatedEffortDone = null;
        this.progressForecastBasedOnHistory = new HashMap<String, Double>();
        this.accumulatedProgressForecastBasedOnHistory = new HashMap<String, Double>();
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getCapacityForecast() {
        return capacityForecast;
    }

    public Double getEffortForecast() {
        return effortForecast;
    }

    public Double getCapacityDone() {
        return capacityDone;
    }

    public Double getEffortDone() {
        return effortDone;
    }

    public void setAccumulatedEffortDone(Double accumulatedEffortDone) {
        this.accumulatedEffortDone = accumulatedEffortDone;
    }

    public Double getAccumulatedEffortDone() {
        return accumulatedEffortDone;
    }

    public void setProgressForecastBasedOnHistory(String historyName, Double progressForecast) {
        progressForecastBasedOnHistory.put(historyName, progressForecast);
    }

    public Double getProgressForecastBasedOnHistory(String historyName) {
        return progressForecastBasedOnHistory.get(historyName);
    }

    public void setAccumulatedProgressForecastBasedOnHistory(String historyName, Double progressForecast) {
        accumulatedProgressForecastBasedOnHistory.put(historyName, progressForecast);
    }

    public Double getAccumulatedProgressForecastBasedOnHistory(String historyName) {
        return accumulatedProgressForecastBasedOnHistory.get(historyName);
    }

    public void accept(SprintDataVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
