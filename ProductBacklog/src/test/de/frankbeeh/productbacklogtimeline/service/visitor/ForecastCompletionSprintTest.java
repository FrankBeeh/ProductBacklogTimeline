package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

@RunWith(EasyMockRunner.class)
public class ForecastCompletionSprintTest extends EasyMockSupport {
    private static final String FORECAST_NAME = "forecast name";

    @Mock
    private Sprints sprints;

    private ForecastCompletionSprint visitor;

    @Test
    public void visit() throws Exception {
        final String sprintName = "Sprint 1";
        final String date = "01.02.2003";
        final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseDate(date), null, null, null, null);
        final double accumulatedEstimate = 10d;
        final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);

        expect(sprints.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
        replayAll();

        visitor.visit(productBacklogItem, sprints);
        verifyAll();

        assertEquals(sprintName + "\n" + date, productBacklogItem.getCompletionForecast(FORECAST_NAME));
    }

    @Before
    public void setUp() {
        visitor = new ForecastCompletionSprint(FORECAST_NAME);
    }

    private ProductBacklogItem createProductBacklogItem(Double accumulatedEstimate) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        return productBacklogItem;
    }

}
