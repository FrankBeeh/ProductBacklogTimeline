package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import de.frankbeeh.productbacklogtimeline.service.DateConverter;

/**
 * Responsibility:
 * <ul>
 * <li>Represent the {@link ProductBacklog}, the {@link VelocityForecast} and the {@link ReleaseForecast} of one point in time.
 * <li>Updates the {@link VelocityForecast} and updates the {@link ProductBacklog} and {@link ReleaseForecast} base on the forecast on creation.
 * </ul>
 */
public class ProductTimestamp {
    private final LocalDateTime dateTime;
    private final String name;
    private final ProductBacklog productBacklog;
    private final VelocityForecast velocityForecast;
    private final ReleaseForecast releaseForecast;

    public ProductTimestamp(LocalDateTime dateTime, String name, ProductBacklog productBacklog, ProductTimestamp previousProductTimestamp) {
        this(dateTime, name, productBacklog, previousProductTimestamp.getVelocityForecast(), previousProductTimestamp.getReleaseForecast());
    }

    public ProductTimestamp(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast velocityForecast, ReleaseForecast releaseForecast) {
        this.dateTime = dateTime;
        this.name = name;
        this.productBacklog = productBacklog;
        this.velocityForecast = velocityForecast;
        this.releaseForecast = releaseForecast;
        update();
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    private void update() {
        velocityForecast.updateForecast();
        productBacklog.updateAllItems(velocityForecast);
        releaseForecast.updateAllReleases(productBacklog);
    }
}
