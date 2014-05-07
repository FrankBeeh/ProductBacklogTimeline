package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ChangeEstimateTest {

    @Test
    public void applyTo_unkownId() {
        try {
            new ChangeEstimate("Unknown Id", 10d).applyTo(new ArrayList<ProductBacklogItem>());
            fail("IllegalArgumentException expected");
        } catch (final IllegalArgumentException exception) {
            assertEquals("Id 'Unknown Id' not found!", exception.getMessage());
        }
    }

    @Test
    public void applyTo() throws Exception {
        final String id = "ID 1";
        final Double previousEstimate = 5d;
        final Double changedEstimate = 10d;
        final List<ProductBacklogItem> productBacklogItems = new ArrayList<ProductBacklogItem>();
        productBacklogItems.add(new ProductBacklogItem(id, null, null, previousEstimate, null, null, null));
        
        new ChangeEstimate(id, changedEstimate).applyTo(productBacklogItems);
        assertEquals(Arrays.asList(new ProductBacklogItem(id, null, null, changedEstimate, null, null, null)).toString(), productBacklogItems.toString());
    }
}
