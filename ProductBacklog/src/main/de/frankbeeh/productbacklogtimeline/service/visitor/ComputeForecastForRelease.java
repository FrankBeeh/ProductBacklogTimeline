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
            for (final String forecastName : Sprints.COMPLETION_FORECASTS) {
                setCompletionForecast(forecastName, release, lastMacthingProductBacklogItem);
            }
        } else {
            release.setAccumulatedEstimate(null);
            for (final String forecastName : Sprints.COMPLETION_FORECASTS) {
                release.setCompletionForecast(forecastName, null);
            }
        }
    }

    private ProductBacklogItem getLastMatchingProductBacklogItem(final List<ProductBacklogItem> matchingProductBacklogItems) {
        return matchingProductBacklogItems.get(matchingProductBacklogItems.size() - 1);
    }

    private void setCompletionForecast(String completionForecastName, Release release, final ProductBacklogItem productBacklogItem) {
        release.setCompletionForecast(completionForecastName, productBacklogItem.getCompletionForecast(completionForecastName));
    }
}
