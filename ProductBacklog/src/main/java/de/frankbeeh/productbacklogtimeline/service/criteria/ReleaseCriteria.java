package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public interface ReleaseCriteria {

    boolean isMatching(ProductBacklogItem productBacklogItem);

}
