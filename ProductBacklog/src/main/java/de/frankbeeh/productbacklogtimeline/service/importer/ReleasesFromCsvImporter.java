package de.frankbeeh.productbacklogtimeline.service.importer;

import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteriaFactory;

public class ReleasesFromCsvImporter extends DataFromCsvImporter<ReleaseForecast> {
    private static final String RELEASE_COLUMN_NAME = "Release";
    private static final String CRITIRIA_COLUMN_NAME = "Criteria";

    @Override
    public void addItem(final ReleaseForecast releaseForecast) {
        releaseForecast.addRelease(new Release(getString(RELEASE_COLUMN_NAME), ReleaseCriteriaFactory.getReleaseCriteria(getString(CRITIRIA_COLUMN_NAME))));
    }

    @Override
    public ReleaseForecast createContainer() {
        return new ReleaseForecast();
    }
}
