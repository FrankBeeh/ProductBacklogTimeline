package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Define the interface for all visitors of a {@link ProductBacklogItem}.
 * </ul>
 */
public interface ProductBacklogItemVisitor {
    void reset();

    void visit(ProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast);
}
