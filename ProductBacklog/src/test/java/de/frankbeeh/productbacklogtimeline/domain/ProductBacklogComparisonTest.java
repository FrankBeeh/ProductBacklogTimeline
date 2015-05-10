package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class ProductBacklogComparisonTest extends EasyMockSupport {
    private ProductBacklogComparison productBacklogComparison;

    @Test
    public void setReferenceProductBacklogWithoutSelectedProductBacklog() throws Exception {
        productBacklogComparison.setReference(new ProductBacklog(Arrays.asList(new ProductBacklogItem("ID 1", "Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1",
                "PlannedRelease 1")))); 
        assertEquals(0, productBacklogComparison.getComparisons().size());
    }

    @Test
    public void setSelectedProductBacklog() throws Exception {
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem("ID 1", "Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1");
        final ProductBacklogItem referenceProductBacklogItem1 = new ProductBacklogItem("ID 1", "Changed Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1");
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Canceled, "Sprint 2", "Rank 2", "PlannedRelease 2");
        final ProductBacklogItem referenceProductBacklogItem3 = new ProductBacklogItem("ID 3", "Title 3", "Description 3", 3d, State.Canceled, "Sprint 3", "Rank 3", "PlannedRelease 3");

        productBacklogComparison.setSelected(new ProductBacklog(Arrays.asList(productBacklogItem1, productBacklogItem2)));
        assertEquals(Arrays.asList(new ProductBacklogItemComparison(productBacklogItem1), new ProductBacklogItemComparison(productBacklogItem2)).toString(),
                productBacklogComparison.getComparisons().toString());

        productBacklogComparison.setReference(new ProductBacklog(Arrays.asList(referenceProductBacklogItem1, referenceProductBacklogItem3)));
        assertEquals(Arrays.asList(new ProductBacklogItemComparison(productBacklogItem1, referenceProductBacklogItem1), new ProductBacklogItemComparison(productBacklogItem2, null)).toString(),
                productBacklogComparison.getComparisons().toString());
    }

    @Before
    public void setUp() {
        productBacklogComparison = new ProductBacklogComparison();
    }
}
