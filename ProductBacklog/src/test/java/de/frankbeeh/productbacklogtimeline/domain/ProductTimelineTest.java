package de.frankbeeh.productbacklogtimeline.domain;

import static org.easymock.EasyMock.same;
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

    private ProductTimeline productTimelineWithMockedComparison;
    private ProductBacklog referenceProductBacklogMock;
    private VelocityForecast referenceVelocityForecast;
    private VelocityForecast selectedVelocityForecast;
    private ProductTimestampComparison productTimestampComparisonMock;
    private ReleaseForecast referenceReleaseForecastMock;
    private ReleaseForecast selectedReleaseForecastMock;

    @Test
    public void initalContent() {
        final ProductTimeline productTimeline = new ProductTimeline();
        assertTrue(productTimeline.getVelocityForecastComparison().getComparisons().isEmpty());
        assertTrue(productTimeline.getProductBacklogComparison().getComparisons().isEmpty());
        assertTrue(productTimeline.getReleaseForecastComparison().getComparisons().isEmpty());
    }

    @Test
    public void addAndSelectProductBacklog() throws Exception {
        final ProductTimestamp referenceProductTimestamp = new ProductTimestamp(REFERENCE_DATE_TIME, REFERENCE, referenceProductBacklogMock, referenceVelocityForecast, referenceReleaseForecastMock);
        final ProductTimestamp selectedProductTimestamp = new ProductTimestamp(SELECTEDT_DATE_TIME, SELECTED, selectedProductBacklogMock, selectedVelocityForecast, selectedReleaseForecastMock);
        productTimelineWithMockedComparison.insertProductTimestamp(referenceProductTimestamp);
        productTimelineWithMockedComparison.insertProductTimestamp(selectedProductTimestamp);

        resetAll();
        productTimestampComparisonMock.setSelectedTimestamp(same(selectedProductTimestamp));
        replayAll();
        productTimelineWithMockedComparison.selectProductTimestamp(getProductTimelineFullName(SELECTEDT_DATE_TIME, SELECTED));
        verifyAll();

        resetAll();
        productTimestampComparisonMock.setReferenceTimestamp(same(referenceProductTimestamp));
        replayAll();
        productTimelineWithMockedComparison.selectReferenceProductTimestamp(getProductTimelineFullName(REFERENCE_DATE_TIME, REFERENCE));
        verifyAll();

    }

    @Before
    public void setUp() {
        ServiceLocator.init(new MockedServiceRegistry());
        selectedProductBacklogMock = createMock("selectedProductBacklogMock", ProductBacklog.class);
        referenceProductBacklogMock = createMock("referenceProductBacklogMock", ProductBacklog.class);
        referenceVelocityForecast = new VelocityForecast();
        selectedVelocityForecast = new VelocityForecast();
        referenceReleaseForecastMock = createMock("referenceReleaseForecastMock", ReleaseForecast.class);
        selectedReleaseForecastMock = createMock("selectedReleaseForecastMock", ReleaseForecast.class);
        productTimestampComparisonMock = createMock(ProductTimestampComparison.class);
        productTimelineWithMockedComparison = new ProductTimeline(productTimestampComparisonMock);
    }

    @After
    public void tearDown() {
        ServiceLocator.init(null);
    }
    
    private String getProductTimelineFullName(LocalDateTime productTimestampDate, String productTimestampName) {
        return DateConverter.formatLocalDateTime(productTimestampDate) + " - " + productTimestampName;
    }
}
