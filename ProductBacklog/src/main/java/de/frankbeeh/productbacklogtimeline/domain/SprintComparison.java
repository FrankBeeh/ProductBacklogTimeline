package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatter;

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

    public ComparedValue getComparedStartDate() {
        return DifferenceFormatter.formatLocalDateDifference(sprint.getStartDate(), referenceSprint.getStartDate());
    }

    public ComparedValue getComparedEndDate() {
        return DifferenceFormatter.formatLocalDateDifference(sprint.getEndDate(), referenceSprint.getEndDate());
    }

    public ComparedValue getComparedCapacityForecast() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getCapacityForecast(), referenceSprint.getCapacityForecast(), false);
    }

    public ComparedValue getComparedEffortForecast() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getEffortForecast(), referenceSprint.getEffortForecast(), false);
    }

    public ComparedValue getComparedCapacityDone() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getCapacityDone(), referenceSprint.getCapacityDone(), false);
    }

    public ComparedValue getComparedEffortDone() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getEffortDone(), referenceSprint.getEffortDone(), false);
    }

    public ComparedValue getComparedAccumulatedEffortDone() {
        return DifferenceFormatter.formatDoubleDifference(sprint.getAccumulatedEffortDone(), referenceSprint.getAccumulatedEffortDone(), false);
    }

    public Double getAccumulatedEffortDone() {
        return sprint.getAccumulatedEffortDone();
    }

    public ComparedValue getComparedProgressForecastBasedOnHistory(String progressForecastName) {
        return DifferenceFormatter.formatDoubleDifference(sprint.getProgressForecastBasedOnHistory(progressForecastName), referenceSprint.getProgressForecastBasedOnHistory(progressForecastName),
                false);
    }

    public ComparedValue getComparedAccumulatedProgressForecastBasedOnHistory(String progressForecastName) {
        return DifferenceFormatter.formatDoubleDifference(sprint.getAccumulatedProgressForecastBasedOnHistory(progressForecastName),
                referenceSprint.getAccumulatedProgressForecastBasedOnHistory(progressForecastName), false);
    }

    public String getComparedState() {
        return DifferenceFormatter.formatStateDifference(sprint.getState(), referenceSprint.getState());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
