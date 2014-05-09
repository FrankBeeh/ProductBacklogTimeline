package de.frankbeeh.productbacklogtimeline.service.diff;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.ChangeEstimateOfProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.DeleteProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.InsertProductBacklogItemAfterId;
import de.frankbeeh.productbacklogtimeline.data.MoveProductBacklogItemAfterId;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogChange;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogDiffTest {
    private static final double ESTIMATE_1 = 10d;
    private static final double CHANGED_ESTIMATE_1 = ESTIMATE_1 + 1;
    private static final String ID_1 = "1";
    private static final String ID_2 = "2";
    private static final String ID_3 = "3";
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_1 = new ProductBacklogItem(ID_1, null, null, ESTIMATE_1, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_1_CHANGED_ESTIMATE = new ProductBacklogItem(ID_1, null, null, CHANGED_ESTIMATE_1, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_2 = new ProductBacklogItem(ID_2, null, null, null, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_3 = new ProductBacklogItem(ID_3, null, null, null, null, null, null);

    private ProductBacklog toProductBacklog;
    private ProductBacklog fromProductBacklog;

    private ProductBacklogDiff productBacklogDiff;

    @Test
    public void computeChanges_twoEmptyProductBacklogs() {
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(toProductBacklog, fromProductBacklog);
        assertTrue(changes.isEmpty());
    }

    @Test
    public void computeChanges_addedOneItemToEmpty() {
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new InsertProductBacklogItemAfterId(null, PRODUCT_BACKLOG_ITEM_1)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_addedOneItemBeforeFirst() {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new InsertProductBacklogItemAfterId(null, PRODUCT_BACKLOG_ITEM_1)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_addedOneItemAfterLast() {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new InsertProductBacklogItemAfterId(ID_1, PRODUCT_BACKLOG_ITEM_2)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_deletedFirstItem() {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new DeleteProductBacklogItem(ID_1)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_moveAfterLastItem() throws Exception {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_3);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_3);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new MoveProductBacklogItemAfterId(ID_1, ID_3)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_moveBeforeFirstItem() throws Exception {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_3);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_3);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new MoveProductBacklogItemAfterId(ID_1, ID_3), new MoveProductBacklogItemAfterId(ID_2, ID_1)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_deletedLastItem() {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new DeleteProductBacklogItem(ID_2)).toString(), changes.toString());
    }

    @Test
    public void computeChanges_changedEstimate() {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1_CHANGED_ESTIMATE);
        final List<ProductBacklogChange> changes = productBacklogDiff.computeChanges(fromProductBacklog, toProductBacklog);
        assertEquals(Arrays.asList(new ChangeEstimateOfProductBacklogItem(ID_1, CHANGED_ESTIMATE_1)).toString(), changes.toString());
    }

    @Test
    public void findChangedEstimates_changedEstimate_wrongSequence() {
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_2);
        fromProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1);
        toProductBacklog.addItem(PRODUCT_BACKLOG_ITEM_1_CHANGED_ESTIMATE);
        try {
            productBacklogDiff.findChangedEstimates(fromProductBacklog.getItems(), toProductBacklog.getItems());
            fail("IllegalArgumentException expected!");
        } catch (final IllegalArgumentException exception) {
            assertEquals("IDs are different for items at position 1: '" + ID_2 + "' vs. '" + ID_1 + "'!", exception.getMessage());
        }
    }

    @Before
    public void setUp() {
        productBacklogDiff = new ProductBacklogDiff();
        toProductBacklog = new ProductBacklog();
        fromProductBacklog = new ProductBacklog();
    }
}
