package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.Release;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.criteria.Criteria;

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
    private ProductBacklog productBacklog;
    @Mock
    private Criteria criteria;

    private ReleaseVisitor visitor;

    @Test
    public void visit_noMatch() throws Exception {
        final Release release = new Release(null, criteria);
        release.setAccumulatedEstimate(ACCUMULATED_ESTIMATE_1);
        release.setCompletionForecast(Sprints.MINIMUM_VELOCITY_FORECAST, MIN_VEL_SPRINT_NAME_1);
        release.setCompletionForecast(Sprints.AVERAGE_VELOCITY_FORECAST, AVG_VEL_SPRINT_NAME_1);
        release.setCompletionForecast(Sprints.MAXIMUM_VELOCITY_FORECAST, MAX_VEL_SPRINT_NAME_1);

        final List<ProductBacklogItem> matchingProductacklogItems = new ArrayList<ProductBacklogItem>();
        expect(productBacklog.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklog);
        verifyAll();
        assertNull(release.getAccumulatedEstimate());
        assertNull(release.getCompletionForecast(Sprints.MINIMUM_VELOCITY_FORECAST));
        assertNull(release.getCompletionForecast(Sprints.AVERAGE_VELOCITY_FORECAST));
        assertNull(release.getCompletionForecast(Sprints.MAXIMUM_VELOCITY_FORECAST));
    }

    @Test
    public void visit_singleMatch() throws Exception {
        final double accumulatedEstimate = 10d;
        final Release release = new Release(null, criteria);
        final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate, MIN_VEL_SPRINT_NAME_1, AVG_VEL_SPRINT_NAME_1, MAX_VEL_SPRINT_NAME_1);

        final List<ProductBacklogItem> matchingProductacklogItems = Arrays.asList(productBacklogItem);
        expect(productBacklog.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklog);
        verifyAll();
        assertEquals(accumulatedEstimate, release.getAccumulatedEstimate());
        assertEquals(MIN_VEL_SPRINT_NAME_1, release.getCompletionForecast(Sprints.MINIMUM_VELOCITY_FORECAST));
        assertEquals(AVG_VEL_SPRINT_NAME_1, release.getCompletionForecast(Sprints.AVERAGE_VELOCITY_FORECAST));
        assertEquals(MAX_VEL_SPRINT_NAME_1, release.getCompletionForecast(Sprints.MAXIMUM_VELOCITY_FORECAST));
    }

    @Test
    public void visit_twoMatches() throws Exception {
        final Release release = new Release(null, criteria);
        final List<ProductBacklogItem> matchingProductacklogItems = Arrays.asList(
                createProductBacklogItem(ACCUMULATED_ESTIMATE_1, MIN_VEL_SPRINT_NAME_1, AVG_VEL_SPRINT_NAME_1, MAX_VEL_SPRINT_NAME_1),
                createProductBacklogItem(ACCUMULATED_ESTIMATE_2, MIN_VEL_SPRINT_NAME_2, AVG_VEL_SPRINT_NAME_2, MAX_VEL_SPRINT_NAME_2));
        expect(productBacklog.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklog);
        verifyAll();
        assertEquals(ACCUMULATED_ESTIMATE_2, release.getAccumulatedEstimate());
        assertEquals(MIN_VEL_SPRINT_NAME_2, release.getCompletionForecast(Sprints.MINIMUM_VELOCITY_FORECAST));
        assertEquals(AVG_VEL_SPRINT_NAME_2, release.getCompletionForecast(Sprints.AVERAGE_VELOCITY_FORECAST));
        assertEquals(MAX_VEL_SPRINT_NAME_2, release.getCompletionForecast(Sprints.MAXIMUM_VELOCITY_FORECAST));
    }

    @Before
    public void setUp() {
        visitor = new ComputeForecastForRelease();
    }

    private SprintData createSprintData(String sprintName) {
        return new SprintData(sprintName, null, null, null, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItem(double accumulatedEstimate, String minVelSprintName, String avgVelSprintName, String maxVelSprintName) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        productBacklogItem.setCompletionForecast(Sprints.MINIMUM_VELOCITY_FORECAST, createSprintData(minVelSprintName));
        productBacklogItem.setCompletionForecast(Sprints.AVERAGE_VELOCITY_FORECAST, createSprintData(avgVelSprintName));
        productBacklogItem.setCompletionForecast(Sprints.MAXIMUM_VELOCITY_FORECAST, createSprintData(maxVelSprintName));
        return productBacklogItem;
    }
}
