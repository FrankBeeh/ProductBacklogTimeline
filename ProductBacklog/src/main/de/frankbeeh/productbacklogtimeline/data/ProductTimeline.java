package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;

public class ProductTimeline {

    private final Map<String, ProductBacklog> productBacklogs = new HashMap<String, ProductBacklog>();
    private Sprints sprints = new Sprints();
    private final Releases releases;
    private final ProductBacklog emptyProductBacklog = new ProductBacklog();
    private String selectedName = null;
    private String referenceName;;

    public ProductTimeline() {
        this(new Releases());
        createDummyReleases();
    }

    @VisibleForTesting
    ProductTimeline(Releases releases) {
        this.releases = releases;
    }

    public void addProductBacklog(String name, ProductBacklog productBacklog) {
        productBacklogs.put(name, productBacklog);
    }

    public ProductBacklog getSelectedProductBacklog() {
        if (selectedName == null) {
            return emptyProductBacklog;
        }
        return productBacklogs.get(selectedName);
    }

    public void selectProductBacklog(String productBacklogName) {
        selectedName = productBacklogName;
        updateProductBacklog();
        updateReleases();
    }

    public void selectReferenceProductBacklog(String productBacklogName) {
        referenceName = productBacklogName;
        updateProductBacklog();
        updateReleases();
    }

    public void setSprints(Sprints sprints) {
        this.sprints = sprints;
        this.sprints.updateAllSprints();
        updateProductBacklog();
        updateReleases();
    }

    public Sprints getSprints() {
        return sprints;
    }

    public Releases getReleases() {
        return releases;
    }

    public List<String> getProductBacklogNames() {
        return new ArrayList<String>(productBacklogs.keySet());
    }

    private void updateProductBacklog() {
        getSelectedProductBacklog().updateAllItems(getSprints(), getReferenceProductBacklog());
    }

    private ProductBacklog getReferenceProductBacklog() {
        if (referenceName == null) {
            return emptyProductBacklog;
        }
        return productBacklogs.get(referenceName);
    }

    private void updateReleases() {
        releases.updateAll(getSelectedProductBacklog());
    }

    private void createDummyReleases() {
        releases.addRelease(new Release("Release 0.8", new ProductBacklogItemIdIsEqual("CRM-793")));
        releases.addRelease(new Release("Release 0.9", new ProductBacklogItemIdIsEqual("CRM-560")));
        releases.addRelease(new Release("Release 1.0", new ProductBacklogItemIdIsEqual("CRM-771")));
        releases.addRelease(new Release("Release 1.2", new ProductBacklogItemIdIsEqual("CRM-554")));
    }
}
