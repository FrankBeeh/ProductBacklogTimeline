package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Responsibility:
 * <ul>
 * <li>Immutable representation of the comparison of two {@link Sprint}s.
 * </ul>
 */
public class SprintComparison {
    private final Sprint sprint;
    private final Sprint referenceSprint;

    public SprintComparison(Sprint sprint) {
        this(sprint, sprint);
    }

    public SprintComparison(Sprint sprint, Sprint referenceSprint) {
        this.sprint = sprint;
        this.referenceSprint = referenceSprint;
    }

    public String getName() {
        return sprint.getName();
    }
    
    public LocalDate getStartDate() {
        return sprint.getStartDate();
    }

    public LocalDate getEndDate() {
        return sprint.getEndDate();
    }

    public Double getCapacityForecast() {
        return sprint.getCapacityForecast();
    }

    public Double getEffortForecast() {
        return sprint.getEffortForecast();
    }

    public Double getCapacityDone() {
        return sprint.getCapacityDone();
    }

    public Double getEffortDone() {
        return sprint.getEffortDone();
    }

    public Double getAccumulatedEffortDone() {
        return sprint.getAccumulatedEffortDone();
    }

    public Double getProgressForecastBasedOnHistory(String progressForecastName) {
        return sprint.getProgressForecastBasedOnHistory(progressForecastName);
    }

    public Double getAccumulatedProgressForecastBasedOnHistory(String progressForecastName) {
        return sprint.getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
    }

    public Double getAccumulatedEffortDoneOrProgressForcast(String progressForecastName) {
        return sprint.getAccumulatedEffortDoneOrProgressForcast(progressForecastName);
    }

    public State getState() {
        return sprint.getState();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
