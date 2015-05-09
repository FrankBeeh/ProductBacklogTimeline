package de.frankbeeh.productbacklogtimeline.service.importer;

import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.Releases;
import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;
import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

public class ReleasesFromCsvImporter extends DataFromCsvImporter<Releases> {
    private static final String RELEASE_COLUMN_NAME = "Release";
    private static final String CRITIRIA_COLUMN_NAME = "Criteria";

    @Override
    public void addItem(final Releases releases) {
        releases.addRelease(new Release(getString(RELEASE_COLUMN_NAME), getReleaseCriteria(getString(CRITIRIA_COLUMN_NAME))));
    }

    @Override
    public Releases createContainer() {
        return new Releases();
    }

    private ReleaseCriteria getReleaseCriteria(String string) {
        final String[] split = string.split("=");
        switch (split[0]) {
            case "plannedRelease":
                return new PlannedReleaseIsEqual(split[1]);
            case "idOfPBI":
                return new ProductBacklogItemIdIsEqual(split[1]);
            default:
                throw new IllegalArgumentException("Unknown criteria '" + string + "'!");
        }
    }
}
