package de.frankbeeh.productbacklogtimeline.domain;

import com.google.common.annotations.VisibleForTesting;

public class ReleaseForecast {
    private final String name;
    private final ProductBacklog productBacklog;
    private VelocityForecast velocityForecast;
    private final Releases releases;

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
}
