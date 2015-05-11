package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatterTest;

public class ProductBacklogItemComparisonTest {
    private static final String VELOCITY_FORECAST = VelocityForecast.AVERAGE_VELOCITY_FORECAST;
    private static final String STRING_VALUE_1 = "Value 1";
    private static final Sprint SPRINT_1 = new Sprint("Sprint 1", null, LocalDate.of(2003, Month.FEBRUARY, 1), null, null, null, null);
    private static final String SPRINT_1_REPRESENTATION = "Sprint 1\n01.02.2003";

    @Test
    public void getComparedTitle_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, STRING_VALUE_1), new ProductBacklogItemComparison(
                newProductBacklogItemWithTitle(STRING_VALUE_1)).getComparedTitle());
    }

    @Test
    public void getComparedTitle_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, STRING_VALUE_1 + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithTitle(STRING_VALUE_1), null).getComparedTitle());
    }

    @Test
    public void getComparedDescription_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, STRING_VALUE_1), new ProductBacklogItemComparison(
                newProductBacklogItemWithDescription(STRING_VALUE_1)).getComparedDescription());
    }

    @Test
    public void getComparedDescription_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, STRING_VALUE_1 + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithDescription(STRING_VALUE_1), null).getComparedDescription());
    }

    @Test
    public void getComparedSprint_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, STRING_VALUE_1), new ProductBacklogItemComparison(
                newProductBacklogItemWithSprint(STRING_VALUE_1)).getComparedJiraSprint());
    }

    @Test
    public void getComparedSprint_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, STRING_VALUE_1 + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithSprint(STRING_VALUE_1), null).getComparedJiraSprint());
    }

    @Test
    public void getComparedPlannedRelease_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, STRING_VALUE_1), new ProductBacklogItemComparison(
                newProductBacklogItemWithPlannedRelease(STRING_VALUE_1)).getComparedPlannedRelease());
    }

    @Test
    public void getComparedPlannedRelease_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, STRING_VALUE_1 + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithPlannedRelease(STRING_VALUE_1), null).getComparedPlannedRelease());
    }

    @Test
    public void getComparedCompletionForecast_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, SPRINT_1_REPRESENTATION), new ProductBacklogItemComparison(
                newProductBacklogItemWithCompletionForecast(SPRINT_1)).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedCompletionForecast_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, SPRINT_1_REPRESENTATION + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithCompletionForecast(SPRINT_1), null).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedState_valueNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null),
                new ProductBacklogItemComparison(newProductBacklogItemWithState(null)).getComparedState());
    }

    @Test
    public void getComparedState_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, State.Todo.toString()), new ProductBacklogItemComparison(
                newProductBacklogItemWithState(State.Todo)).getComparedState());
    }

    @Test
    public void getComparedState_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, State.Todo.toString() + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithState(State.Todo), null).getComparedState());
    }

    @Test
    public void getComparedState_referenceValueNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, State.Todo.toString() + "\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithState(State.Todo), newProductBacklogItemWithState(null)).getComparedState());
    }

    @Test
    public void getComparedEstimate_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2.0"),
                new ProductBacklogItemComparison(newProductBacklogItemWithEstimate(2d)).getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "   2.0\n(NEW)"),
                new ProductBacklogItemComparison(newProductBacklogItemWithEstimate(2d), null).getComparedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2.0"),
                new ProductBacklogItemComparison(newProductBacklogItemWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "   2.0\n(NEW)"), new ProductBacklogItemComparison(
                newProductBacklogItemWithAccumulatedEstimate(2d), null).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedProductBacklogRank_noReference() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2"),
                new ProductBacklogItemComparison(newProductBacklogItemWithRank(2)).getComparedProductBacklogRank());
    }

    @Test
    public void getComparedProductBacklogRank_referenceNull() throws Exception {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "     2\n(NEW)"),
                new ProductBacklogItemComparison(newProductBacklogItemWithRank(2), null).getComparedProductBacklogRank());
    }

    private ProductBacklogItem newProductBacklogItem() {
        return new ProductBacklogItem(null, null, null, null, null, null, null, null);
    }

    private ProductBacklogItem newProductBacklogItemWithState(State state) {
        return new ProductBacklogItem(null, null, null, null, state, null, null, null);
    }

    private ProductBacklogItem newProductBacklogItemWithTitle(String title) {
        return new ProductBacklogItem(null, title, null, null, null, null, null, null);
    }

    private ProductBacklogItem newProductBacklogItemWithDescription(String description) {
        return new ProductBacklogItem(null, null, description, null, null, null, null, null);
    }

    private ProductBacklogItem newProductBacklogItemWithPlannedRelease(String plannedRelease) {
        return new ProductBacklogItem(null, null, null, null, null, null, null, plannedRelease);
    }

    private ProductBacklogItem newProductBacklogItemWithSprint(String sprint) {
        return new ProductBacklogItem(null, null, null, null, null, sprint, null, null);
    }

    private ProductBacklogItem newProductBacklogItemWithCompletionForecast(Sprint sprint) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        productBacklogItem.setCompletionForecast(VELOCITY_FORECAST, sprint);
        return productBacklogItem;
    }

    private ProductBacklogItem newProductBacklogItemWithEstimate(Double estimate) {
        return new ProductBacklogItem(null, null, null, estimate, null, null, null, null);
    }

    private ProductBacklogItem newProductBacklogItemWithAccumulatedEstimate(double accumulatedEstimate) {
        final ProductBacklogItem newProductBacklogItem = newProductBacklogItem();
        newProductBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        return newProductBacklogItem;
    }

    private ProductBacklogItem newProductBacklogItemWithRank(int rank) {
        final ProductBacklogItem productBacklogItem = newProductBacklogItem();
        productBacklogItem.setProductBacklogRank(rank);
        return productBacklogItem;
    }
}
