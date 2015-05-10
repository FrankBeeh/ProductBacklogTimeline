package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.DateConverter;

public class ProductTimestamp {
    private final LocalDateTime dateTime;
    private final String name;
    private final ProductBacklog productBacklog;
    private VelocityForecast velocityForecast;
    private Releases releases;

    public ProductTimestamp(LocalDateTime dateTime, String name, ProductBacklog productBacklog, ProductTimestamp previousProductTimestamp) {
        this(dateTime, name, productBacklog, previousProductTimestamp.getVelocityForecast(), previousProductTimestamp.getReleases());
    }

    public ProductTimestamp(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast velocityForecast, Releases releases) {
        this.dateTime = dateTime;
        this.name = name;
        this.productBacklog = productBacklog;
        this.velocityForecast = velocityForecast;
        this.releases = releases;
    }

    @VisibleForTesting
    ProductTimestamp(LocalDateTime dateTime, String name, Releases releases) {
        this(dateTime, name, new ProductBacklog(), new VelocityForecast(), releases);
    }

    public String getName() {
        return name;
    }
    
    public String getFullName() {
        return DateConverter.formatLocalDateTime(dateTime) + " - " + name;
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

    public void setReleases(Releases releases) {
        this.releases = releases;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void updateProductBacklog() {
        productBacklog.updateAllItems(velocityForecast);
    }

    public void updateVelocityForecast() {
        velocityForecast.updateForecast();
    }
}
