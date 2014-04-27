package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

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

    public Sprints() {
        this(new AccumulateEffortDone(), new ComputeProgressForecastByVelocity(AVERAGE_VELOCITY_FORECAST, new ComputeAverageVelocityStrategy()), new ComputeProgressForecastByVelocity(
                MINIMUM_VELOCITY_FORECAST, new ComputeMinimumVelocityStrategy()), new ComputeProgressForecastByVelocity(MAXIMUM_VELOCITY_FORECAST, new ComputeMaximumVelocityStrategy()));
    }

    // Visible for testing
    Sprints(SprintDataVisitor... visitors) {
        this.visitors = visitors;
        this.sprints = new ArrayList<SprintData>();
    }

    public List<SprintData> getSprints() {
        return sprints;
    }

    public void addItem(SprintData sprint) {
        sprints.add(sprint);
    }

    public void visitAllSprints() {
        for (final SprintDataVisitor visitor : visitors) {
            visitor.reset();
            for (final SprintData sprintData : sprints) {
                sprintData.accept(visitor);
            }
        }
    }
}
