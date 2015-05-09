package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.ProductTimestampService;

public class ProductTimeline {
    private static final String INITIAL_NAME = "Initial";
    private final List<ProductTimestamp> productTimestamps = new ArrayList<ProductTimestamp>();
    private String selectedName = null;
    private String referenceName = null;
    private final ProductTimestampComparison productTimestampComparison;
    private ProductTimestamp emptyProductTimestamp;

    public ProductTimeline() {
        this(new ProductTimestampComparison());
    }

    public void addProductTimestamp(ProductTimestamp productTimestamp) {
        ServiceLocator.getService(ProductTimestampService.class).insert(productTimestamp);
        updateAndAddProductTimestamp(productTimestamp);
    }

    @VisibleForTesting
    ProductBacklog getSelectedProductBacklog() {
        return getSelectedProductTimestamp().getProductBacklog();
    }

    public ProductTimestamp getSelectedProductTimestamp() {
        return getProductTimestampByFullName(selectedName);
    }

    public void selectProductTimestamp(String selectedName) {
        this.selectedName = selectedName;
        productTimestampComparison.setSelectedTimestamp(getSelectedProductTimestamp());
        updateReleases();
    }

    public void selectReferenceProductTimestamp(String referenceName) {
        this.referenceName = referenceName;
        productTimestampComparison.setReferenceTimestamp(getReferenceProductTimestamp());
        updateReleases();
    }

    public VelocityForecastComparison getSelectedVelocityForecastComparison() {
        return productTimestampComparison.getVelocityForecastComparison();
    }

    public Releases getSelectedReleases() {
        return getSelectedProductTimestamp().getReleases();
    }

    public List<String> getProductTimestampFullNames() {
        final List<String> names = new ArrayList<String>();
        for (ProductTimestamp productTimestamp : productTimestamps) {
            names.add(productTimestamp.getFullName());
        }
        return names;
    }

    public ProductBacklogComparison getProductBacklogComparison() {
        return productTimestampComparison.getProductBacklogComparision();
    }

    public void loadFromDataBase() {
        final ProductTimestampService service = ServiceLocator.getService(ProductTimestampService.class);
        final List<LocalDateTime> allIds = service.getAllIds();
        for (LocalDateTime localDateTime : allIds) {
            updateAndAddProductTimestamp(service.get(localDateTime));
        }
    }

    private void updateReleases() {
        getSelectedReleases().updateAll(productTimestampComparison.getProductBacklogComparision());
    }

    private ProductTimestamp getProductTimestampByFullName(String fullName) {
        if (fullName == null) {
            return emptyProductTimestamp;
        }
        for (ProductTimestamp productTimestamp : productTimestamps) {
            if (productTimestamp.getFullName().equals(fullName)) {
                return productTimestamp;
            }
        }
        throw new IllegalArgumentException("Release forcast '" + fullName + "' not found!");
    }

    @VisibleForTesting
    void insertProductTimestamp(ProductTimestamp productTimestamp) {
        productTimestamps.add(productTimestamp);
    }

    private ProductTimestamp getReferenceProductTimestamp() {
        return getProductTimestampByFullName(referenceName);
    }

    private void updateProductTimestamp(ProductTimestamp productTimestamp) {
        productTimestamp.updateVelocityForecast();
        productTimestamp.updateProductBacklog();
    }

    private void updateAndAddProductTimestamp(ProductTimestamp productTimestamp) {
        updateProductTimestamp(productTimestamp);
        productTimestamps.add(productTimestamp);
    }

    @VisibleForTesting
    ProductTimeline(ProductTimestampComparison productTimestampComparison) {
        this.productTimestampComparison = productTimestampComparison;
        this.emptyProductTimestamp = new ProductTimestamp(null, INITIAL_NAME, new Releases());
        productTimestampComparison.setSelectedTimestamp(getSelectedProductTimestamp());
    }
}
