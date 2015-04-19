package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparisonItem;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.Release;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

@RunWith(EasyMockRunner.class)
public class ComputeForecastForReleaseTest extends EasyMockSupport {
    private static final double ACCUMULATED_ESTIMATE_1 = 10d;
    private static final double ACCUMULATED_ESTIMATE_2 = 20d;
    private static final String AVG_VEL_SPRINT_NAME_1 = "Avg. Vel. Sprint 1";
    private static final String AVG_VEL_SPRINT_NAME_2 = "Avg. Vel. Sprint 2";
    private static final String MAX_VEL_SPRINT_NAME_1 = "Max. Vel. Sprint 1";
    private static final String MAX_VEL_SPRINT_NAME_2 = "Max. Vel. Sprint 2";
    private static final String MIN_VEL_SPRINT_NAME_1 = "Min. Vel. Sprint 1";
    private static final String MIN_VEL_SPRINT_NAME_2 = "Min. Vel. Sprint 2";

    @Mock
    private ProductBacklogComparison productBacklogComparison;
    @Mock
    private ReleaseCriteria criteria;

    private ReleaseVisitor visitor;

    @Test
    public void visit_noMatch() throws Exception {
        final Release release = new Release(null, criteria);
        release.setAccumulatedEstimate(ACCUMULATED_ESTIMATE_1);
        release.setCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST, MIN_VEL_SPRINT_NAME_1);
        release.setCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST, AVG_VEL_SPRINT_NAME_1);
        release.setCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST, MAX_VEL_SPRINT_NAME_1);

        final List<ProductBacklogComparisonItem> matchingProductacklogItems = new ArrayList<ProductBacklogComparisonItem>();
        expect(productBacklogComparison.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklogComparison);
        verifyAll();
        assertNull(release.getAccumulatedEstimate());
        assertNull(release.getCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        assertNull(release.getCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        assertNull(release.getCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
    }

    @Test
    public void visit_singleMatch() throws Exception {
        final double accumulatedEstimate = 10d;
        final Release release = new Release(null, criteria);
        final ProductBacklogComparisonItem productBacklogItem = createProductBacklogItem(accumulatedEstimate, MIN_VEL_SPRINT_NAME_1, AVG_VEL_SPRINT_NAME_1, MAX_VEL_SPRINT_NAME_1);

        final List<ProductBacklogComparisonItem> matchingProductacklogItems = Arrays.asList(productBacklogItem);
        expect(productBacklogComparison.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklogComparison);
        verifyAll();
        assertEquals(new Double(accumulatedEstimate), release.getAccumulatedEstimate());
        assertEquals(MIN_VEL_SPRINT_NAME_1, release.getCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        assertEquals(AVG_VEL_SPRINT_NAME_1, release.getCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        assertEquals(MAX_VEL_SPRINT_NAME_1, release.getCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
    }

    @Test
    public void visit_twoMatches() throws Exception {
        final Release release = new Release(null, criteria);
        final List<ProductBacklogComparisonItem> matchingProductacklogItems = Arrays.asList(
                createProductBacklogItem(ACCUMULATED_ESTIMATE_1, MIN_VEL_SPRINT_NAME_1, AVG_VEL_SPRINT_NAME_1, MAX_VEL_SPRINT_NAME_1),
                createProductBacklogItem(ACCUMULATED_ESTIMATE_2, MIN_VEL_SPRINT_NAME_2, AVG_VEL_SPRINT_NAME_2, MAX_VEL_SPRINT_NAME_2));
        expect(productBacklogComparison.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklogComparison);
        verifyAll();
        assertEquals(new Double(ACCUMULATED_ESTIMATE_2), release.getAccumulatedEstimate());
        assertEquals(MIN_VEL_SPRINT_NAME_2, release.getCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        assertEquals(AVG_VEL_SPRINT_NAME_2, release.getCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        assertEquals(MAX_VEL_SPRINT_NAME_2, release.getCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
    }

    @Before
    public void setUp() {
        visitor = new ComputeForecastForRelease();
    }

    private SprintData createSprintData(String sprintName) {
        return new SprintData(sprintName, null, null, null, null, null, null);
    }

    private ProductBacklogComparisonItem createProductBacklogItem(double accumulatedEstimate, String minVelSprintName, String avgVelSprintName, String maxVelSprintName) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        productBacklogItem.setCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST, createSprintData(minVelSprintName));
        productBacklogItem.setCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST, createSprintData(avgVelSprintName));
        productBacklogItem.setCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST, createSprintData(maxVelSprintName));
        return new ProductBacklogComparisonItem(productBacklogItem, null);
    }
}
