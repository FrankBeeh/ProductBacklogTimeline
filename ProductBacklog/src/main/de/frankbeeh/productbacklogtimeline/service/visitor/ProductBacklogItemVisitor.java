package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

/**
 * Responsibility:
 * <ul>
 * <li>Define the interface for all visitors of a {@link ProductBacklogItem}.
 * </ul>
 */
public interface ProductBacklogItemVisitor {
    void reset();

    void visit(ProductBacklogItem productBacklogItem);
}
