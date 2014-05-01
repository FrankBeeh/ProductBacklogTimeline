package de.frankbeeh.productbacklogtimeline.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
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

    public void setProgressForecastBasedOnHistory(String progressForecastName, Double progressForecast) {
        progressForecastBasedOnHistory.put(progressForecastName, roundToOneDecimal(progressForecast));
    }

    public Double getProgressForecastBasedOnHistory(String progressForecastName) {
        return progressForecastBasedOnHistory.get(progressForecastName);
    }

    public void setAccumulatedProgressForecastBasedOnHistory(String progressForecastName, Double progressForecast) {
        accumulatedProgressForecastBasedOnHistory.put(progressForecastName, roundToOneDecimal(progressForecast));
    }

    public Double getAccumulatedProgressForecastBasedOnHistory(String progressForecastName) {
        return accumulatedProgressForecastBasedOnHistory.get(progressForecastName);
    }

    public void accept(SprintDataVisitor visitor) {
        visitor.visit(this);
    }

    public Double getAccumulatedEffortDoneOrProgressForcast(String progressForecastName) {
        final Double accumulatedEffortDone = getAccumulatedEffortDone();
        if (accumulatedEffortDone != null) {
            return accumulatedEffortDone;
        }
        return getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
    }

    public String getDescription() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        final Date endDate = getEndDate();
        if (endDate != null) {
            stringBuilder.append("\n");
            stringBuilder.append(FormatUtility.formatDate(endDate));
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private Double roundToOneDecimal(Double value) {
        if (value == null) {
            return value;
        }
        return Math.round(value.doubleValue() * 10.0) / 10.0;
    }
}
