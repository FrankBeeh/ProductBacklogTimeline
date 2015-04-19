package de.frankbeeh.productbacklogtimeline.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

public class SprintData {

    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Date> startDate = new SimpleObjectProperty<Date>();
    private final ObjectProperty<Date> endDate = new SimpleObjectProperty<Date>();
    private final ObjectProperty<Double> capacityForecast = new SimpleObjectProperty<Double>();
    private final ObjectProperty<Double> effortForecast = new SimpleObjectProperty<Double>();
    private final ObjectProperty<Double> capacityDone = new SimpleObjectProperty<Double>();;
    private final ObjectProperty<Double> effortDone = new SimpleObjectProperty<Double>();;

    private final Map<String, Double> progressForecastBasedOnHistory;
    private final Map<String, Double> accumulatedProgressForecastBasedOnHistory;
    private Double accumulatedEffortDone;

    public SprintData() {
        this("", null, null, null, null, null, null);
    }

    public SprintData(String name, Date startDate, Date endDate, Double capacityForecast, Double effortForecast, Double capacityDone, Double effortDone) {
        this.name.set(name);
        this.startDate.set(startDate);
        this.endDate.set(endDate);
        this.capacityForecast.set(capacityForecast);
        this.effortForecast.set(effortForecast);
        this.capacityDone.set(capacityDone);
        this.effortDone.set(effortDone);
        this.accumulatedEffortDone = null;
        this.progressForecastBasedOnHistory = new HashMap<String, Double>();
        this.accumulatedProgressForecastBasedOnHistory = new HashMap<String, Double>();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }

    public Double getCapacityForecast() {
        return capacityForecast.get();
    }

    public ObjectProperty<Double> capacityForecastProperty() {
        return capacityForecast;
    }

    public Double getEffortForecast() {
        return effortForecast.get();
    }

    public ObjectProperty<Double> effortForecastProperty() {
        return effortForecast;
    }

    public Double getCapacityDone() {
        return capacityDone.get();
    }

    public ObjectProperty<Double> capacityDoneProperty() {
        return capacityDone;
    }

    public Double getEffortDone() {
        return effortDone.get();
    }

    public ObjectProperty<Double> effortDoneProperty() {
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

    public String getComparedForecast(SprintData referenceSprintData) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String sprintName = getName();
        stringBuilder.append(sprintName);
        if (referenceSprintData != null) {
            final String referenceSprintName = referenceSprintData.getName();
            if (!sprintName.equals(referenceSprintName)) {
                stringBuilder.append("\n(").append(referenceSprintName).append(")");
            }
        }
        final Date endDate = getEndDate();
        if (endDate != null) {
            stringBuilder.append("\n");
            stringBuilder.append(FormatUtility.formatDate(endDate));
            if (referenceSprintData != null) {
                final Date referenceEndDate = referenceSprintData.getEndDate();
                if (referenceEndDate != null) {
                    final long diffDays = (endDate.getTime() - referenceEndDate.getTime()) / (1000 * 60 * 60 * 24);
                    if (diffDays != 0) {
                        stringBuilder.append("\n(").append(FormatUtility.formatDifferenceLong(diffDays)).append("d)");
                    }
                }
            }
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
