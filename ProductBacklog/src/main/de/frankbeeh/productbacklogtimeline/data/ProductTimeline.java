package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;

public class ProductTimeline {
    private static final String INITIAL_NAME = "Initial";
    private final List<ReleaseForecast> releaseForecasts = new ArrayList<ReleaseForecast>();
    private final Releases releases;
    private String selectedName = INITIAL_NAME;
    private String referenceName = INITIAL_NAME;

    public ProductTimeline() {
        this(new Releases());
        createDummyReleases();
    }

    @VisibleForTesting
    ProductTimeline(Releases releases) {
        this.releases = releases;
        this.releaseForecasts.add(new ReleaseForecast(INITIAL_NAME, new ProductBacklog(), new VelocityForecast()));
    }

    public void addProductBacklog(String name, ProductBacklog productBacklog) {
        releaseForecasts.add(new ReleaseForecast(name, productBacklog, releaseForecasts.get(releaseForecasts.size()-1).getVelocityForecast()));
    }

    public ProductBacklog getSelectedProductBacklog() {
        return getProductBacklog(selectedName);
    }

    public void selectReleaseForecast(String selectedName) {
        this.selectedName = selectedName;
        updateProductBacklog();
        updateReleases();
    }

    public void selectReferenceReleaseForecast(String referenceName) {
        this.referenceName = referenceName;
        updateProductBacklog();
        updateReleases();
    }

    public void setSelectedVelocityForecast(VelocityForecast velocityForecast) {
        velocityForecast.updateForecast();
        getReleaseForecast(selectedName).setVelocityForecast(velocityForecast);
        updateProductBacklog();
        updateReleases();
    }

    public VelocityForecast getSelectedVelocityForecast() {
        return getReleaseForecast(selectedName).getVelocityForecast();
    }

    public Releases getReleases() {
        return releases;
    }

    public List<String> getReleaseForecastNames() {
        final List<String> names = new ArrayList<String>();
        for (ReleaseForecast releaseForecast : releaseForecasts) {
            names.add(releaseForecast.getName());
        }
        return names;
    }

    private void updateProductBacklog() {
        getSelectedProductBacklog().updateAllItems(getSelectedVelocityForecast(), getReferenceProductBacklog());
    }

    private ProductBacklog getReferenceProductBacklog() {
        return getProductBacklog(referenceName);
    }

    private void updateReleases() {
        releases.updateAll(getSelectedProductBacklog());
    }

    private void createDummyReleases() {
        releases.addRelease(new Release("Release 0.8", new ProductBacklogItemIdIsEqual("CRM-793")));
        releases.addRelease(new Release("Release 0.9", new ProductBacklogItemIdIsEqual("CRM-560")));
        releases.addRelease(new Release("Release 1.0", new ProductBacklogItemIdIsEqual("CRM-771")));
        releases.addRelease(new Release("Release 1.2", new ProductBacklogItemIdIsEqual("CRM-554")));
    }

    private ProductBacklog getProductBacklog(String name) {
        return getReleaseForecast(name).getProductBacklog();
    }

    private ReleaseForecast getReleaseForecast(String name) {
        if (name == null) {
            return getReleaseForecast(INITIAL_NAME);
        }
        for (ReleaseForecast releaseForecast : releaseForecasts) {
            if (releaseForecast.getName().equals(name)) {
                return releaseForecast;
            }
        }
        throw new IllegalArgumentException("Release forcast '" + name + "' not found!");
    }
}
