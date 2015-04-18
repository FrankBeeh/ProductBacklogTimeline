package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.data.Release;

/**
 * Responsibility:
 * <ul>
 * <li>Define the interface for all visitors of a {@link Release}.
 * </ul>
 */
public interface ReleaseVisitor {
    void reset();

    void visit(Release release, ProductBacklogComparison productBacklogComparison);
}
