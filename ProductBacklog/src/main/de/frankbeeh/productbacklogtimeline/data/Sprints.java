package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEffortDone;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeAverageVelocityStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeMaximumVelocityStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeMinimumVelocityStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

public class Sprints {
    public static final String AVERAGE_VELOCITY_FORECAST = "Avg. Vel.";
    public static final String MINIMUM_VELOCITY_FORECAST = "Min. Vel.";
    public static final String MAXIMUM_VELOCITY_FORECAST = "Max. Vel.";

    private final List<SprintData> sprints;
    private final SprintDataVisitor[] visitors;
    private final Map<String, Integer> sortIndexMap;

    public Sprints() {
        this(new AccumulateEffortDone(), new ComputeProgressForecastByVelocity(AVERAGE_VELOCITY_FORECAST, new ComputeAverageVelocityStrategy()), new ComputeProgressForecastByVelocity(
                MINIMUM_VELOCITY_FORECAST, new ComputeMinimumVelocityStrategy()), new ComputeProgressForecastByVelocity(MAXIMUM_VELOCITY_FORECAST, new ComputeMaximumVelocityStrategy()));
    }

    @VisibleForTesting
    Sprints(SprintDataVisitor... visitors) {
        this.visitors = visitors;
        this.sprints = new ArrayList<SprintData>();
        this.sortIndexMap = new HashMap<String, Integer>();
    }

    public List<SprintData> getSprints() {
        return sprints;
    }

    public void addItem(SprintData sprint) {
        sprints.add(sprint);
        sortIndexMap.put(sprint.getName(), sprints.size());
    }

    public void updateAllSprints() {
        for (final SprintDataVisitor visitor : visitors) {
            visitor.reset();
            for (final SprintData sprintData : sprints) {
                sprintData.accept(visitor);
            }
        }
    }

    public SprintData getCompletionSprintForecast(String progressForecastName, double accumulatedEstimate) {
        for (final SprintData sprint : sprints) {
            final Double accumulatedEffortDoneOrProgressForcast = sprint.getAccumulatedEffortDoneOrProgressForcast(progressForecastName);
            if (accumulatedEffortDoneOrProgressForcast != null && accumulatedEstimate <= accumulatedEffortDoneOrProgressForcast) {
                return sprint;
            }
        }
        return null;
    }

    public int getSortIndex(String sprintNames) {
        final String[] splittedSprintNames = sprintNames.split(", ");
        final Integer sortIndex = sortIndexMap.get(splittedSprintNames[splittedSprintNames.length - 1]);
        if (sortIndex == null) {
            return Integer.MAX_VALUE;
        }
        return sortIndex;
    }
}
