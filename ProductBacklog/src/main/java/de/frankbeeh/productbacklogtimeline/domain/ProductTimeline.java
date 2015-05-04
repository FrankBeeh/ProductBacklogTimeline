package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;

public class ProductTimeline {
    private static final String INITIAL_NAME = "Initial";
    private final List<ProductTimestamp> productTimestamps = new ArrayList<ProductTimestamp>();
    private String selectedName = INITIAL_NAME;
    private String referenceName = INITIAL_NAME;
    private final ProductBacklogComparison productBacklogComparison;

    public ProductTimeline() {
        this(createDummyReleases(), new ProductBacklogComparison());
    }

    @VisibleForTesting
    ProductTimeline(Releases releases, ProductBacklogComparison productBacklogComparison) {
        this.productBacklogComparison = productBacklogComparison;
        this.productTimestamps.add(new ProductTimestamp(null, INITIAL_NAME, releases));
        productBacklogComparison.setSelectedProductBacklog(getSelectedProductBacklog());
    }

    public void addProductTimestamp(ProductTimestamp productTimestamp) {
        productTimestamp.updateVelocityForecast();
        // TODO: Do not overwrite as soon as the releases are read from CSV or DB!
        productTimestamp.setReleases(getPreviousProductTimestamp().getReleases());
        productTimestamp.updateProductBacklog();
        productTimestamps.add(productTimestamp);
    }

    public ProductBacklog getSelectedProductBacklog() {
        return getProductBacklog(selectedName);
    }

    public ProductTimestamp getSelectedProductTimestamp() {
        return getProductTimestamp(selectedName);
    }

    public void selectProductTimestamp(String selectedName) {
        this.selectedName = selectedName;
        productBacklogComparison.setSelectedProductBacklog(getSelectedProductBacklog());
        updateProductBacklogComparison();
        updateReleases();
    }

    public void selectReferenceProductTimestamp(String referenceName) {
        this.referenceName = referenceName;
        productBacklogComparison.setReferenceProductBacklog(getReferenceProductBacklog());
        updateProductBacklogComparison();
        updateReleases();
    }

    public VelocityForecast getSelectedVelocityForecast() {
        return getProductTimestamp(selectedName).getVelocityForecast();
    }

    public Releases getSelectedReleases() {
        return getProductTimestamp(selectedName).getReleases();
    }

    public List<String> getProductTimestampNames() {
        final List<String> names = new ArrayList<String>();
        for (ProductTimestamp productTimestamp : productTimestamps) {
            names.add(productTimestamp.getName());
        }
        return names;
    }

    public ProductBacklogComparison getProductBacklogComparison() {
        return productBacklogComparison;
    }

    private void updateProductBacklogComparison() {
        productBacklogComparison.updateAllItems();
    }

    private ProductBacklog getReferenceProductBacklog() {
        return getProductBacklog(referenceName);
    }

    private void updateReleases() {
        getSelectedReleases().updateAll(productBacklogComparison);
    }

    private ProductBacklog getProductBacklog(String name) {
        return getProductTimestamp(name).getProductBacklog();
    }

    private ProductTimestamp getProductTimestamp(String name) {
        if (name == null) {
            return getProductTimestamp(INITIAL_NAME);
        }
        for (ProductTimestamp productTimestamp : productTimestamps) {
            if (productTimestamp.getName().equals(name)) {
                return productTimestamp;
            }
        }
        throw new IllegalArgumentException("Release forcast '" + name + "' not found!");
    }

    @VisibleForTesting
    void addProductBacklog(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast referenceVelocityForecast, Releases releases) {
        productTimestamps.add(new ProductTimestamp(dateTime, name, productBacklog, referenceVelocityForecast, releases));
    }

    private ProductTimestamp getPreviousProductTimestamp() {
        return productTimestamps.get(productTimestamps.size() - 1);
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
