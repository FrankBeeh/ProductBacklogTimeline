package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;

public class ProductTimeline {

    private final List<ProductBacklog> productBacklogs = new ArrayList<ProductBacklog>();
    private Sprints sprints = new Sprints();
    private final Releases releases = new Releases();

    public ProductTimeline() {
        createDummyReleases();
    }

    public void addProductBacklog(ProductBacklog productBacklog) {
        productBacklogs.add(productBacklog);
        productBacklog.updateAllItems(sprints);
        updateReleases();
    }

    public ProductBacklog getSelectedProductBacklog() {
        if (productBacklogs.isEmpty()) {
            return new ProductBacklog();
        }
        return productBacklogs.get(productBacklogs.size() - 1);
    }

    public void setSprints(Sprints sprints) {
        this.sprints = sprints;
        this.sprints.updateAllSprints();
        getSelectedProductBacklog().updateAllItems(getSprints());
        updateReleases();
    }

    public Sprints getSprints() {
        return sprints;
    }

    public Releases getReleases() {
        return releases;
    }

    private void createDummyReleases() {
        releases.addRelease(new Release("Release 0.8", new ProductBacklogItemIdIsEqual("CRM-793")));
        releases.addRelease(new Release("Release 0.9", new ProductBacklogItemIdIsEqual("CRM-560")));
        releases.addRelease(new Release("Release 1.0", new ProductBacklogItemIdIsEqual("CRM-771")));
        releases.addRelease(new Release("Release 1.2", new ProductBacklogItemIdIsEqual("CRM-554")));
    }

    private void updateReleases() {
        releases.updateAll(getSelectedProductBacklog());
    }
}
