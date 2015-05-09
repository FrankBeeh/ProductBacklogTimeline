package de.frankbeeh.productbacklogtimeline.domain;

import java.util.List;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link VelocityForecast}s.
 * </ul>
 */
public class VelocityForecastComparison extends Comparison<VelocityForecast, Sprint, SprintComparison> {

    @Override
    protected List<Sprint> getItems() {
        return getSelected().getSprints();
    }

    @Override
    protected SprintComparison createComparison(Sprint sprint) {
        return new SprintComparison(sprint, getReference().getSprintByEndDate(sprint.getEndDate()));
    }

    @Override
    protected SprintComparison createComparisonWithSelf(Sprint sprint) {
        return new SprintComparison(sprint);
    }
}
