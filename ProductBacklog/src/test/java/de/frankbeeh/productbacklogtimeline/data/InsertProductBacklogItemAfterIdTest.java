package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class InsertProductBacklogItemAfterIdTest {
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM = new ProductBacklogItem("ID 1", null, null, null, null, null, null, null);
    private static final ProductBacklogItem NEW_PRODUCT_BACKLOG_ITEM = new ProductBacklogItem("ID 2", null, null, null, null, null, null, null);

    @Test
    public void applyTo_empty() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        new InsertProductBacklogItemAfterId(null, NEW_PRODUCT_BACKLOG_ITEM).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(NEW_PRODUCT_BACKLOG_ITEM).toString(), productBacklogItems.toString());
    }

    @Test
    public void applyTo_beforeFirst() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM);
        new InsertProductBacklogItemAfterId(null, NEW_PRODUCT_BACKLOG_ITEM).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(NEW_PRODUCT_BACKLOG_ITEM, PRODUCT_BACKLOG_ITEM).toString(), productBacklogItems.toString());
    }

    @Test
    public void applyTo_afterFirst() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM);
        new InsertProductBacklogItemAfterId("ID 1", NEW_PRODUCT_BACKLOG_ITEM).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(PRODUCT_BACKLOG_ITEM, NEW_PRODUCT_BACKLOG_ITEM).toString(), productBacklogItems.toString());
    }

    @Test
    public void applyTo_unknownId() {
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM);
        try {
            new InsertProductBacklogItemAfterId("Unknown ID", NEW_PRODUCT_BACKLOG_ITEM).applyTo(productBacklogItems);
            fail("IllegalArgumentException expected!");
        } catch (final IllegalArgumentException exception) {
            assertEquals("Id 'Unknown ID' not found!", exception.getMessage());
        }
    }
}
