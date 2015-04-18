package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProductBacklogComparisonItemTest {

    private ProductBacklogComparisonItem productBacklogComparisonItem;
    private ProductBacklogItem referenceProductBacklogItem;
    private ProductBacklogItem productBacklogItem;

    @Test
    public void getAccumulatedEstimateDescription_decrease() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(15d);
        assertEquals("     2.0\n(-13.0)",productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    @Test
    public void getAccumulatedEstimateDescription_increase() throws Exception {
        productBacklogItem.setAccumulatedEstimate(10d);
        referenceProductBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("   10.0\n(+8.0)",productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    // FIXME
    @Ignore
    @Test
    public void getAccumulatedEstimateDescription() throws Exception {
        productBacklogItem.setAccumulatedEstimate(12345d);
        referenceProductBacklogItem.setAccumulatedEstimate(12345.6d);
        assertEquals("12345.6\n (-0.6)",productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    @Before
    public void setUp() {
        productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        referenceProductBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        productBacklogComparisonItem = new ProductBacklogComparisonItem(productBacklogItem, referenceProductBacklogItem);
    }
}
