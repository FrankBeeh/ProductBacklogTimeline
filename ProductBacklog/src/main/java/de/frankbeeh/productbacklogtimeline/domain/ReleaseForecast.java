package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import com.google.common.annotations.VisibleForTesting;

public class ReleaseForecast {
    private final String name;
    private final ProductBacklog productBacklog;
    private VelocityForecast velocityForecast;
    private final Releases releases;
    private final LocalDateTime dateTime;

    public ReleaseForecast(LocalDateTime dateTime, String name, ProductBacklog productBacklog, ReleaseForecast previousReleaseForecast) {
        this(dateTime, name, productBacklog, previousReleaseForecast.getVelocityForecast(), previousReleaseForecast.getReleases());
    }

    @VisibleForTesting
    ReleaseForecast(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast velocityForecast, Releases releases) {
        this.dateTime = dateTime;
        this.name = name;
        this.productBacklog = productBacklog;
        this.velocityForecast = velocityForecast;
        this.releases = releases;
    }

    @VisibleForTesting
    ReleaseForecast(LocalDateTime dateTime, String name, Releases releases) {
        this(dateTime, name, new ProductBacklog(), new VelocityForecast(), releases);
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
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
