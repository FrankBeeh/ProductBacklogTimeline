package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

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
            for (final String forecastName : VelocityForecast.COMPLETION_FORECASTS) {
                release.setCompletionForecast(forecastName, lastMacthingProductBacklogItem.getCompletionForecast(forecastName));
            }
        } else {
            release.setAccumulatedEstimate(null);
            for (final String forecastName : VelocityForecast.COMPLETION_FORECASTS) {
                release.setCompletionForecast(forecastName, null);
            }
        }
    }

    private ProductBacklogItem getLastMatchingProductBacklogItem(final List<ProductBacklogItem> matchingProductBacklogItems) {
        return matchingProductBacklogItems.get(matchingProductBacklogItems.size() - 1);
    }
}
