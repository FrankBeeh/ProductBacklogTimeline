package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByHistory;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMinimumVelocity;

public class Sprints {
    private final List<SprintData> sprints;
    private final ComputeProgressForecastByHistory[] visitors;

    public Sprints() {
        this(new ComputeProgressForecastByAverageVelocity(), new ComputeProgressForecastByMinimumVelocity(), new ComputeProgressForecastByMaximumVelocity());
    }

    // Visible for testing
    Sprints(ComputeProgressForecastByHistory... visitors) {
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
        for (final ComputeProgressForecastByHistory visitor : visitors) {
            visitor.reset();
            for (final SprintData sprintData : sprints) {
                sprintData.accept(visitor);
            }
        }
    }
}
