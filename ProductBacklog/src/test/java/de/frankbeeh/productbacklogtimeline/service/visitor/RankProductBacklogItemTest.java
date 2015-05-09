package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class RankProductBacklogItemTest {

    private RankProductBacklogItem visitor;

    @Test
    public void visitFirst() {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        visitor.visit(productBacklogItem, null);
        assertEquals(1, productBacklogItem.getProductBacklogRank());
    }
    
    @Test
    public void visitSecond() {
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        visitor.visit(productBacklogItem1, null);
        visitor.visit(productBacklogItem2, null);
        assertEquals(2, productBacklogItem2.getProductBacklogRank());
    }
    
    @Test
    public void visitSecondResetVisitThird() {
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        final ProductBacklogItem productBacklogItem3 = new ProductBacklogItem(null, null, null, null, null, null, null, null);
        visitor.visit(productBacklogItem1, null);
        visitor.visit(productBacklogItem2, null);
        visitor.reset();
        visitor.visit(productBacklogItem3, null);
        assertEquals(1, productBacklogItem3.getProductBacklogRank());
    }

    
    @Before
    public void setUp() {
        visitor = new RankProductBacklogItem();
    }
}
