package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

@RunWith(EasyMockRunner.class)
public class ProductBacklogComparisonTest extends EasyMockSupport {
    @Mock
    private ReleaseCriteria criteriaMock;
    private ProductBacklogItemComparison productBacklogItemComparison1 = new ProductBacklogItemComparison(new ProductBacklogItem("ID 1", null, null, null, null, "", "1", null), null);
    private ProductBacklogItemComparison productBacklogItemComparison2 = new ProductBacklogItemComparison(new ProductBacklogItem("ID 2", null, null, null, null, "", "2", null), null);
    private ProductBacklogComparison productBacklogComparisonWithProductBacklogItemComparisons;
    private ProductBacklogComparison productBacklogComparison;

    @Test
    public void getMatchingProductBacklogItems_noneIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItemComparison1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItemComparison2)).andReturn(false);
        replayAll();
        assertEquals(new ArrayList<ProductBacklogItem>(), productBacklogComparisonWithProductBacklogItemComparisons.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_firstIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItemComparison1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItemComparison2)).andReturn(false);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItemComparison1), productBacklogComparisonWithProductBacklogItemComparisons.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_secondIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItemComparison1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItemComparison2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItemComparison2), productBacklogComparisonWithProductBacklogItemComparisons.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_bothAreMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItemComparison1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItemComparison2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItemComparison1, productBacklogItemComparison2),
                productBacklogComparisonWithProductBacklogItemComparisons.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

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
        productBacklogComparisonWithProductBacklogItemComparisons = new ProductBacklogComparison(productBacklogItemComparison1, productBacklogItemComparison2);
    }
}
