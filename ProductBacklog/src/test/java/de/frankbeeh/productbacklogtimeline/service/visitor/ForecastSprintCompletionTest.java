package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.SprintData;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

@RunWith(EasyMockRunner.class)
public class ForecastSprintCompletionTest extends EasyMockSupport {
    private static final String ID = "ID";

    private static final String FORECAST_NAME = "forecast name";

    @Mock(name="referenceVelocityForecast")
    private VelocityForecast referenceVelocityForecast;
    @Mock(name="selectedVelocityForecast")
    private VelocityForecast selectedVelocityForecast;

    private ForecastSprintOfCompletion visitor;

    @Test
    public void visit() throws Exception {
        final String sprintName = "Sprint 1";
        final String date = "01.02.2003";
        final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseLocalDate(date), null, null, null, null);
        final double accumulatedEstimate = 10d;
        final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);

        expect(selectedVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
        replayAll();

        visitor.visit(productBacklogItem, selectedVelocityForecast);
        assertSame(sprintData, productBacklogItem.getCompletionForecast(FORECAST_NAME));
        verifyAll();
    }

    @Before
    public void setUp() {
        visitor = new ForecastSprintOfCompletion(FORECAST_NAME);
    }

    private ProductBacklogItem createProductBacklogItem(Double accumulatedEstimate) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(ID, null, null, null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        return productBacklogItem;
    }
}
