package de.frankbeeh.productbacklogtimeline.service.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class PlannedReleaseIsEqualTest {

    @Test
    public void isMatching() {
        final ReleaseCriteria criteria = new PlannedReleaseIsEqual("Release");
        assertTrue(criteria.isMatching(new ProductBacklogItem(null, null, null, null, null, null, null, "Release")));
    }

    @Test
    public void isNotMatching() {
        final ReleaseCriteria criteria = new ProductBacklogItemIdIsEqual("Other Release");
        assertFalse(criteria.isMatching(new ProductBacklogItem(null, null, null, null, null, null, null, "Release")));
    }

    @Test
    public void toStringRepresentation() throws Exception {
        assertEquals("plannedRelease=\nRelease", new PlannedReleaseIsEqual("Release").toString());
    }
}
