package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class ProductTimelineTest extends EasyMockSupport {
    private static final String REFERENCE = "reference";
    private static final String SELECTED = "selected";
    private ProductBacklog selectedProductBacklogMock;
    private VelocityForecast velocityForecast;
    private Releases releases;

    private ProductTimeline productTimelineWithMockedReleases;
    private ProductBacklog referenceProductBacklogMock;
    private VelocityForecast referenceVelocityForecast;
    private VelocityForecast selectedVelocityForecast;

    @Test
    public void initalContent() {
        assertTrue(productTimelineWithMockedReleases.getSelectedVelocityForecast().getSprints().isEmpty());
        assertTrue(productTimelineWithMockedReleases.getSelectedProductBacklog().getItems().isEmpty());
        assertSame(releases, productTimelineWithMockedReleases.getReleases());
    }

    @Test
    public void addAndSelectProductBacklog() throws Exception {
        productTimelineWithMockedReleases.addProductBacklog(REFERENCE, referenceProductBacklogMock);
        productTimelineWithMockedReleases.selectReleaseForecast(REFERENCE);
        productTimelineWithMockedReleases.setSelectedVelocityForecast(referenceVelocityForecast);

        productTimelineWithMockedReleases.addProductBacklog(SELECTED, selectedProductBacklogMock);
        productTimelineWithMockedReleases.selectReleaseForecast(SELECTED);
        productTimelineWithMockedReleases.setSelectedVelocityForecast(selectedVelocityForecast);

        resetAll();
        selectedProductBacklogMock.updateAllItems(same(selectedVelocityForecast), same(referenceProductBacklogMock), same(referenceVelocityForecast));
        releases.updateAll(same(productTimelineWithMockedReleases.getSelectedProductBacklog()));
        replayAll();
        productTimelineWithMockedReleases.selectReferenceReleaseForecast(REFERENCE);
        verifyAll();

        resetAll();
        selectedProductBacklogMock.updateAllItems(same(selectedVelocityForecast), same(referenceProductBacklogMock), same(referenceVelocityForecast));
        releases.updateAll(same(selectedProductBacklogMock));
        replayAll();
        productTimelineWithMockedReleases.selectReleaseForecast(SELECTED);
        assertSame(selectedProductBacklogMock, productTimelineWithMockedReleases.getSelectedProductBacklog());
        verifyAll();
    }

    @Test
    public void addAndSelectProductBacklog_noReference() throws Exception {
        final String selectedName = SELECTED;
        productTimelineWithMockedReleases.addProductBacklog(selectedName, selectedProductBacklogMock);
        resetAll();
        selectedProductBacklogMock.updateAllItems(same(productTimelineWithMockedReleases.getSelectedVelocityForecast()), same(productTimelineWithMockedReleases.getSelectedProductBacklog()),
                same(productTimelineWithMockedReleases.getSelectedVelocityForecast()));
        releases.updateAll(same(selectedProductBacklogMock));
        replayAll();
        productTimelineWithMockedReleases.selectReleaseForecast(selectedName);
        assertSame(selectedProductBacklogMock, productTimelineWithMockedReleases.getSelectedProductBacklog());
        verifyAll();
    }

    @Test
    public void setSelectedVelocityForecast() throws Exception {
        final ProductTimeline productTimeline = new ProductTimeline(new Releases());
        productTimeline.addProductBacklog(SELECTED, new ProductBacklog());
        productTimeline.addProductBacklog(REFERENCE, new ProductBacklog());

        final VelocityForecast velocityForecast1 = new VelocityForecast();
        productTimeline.selectReleaseForecast(REFERENCE);
        productTimeline.setSelectedVelocityForecast(velocityForecast1);

        final VelocityForecast velocityForecast2 = new VelocityForecast();
        productTimeline.selectReleaseForecast(SELECTED);
        productTimeline.setSelectedVelocityForecast(velocityForecast2);

        assertSame(velocityForecast2, productTimeline.getSelectedVelocityForecast());
        productTimeline.selectReleaseForecast(REFERENCE);
        assertSame(velocityForecast1, productTimeline.getSelectedVelocityForecast());
    }

    @Test
    public void setSelectedVelocityForecast_updates() throws Exception {
        addAndSelectProductBacklog();
        resetAll();
        velocityForecast.updateForecast();
        selectedProductBacklogMock.updateAllItems(same(velocityForecast), same(referenceProductBacklogMock), same(referenceVelocityForecast));
        releases.updateAll(same(productTimelineWithMockedReleases.getSelectedProductBacklog()));
        replayAll();
        productTimelineWithMockedReleases.setSelectedVelocityForecast(velocityForecast);
        verifyAll();
    }

    @Before
    public void setUp() {
        selectedProductBacklogMock = createMock("selectedProductBacklogMock", ProductBacklog.class);
        referenceProductBacklogMock = createMock("referenceProductBacklogMock", ProductBacklog.class);
        velocityForecast = createMock(VelocityForecast.class);
        referenceVelocityForecast = new VelocityForecast();
        selectedVelocityForecast = new VelocityForecast();
        releases = createMock(Releases.class);
        productTimelineWithMockedReleases = new ProductTimeline(releases);
    }

}
