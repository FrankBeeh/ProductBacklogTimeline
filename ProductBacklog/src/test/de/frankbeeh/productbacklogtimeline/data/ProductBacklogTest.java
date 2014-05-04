package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.same;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.criteria.Criteria;
import de.frankbeeh.productbacklogtimeline.service.sort.ProductBacklogSortingStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;

@RunWith(EasyMockRunner.class)
public class ProductBacklogTest extends EasyMockSupport {
    @Mock
    private AccumulateEstimate visitorMock1;
    @Mock
    private AccumulateEstimate visitorMock2;
    @Mock
    private ProductBacklogItem productBacklogItemMock1;
    @Mock
    private ProductBacklogItem productBacklogItemMock2;
    @Mock
    private ProductBacklogSortingStrategy sortingStrategyMock;
    @Mock
    private Criteria criteriaMock;

    @Test
    public void updateAllItems() {
        final Sprints sprints = new Sprints();
        final ProductBacklog productBacklog = new ProductBacklog(sortingStrategyMock, visitorMock1, visitorMock2);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem("ID 1", null, null, null, null, "", 1);
        productBacklog.addItem(productBacklogItem1);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem("ID 2", null, null, null, null, "", 2);
        productBacklog.addItem(productBacklogItem2);

        sortingStrategyMock.sortProductBacklog(same(productBacklog), same(sprints));
        visitorMock1.reset();
        visitorMock1.visit(same(productBacklogItem1), same(sprints));
        visitorMock1.visit(same(productBacklogItem2), same(sprints));
        visitorMock2.reset();
        visitorMock2.visit(same(productBacklogItem1), same(sprints));
        visitorMock2.visit(same(productBacklogItem2), same(sprints));
        replayAll();
        productBacklog.updateAllItems(sprints);
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_noneIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(false);
        replayAll();
        assertEquals(new ArrayList<ProductBacklogItem>(), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_firstIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(false);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItemMock1), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_secondIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItemMock2), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_bothAreMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItemMock1, productBacklogItemMock2), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    private ProductBacklog createProductBacklogWithMockedItems() {
        final ProductBacklog productBacklog = new ProductBacklog();
        productBacklog.addItem(productBacklogItemMock1);
        productBacklog.addItem(productBacklogItemMock2);
        return productBacklog;
    }
}
