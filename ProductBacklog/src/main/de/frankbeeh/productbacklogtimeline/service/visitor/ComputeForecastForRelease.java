package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.util.List;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.Release;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public class ComputeForecastForRelease implements ReleaseVisitor {

    @Override
    public void reset() {
        // Nothing to do.
    }

    @Override
    public void visit(Release release, ProductBacklog productBacklog) {
        final List<ProductBacklogItem> matchingProductBacklogItems = productBacklog.getMatchingProductBacklogItems(release.getCriteria());
        if (!matchingProductBacklogItems.isEmpty()) {
            final ProductBacklogItem lastMacthingProductBacklogItem = getLastMatchingProductBacklogItem(matchingProductBacklogItems);
            release.setAccumulatedEstimate(lastMacthingProductBacklogItem.getAccumulatedEstimate());
            setCompletionForecast(release, lastMacthingProductBacklogItem, Sprints.MINIMUM_VELOCITY_FORECAST);
            setCompletionForecast(release, lastMacthingProductBacklogItem, Sprints.AVERAGE_VELOCITY_FORECAST);
            setCompletionForecast(release, lastMacthingProductBacklogItem, Sprints.MAXIMUM_VELOCITY_FORECAST);
        }
    }

    private ProductBacklogItem getLastMatchingProductBacklogItem(final List<ProductBacklogItem> matchingProductBacklogItems) {
        return matchingProductBacklogItems.get(matchingProductBacklogItems.size() - 1);
    }

    private void setCompletionForecast(Release release, final ProductBacklogItem productBacklogItem, String completionForecastName) {
        release.setCompletionForecast(completionForecastName, productBacklogItem.getCompletionForecast(completionForecastName));
    }

}
