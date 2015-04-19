package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProductBacklogComparisonItemTest {

    private ProductBacklogComparisonItem productBacklogComparisonItem;
    private ProductBacklogItem referenceProductBacklogItem;
    private ProductBacklogItem productBacklogItem;

    @Test
    public void getStateDescription_valueNull() throws Exception {
        assertNull(new ProductBacklogComparisonItem(createProductBacklogItemWithState(null), null).getComparedState());
    }

    @Test
    public void getStateDescription_referenceNull() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo), null).getComparedState());
    }

    @Test
    public void getStateDescription_referenceValueNull() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo), createProductBacklogItemWithState(null)).getComparedState());
    }

    @Test
    public void getStateDescription_equal() throws Exception {
        assertEquals(State.Todo.toString(), new ProductBacklogComparisonItem(createProductBacklogItemWithState(State.Todo), createProductBacklogItemWithState(State.Todo)).getComparedState());
    }

    @Test
    public void getStateDescription_notEqual() throws Exception {
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
    public void setUp() {
        productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        referenceProductBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        productBacklogComparisonItem = new ProductBacklogComparisonItem(productBacklogItem, referenceProductBacklogItem);
    }

    private ProductBacklogItem createProductBacklogItemWithState(State state) {
        return new ProductBacklogItem(null, null, null, null, state, null, null);
    }
}
