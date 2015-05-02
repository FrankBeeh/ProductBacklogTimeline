package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<LocalDate>();
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

    public SprintData(String name, LocalDate startDate, LocalDate endDate, Double capacityForecast, Double effortForecast, Double capacityDone, Double effortDone) {
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

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
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
        final LocalDate endDate = getEndDate();
        if (endDate != null) {
            stringBuilder.append("\n");
            stringBuilder.append(FormatUtility.formatLocalDate(endDate));
            if (referenceSprintData != null) {
                final LocalDate referenceEndDate = referenceSprintData.getEndDate();
                if (referenceEndDate != null) {
                    final long diffDays = ChronoUnit.DAYS.between(referenceEndDate,  endDate);
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

    public State getState() {
        if (getEffortDone() != null){
            return State.Done; 
        }
        if (getEffortForecast() != null){
            return State.InProgress;
        }
        return State.Todo;
    }
}
