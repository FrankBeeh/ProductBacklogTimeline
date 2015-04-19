package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class ProductBacklogComparisonItemTest {
    private static final String VELOCITY_FORECAST = VelocityForecast.AVERAGE_VELOCITY_FORECAST;
    private static final String STRING_VALUE_1 = "Value 1";
    private static final String STRING_VALUE_2 = "Value 2";
    private ProductBacklogComparisonItem productBacklogComparisonItem;
    private ProductBacklogItem referenceProductBacklogItem;
    private ProductBacklogItem productBacklogItem;
    private SprintData sprint1;
    private SprintData sprint2;

    @Test
    public void getComparedTitle_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(null), null).getComparedTitle());
    }

    @Test
    public void getComparedTitle_referenceNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1), null).getComparedTitle());
    }

    @Test
    public void getComparedTitle_referenceValueNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1), createProductBacklogItemWithTitle(null)).getComparedTitle());
    }

    @Test
    public void getComparedTitle_equal() throws Exception {
        assertEquals(STRING_VALUE_1,
                new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1), createProductBacklogItemWithTitle(STRING_VALUE_1)).getComparedTitle());
    }

    @Test
    public void getComparedTitle_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1),
                createProductBacklogItemWithTitle(STRING_VALUE_2)).getComparedTitle());
    }

    @Test
    public void getComparedDescription_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(null), null).getComparedDescription());
    }

    @Test
    public void getComparedDescription_referenceNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(STRING_VALUE_1), null).getComparedDescription());
    }

    @Test
    public void getComparedDescription_referenceValueNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(STRING_VALUE_1), createProductBacklogItemWithDescription(null)).getComparedDescription());
    }

    @Test
    public void getComparedDescription_equal() throws Exception {
        assertEquals(STRING_VALUE_1,
                new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(STRING_VALUE_1), createProductBacklogItemWithDescription(STRING_VALUE_1)).getComparedDescription());
    }

    @Test
    public void getComparedDescription_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(STRING_VALUE_1),
                createProductBacklogItemWithDescription(STRING_VALUE_2)).getComparedDescription());
    }

    @Test
    public void getComparedSprint_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(null), null).getComparedSprint());
    }

    @Test
    public void getComparedSprint_referenceNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1), null).getComparedSprint());
    }

    @Test
    public void getComparedSprint_referenceValueNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1), createProductBacklogItemWithSprint(null)).getComparedSprint());
    }

    @Test
    public void getComparedSprint_equal() throws Exception {
        assertEquals(STRING_VALUE_1,
                new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1), createProductBacklogItemWithSprint(STRING_VALUE_1)).getComparedSprint());
    }

    @Test
    public void getComparedSprint_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1),
                createProductBacklogItemWithSprint(STRING_VALUE_2)).getComparedSprint());
    }
    
    @Test
    public void getComparedCompletionForecast_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(null), null).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedCompletionForecast_referenceNull() throws Exception {
        assertEquals(sprint1.getComparedForecast(null),
                new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(sprint1), null).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedCompletionForecast_referenceValueNull() throws Exception {
        assertEquals(
                sprint1.getComparedForecast(null),
                new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(sprint1), createProductBacklogItemWithCompletionForecast(null)).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedCompletionForecast_equal() throws Exception {
        assertEquals(sprint1.getComparedForecast(null), new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(sprint1),
                createProductBacklogItemWithCompletionForecast(sprint1)).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedCompletionForecast_notEqual() throws Exception {
        assertEquals("Sprint 1\n(Sprint 2)\n01.02.2003\n(-14d)", new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(sprint1),
                createProductBacklogItemWithCompletionForecast(sprint2)).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedState_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithState(null), null).getComparedState());
    }

    @Test
    public void getComparedState_referenceNull() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo), null).getComparedState());
    }

    @Test
    public void getComparedState_referenceValueNull() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo), createProductBacklogItemWithState(null)).getComparedState());
    }

    @Test
    public void getComparedState_equal() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo), createProductBacklogItemWithState(State.Todo)).getComparedState());
    }

    @Test
    public void getComparedState_notEqual() throws Exception {
        assertEquals(State.Todo.toString() + "\n(" + State.Done.toString() + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo),
                createProductBacklogItemWithState(State.Done)).getComparedState());
    }

    @Test
    public void getComparedEstimate_valueNull() throws Exception {
        productBacklogItem.setEstimate(null);
        assertNull(productBacklogComparisonItem.getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_referenceNull() throws Exception {
        productBacklogItem.setEstimate(2d);
        assertEquals("2.0", new ProductBacklogComparisonItem(productBacklogItem, null).getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_referenceValueNull() throws Exception {
        productBacklogItem.setEstimate(2d);
        referenceProductBacklogItem.setEstimate(null);
        assertEquals("2.0", productBacklogComparisonItem.getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_equal() throws Exception {
        productBacklogItem.setEstimate(2d);
        referenceProductBacklogItem.setEstimate(2d);
        assertEquals("2.0", productBacklogComparisonItem.getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_decrease() throws Exception {
        productBacklogItem.setEstimate(2d);
        referenceProductBacklogItem.setEstimate(15d);
        assertEquals("     2.0\n(-13.0)", productBacklogComparisonItem.getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_increase() throws Exception {
        productBacklogItem.setEstimate(10d);
        referenceProductBacklogItem.setEstimate(2d);
        assertEquals("   10.0\n(+8.0)", productBacklogComparisonItem.getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_shortDifference() throws Exception {
        productBacklogItem.setEstimate(123456d);
        referenceProductBacklogItem.setEstimate(123456.7d);
        assertEquals("123456.0\n    (-0.7)", productBacklogComparisonItem.getComparedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_valueNull() throws Exception {
        productBacklogItem.setAccumulatedEstimate(null);
        assertNull(productBacklogComparisonItem.getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceNull() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("2.0", new ProductBacklogComparisonItem(productBacklogItem, null).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceValueNull() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(null);
        assertEquals("2.0", productBacklogComparisonItem.getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_equal() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("2.0", productBacklogComparisonItem.getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_decrease() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(15d);
        assertEquals("     2.0\n(-13.0)", productBacklogComparisonItem.getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_increase() throws Exception {
        productBacklogItem.setAccumulatedEstimate(10d);
        referenceProductBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("   10.0\n(+8.0)", productBacklogComparisonItem.getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_shortDifference() throws Exception {
        productBacklogItem.setAccumulatedEstimate(123456d);
        referenceProductBacklogItem.setAccumulatedEstimate(123456.7d);
        assertEquals("123456.0\n    (-0.7)", productBacklogComparisonItem.getComparedAccumulatedEstimate());
    }

    @Before
    public void setUp() throws Exception {
        productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        referenceProductBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        productBacklogComparisonItem = new ProductBacklogComparisonItem(productBacklogItem, referenceProductBacklogItem);
        sprint1 = new SprintData("Sprint 1", null, FormatUtility.parseDate("01.02.2003"), null, null, null, null);
        sprint2 = new SprintData("Sprint 2", null, FormatUtility.parseDate("15.02.2003"), null, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithState(State state) {
        return new ProductBacklogItem(null, null, null, null, state, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithTitle(String title) {
        return new ProductBacklogItem(null, title, null, null, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithDescription(String description) {
        return new ProductBacklogItem(null, null, description, null, null, null, null);
    }
    
    private ProductBacklogItem createProductBacklogItemWithSprint(String sprint) {
        return new ProductBacklogItem(null, null, null, null, null, sprint, null);
    }

    private ProductBacklogItem createProductBacklogItemWithCompletionForecast(SprintData sprintData) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        productBacklogItem.setCompletionForecast(VELOCITY_FORECAST, sprintData);
        return productBacklogItem;
    }

}
