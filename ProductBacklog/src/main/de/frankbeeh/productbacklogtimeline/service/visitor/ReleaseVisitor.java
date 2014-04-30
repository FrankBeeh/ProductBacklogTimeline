package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.Release;

public interface ReleaseVisitor {
    void reset();

    void visit(Release release, ProductBacklog productBacklog);
}
