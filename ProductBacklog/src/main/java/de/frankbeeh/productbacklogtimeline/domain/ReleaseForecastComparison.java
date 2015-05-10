package de.frankbeeh.productbacklogtimeline.domain;

import java.util.List;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link ReleaseForecast}s.
 * </ul>
 */
public class ReleaseForecastComparison extends Comparison<ReleaseForecast, Release, ReleaseComparison>{

    @Override
    protected List<Release> getSelectedItems() {
        return getSelected().getReleases();
    }

    @Override
    protected ReleaseComparison createComparison(Release release) {
        return new ReleaseComparison(release, getReference().getReleaseByName(release.getName()));
    }

    @Override
    protected ReleaseComparison createComparisonWithSelf(Release release) {
        return new ReleaseComparison(release);
    }
}
