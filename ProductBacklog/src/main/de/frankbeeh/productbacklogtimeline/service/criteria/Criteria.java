package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparisonItem;

public interface Criteria {

    boolean isMatching(ProductBacklogComparisonItem productBacklogComparisonItem);

}
