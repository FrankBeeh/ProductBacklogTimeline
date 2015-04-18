package de.frankbeeh.productbacklogtimeline.service.visitor;

public class CompareSprintOfCompletionTest {
    // FIXME implement (see ForecastSprintCompletionTest)
    //
    // @Test
    // public void visit_referenceItemNotFound() throws Exception {
    // final String sprintName = "Sprint 1";
    // final String date = "01.02.2003";
    // final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseDate(date), null, null, null, null);
    // final double accumulatedEstimate = 10d;
    // final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);
    //
    // expect(selectedVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
    // expect(referenceProductBacklogMock.getItemById(ID)).andReturn(null);
    // replayAll();
    //
    // visitor.visit(productBacklogItem, selectedVelocityForecast, referenceProductBacklogMock, referenceVelocityForecast);
    // assertEquals(sprintName + "\n" + date, productBacklogItem.getCompletionForecast(FORECAST_NAME));
    // verifyAll();
    // }
    //
    // @Test
    // public void visit_sameReferenceSprint() throws Exception {
    // final String sprintName = "Sprint 1";
    // final String referenceSprintName = sprintName;
    // final String date = "01.02.2003";
    // final String referenceDate = date;
    // final SprintData sprintData = new SprintData(sprintName, null, FormatUtility.parseDate(date), null, null, null, null);
    // final SprintData referenceSprintData = new SprintData(referenceSprintName, null, FormatUtility.parseDate(referenceDate), null, null, null, null);
    // final double accumulatedEstimate = 10d;
    // final double referenceAccumulatedEstimate = 15d;
    // final ProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);
    //
    // expect(selectedVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).andReturn(sprintData);
    // expect(referenceVelocityForecast.getCompletionSprintForecast(FORECAST_NAME, referenceAccumulatedEstimate)).andReturn(referenceSprintData);
    // expect(referenceProductBacklogMock.getItemById(ID)).andReturn(createProductBacklogItem(referenceAccumulatedEstimate));
    // replayAll();
    //
    // visitor.visit(productBacklogItem, selectedVelocityForecast, referenceProductBacklogMock, referenceVelocityForecast);
    // assertEquals(sprintName + "\n" + date, productBacklogItem.getCompletionForecast(FORECAST_NAME));
    // verifyAll();
    // }
}
