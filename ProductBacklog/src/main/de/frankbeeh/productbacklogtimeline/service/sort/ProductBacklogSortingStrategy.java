package de.frankbeeh.productbacklogtimeline.service.sort;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

public interface ProductBacklogSortingStrategy {

    void sortProductBacklog(ProductBacklog productBacklog, VelocityForecast velocityForecast);

}
