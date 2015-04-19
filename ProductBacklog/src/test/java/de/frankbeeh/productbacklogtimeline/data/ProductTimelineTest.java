package de.frankbeeh.productbacklogtimeline.data;

import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
    private ProductBacklogComparison productBacklogComparisonMock;

    @Test
    public void initalContent() {
        assertTrue(productTimelineWithMockedReleases.getSelectedVelocityForecast().getSprints().isEmpty());
        assertTrue(productTimelineWithMockedReleases.getSelectedProductBacklog().getItems().isEmpty());
        assertSame(releases, productTimelineWithMockedReleases.getReleases());
    }

    @Test
    public void addAndSelectProductBacklog() throws Exception {
        productTimelineWithMockedReleases.addProductBacklog(REFERENCE, referenceProductBacklogMock, referenceVelocityForecast);
        productTimelineWithMockedReleases.addProductBacklog(SELECTED, selectedProductBacklogMock, selectedVelocityForecast);

        resetAll();
        productBacklogComparisonMock.setReferenceProductBacklog(referenceProductBacklogMock);
        productBacklogComparisonMock.updateAllItems();
        releases.updateAll(same(productBacklogComparisonMock));
        replayAll();
        productTimelineWithMockedReleases.selectReferenceReleaseForecast(REFERENCE);
        verifyAll();

        resetAll();
        productBacklogComparisonMock.setSelectedProductBacklog(selectedProductBacklogMock);
        productBacklogComparisonMock.updateAllItems();
        releases.updateAll(productBacklogComparisonMock);
        replayAll();
        productTimelineWithMockedReleases.selectReleaseForecast(SELECTED);
        assertSame(selectedProductBacklogMock, productTimelineWithMockedReleases.getSelectedProductBacklog());
        verifyAll();
    }

    @Test
    public void setSelectedVelocityForecast() throws Exception {
        final ProductTimeline productTimeline = new ProductTimeline(new Releases(), new ProductBacklogComparison());
        productTimeline.addProductBacklog(SELECTED, new ProductBacklog());
        productTimeline.addProductBacklog(REFERENCE, new ProductBacklog());

        final VelocityForecast velocityForecast1 = new VelocityForecast();
        productTimeline.selectReleaseForecast(REFERENCE);
        productTimeline.setVelocityForecastForSelectedReleaseForecast(velocityForecast1);

        final VelocityForecast velocityForecast2 = new VelocityForecast();
        productTimeline.selectReleaseForecast(SELECTED);
        productTimeline.setVelocityForecastForSelectedReleaseForecast(velocityForecast2);

        assertSame(velocityForecast2, productTimeline.getSelectedVelocityForecast());
        productTimeline.selectReleaseForecast(REFERENCE);
        assertSame(velocityForecast1, productTimeline.getSelectedVelocityForecast());
    }

    @Test
    public void setSelectedVelocityForecast_updates() throws Exception {
        addAndSelectProductBacklog();
        resetAll();
        velocityForecast.updateForecast();
        productBacklogComparisonMock.updateAllItems();
        selectedProductBacklogMock.updateAllItems(same(velocityForecast));
        releases.updateAll(productBacklogComparisonMock);
        replayAll();
        productTimelineWithMockedReleases.setVelocityForecastForSelectedReleaseForecast(velocityForecast);
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
        productBacklogComparisonMock = createMock(ProductBacklogComparison.class);
        productTimelineWithMockedReleases = new ProductTimeline(releases, productBacklogComparisonMock);
    }

}