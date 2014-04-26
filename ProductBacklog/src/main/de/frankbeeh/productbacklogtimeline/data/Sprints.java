package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

public class Sprints {

    private final List<SprintData> sprints;

    public Sprints() {
        sprints = new ArrayList<SprintData>();
    }

    public List<SprintData> getSprints() {
        return sprints;
    }

    public void addItem(SprintData sprint) {
        sprints.add(sprint);
    }

}
