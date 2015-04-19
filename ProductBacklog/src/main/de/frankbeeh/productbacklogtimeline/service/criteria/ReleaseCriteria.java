package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparisonItem;

public interface ReleaseCriteria {

    boolean isMatching(ProductBacklogComparisonItem productBacklogComparisonItem);

}
