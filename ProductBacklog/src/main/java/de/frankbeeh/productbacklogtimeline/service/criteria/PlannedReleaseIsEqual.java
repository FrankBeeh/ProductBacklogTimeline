package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class PlannedReleaseIsEqual implements ReleaseCriteria {

    private final String plannedRelase;

    public PlannedReleaseIsEqual(String plannedRelase) {
        this.plannedRelase = plannedRelase;
    }

    @Override
    public boolean isMatching(ProductBacklogItem productBacklogItem) {
        return plannedRelase.equals(productBacklogItem.getPlannedRelease());
    }

    @Override
    public String toString() {
        return "plannedRelease=\n" + plannedRelase;
    }
}


