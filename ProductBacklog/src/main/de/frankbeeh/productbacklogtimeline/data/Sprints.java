package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEffortDone;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMinimumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

public class Sprints {
    private final List<SprintData> sprints;
    private final SprintDataVisitor[] visitors;

    public Sprints() {
        this(new ComputeProgressForecastByAverageVelocity(), new ComputeProgressForecastByMinimumVelocity(), new ComputeProgressForecastByMaximumVelocity());
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

    public void computeEffortForecasts() {
        for (final SprintDataVisitor visitor : visitors) {
            visitor.reset();
            for (final SprintData sprintData : sprints) {
                sprintData.accept(visitor);
            }
        }
    }

    public void computeAccumumatedEffort() {
        final AccumulateEffortDone visitor = new AccumulateEffortDone();
        for (final SprintData sprintData : sprints) {
            sprintData.accept(visitor);
        }
    }
}
