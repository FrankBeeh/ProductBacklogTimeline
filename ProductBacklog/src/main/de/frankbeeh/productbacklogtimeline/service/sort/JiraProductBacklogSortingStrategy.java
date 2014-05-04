package de.frankbeeh.productbacklogtimeline.service.sort;

import java.util.Collections;
import java.util.Comparator;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public class JiraProductBacklogSortingStrategy implements ProductBacklogSortingStrategy {

    private static class ProductBacklogItemComparator implements Comparator<ProductBacklogItem> {
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

    @Override
    public void sortProductBacklog(ProductBacklog productBacklog, Sprints sprints) {
        Collections.sort(productBacklog.getItems(), new ProductBacklogItemComparator(sprints));
    }
}
