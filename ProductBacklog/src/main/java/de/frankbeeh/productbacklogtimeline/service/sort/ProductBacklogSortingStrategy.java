package de.frankbeeh.productbacklogtimeline.service.sort;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public interface ProductBacklogSortingStrategy {

    void sortProductBacklog(ProductBacklog productBacklog, VelocityForecast velocityForecast);

}
