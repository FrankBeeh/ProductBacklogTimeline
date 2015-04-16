package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;
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

    private ProductBacklog referenceProductBacklogMock;

    @Test
    public void visit() throws Exception {
        final String sprintName = "Sprint 1";
        final String referenceSprintName = "Sprint 2";
        final String date = "01.02.2003";
        final String referenceDate = "02.03.2003";
        final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseDate(date), null, null, null, null);
        final SprintData referenceSprintData = new SprintData(referenceSprintName, null, FormatUtility.parseDate(referenceDate), null, null, null, null);
        final double accumulatedEstimate = 10d;
        final double referenceAccumulatedEstimate = 15d;
        final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);

        expect(selectedVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
        expect(referenceVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, referenceAccumulatedEstimate)).andReturn(referenceSprintData);
        expect(referenceProductBacklogMock.getItemById(ID)).andReturn(createProductBacklogItem(referenceAccumulatedEstimate));
        replayAll();

        visitor.visit(productBacklogItem, selectedVelocityForecast, referenceProductBacklogMock, referenceVelocityForecast);
        assertEquals(sprintName + "\n(" + referenceSprintName + ")\n" + date + "\n(-29d)", productBacklogItem.getCompletionForecast(FORECAST_NAME));
        verifyAll();
    }

    @Test
    public void visit_referenceItemNotFound() throws Exception {
        final String sprintName = "Sprint 1";
        final String date = "01.02.2003";
        final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseDate(date), null, null, null, null);
        final double accumulatedEstimate = 10d;
        final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);

        expect(selectedVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
        expect(referenceProductBacklogMock.getItemById(ID)).andReturn(null);
        replayAll();

        visitor.visit(productBacklogItem, selectedVelocityForecast, referenceProductBacklogMock, referenceVelocityForecast);
        assertEquals(sprintName + "\n" + date, productBacklogItem.getCompletionForecast(FORECAST_NAME));
        verifyAll();
    }

    @Test
    public void visit_sameReferenceSprint() throws Exception {
        final String sprintName = "Sprint 1";
        final String referenceSprintName = sprintName;
        final String date = "01.02.2003";
        final String referenceDate = date;
        final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseDate(date), null, null, null, null);
        final SprintData referenceSprintData = new SprintData(referenceSprintName, null, FormatUtility.parseDate(referenceDate), null, null, null, null);
        final double accumulatedEstimate = 10d;
        final double referenceAccumulatedEstimate = 15d;
        final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);

        expect(selectedVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
        expect(referenceVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, referenceAccumulatedEstimate)).andReturn(referenceSprintData);
        expect(referenceProductBacklogMock.getItemById(ID)).andReturn(createProductBacklogItem(referenceAccumulatedEstimate));
        replayAll();

        visitor.visit(productBacklogItem, selectedVelocityForecast, referenceProductBacklogMock, referenceVelocityForecast);
        assertEquals(sprintName + "\n" + date, productBacklogItem.getCompletionForecast(FORECAST_NAME));
        verifyAll();
    }

    @Before
    public void setUp() {
        visitor = new ForecastSprintOfCompletion(FORECAST_NAME);
        referenceProductBacklogMock = createMock(ProductBacklog.class);
    }

    private ProductBacklogItem createProductBacklogItem(Double accumulatedEstimate) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(ID, null, null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        return productBacklogItem;
    }

}
