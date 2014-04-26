package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByMinimumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByHistory;

public class Sprints {
    private final List<SprintData> sprints;
    private final ComputeEffortForecastByHistory[] visitors;

    public Sprints() {
        this(new ComputeEffortForecastByAverageVelocity(), new ComputeEffortForecastByMinimumVelocity());
    }

    // Visible for testing
    Sprints(ComputeEffortForecastByHistory... visitors) {
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
        for (final ComputeEffortForecastByHistory visitor : visitors) {
            visitor.reset();
            for (final SprintData sprintData : sprints) {
                sprintData.accept(visitor);
            }
        }
    }
}
