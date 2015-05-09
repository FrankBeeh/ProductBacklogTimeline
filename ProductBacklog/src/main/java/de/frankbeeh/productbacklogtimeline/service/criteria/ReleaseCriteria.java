package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;

public interface ReleaseCriteria {

    boolean isMatching(ProductBacklogItemComparison productBacklogComparisonItem);

}
