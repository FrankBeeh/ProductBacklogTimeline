package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEffortDone;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMinimumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

public class Sprints {
    public static final String AVERAGE_VELOCITY_FORECAST = "Avg. Vel.";
    public static final String MINIMUM_VELOCITY_FORECAST = "Min. Vel.";
    public static final String MAXIMUM_VELOCITY_FORECAST = "Max. Vel.";

    private final List<SprintData> sprints;
    private final SprintDataVisitor[] visitors;

    public Sprints() {
        this(new AccumulateEffortDone(), new ComputeProgressForecastByAverageVelocity(AVERAGE_VELOCITY_FORECAST), new ComputeProgressForecastByMinimumVelocity(MINIMUM_VELOCITY_FORECAST),
                new ComputeProgressForecastByMaximumVelocity(MAXIMUM_VELOCITY_FORECAST));
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
