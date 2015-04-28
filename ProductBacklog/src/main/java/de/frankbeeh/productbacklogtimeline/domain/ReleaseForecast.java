package de.frankbeeh.productbacklogtimeline.domain;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;

public class ReleaseForecast {
    private final String name;
    private final ProductBacklog productBacklog;
    private VelocityForecast velocityForecast;
    private final Releases releases;

    public ReleaseForecast(String name) {
        this(name, createDummyReleases());
    }

    public ReleaseForecast(String name, ProductBacklog productBacklog, ReleaseForecast previousReleaseForecast) {
        this(name, productBacklog, previousReleaseForecast.getVelocityForecast(), previousReleaseForecast.getReleases());
    }

    @VisibleForTesting
    ReleaseForecast(String name, ProductBacklog productBacklog, VelocityForecast velocityForecast, Releases releases) {
        this.name = name;
        this.productBacklog = productBacklog;
        this.velocityForecast = velocityForecast;
        this.releases = releases;
    }

    @VisibleForTesting
    ReleaseForecast(String name, Releases releases) {
        this(name, new ProductBacklog(), new VelocityForecast(), releases);
    }

    public String getName() {
        return name;
    }

    public ProductBacklog getProductBacklog() {
        return productBacklog;
    }

    public VelocityForecast getVelocityForecast() {
        return velocityForecast;
    }

    public Releases getReleases() {
        return releases;
    }

    public void setVelocityForecast(VelocityForecast velocityForecast) {
        this.velocityForecast = velocityForecast;
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
