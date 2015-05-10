package de.frankbeeh.productbacklogtimeline.service.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class ProductBacklogItemIdIsEqualTest {

    @Test
    public void isMatching() {
        final ReleaseCriteria criteria = new ProductBacklogItemIdIsEqual("10");
        assertTrue(criteria.isMatching(new ProductBacklogItem("10", null, null, null, null, null, null, null)));
    }

    @Test
    public void isNotMatching() {
        final ReleaseCriteria criteria = new ProductBacklogItemIdIsEqual("11");
        assertFalse(criteria.isMatching(new ProductBacklogItem("10", null, null, null, null, null, null, null)));
    }

    @Test
    public void toStringRepresentation() throws Exception {
        assertEquals("idOfPBI=10", new ProductBacklogItemIdIsEqual("10").toString());
    }
}
