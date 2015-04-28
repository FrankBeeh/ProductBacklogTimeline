package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparisonItem;

public interface ReleaseCriteria {

    boolean isMatching(ProductBacklogComparisonItem productBacklogComparisonItem);

}
