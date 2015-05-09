package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public class ComputeForecastForRelease implements ReleaseVisitor {

    @Override
    public void reset() {
        // Nothing to do.
    }

    @Override
    public void visit(Release release, ProductBacklogComparison productBacklogComparison) {
        final List<ProductBacklogItemComparison> matchingProductBacklogItems = productBacklogComparison.getMatchingProductBacklogItems(release.getCriteria());
        if (!matchingProductBacklogItems.isEmpty()) {
            final ProductBacklogItemComparison lastMacthingProductBacklogComparisonItem = getLastMatchingProductBacklogItem(matchingProductBacklogItems);
            release.setAccumulatedEstimate(lastMacthingProductBacklogComparisonItem.getAccumulatedEstimate());
            for (final String forecastName : VelocityForecast.COMPLETION_FORECASTS) {
                setCompletionForecast(forecastName, release, lastMacthingProductBacklogComparisonItem);
            }
        } else {
            release.setAccumulatedEstimate(null);
            for (final String forecastName : VelocityForecast.COMPLETION_FORECASTS) {
                release.setCompletionForecast(forecastName, null);
            }
        }
    }

    private ProductBacklogItemComparison getLastMatchingProductBacklogItem(final List<ProductBacklogItemComparison> matchingProductBacklogItems) {
        return matchingProductBacklogItems.get(matchingProductBacklogItems.size() - 1);
    }

    private void setCompletionForecast(String completionForecastName, Release release, final ProductBacklogItemComparison productBacklogComparisonItem) {
        release.setCompletionForecast(completionForecastName, productBacklogComparisonItem.getComparedCompletionForecast(completionForecastName));
    }
}
