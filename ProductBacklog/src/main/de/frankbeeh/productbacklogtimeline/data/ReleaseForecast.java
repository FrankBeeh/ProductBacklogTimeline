package de.frankbeeh.productbacklogtimeline.data;

public class ReleaseForecast {

    private final String name;
    private final ProductBacklog productBacklog;
    private VelocityForecast velocityForecast;

    public ReleaseForecast(String name, ProductBacklog productBacklog, VelocityForecast velocityForecast) {
        this.name = name;
        this.productBacklog = productBacklog;
        this.velocityForecast = velocityForecast;
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

    public void setVelocityForecast(VelocityForecast velocityForecast) {
        this.velocityForecast = velocityForecast;
    }
}
