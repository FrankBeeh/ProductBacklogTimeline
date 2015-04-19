package de.frankbeeh.productbacklogtimeline.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DeleteProductBacklogItemTest {
    private static final String ID_1 = "Id 1";
    private static final String ID_2 = "Id 2";
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_1 = new ProductBacklogItem(ID_1, null, null, null, null, null, null, null);
    private static final ProductBacklogItem PRODUCT_BACKLOG_ITEM_2 = new ProductBacklogItem(ID_2, null, null, null, null, null, null, null);

    private List<ProductBacklogItem> productBacklogItems;

    @Test
    public void applyTo() {
        new DeleteProductBacklogItem(ID_1).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(PRODUCT_BACKLOG_ITEM_2), productBacklogItems);
    }

    @Test
    public void applyTo_unknownId() {
        try {
            new DeleteProductBacklogItem("Unknown ID").applyTo(productBacklogItems);
            assertEquals(new ArrayList<ProductBacklogItem>(), productBacklogItems);
            fail("IllegalArgumentException expected!");
        } catch (final IllegalArgumentException exception) {
            assertEquals("Id 'Unknown ID' not found!", exception.getMessage());
        }
    }

    @Before
    public void setUp() {
        productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_1);
        productBacklogItems.add(PRODUCT_BACKLOG_ITEM_2);
    }
}
