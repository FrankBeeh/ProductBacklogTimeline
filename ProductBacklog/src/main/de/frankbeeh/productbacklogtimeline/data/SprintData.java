package de.frankbeeh.productbacklogtimeline.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByHistory;

public class SprintData {

    private final String name;
    private final Date startDate;
    private final Date endDate;
    private final Double capacityForecast;
    private final Double effortForecast;
    private final Double capacityDone;
    private final Double effortDone;
    private final Map<String, Double> effortForecastBasedOnHistory;

    public SprintData(String name, Date startDate, Date endDate, Double capacityForecast, Double effortForecast, Double capacityDone, Double effortDone) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacityForecast = capacityForecast;
        this.effortForecast = effortForecast;
        this.capacityDone = capacityDone;
        this.effortDone = effortDone;
        this.effortForecastBasedOnHistory = new HashMap<String, Double>();
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

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    public Double getEffortForecastBasedOnHistory(String historyName) {
        return effortForecastBasedOnHistory.get(historyName);
    }

    public void setEffortForecastBasedOnHistory(String historyName, Double effortForecast) {
        effortForecastBasedOnHistory.put(historyName, effortForecast);
    }

    public void accept(ComputeEffortForecastByHistory visitor) {
        visitor.visit(this);
    }
}
