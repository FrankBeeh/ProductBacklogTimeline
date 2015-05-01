package de.frankbeeh.productbacklogtimeline.domain;

import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.MockedServiceRegistry;

public class ProductTimelineTest extends EasyMockSupport {
    private static final LocalDateTime REFERENCE_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime SELECTEDT_DATE_TIME = REFERENCE_DATE_TIME.minusYears(1);
    private static final String REFERENCE = "reference";
    private static final String SELECTED = "selected";
    private ProductBacklog selectedProductBacklogMock;
    private VelocityForecast velocityForecast;

    private ProductTimeline productTimelineWithMockedReleases;
    private ProductBacklog referenceProductBacklogMock;
    private VelocityForecast referenceVelocityForecast;
    private VelocityForecast selectedVelocityForecast;
    private ProductBacklogComparison productBacklogComparisonMock;
    private Releases referenceReleasesMock;
    private Releases selectedReleasesMock;
    private Releases initialReleasesMock;

    @Test
    public void initalContent() {
        assertTrue(productTimelineWithMockedReleases.getSelectedVelocityForecast().getSprints().isEmpty());
        assertTrue(productTimelineWithMockedReleases.getSelectedProductBacklog().getItems().isEmpty());
        assertSame(initialReleasesMock, productTimelineWithMockedReleases.getReleases());
    }

    @Test
    public void addAndSelectProductBacklog() throws Exception {
        productTimelineWithMockedReleases.addProductBacklog(REFERENCE_DATE_TIME, REFERENCE, referenceProductBacklogMock, referenceVelocityForecast, referenceReleasesMock);
        productTimelineWithMockedReleases.addProductBacklog(SELECTEDT_DATE_TIME, SELECTED, selectedProductBacklogMock, selectedVelocityForecast, selectedReleasesMock);

        resetAll();
        productBacklogComparisonMock.setSelectedProductBacklog(selectedProductBacklogMock);
        productBacklogComparisonMock.updateAllItems();
        selectedReleasesMock.updateAll(productBacklogComparisonMock);
        replayAll();
        productTimelineWithMockedReleases.selectReleaseForecast(SELECTED);
        assertSame(selectedProductBacklogMock, productTimelineWithMockedReleases.getSelectedProductBacklog());
        verifyAll();

        resetAll();
        productBacklogComparisonMock.setReferenceProductBacklog(referenceProductBacklogMock);
        productBacklogComparisonMock.updateAllItems();
        selectedReleasesMock.updateAll(same(productBacklogComparisonMock));
        replayAll();
        productTimelineWithMockedReleases.selectReferenceReleaseForecast(REFERENCE);
        verifyAll();

    }

    @Test
    public void setSelectedVelocityForecast() throws Exception {
        final ProductTimeline productTimeline = new ProductTimeline(new Releases(), new ProductBacklogComparison());
        productTimeline.addProductBacklog(SELECTEDT_DATE_TIME, SELECTED, new ProductBacklog());
        productTimeline.addProductBacklog(REFERENCE_DATE_TIME, REFERENCE, new ProductBacklog());

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
        selectedReleasesMock.updateAll(productBacklogComparisonMock);
        replayAll();
        productTimelineWithMockedReleases.setVelocityForecastForSelectedReleaseForecast(velocityForecast);
        verifyAll();
    }

    @Before
    public void setUp() {
        ServiceLocator.init(new MockedServiceRegistry());
        selectedProductBacklogMock = createMock("selectedProductBacklogMock", ProductBacklog.class);
        referenceProductBacklogMock = createMock("referenceProductBacklogMock", ProductBacklog.class);
        velocityForecast = createMock(VelocityForecast.class);
        referenceVelocityForecast = new VelocityForecast();
        selectedVelocityForecast = new VelocityForecast();
        initialReleasesMock = createMock("initialReleasesMock", Releases.class);
        referenceReleasesMock = createMock("referenceReleasesMock", Releases.class);
        selectedReleasesMock = createMock("selectedReleasesMock", Releases.class);
        productBacklogComparisonMock = createMock(ProductBacklogComparison.class);
        productTimelineWithMockedReleases = new ProductTimeline(initialReleasesMock, productBacklogComparisonMock);
    }

    @After
    public void tearDown(){
        ServiceLocator.init(null);
    }
}
