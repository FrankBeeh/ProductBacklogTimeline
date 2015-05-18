package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class RankProductBacklogItemTest {

    private RankProductBacklogItem visitor;

    @Test
    public void visitFirst() {
        final ProductBacklogItem productBacklogItem = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        visitor.visit(productBacklogItem, null);
        assertEquals(Integer.valueOf(1), productBacklogItem.getProductBacklogRank());
    }
    
    @Test
    public void visitSecond() {
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        visitor.visit(productBacklogItem1, null);
        visitor.visit(productBacklogItem2, null);
        assertEquals(Integer.valueOf(2), productBacklogItem2.getProductBacklogRank());
    }
    
    @Test
    public void visitSecondResetVisitThird() {
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        final ProductBacklogItem productBacklogItem3 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        visitor.visit(productBacklogItem1, null);
        visitor.visit(productBacklogItem2, null);
        visitor.reset();
        visitor.visit(productBacklogItem3, null);
        assertEquals(Integer.valueOf(1), productBacklogItem3.getProductBacklogRank());
    }
    
    @Test
    public void visitThreeSecondIsCanceled() {
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(null, null, null, null, State.Canceled, null, null, null);
        final ProductBacklogItem productBacklogItem3 = new ProductBacklogItem(null, null, null, null, State.Todo, null, null, null);
        visitor.visit(productBacklogItem1, null);
        visitor.visit(productBacklogItem2, null);
        visitor.visit(productBacklogItem3, null);
        assertNull(productBacklogItem2.getProductBacklogRank());
        assertEquals(Integer.valueOf(2), productBacklogItem3.getProductBacklogRank());
    }

    
    @Before
    public void setUp() {
        visitor = new RankProductBacklogItem();
    }
}
