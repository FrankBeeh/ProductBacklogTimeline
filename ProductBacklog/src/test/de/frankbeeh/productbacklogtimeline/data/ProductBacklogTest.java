package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.same;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.criteria.Criteria;
import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;

@RunWith(EasyMockRunner.class)
public class ProductBacklogTest extends EasyMockSupport {
    private static final double ACCUMULATED_ESTIMATE_1 = 7d;
    private static final double ACCUMULATED_ESTIMATE_2 = 11d;
    @Mock
    private AccumulateEstimate visitorMock1;
    @Mock
    private AccumulateEstimate visitorMock2;
    @Mock
    private ProductBacklogItem productBacklogItemMock1;
    @Mock
    private ProductBacklogItem productBacklogItemMock2;
    @Mock
    private Criteria criteriaMock;

    @Test
    public void updateAllItems() {
        final Sprints sprints = new Sprints();
        final ProductBacklog productBacklog = new ProductBacklog(visitorMock1, visitorMock2);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem("ID 1", null, null, null, null);
        productBacklog.addItem(productBacklogItem1);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem("ID 2", null, null, null, null);
        productBacklog.addItem(productBacklogItem2);

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
    public void getAccumulatedEstimate_noProductBacklogItemIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(false);
        replayAll();
        assertNull(productBacklog.getAccumulatedEstimate(criteriaMock));
        verifyAll();
    }

    @Test
    public void getAccumulatedEstimate_firstProductBacklogItemIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(true);
        expect(productBacklogItemMock1.getAccumulatedEstimate()).andReturn(ACCUMULATED_ESTIMATE_2);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(false);
        replayAll();
        assertEquals(ACCUMULATED_ESTIMATE_2, productBacklog.getAccumulatedEstimate(criteriaMock));
        verifyAll();
    }

    @Test
    public void getAccumulatedEstimate_secondProductBacklogItemIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(true);
        expect(productBacklogItemMock2.getAccumulatedEstimate()).andReturn(ACCUMULATED_ESTIMATE_2);
        replayAll();
        assertEquals(ACCUMULATED_ESTIMATE_2, productBacklog.getAccumulatedEstimate(criteriaMock));
        verifyAll();
    }

    @Test
    public void getAccumulatedEstimate_bothProductBacklogItemIsMatching() throws Exception {
        final ProductBacklog productBacklog = createProductBacklogWithMockedItems();

        expect(criteriaMock.isMatching(productBacklogItemMock1)).andReturn(true);
        expect(productBacklogItemMock1.getAccumulatedEstimate()).andReturn(ACCUMULATED_ESTIMATE_1);
        expect(criteriaMock.isMatching(productBacklogItemMock2)).andReturn(true);
        expect(productBacklogItemMock2.getAccumulatedEstimate()).andReturn(ACCUMULATED_ESTIMATE_2);
        replayAll();
        assertEquals(ACCUMULATED_ESTIMATE_2, productBacklog.getAccumulatedEstimate(criteriaMock));
        verifyAll();
    }

    private ProductBacklog createProductBacklogWithMockedItems() {
        final ProductBacklog productBacklog = new ProductBacklog();
        productBacklog.addItem(productBacklogItemMock1);
        productBacklog.addItem(productBacklogItemMock2);
        return productBacklog;
    }
}
