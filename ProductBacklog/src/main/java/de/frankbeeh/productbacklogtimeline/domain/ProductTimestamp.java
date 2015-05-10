package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.DateConverter;

public class ProductTimestamp {
    private final LocalDateTime dateTime;
    private final String name;
    private final ProductBacklog productBacklog;
    private VelocityForecast velocityForecast;
    private ReleaseForecast releaseForecast;

    public ProductTimestamp(LocalDateTime dateTime, String name, ProductBacklog productBacklog, ProductTimestamp previousProductTimestamp) {
        this(dateTime, name, productBacklog, previousProductTimestamp.getVelocityForecast(), previousProductTimestamp.getReleaseForecast());
    }

    public ProductTimestamp(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast velocityForecast, ReleaseForecast releaseForecast) {
        this.dateTime = dateTime;
        this.name = name;
        this.productBacklog = productBacklog;
        this.velocityForecast = velocityForecast;
        this.releaseForecast = releaseForecast;
    }

    @VisibleForTesting
    ProductTimestamp(LocalDateTime dateTime, String name, ReleaseForecast releaseForecast) {
        this(dateTime, name, new ProductBacklog(), new VelocityForecast(), releaseForecast);
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

    public ReleaseForecast getReleaseForecast() {
        return releaseForecast;
    }

    public void setVelocityForecast(VelocityForecast velocityForecast) {
        this.velocityForecast = velocityForecast;
    }

    public void setReleaseForecast(ReleaseForecast releaseForecast) {
        this.releaseForecast = releaseForecast;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void update() {
        updateVelocityForecast();
        updateProductBacklog();
        updateReleaseForecast();
    }

    private void updateProductBacklog() {
        productBacklog.updateAllItems(velocityForecast);
    }

    private void updateVelocityForecast() {
        velocityForecast.updateForecast();
    }

    private void updateReleaseForecast() { 
        releaseForecast.updateAll(productBacklog);
    }
}
