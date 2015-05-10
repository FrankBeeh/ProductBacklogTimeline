package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.ProductTimestampService;

/**
 * Responsibility:
 * <ul>
 * <li>Compares the selected and the reference {@link ProductTimestamp} using ({@link ProductTimestampComparison}.
 * <li>Load and store {@link ProductTimestamp}s from/into the data base.
 * </ul>
 */
public class ProductTimeline {
    private static final String INITIAL_NAME = "<Empty>";
    private final List<ProductTimestamp> productTimestamps = new ArrayList<ProductTimestamp>();
    private String selectedName = null;
    private String referenceName = null;
    private final ProductTimestampComparison productTimestampComparison;
    private final ProductTimestamp emptyProductTimestamp;

    public ProductTimeline() {
        this(new ProductTimestampComparison());
    }

    public void addProductTimestamp(ProductTimestamp productTimestamp) {
        ServiceLocator.getService(ProductTimestampService.class).insert(productTimestamp);
        productTimestamps.add(productTimestamp);
    }

    public void selectProductTimestamp(String selectedName) {
        this.selectedName = selectedName;
        productTimestampComparison.setSelectedTimestamp(getSelectedProductTimestamp());
    }

    public void selectReferenceProductTimestamp(String referenceName) {
        this.referenceName = referenceName;
        productTimestampComparison.setReferenceTimestamp(getReferenceProductTimestamp());
    }

    public ProductTimestamp getSelectedProductTimestamp() {
        return getProductTimestampByFullName(selectedName);
    }

    public ProductBacklogComparison getProductBacklogComparison() {
        return productTimestampComparison.getProductBacklogComparision();
    }

    public VelocityForecastComparison getVelocityForecastComparison() {
        return productTimestampComparison.getVelocityForecastComparison();
    }

    public ReleaseForecastComparison getReleaseForecastComparison() {
        return productTimestampComparison.getReleaseForecastComparison();
    }

    public List<String> getProductTimestampFullNames() {
        final List<String> names = new ArrayList<String>();
        for (ProductTimestamp productTimestamp : productTimestamps) {
            names.add(productTimestamp.getFullName());
        }
        return names;
    }

    public void loadFromDataBase() {
        final ProductTimestampService service = ServiceLocator.getService(ProductTimestampService.class);
        final List<LocalDateTime> allIds = service.getAllIds();
        for (LocalDateTime localDateTime : allIds) {
            ProductTimestamp productTimestamp = service.get(localDateTime);
            productTimestamps.add(productTimestamp);
        }
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

    @VisibleForTesting
    ProductTimeline(ProductTimestampComparison productTimestampComparison) {
        this.productTimestampComparison = productTimestampComparison;
        this.emptyProductTimestamp = new ProductTimestamp(null, INITIAL_NAME, new ProductBacklog(), new VelocityForecast(), new ReleaseForecast());
        productTimestampComparison.setSelectedTimestamp(emptyProductTimestamp);
    }
}
