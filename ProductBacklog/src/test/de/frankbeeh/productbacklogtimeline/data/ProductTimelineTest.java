package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.same;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class ProductTimelineTest extends EasyMockSupport {
    private ProductBacklog selectedProductBacklogMock;
    private Sprints sprints;
    private Releases releases;

    private ProductTimeline productTimeline;
    private ProductBacklog referenceProductBacklogMock;

    @Test
    public void initalContent() {
        assertTrue(productTimeline.getSprints().getSprints().isEmpty());
        assertTrue(productTimeline.getSelectedProductBacklog().getItems().isEmpty());
        assertSame(releases, productTimeline.getReleases());
    }

    @Test
    public void addAndSelectProductBacklog() throws Exception {
        final String selectedName = "selected";
        final String referenceName = "reference";
        productTimeline.addProductBacklog(referenceName, referenceProductBacklogMock);
        productTimeline.addProductBacklog(selectedName, selectedProductBacklogMock);

        resetAll();
        releases.updateAll(same(productTimeline.getSelectedProductBacklog()));
        replayAll();
        productTimeline.selectReferenceProductBacklog(referenceName);
        verifyAll();

        resetAll();
        selectedProductBacklogMock.updateAllItems(same(productTimeline.getSprints()), same(referenceProductBacklogMock));
        releases.updateAll(same(selectedProductBacklogMock));
        replayAll();
        productTimeline.selectProductBacklog(selectedName);
        assertSame(selectedProductBacklogMock, productTimeline.getSelectedProductBacklog());
        verifyAll();
    }

    @Test
    public void addAndSelectProductBacklog_noReference() throws Exception {
        final String selectedName = "selected";
        productTimeline.addProductBacklog(selectedName, selectedProductBacklogMock);
        resetAll();
        selectedProductBacklogMock.updateAllItems(same(productTimeline.getSprints()), same(productTimeline.getSelectedProductBacklog()));
        releases.updateAll(same(selectedProductBacklogMock));
        replayAll();
        productTimeline.selectProductBacklog(selectedName);
        assertSame(selectedProductBacklogMock, productTimeline.getSelectedProductBacklog());
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
    public void setSprintsAfterSelectingProductBacklog() throws Exception {
        addAndSelectProductBacklog();
        resetAll();
        sprints.updateAllSprints();
        selectedProductBacklogMock.updateAllItems(same(sprints), same(referenceProductBacklogMock));
        releases.updateAll(same(productTimeline.getSelectedProductBacklog()));
        replayAll();
        productTimeline.setSprints(sprints);
        verifyAll();
    }

    @Before
    public void setUp() {
        selectedProductBacklogMock = createMock("selectedProductBacklogMock", ProductBacklog.class);
        referenceProductBacklogMock = createMock("referenceProductBacklogMock", ProductBacklog.class);
        sprints = createMock(Sprints.class);
        releases = createMock(Releases.class);
        productTimeline = new ProductTimeline(releases);
    }

}
