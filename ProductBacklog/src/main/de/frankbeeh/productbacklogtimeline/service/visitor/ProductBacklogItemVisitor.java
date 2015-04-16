package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Define the interface for all visitors of a {@link ProductBacklogItem}.
 * </ul>
 */
public interface ProductBacklogItemVisitor {
    void reset();

    void visit(ProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast, ProductBacklog referenceProductBacklog, VelocityForecast referenceVelocityForecast);
}
