package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;
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
    private ProductBacklogItemComparison productBacklogItem1 = new ProductBacklogItemComparison(new ProductBacklogItem("ID 1", null, null, null, null, "", "1", null), null);
    private ProductBacklogItemComparison productBacklogItem2 = new ProductBacklogItemComparison(new ProductBacklogItem("ID 2", null, null, null, null, "", "2", null), null);
    private ProductBacklogComparison productBacklogComparison;

    @Test
    public void getMatchingProductBacklogItems_noneIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(false);
        replayAll();
        assertEquals(new ArrayList<ProductBacklogItem>(), productBacklogComparison.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_firstIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(false);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItem1), productBacklogComparison.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_secondIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItem2), productBacklogComparison.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_bothAreMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItem1, productBacklogItem2), productBacklogComparison.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Before
    public void setUp() {
        productBacklogComparison = new ProductBacklogComparison(productBacklogItem1, productBacklogItem2);
    }
}
