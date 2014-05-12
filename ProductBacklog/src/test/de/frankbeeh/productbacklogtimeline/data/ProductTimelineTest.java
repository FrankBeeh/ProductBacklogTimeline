package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.same;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class ProductTimelineTest extends EasyMockSupport {
    private ProductBacklog productBacklog;
    private Sprints sprints;
    private Releases releases;

    private ProductTimeline productTimeline;

    @Test
    public void initalContent() {
        assertTrue(productTimeline.getSprints().getSprints().isEmpty());
        assertTrue(productTimeline.getSelectedProductBacklog().getItems().isEmpty());
        assertSame(releases, productTimeline.getReleases());
    }

    @Test
    public void addProductBacklog() throws Exception {
        productBacklog.updateAllItems(same(productTimeline.getSprints()));
        releases.updateAll(same(productBacklog));
        replayAll();
        productTimeline.addProductBacklog(productBacklog);
        assertSame(productBacklog, productTimeline.getSelectedProductBacklog());
        verifyAll();
    }

    @Test
    public void setSprints() throws Exception {
        sprints.updateAllSprints();
        releases.updateAll(same(productTimeline.getSelectedProductBacklog()));
        replayAll();
        productTimeline.setSprints(sprints);
        verifyAll();
    }

    @Test
    public void setSprintsAfterAddingProductBacklog() throws Exception {
        addProductBacklog();
        resetAll();
        sprints.updateAllSprints();
        productBacklog.updateAllItems(same(sprints));
        releases.updateAll(same(productTimeline.getSelectedProductBacklog()));
        replayAll();
        productTimeline.setSprints(sprints);
        verifyAll();
    }

    @Before
    public void setUp() {
        productBacklog = createMock(ProductBacklog.class);
        sprints = createMock(Sprints.class);
        releases = createMock(Releases.class);
        productTimeline = new ProductTimeline(releases);
    }

}
