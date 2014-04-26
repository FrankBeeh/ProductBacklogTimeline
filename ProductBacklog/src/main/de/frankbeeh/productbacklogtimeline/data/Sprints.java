package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.ComputeEffortForecastByAverageVelocity;

public class Sprints {
    private final List<SprintData> sprints;
    private final ComputeEffortForecastByAverageVelocity computeEffortForecastByAverageVelocity;

    public Sprints() {
        this(new ComputeEffortForecastByAverageVelocity());
    }

    // Visible for testing
    Sprints(ComputeEffortForecastByAverageVelocity computeEffortForecastByAverageVelocity) {
        this.sprints = new ArrayList<SprintData>();
        this.computeEffortForecastByAverageVelocity = computeEffortForecastByAverageVelocity;
    }

    public List<SprintData> getSprints() {
        return sprints;
    }

    public void addItem(SprintData sprint) {
        sprints.add(sprint);
    }

    public void computeEffortForecasts() {
        computeEffortForecastByAverageVelocity.reset();
        for (final SprintData sprintData : sprints) {
            sprintData.accept(computeEffortForecastByAverageVelocity);
        }
    }
}
