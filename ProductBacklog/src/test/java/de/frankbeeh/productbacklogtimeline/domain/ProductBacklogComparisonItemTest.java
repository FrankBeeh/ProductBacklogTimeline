package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparisonItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class ProductBacklogComparisonItemTest {
    private static final String VELOCITY_FORECAST = VelocityForecast.AVERAGE_VELOCITY_FORECAST;
    private static final String STRING_VALUE_1 = "Value 1";
    private static final String STRING_VALUE_2 = "Value 2";
    private ProductBacklogComparisonItem productBacklogComparisonItem;
    private ProductBacklogItem referenceProductBacklogItem;
    private ProductBacklogItem productBacklogItem;
    private Sprint sprint1;
    private Sprint sprint2;

    @Test
    public void getComparedTitle_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(null)).getComparedTitle());
    }

    @Test
    public void getComparedTitle_noReference() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1)).getComparedTitle());
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
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1), createProductBacklogItemWithTitle(STRING_VALUE_1)).getComparedTitle());
    }

    @Test
    public void getComparedTitle_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithTitle(STRING_VALUE_1),
                createProductBacklogItemWithTitle(STRING_VALUE_2)).getComparedTitle());
    }

    @Test
    public void getComparedDescription_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(null)).getComparedDescription());
    }

    @Test
    public void getComparedDescription_noReference() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithDescription(STRING_VALUE_1)).getComparedDescription());
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
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(null)).getComparedJiraSprint());
    }

    @Test
    public void getComparedSprint_noReference() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1)).getComparedJiraSprint());
    }

    @Test
    public void getComparedSprint_referenceNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1), null).getComparedJiraSprint());
    }

    @Test
    public void getComparedSprint_referenceValueNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1), createProductBacklogItemWithSprint(null)).getComparedJiraSprint());
    }

    @Test
    public void getComparedSprint_equal() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1), createProductBacklogItemWithSprint(STRING_VALUE_1)).getComparedJiraSprint());
    }

    @Test
    public void getComparedSprint_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithSprint(STRING_VALUE_1),
                createProductBacklogItemWithSprint(STRING_VALUE_2)).getComparedJiraSprint());
    }

    @Test
    public void getComparedPlannedRelease_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithPlannedRelease(null)).getComparedPlannedRelease());
    }

    @Test
    public void getComparedPlannedRelease_noReference() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithPlannedRelease(STRING_VALUE_1)).getComparedPlannedRelease());
    }

    @Test
    public void getComparedPlannedRelease_referenceNull() throws Exception {
        assertEquals(STRING_VALUE_1, new ProductBacklogComparisonItem(createProductBacklogItemWithPlannedRelease(STRING_VALUE_1), null).getComparedPlannedRelease());
    }

    @Test
    public void getComparedPlannedRelease_referenceValueNull() throws Exception {
        assertEquals(STRING_VALUE_1,
                new ProductBacklogComparisonItem(createProductBacklogItemWithPlannedRelease(STRING_VALUE_1), createProductBacklogItemWithPlannedRelease(null)).getComparedPlannedRelease());
    }

    @Test
    public void getComparedPlannedRelease_equal() throws Exception {
        assertEquals(STRING_VALUE_1,
                new ProductBacklogComparisonItem(createProductBacklogItemWithPlannedRelease(STRING_VALUE_1), createProductBacklogItemWithPlannedRelease(STRING_VALUE_1)).getComparedPlannedRelease());
    }

    @Test
    public void getComparedPlannedRelease_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", new ProductBacklogComparisonItem(createProductBacklogItemWithPlannedRelease(STRING_VALUE_1),
                createProductBacklogItemWithPlannedRelease(STRING_VALUE_2)).getComparedPlannedRelease());
    }

    @Test
    public void getComparedCompletionForecast_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(null), null).getComparedCompletionForecast(VELOCITY_FORECAST));
    }

    @Test
    public void getComparedCompletionForecast_noReference() throws Exception {
        assertEquals(sprint1.getComparedForecast(null), new ProductBacklogComparisonItem(createProductBacklogItemWithCompletionForecast(sprint1)).getComparedCompletionForecast(VELOCITY_FORECAST));
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
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithState(null)).getComparedState());
    }

    @Test
    public void getComparedState_noReference() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo)).getComparedState());
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
        assertNull(new ProductBacklogComparisonItem(productBacklogItem).getComparedEstimate());
    }

    @Test
    public void getComparedEstimate_noReference() throws Exception {
        productBacklogItem.setEstimate(2d);
        assertEquals("2.0", new ProductBacklogComparisonItem(productBacklogItem).getComparedEstimate());
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
        assertNull(new ProductBacklogComparisonItem(productBacklogItem).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_noReference() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("2.0", new ProductBacklogComparisonItem(productBacklogItem).getComparedAccumulatedEstimate());
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

    @Test
    public void getComparedProductBacklogRank_noReference() throws Exception {
        productBacklogItem.setProductBacklogRank(2);
        assertEquals("2", new ProductBacklogComparisonItem(productBacklogItem).getComparedProductBacklogRank());
    }

    @Test
    public void getComparedProductBacklogRank_referenceNull() throws Exception {
        productBacklogItem.setProductBacklogRank(2);
        assertEquals("     2\n(NEW)", new ProductBacklogComparisonItem(productBacklogItem, null).getComparedProductBacklogRank());
    }

    @Test
    public void getComparedProductBacklogRank_equal() throws Exception {
        productBacklogItem.setProductBacklogRank(2);
        referenceProductBacklogItem.setProductBacklogRank(2);
        assertEquals("2", productBacklogComparisonItem.getComparedProductBacklogRank());
    }

    @Test
    public void getComparedProductBacklogRank_decrease() throws Exception {
        productBacklogItem.setProductBacklogRank(2);
        referenceProductBacklogItem.setProductBacklogRank(15);
        assertEquals("     2\n(-13)", productBacklogComparisonItem.getComparedProductBacklogRank());
    }

    @Test
    public void getComparedProductBacklogRank_increase() throws Exception {
        productBacklogItem.setProductBacklogRank(10);
        referenceProductBacklogItem.setProductBacklogRank(2);
        assertEquals("   10\n(+8)", productBacklogComparisonItem.getComparedProductBacklogRank());
    }

    @Before
    public void setUp() throws Exception {
        productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        referenceProductBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        productBacklogComparisonItem = new ProductBacklogComparisonItem(productBacklogItem, referenceProductBacklogItem);
        sprint1 = new Sprint("Sprint 1", null, FormatUtility.parseLocalDate("01.02.2003"), null, null, null, null);
        sprint2 = new Sprint("Sprint 2", null, FormatUtility.parseLocalDate("15.02.2003"), null, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithState(State state) {
        return new ProductBacklogItem(null, null, null, null, state, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithTitle(String title) {
        return new ProductBacklogItem(null, title, null, null, null, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithDescription(String description) {
        return new ProductBacklogItem(null, null, description, null, null, null, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithPlannedRelease(String plannedRelease) {
        return new ProductBacklogItem(null, null, null, null, null, null, null, plannedRelease);
    }

    private ProductBacklogItem createProductBacklogItemWithSprint(String sprint) {
        return new ProductBacklogItem(null, null, null, null, null, sprint, null, null);
    }

    private ProductBacklogItem createProductBacklogItemWithCompletionForecast(Sprint sprintData) {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        productBacklogItem.setCompletionForecast(VELOCITY_FORECAST, sprintData);
        return productBacklogItem;
    }

}
