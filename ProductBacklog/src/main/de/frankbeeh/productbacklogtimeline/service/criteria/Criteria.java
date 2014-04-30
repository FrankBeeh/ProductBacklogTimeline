package de.frankbeeh.productbacklogtimeline.service.criteria;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public interface Criteria {

    boolean isMatching(ProductBacklogItem productBacklogItem);

}
