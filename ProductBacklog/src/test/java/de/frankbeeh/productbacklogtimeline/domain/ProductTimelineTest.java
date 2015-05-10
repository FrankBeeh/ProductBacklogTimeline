package de.frankbeeh.productbacklogtimeline.domain;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.DateConverter;
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
    private ProductTimestampComparison productTimestampComparisonMock;
    private Releases referenceReleasesMock;
    private Releases selectedReleasesMock;
    private ProductBacklogComparison productBacklogComparisonMock;

    @Test
    public void initalContent() {
        final ProductTimeline productTimeline = new ProductTimeline();
        final VelocityForecastComparison selectedVelocityForecastComparison = productTimeline.getSelectedVelocityForecastComparison();
        assertTrue(selectedVelocityForecastComparison.getComparisons().isEmpty());
        assertTrue(productTimeline.getSelectedProductBacklog().getItems().isEmpty());
        assertTrue(productTimeline.getSelectedReleases().getReleases().isEmpty());
    }

    @Test
    public void addAndSelectProductBacklog() throws Exception {
        final ProductTimestamp referenceProductTimestamp = new ProductTimestamp(REFERENCE_DATE_TIME, REFERENCE, referenceProductBacklogMock, referenceVelocityForecast, referenceReleasesMock);
        final ProductTimestamp selectedProductTimestamp = new ProductTimestamp(SELECTEDT_DATE_TIME, SELECTED, selectedProductBacklogMock, selectedVelocityForecast, selectedReleasesMock);
        productTimelineWithMockedReleases.insertProductTimestamp(referenceProductTimestamp);
        productTimelineWithMockedReleases.insertProductTimestamp(selectedProductTimestamp);

        resetAll();
        productTimestampComparisonMock.setSelectedTimestamp(same(selectedProductTimestamp));
        expect(productTimestampComparisonMock.getProductBacklogComparision()).andReturn(productBacklogComparisonMock);
        selectedReleasesMock.updateAll(productBacklogComparisonMock);
        replayAll();
        productTimelineWithMockedReleases.selectProductTimestamp(getProductTimelineFullName(SELECTEDT_DATE_TIME, SELECTED));
        assertSame(selectedProductBacklogMock, productTimelineWithMockedReleases.getSelectedProductBacklog());
        verifyAll();

        resetAll();
        productTimestampComparisonMock.setReferenceTimestamp(same(referenceProductTimestamp));
        expect(productTimestampComparisonMock.getProductBacklogComparision()).andReturn(productBacklogComparisonMock);
        selectedReleasesMock.updateAll(productBacklogComparisonMock);
        replayAll();
        productTimelineWithMockedReleases.selectReferenceProductTimestamp(getProductTimelineFullName(REFERENCE_DATE_TIME, REFERENCE));
        verifyAll();

    }

    @Before
    public void setUp() {
        ServiceLocator.init(new MockedServiceRegistry());
        selectedProductBacklogMock = createMock("selectedProductBacklogMock", ProductBacklog.class);
        productBacklogComparisonMock = createMock("selectedProductBacklogComparisonMock", ProductBacklogComparison.class);
        referenceProductBacklogMock = createMock("referenceProductBacklogMock", ProductBacklog.class);
        referenceVelocityForecast = new VelocityForecast();
        selectedVelocityForecast = new VelocityForecast();
        referenceReleasesMock = createMock("referenceReleasesMock", Releases.class);
        selectedReleasesMock = createMock("selectedReleasesMock", Releases.class);
        productTimestampComparisonMock = createMock(ProductTimestampComparison.class);
        productTimelineWithMockedReleases = new ProductTimeline(productTimestampComparisonMock);
    }

    @After
    public void tearDown() {
        ServiceLocator.init(null);
    }
    
    private String getProductTimelineFullName(LocalDateTime productTimestampDate, String productTimestampName) {
        return DateConverter.formatLocalDateTime(productTimestampDate) + " - " + productTimestampName;
    }
}
