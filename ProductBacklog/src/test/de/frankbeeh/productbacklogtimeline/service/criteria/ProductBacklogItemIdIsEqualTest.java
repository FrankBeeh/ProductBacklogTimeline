package de.frankbeeh.productbacklogtimeline.service.criteria;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogItemIdIsEqualTest {

    @Test
    public void isMatching() {
        final Criteria criteria = new ProductBacklogItemIdIsEqual("10");
        assertTrue(criteria.isMatching(new ProductBacklogItem("10", null, null, null, null, null, null)));
    }

    @Test
    public void isNotMatching() {
        final Criteria criteria = new ProductBacklogItemIdIsEqual("11");
        assertFalse(criteria.isMatching(new ProductBacklogItem("10", null, null, null, null, null, null)));
    }

    @Test
    public void toStringRepresentation() throws Exception {
        assertEquals("id=10", new ProductBacklogItemIdIsEqual("10").toString());
    }
}
