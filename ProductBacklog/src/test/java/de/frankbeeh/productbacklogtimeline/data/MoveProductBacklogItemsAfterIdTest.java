package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MoveProductBacklogItemsAfterIdTest {
    private static final String ID_1 = "ID 1";
    private static final String ID_2 = "ID 2";
    private static final String ID_3 = "ID 3";
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_1 = new ProductBacklogItem(ID_1, null, null, null, null, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_2 = new ProductBacklogItem(ID_2, null, null, null, null, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_3 = new ProductBacklogItem(ID_3, null, null, null, null, null, null, null);

    @Test
    public void applyTo_moveAfterLast() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_1);
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_2);
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_3);
        new MoveProductBacklogItemsAfterId(ID_1, ID_3, 2).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(PRODUCT_BACKLOG_ITEM_3, PRODUCT_BACKLOG_ITEM_1, PRODUCT_BACKLOG_ITEM_2).toString(), productBacklogItems.toString());
    }

    @Test
    public void applyTo_moveBeforeFirst() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_1);
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_2);
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_3);
        new MoveProductBacklogItemsAfterId(ID_2, null, 2).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(PRODUCT_BACKLOG_ITEM_2, PRODUCT_BACKLOG_ITEM_3, PRODUCT_BACKLOG_ITEM_1).toString(), productBacklogItems.toString());
    }

    @Test
    public void applyTo_unknownSourceId() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_1);
        try {
            new MoveProductBacklogItemsAfterId("Unknown ID", ID_1, 1).applyTo(productBacklogItems);
            fail("IllegalArgumentException expected!");
        } catch (final IllegalArgumentException exception) {
            assertEquals("Id 'Unknown ID' not found!", exception.getMessage());
        }
    }

    @Test
    public void applyTo_unknownDestinationId() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_1);
        try {
            new MoveProductBacklogItemsAfterId(ID_1, "Unknown ID", 1).applyTo(productBacklogItems);
            fail("IllegalArgumentException expected!");
        } catch (final IllegalArgumentException exception) {
            assertEquals("Id 'Unknown ID' not found!", exception.getMessage());
        }
    }
}
