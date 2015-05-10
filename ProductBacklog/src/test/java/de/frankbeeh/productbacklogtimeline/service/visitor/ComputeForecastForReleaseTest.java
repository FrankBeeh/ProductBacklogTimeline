package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

@RunWith(EasyMockRunner.class)
public class ComputeForecastForReleaseTest extends EasyMockSupport {
    private static final double ACCUMULATED_ESTIMATE_1 = 10d;
    private static final double ACCUMULATED_ESTIMATE_2 = 20d;
    private static final Sprint MIN_VEL_SPRINT_1 = newSprint("Min. Vel. Sprint 1");
    private static final Sprint AVG_VEL_SPRINT_1 = newSprint("Avg. Vel. Sprint 1");
    private static final Sprint MAX_VEL_SPRINT_1 = newSprint("Max. Vel. Sprint 1");
    private static final Sprint MIN_VEL_SPRINT_2 = newSprint("Min. Vel. Sprint 2");
    private static final Sprint AVG_VEL_SPRINT_2 = newSprint("Avg. Vel. Sprint 2");
    private static final Sprint MAX_VEL_SPRINT_2 = newSprint("Max. Vel. Sprint 2");

    @Mock
    private ProductBacklog productBacklog;
    @Mock
    private ReleaseCriteria criteria;

    private ReleaseVisitor visitor;

    @Test
    public void visit_noMatch() throws Exception {
        final Release release = new Release(null, criteria);
        release.setAccumulatedEstimate(ACCUMULATED_ESTIMATE_1);
        release.setCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST, MIN_VEL_SPRINT_1);
        release.setCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST, AVG_VEL_SPRINT_1);
        release.setCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST, MAX_VEL_SPRINT_1);

        final List<ProductBacklogItem> matchingProductacklogItems = new ArrayList<ProductBacklogItem>();
        expect(productBacklog.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklog);
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
        final ProductBacklogItem productBacklogItem = newProductBacklogItem(accumulatedEstimate, MIN_VEL_SPRINT_1, AVG_VEL_SPRINT_1, MAX_VEL_SPRINT_1);

        final List<ProductBacklogItem> matchingProductacklogItems = Arrays.asList(productBacklogItem);
        expect(productBacklog.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklog);
        verifyAll();
        assertEquals(new Double(accumulatedEstimate), release.getAccumulatedEstimate());
        assertSame(MIN_VEL_SPRINT_1, release.getCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        assertSame(AVG_VEL_SPRINT_1, release.getCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        assertSame(MAX_VEL_SPRINT_1, release.getCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
    }

    @Test
    public void visit_twoMatches() throws Exception {
        final Release release = new Release(null, criteria);
        final List<ProductBacklogItem> matchingProductacklogItems = Arrays.asList(newProductBacklogItem(ACCUMULATED_ESTIMATE_1, MIN_VEL_SPRINT_1, AVG_VEL_SPRINT_1, MAX_VEL_SPRINT_1),
                newProductBacklogItem(ACCUMULATED_ESTIMATE_2, MIN_VEL_SPRINT_2, AVG_VEL_SPRINT_2, MAX_VEL_SPRINT_2));
        expect(productBacklog.getMatchingProductBacklogItems(criteria)).andReturn(matchingProductacklogItems);
        replayAll();
        visitor.visit(release, productBacklog);
        verifyAll();
        assertEquals(new Double(ACCUMULATED_ESTIMATE_2), release.getAccumulatedEstimate());
        assertSame(MIN_VEL_SPRINT_2, release.getCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        assertSame(AVG_VEL_SPRINT_2, release.getCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        assertSame(MAX_VEL_SPRINT_2, release.getCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
    }

    @Before
    public void setUp() {
        visitor = new ComputeForecastForRelease();
    }

    private static Sprint newSprint(String name) {
        return new Sprint(name, null, null, null, null, null, null);
    }

    private ProductBacklogItem newProductBacklogItem(double accumulatedEstimate, Sprint minVelSprint1, Sprint avgVelSprint1, Sprint maxVelSprint1) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        productBacklogItem.setCompletionForecast(VelocityForecast.MINIMUM_VELOCITY_FORECAST, minVelSprint1);
        productBacklogItem.setCompletionForecast(VelocityForecast.AVERAGE_VELOCITY_FORECAST, avgVelSprint1);
        productBacklogItem.setCompletionForecast(VelocityForecast.MAXIMUM_VELOCITY_FORECAST, maxVelSprint1);
        return productBacklogItem;
    }
}
