package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.Release;

public class AccumulateEffortForRelease implements ReleaseVisitor {
    @Override
    public void reset() {
        // Do nothing.
    }

    @Override
    public void visit(Release release, ProductBacklog productBacklog) {
        release.setAccumulatedEstimate(productBacklog.getAccumulatedEstimate(release.getCriteria()));
    }
}
