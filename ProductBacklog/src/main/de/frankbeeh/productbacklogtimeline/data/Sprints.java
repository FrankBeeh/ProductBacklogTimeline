package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEffortDone;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeAccumulatedProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeAccumulatedProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeAccumulatedProgressForecastByMinimumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMinimumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

public class Sprints {
    private final List<SprintData> sprints;
    private final SprintDataVisitor[] visitors;

    public Sprints() {
        this(new AccumulateEffortDone(), new ComputeProgressForecastByAverageVelocity(), new ComputeProgressForecastByMinimumVelocity(), new ComputeProgressForecastByMaximumVelocity(),
                new ComputeAccumulatedProgressForecastByAverageVelocity(), new ComputeAccumulatedProgressForecastByMinimumVelocity(), new ComputeAccumulatedProgressForecastByMaximumVelocity());
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
