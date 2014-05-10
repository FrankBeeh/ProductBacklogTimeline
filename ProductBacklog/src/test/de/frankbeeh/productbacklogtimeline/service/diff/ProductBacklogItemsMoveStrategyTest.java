package de.frankbeeh.productbacklogtimeline.service.diff;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogItemsMoveStrategyTest {

    private static final String ID_1 = "ID 1";
    private static final String ID_2 = "ID 2";
    private static final String ID_3 = "ID 3";
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_1 = new ProductBacklogItem(ID_1, null, null, null, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_2 = new ProductBacklogItem(ID_2, null, null, null, null, null, null);
    private static final ProductBacklogItem OTHER_PRODUCT_BACKLOG_ITEM_1 = new ProductBacklogItem(ID_1, null, null, null, null, null, null);
    private static final ProductBacklogItem OTHER_PRODUCT_BACKLOG_ITEM_2 = new ProductBacklogItem(ID_2, null, null, null, null, null, null);
    private static final ProductBacklogItem OTHER_PRODUCT_BACKLOG_ITEM_3 = new ProductBacklogItem(ID_3, null, null, null, null, null, null);
    private ProductBacklogItemsMoveStrategy productBacklogItemsMoveStrategy;

    @Test
    public void hasSameSequence_true() throws Exception {
        assertTrue(productBacklogItemsMoveStrategy.hasSameSequence(Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2),
                Arrays.asList(OTHER_PRODUCT_BACKLOG_ITEM_1, OTHER_PRODUCT_BACKLOG_ITEM_2)));
    }

    @Test
    public void hasSameSequence_secondIdDifferent() throws Exception {
        assertFalse(productBacklogItemsMoveStrategy.hasSameSequence(Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2),
                Arrays.asList(OTHER_PRODUCT_BACKLOG_ITEM_1, OTHER_PRODUCT_BACKLOG_ITEM_3)));
    }

    @Test
    public void hasSameSequence_firstIdDifferent() throws Exception {
        assertFalse(productBacklogItemsMoveStrategy.hasSameSequence(Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2),
                Arrays.asList(OTHER_PRODUCT_BACKLOG_ITEM_3, OTHER_PRODUCT_BACKLOG_ITEM_2)));
    }

    @Test
    public void hasSameSequence_sizeDifferent() throws Exception {
        assertFalse(productBacklogItemsMoveStrategy.hasSameSequence(Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2),
                Arrays.asList(OTHER_PRODUCT_BACKLOG_ITEM_1, OTHER_PRODUCT_BACKLOG_ITEM_2, OTHER_PRODUCT_BACKLOG_ITEM_3)));
    }

    @Test
    public void findPredecessorId_firstId() throws Exception {
        assertNull(productBacklogItemsMoveStrategy.findPredecessorId(ID_1, Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2)));
    }

    @Test
    public void findPredecessorId_secondId() throws Exception {
        assertEquals(ID_1, productBacklogItemsMoveStrategy.findPredecessorId(ID_2, Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findPredecessorId_unknownId() throws Exception {
        productBacklogItemsMoveStrategy.findPredecessorId("Unknown ID", Arrays.asList(PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2));
    }

    @Before
    public void setUp() {
        productBacklogItemsMoveStrategy = new ProductBacklogItemsMoveStrategy();
    }

}
