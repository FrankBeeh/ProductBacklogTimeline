package de.frankbeeh.productbacklogtimeline.service.sort;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public interface ProductBacklogSortingStrategy {

    void sortProductBacklog(ProductBacklog productBacklog, Sprints sprints);

}
