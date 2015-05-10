package de.frankbeeh.productbacklogtimeline.domain;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatter;

public class ReleaseComparison {
    private final Release release;
    private Release referenceRelease;

    public ReleaseComparison(Release release) {
        this(release, release);
    }

    public ReleaseComparison(Release release, Release referenceRelease) {
        this.release = release;
        if (referenceRelease == null) {
            this.referenceRelease = new Release(null, null);
        } else {
            this.referenceRelease = referenceRelease;
        }
    }

    public String getComparedName() {
        return DifferenceFormatter.formatTextualDifference(release.getName(), referenceRelease.getName());
    }

    public String getComparedCriteria() {
        return DifferenceFormatter.formatReleaseCriteriaDifference(release.getCriteria(), referenceRelease.getCriteria());
    }

    public String getComparedAccumulatedEstimate() {
        return DifferenceFormatter.formatDoubleDifference(release.getAccumulatedEstimate(), referenceRelease.getAccumulatedEstimate());
    }

    public String getComparedCompletionForecast(String progressForecastName) {
        return DifferenceFormatter.formatSprintDifference(release.getCompletionForecast(progressForecastName), referenceRelease.getCompletionForecast(progressForecastName));
    }
}
