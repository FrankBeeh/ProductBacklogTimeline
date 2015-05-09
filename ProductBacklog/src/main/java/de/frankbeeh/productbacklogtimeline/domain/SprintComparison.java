package de.frankbeeh.productbacklogtimeline.domain;

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
        if (referenceSprint == null) {
            this.referenceSprint = new Sprint(null, null, null, null, null, null, null);
        } else {
            this.referenceSprint = referenceSprint;
        }
    }

    public String getComparedName() {
        return DifferenceFormatter.formatTextualDifference(sprint.getName(), referenceSprint.getName());
    }

    public String getComparedStartDate() {
        return DifferenceFormatter.formatDateDifference(sprint.getStartDate(), referenceSprint.getStartDate());
    }

    public String getComparedEndDate() {
        return DifferenceFormatter.formatDateDifference(sprint.getEndDate(), referenceSprint.getEndDate());
    }

    public String getComparedCapacityForecast() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getCapacityForecast(), referenceSprint.getCapacityForecast());
    }

    public String getComparedEffortForecast() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getEffortForecast(), referenceSprint.getEffortForecast());
    }

    public String getComparedCapacityDone() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getCapacityDone(), referenceSprint.getCapacityDone());
    }

    public String getComparedEffortDone() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getEffortDone(), referenceSprint.getEffortDone());
    }

    public String getComparedAccumulatedEffortDone() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getAccumulatedEffortDone(), referenceSprint.getAccumulatedEffortDone());
    }

    public Double getAccumulatedEffortDone() {
        return sprint.getAccumulatedEffortDone();
    }

    public String getComparedProgressForecastBasedOnHistory(String progressForecastName) {
        return DifferenceFormatter.formatDoubleDifference(sprint.getProgressForecastBasedOnHistory(progressForecastName), referenceSprint.getProgressForecastBasedOnHistory(progressForecastName));
    }

    public String getComparedAccumulatedProgressForecastBasedOnHistory(String progressForecastName) {
        return DifferenceFormatter.formatDoubleDifference(sprint.getAccumulatedProgressForecastBasedOnHistory(progressForecastName),
                referenceSprint.getAccumulatedProgressForecastBasedOnHistory(progressForecastName));
    }

    public State getState() {
        return sprint.getState();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
