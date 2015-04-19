package de.frankbeeh.productbacklogtimeline.service.sort;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;

import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

@RunWith(EasyMockRunner.class)
public class JiraProductBacklogSortingStrategyTest extends EasyMockSupport {
    @Mock
    private VelocityForecast velocityForecastMock;

    private JiraProductBacklogSortingStrategy sortingStrategy;

    @Test
    public void comparator_sprintDifferent() {
        final String firstSprintName = "sprint 1";
        final String secondSprintName = "sprint 2";
        final String secondRank = "7";
        final String firstRank = "5";
        expect(velocityForecastMock.getSortIndex(firstSprintName)).andReturn(1).anyTimes();
        expect(velocityForecastMock.getSortIndex(secondSprintName)).andReturn(10).anyTimes();
        replayAll();
        final ProductBacklog productBacklog = new ProductBacklog();
        final ProductBacklogItem firstSprintFirstRank = createProductBacklogItem(firstSprintName, firstRank);
        final ProductBacklogItem firstSprintSecondRank = createProductBacklogItem(firstSprintName, secondRank);
        final ProductBacklogItem secondSprintFirstRank = createProductBacklogItem(secondSprintName, firstRank);
        final ProductBacklogItem secondSprintSecondRank = createProductBacklogItem(secondSprintName, secondRank);
        productBacklog.addItem(firstSprintSecondRank);
        productBacklog.addItem(secondSprintFirstRank);
        productBacklog.addItem(firstSprintFirstRank);
        productBacklog.addItem(secondSprintSecondRank);
        sortingStrategy.sortProductBacklog(productBacklog, velocityForecastMock);
        assertEquals(Arrays.asList(firstSprintFirstRank, firstSprintSecondRank, secondSprintFirstRank, secondSprintSecondRank), productBacklog.getItems());
        verifyAll();
    }

    @Before
    public void setUp() {
        sortingStrategy = new JiraProductBacklogSortingStrategy();
    }

    private ProductBacklogItem createProductBacklogItem(String sprintName, String rank) {
        return new ProductBacklogItem(null, null, null, null, null, sprintName, rank);
    }
}
