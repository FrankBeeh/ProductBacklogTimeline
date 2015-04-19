package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProductBacklogComparisonItemTest {

    private ProductBacklogComparisonItem productBacklogComparisonItem;
    private ProductBacklogItem referenceProductBacklogItem;
    private ProductBacklogItem productBacklogItem;

    @Test
    public void getEstimateDescription_valueNull() throws Exception {
        productBacklogItem.setEstimate(null);
        assertNull(productBacklogComparisonItem.getEstimateDescription());
    }
    
    @Test
    public void getEstimateDescription_referenceNull() throws Exception {
        productBacklogItem.setEstimate(2d);
        assertEquals("2.0", new ProductBacklogComparisonItem(productBacklogItem, null).getEstimateDescription());
    }
    
    @Test
    public void getEstimateDescription_referenceValueNull() throws Exception {
        productBacklogItem.setEstimate(2d);
        referenceProductBacklogItem.setEstimate(null);
        assertEquals("2.0", productBacklogComparisonItem.getEstimateDescription());
    }
    
    @Test
    public void getEstimateDescription_equal() throws Exception {
        productBacklogItem.setEstimate(2d);
        referenceProductBacklogItem.setEstimate(2d);
        assertEquals("2.0", productBacklogComparisonItem.getEstimateDescription());
    }

    @Test
    public void getEstimateDescription_decrease() throws Exception {
        productBacklogItem.setEstimate(2d);
        referenceProductBacklogItem.setEstimate(15d);
        assertEquals("     2.0\n(-13.0)", productBacklogComparisonItem.getEstimateDescription());
    }
    
    @Test
    public void getEstimateDescription_increase() throws Exception {
        productBacklogItem.setEstimate(10d);
        referenceProductBacklogItem.setEstimate(2d);
        assertEquals("   10.0\n(+8.0)", productBacklogComparisonItem.getEstimateDescription());
    }
    
    @Test
    public void getEstimateDescription_shortDifference() throws Exception {
        productBacklogItem.setEstimate(123456d);
        referenceProductBacklogItem.setEstimate(123456.7d);
        assertEquals("123456.0\n    (-0.7)", productBacklogComparisonItem.getEstimateDescription());
    }

    @Test
    public void getAccumulatedEstimateDescription_valueNull() throws Exception {
        productBacklogItem.setAccumulatedEstimate(null);
        assertNull(productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }
    
    @Test
    public void getAccumulatedEstimateDescription_referenceNull() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("2.0", new ProductBacklogComparisonItem(productBacklogItem, null).getAccumulatedEstimateDescription());
    }

    @Test
    public void getAccumulatedEstimateDescription_referenceValueNull() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(null);
        assertEquals("2.0", productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    @Test
    public void getAccumulatedEstimateDescription_equal() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("2.0", productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }
    
    @Test
    public void getAccumulatedEstimateDescription_decrease() throws Exception {
        productBacklogItem.setAccumulatedEstimate(2d);
        referenceProductBacklogItem.setAccumulatedEstimate(15d);
        assertEquals("     2.0\n(-13.0)", productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    @Test
    public void getAccumulatedEstimateDescription_increase() throws Exception {
        productBacklogItem.setAccumulatedEstimate(10d);
        referenceProductBacklogItem.setAccumulatedEstimate(2d);
        assertEquals("   10.0\n(+8.0)", productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    @Test
    public void getAccumulatedEstimateDescription_shortDifference() throws Exception {
        productBacklogItem.setAccumulatedEstimate(123456d);
        referenceProductBacklogItem.setAccumulatedEstimate(123456.7d);
        assertEquals("123456.0\n    (-0.7)", productBacklogComparisonItem.getAccumulatedEstimateDescription());
    }

    @Before
    public void setUp() {
        productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        referenceProductBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        productBacklogComparisonItem = new ProductBacklogComparisonItem(productBacklogItem, referenceProductBacklogItem);
    }
}
