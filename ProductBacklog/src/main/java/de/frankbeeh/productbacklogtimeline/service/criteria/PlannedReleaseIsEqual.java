package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparisonItem;

public class PlannedReleaseIsEqual implements ReleaseCriteria {

    private final String plannedRelase;

    public PlannedReleaseIsEqual(String plannedRelase) {
        this.plannedRelase = plannedRelase;
    }

    @Override
    public boolean isMatching(ProductBacklogComparisonItem productBacklogComparisonItem) {
        return plannedRelase.equals(productBacklogComparisonItem.getPlannedRelease());
    }

    @Override
    public String toString() {
        return "plannedRelease=\n" + plannedRelase;
    }
}
