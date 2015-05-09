package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;

public class AccumulateEffortDone implements SprintDataVisitor {

    private Double accumulatedEffortDone;

    public AccumulateEffortDone() {
        reset();
    }

    @Override
    public void reset() {
        accumulatedEffortDone = null;
    }

    @Override
    public void visit(Sprint sprintData) {
        accumulatedEffortDone = computeAccumulatedEffortDone(sprintData);
        sprintData.setAccumulatedEffortDone(accumulatedEffortDone);
    }

    private Double computeAccumulatedEffortDone(Sprint sprintData) {
        final Double effortDone = sprintData.getEffortDone();
        if (accumulatedEffortDone == null) {
            return effortDone;
        }
        if (effortDone == null) {
            return null;
        }
        return accumulatedEffortDone.doubleValue() + effortDone;
    }
}
