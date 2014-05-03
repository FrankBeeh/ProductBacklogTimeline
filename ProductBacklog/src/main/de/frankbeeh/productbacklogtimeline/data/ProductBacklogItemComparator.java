package de.frankbeeh.productbacklogtimeline.data;

import java.util.Comparator;

final class ProductBacklogItemComparator implements Comparator<ProductBacklogItem> {
    private final Sprints sprints;

    public ProductBacklogItemComparator(Sprints sprints) {
        this.sprints = sprints;
    }

    @Override
    public int compare(ProductBacklogItem productBacklogItem1, ProductBacklogItem productBacklogItem2) {
        final int sortIndexDifference = sprints.getSortIndex(productBacklogItem1.getSprint()) - sprints.getSortIndex(productBacklogItem2.getSprint());
        if (sortIndexDifference == 0) {
            return productBacklogItem1.getRank() - productBacklogItem2.getRank();
        }
        return sortIndexDifference;
    }
}