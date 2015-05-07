package de.frankbeeh.productbacklogtimeline.domain;

import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.MockedServiceRegistry;

public class ProductTimelineTest extends EasyMockSupport {
    private static final LocalDateTime REFERENCE_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime SELECTEDT_DATE_TIME = REFERENCE_DATE_TIME.minusYears(1);
    private static final String REFERENCE = "reference";
    private static final String SELECTED = "selected";
    private ProductBacklog selectedProductBacklogMock;

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
        assertSame(initialReleasesMock, productTimelineWithMockedReleases.getSelectedReleases());
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
        productTimelineWithMockedReleases.selectProductTimestamp(getProductTimelineFullName(SELECTEDT_DATE_TIME, SELECTED));
        assertSame(selectedProductBacklogMock, productTimelineWithMockedReleases.getSelectedProductBacklog());
        verifyAll();

        resetAll();
        productBacklogComparisonMock.setReferenceProductBacklog(referenceProductBacklogMock);
        productBacklogComparisonMock.updateAllItems();
        selectedReleasesMock.updateAll(same(productBacklogComparisonMock));
        replayAll();
        productTimelineWithMockedReleases.selectReferenceProductTimestamp(getProductTimelineFullName(REFERENCE_DATE_TIME, REFERENCE));
        verifyAll();

    }

    @Before
    public void setUp() {
        ServiceLocator.init(new MockedServiceRegistry());
        selectedProductBacklogMock = createMock("selectedProductBacklogMock", ProductBacklog.class);
        referenceProductBacklogMock = createMock("referenceProductBacklogMock", ProductBacklog.class);
        referenceVelocityForecast = new VelocityForecast();
        selectedVelocityForecast = new VelocityForecast();
        initialReleasesMock = createMock("initialReleasesMock", Releases.class);
        referenceReleasesMock = createMock("referenceReleasesMock", Releases.class);
        selectedReleasesMock = createMock("selectedReleasesMock", Releases.class);
        productBacklogComparisonMock = createMock(ProductBacklogComparison.class);
        productTimelineWithMockedReleases = new ProductTimeline(initialReleasesMock, productBacklogComparisonMock);
    }

    @After
    public void tearDown() {
        ServiceLocator.init(null);
    }
    
    private String getProductTimelineFullName(LocalDateTime productTimestampDate, String productTimestampName) {
        return FormatUtility.formatLocalDateTime(productTimestampDate) + " - " + productTimestampName;
    }
}
