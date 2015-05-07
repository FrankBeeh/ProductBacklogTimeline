package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;
import de.frankbeeh.productbacklogtimeline.service.database.ProductTimestampService;

public class ProductTimeline {
    private static final String INITIAL_NAME = "Initial";
    private final List<ProductTimestamp> productTimestamps = new ArrayList<ProductTimestamp>();
    private String selectedName = null;
    private String referenceName = null;
    private final ProductBacklogComparison productBacklogComparison;
    private ProductTimestamp emptyProductTimestamp;

    public ProductTimeline() {
        this(createDummyReleases(), new ProductBacklogComparison());
    }

    @VisibleForTesting
    ProductTimeline(Releases releases, ProductBacklogComparison productBacklogComparison) {
        this.productBacklogComparison = productBacklogComparison;
        this.emptyProductTimestamp = new ProductTimestamp(null, INITIAL_NAME, releases);
        this.productBacklogComparison.setSelectedProductBacklog(getSelectedProductBacklog());
    }

    public void addProductTimestamp(ProductTimestamp productTimestamp) {
        ServiceLocator.getService(ProductTimestampService.class).insert(productTimestamp);
        updateAndAddProductTimestamp(productTimestamp);
    }

    public ProductBacklog getSelectedProductBacklog() {
        return getProductBacklogByFullName(selectedName);
    }

    public ProductTimestamp getSelectedProductTimestamp() {
        return getProductTimestampByFullName(selectedName);
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
        return getProductTimestampByFullName(selectedName).getVelocityForecast();
    }

    public Releases getSelectedReleases() {
        return getProductTimestampByFullName(selectedName).getReleases();
    }

    public List<String> getProductTimestampFullNames() {
        final List<String> names = new ArrayList<String>();
        for (ProductTimestamp productTimestamp : productTimestamps) {
            names.add(productTimestamp.getFullName());
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
        return getProductBacklogByFullName(referenceName);
    }

    private void updateReleases() {
        getSelectedReleases().updateAll(productBacklogComparison);
    }

    private ProductBacklog getProductBacklogByFullName(String fullName) {
        return getProductTimestampByFullName(fullName).getProductBacklog();
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
    void addProductBacklog(LocalDateTime dateTime, String name, ProductBacklog productBacklog, VelocityForecast referenceVelocityForecast, Releases releases) {
        productTimestamps.add(new ProductTimestamp(dateTime, name, productBacklog, referenceVelocityForecast, releases));
    }

    private ProductTimestamp getPreviousProductTimestamp() {
        if (productTimestamps.isEmpty()){
            return emptyProductTimestamp;
        }
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

    public void loadFromDataBase() {
        final ProductTimestampService service = ServiceLocator.getService(ProductTimestampService.class);
        final List<LocalDateTime> allIds = service.getAllIds();
        for (LocalDateTime localDateTime : allIds) {
            updateAndAddProductTimestamp(service.get(localDateTime));
        }
    }

    private void updateProductTimestamp(ProductTimestamp productTimestamp) {
        productTimestamp.updateVelocityForecast();
        // TODO: Do not overwrite as soon as the releases are read from CSV or DB!
        productTimestamp.setReleases(getPreviousProductTimestamp().getReleases());
        productTimestamp.updateProductBacklog();
    }
    
    private void updateAndAddProductTimestamp(ProductTimestamp productTimestamp) {
        updateProductTimestamp(productTimestamp);
        productTimestamps.add(productTimestamp);
    }
}
