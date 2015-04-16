package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.State;

public class AccumulateEstimateTest {

    private AccumulateEstimate visitor;
    private ProductBacklog referenceProductBacklogMock;

    @Test
    public void visitFirst_noEstimate() {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null);
        visitor.visit(productBacklogItem, null, referenceProductBacklogMock, null);
        assertEquals(0d, productBacklogItem.getAccumulatedEstimate());
    }

    @Test
    public void visitFirst_withEstimate() {
        final Double estimate = Double.valueOf(1d);
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, estimate, null, null, null);
        visitor.visit(productBacklogItem, null, referenceProductBacklogMock, null);
        assertEquals(estimate, productBacklogItem.getAccumulatedEstimate());
    }
    
    @Test
    public void visitFirst_Canceled() {
        final Double estimate = Double.valueOf(1d);
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, estimate, State.Canceled, null, null);
        visitor.visit(productBacklogItem, null, referenceProductBacklogMock, null);
        assertEquals(0d, productBacklogItem.getAccumulatedEstimate());
    }

    @Test
    public void visitSecond_withEstimate() {
        final Double estimate1 = Double.valueOf(1d);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, estimate1, null, null, null);
        visitor.visit(productBacklogItem1, null, referenceProductBacklogMock, null);

        final Double estimate2 = Double.valueOf(2d);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, estimate2, null, null, null);
        visitor.visit(productBacklogItem2, null, referenceProductBacklogMock, null);
        assertEquals(estimate1 + estimate2, productBacklogItem2.getAccumulatedEstimate());
    }
    
    @Test
    public void visitSecond_canceled() {
        final Double estimate1 = Double.valueOf(1d);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, estimate1, null, null, null);
        visitor.visit(productBacklogItem1, null, referenceProductBacklogMock, null);

        final Double estimate2 = Double.valueOf(2d);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, estimate2, State.Canceled, null, null);
        visitor.visit(productBacklogItem2, null, referenceProductBacklogMock, null);
        assertEquals(estimate1, productBacklogItem2.getAccumulatedEstimate());
    }

    @Test
    public void visitSecond_noEstimate() {
        final Double estimate1 = Double.valueOf(1d);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, estimate1, null, null, null);
        visitor.visit(productBacklogItem1, null, referenceProductBacklogMock, null);

        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, null, null, null, null);
        visitor.visit(productBacklogItem2, null, referenceProductBacklogMock, null);
        assertEquals(estimate1, productBacklogItem2.getAccumulatedEstimate());
    }

    @Test
    public void reset() {
        final Double estimate1 = Double.valueOf(1d);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, estimate1, null, null, null);
        visitor.visit(productBacklogItem1, null, referenceProductBacklogMock, null);

        visitor.reset();

        final Double estimate2 = Double.valueOf(2d);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, estimate2, null, null, null);
        visitor.visit(productBacklogItem2, null, referenceProductBacklogMock, null);
        assertEquals(estimate2, productBacklogItem2.getAccumulatedEstimate());
    }

    @Before
    public void setUp() {
        visitor = new AccumulateEstimate();
        referenceProductBacklogMock = createMock(ProductBacklog.class);
    }
}
