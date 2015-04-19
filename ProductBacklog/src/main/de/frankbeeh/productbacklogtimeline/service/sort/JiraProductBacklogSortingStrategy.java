package de.frankbeeh.productbacklogtimeline.service.sort;

import java.util.Collections;
import java.util.Comparator;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

public class JiraProductBacklogSortingStrategy implements ProductBacklogSortingStrategy {

    private static class ProductBacklogItemComparator implements Comparator<ProductBacklogItem> {
        private final VelocityForecast velocityForecast;

        public ProductBacklogItemComparator(VelocityForecast velocityForecast) {
            this.velocityForecast = velocityForecast;
        }

        @Override
        public int compare(ProductBacklogItem productBacklogItem1, ProductBacklogItem productBacklogItem2) {
            final int sortIndexDifference = velocityForecast.getSortIndex(productBacklogItem1.getSprint()) - velocityForecast.getSortIndex(productBacklogItem2.getSprint());
            if (sortIndexDifference == 0) {
                return productBacklogItem1.getRank().compareTo(productBacklogItem2.getRank());
            }
            return sortIndexDifference;
        }
    }

    @Override
    public void sortProductBacklog(ProductBacklog productBacklog, VelocityForecast velocityForecast) {
        Collections.sort(productBacklog.getItems(), new ProductBacklogItemComparator(velocityForecast));
    }
}
