package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;

public class ProductTimeline {
    private static final String INITIAL_NAME = "Initial";
    private final List<ReleaseForecast> releaseForecasts = new ArrayList<ReleaseForecast>();
    private String selectedName = INITIAL_NAME;
    private String referenceName = INITIAL_NAME;
    private final ProductBacklogComparison productBacklogComparison;

    public ProductTimeline() {
        this(createDummyReleases(), new ProductBacklogComparison());
    }

    @VisibleForTesting
    ProductTimeline(Releases releases, ProductBacklogComparison productBacklogComparison) {
        this.productBacklogComparison = productBacklogComparison;
        this.releaseForecasts.add(new ReleaseForecast(null, INITIAL_NAME, releases));
        productBacklogComparison.setSelectedProductBacklog(getSelectedProductBacklog());
    }

    public void addProductBacklog(LocalDateTime dateTime, String name, ProductBacklog productBacklog) {
        updateProductBacklog(productBacklog);
        releaseForecasts.add(new ReleaseForecast(dateTime, name, productBacklog, releaseForecasts.get(releaseForecasts.size() - 1)));
    }

    public ProductBacklog getSelectedProductBacklog() {
        return getProductBacklog(selectedName);
    }
    
    public ReleaseForecast getSelectedReleaseForecast() {
        return getReleaseForecast(selectedName);
    }

    public void selectReleaseForecast(String selectedName) {
        this.selectedName = selectedName;
        productBacklogComparison.setSelectedProductBacklog(getSelectedProductBacklog());
        updateProductBacklogComparison();
        updateReleases();
    }

    public void selectReferenceReleaseForecast(String referenceName) {
        this.referenceName = referenceName;
        productBacklogComparison.setReferenceProductBacklog(getReferenceProductBacklog());
        updateProductBacklogComparison();
        updateReleases();
    }

    public void setVelocityForecastForSelectedReleaseForecast(VelocityForecast velocityForecast) {
        velocityForecast.updateForecast();
        getReleaseForecast(selectedName).setVelocityForecast(velocityForecast);
        updateProductBacklog(getSelectedProductBacklog());
        updateProductBacklogComparison();
        updateReleases();
    }

    public VelocityForecast getSelectedVelocityForecast() {
        return getReleaseForecast(selectedName).getVelocityForecast();
    }

    public Releases getReleases() {
        return getReleaseForecast(selectedName).getReleases();
    }

    public List<String> getReleaseForecastNames() {
        final List<String> names = new ArrayList<String>();
        for (ReleaseForecast releaseForecast : releaseForecasts) {
            names.add(releaseForecast.getName());
        }
        return names;
    }

    private void updateProductBacklog(ProductBacklog productBacklog) {
        productBacklog.updateAllItems(getSelectedVelocityForecast());
    }

    private void updateProductBacklogComparison() {
        productBacklogComparison.updateAllItems();
    }

    private ProductBacklog getReferenceProductBacklog() {
        return getProductBacklog(referenceName);
    }

    private void updateReleases() {
        getReleases().updateAll(productBacklogComparison);
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

    public ProductBacklogComparison getProductBacklogComparison() {
        return productBacklogComparison;
    }

    @VisibleForTesting
    void addProductBacklog(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast referenceVelocityForecast, Releases releases) {
        releaseForecasts.add(new ReleaseForecast(dateTime, name, productBacklog, referenceVelocityForecast, releases));
    }
    
    private static Releases createDummyReleases() {
        final Releases releases = new Releases();
        releases.addRelease(new Release("TP1: Technical Preview Basis", new PlannedReleaseIsEqual("TP1: Technical Preview Basis")));
        releases.addRelease(new Release("TP 2: Technical Preview Erweitert", new PlannedReleaseIsEqual("TP 2: Technical Preview Erweitert")));
        releases.addRelease(new Release("CP: Consumer Preview", new PlannedReleaseIsEqual("CP: Consumer Preview")));
        releases.addRelease(new Release("Full Launch", new PlannedReleaseIsEqual("Full Launch")));
        releases.addRelease(new Release("Weiterentwicklung", new PlannedReleaseIsEqual("Weiterentwicklung")));
        releases.addRelease(new Release("Phase Out bestehende App", new PlannedReleaseIsEqual("Phase Out bestehende App")));
        return releases;
    }
}
