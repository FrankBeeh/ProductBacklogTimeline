package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.same;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;
import de.frankbeeh.productbacklogtimeline.service.sort.ProductBacklogSortingStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ProductBacklogItemVisitor;

@RunWith(EasyMockRunner.class)
public class ProductBacklogTest extends EasyMockSupport {
    @Mock
    private ReleaseCriteria criteriaMock;

    private static final String ID_2 = "ID 2";
    private static final String ID_1 = "ID 1";
    private ProductBacklogSortingStrategy sortingStrategyMock;
    private ProductBacklogItemVisitor visitorMock1;
    private ProductBacklogItemVisitor visitorMock2;

    private ProductBacklogItem productBacklogItem1 = new ProductBacklogItem(ID_1, null, null, null, null, "", "1", null);
    private ProductBacklogItem productBacklogItem2 = new ProductBacklogItem(ID_2, null, null, null, null, "", "2", null);
    private ProductBacklog productBacklog;

    @Test
    public void getMatchingProductBacklogItems_noneIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(false);
        replayAll();
        assertEquals(new ArrayList<ProductBacklogItem>(), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_firstIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(false);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItem1), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_secondIsMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(false);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItem2), productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }

    @Test
    public void getMatchingProductBacklogItems_bothAreMatching() throws Exception {
        expect(criteriaMock.isMatching(productBacklogItem1)).andReturn(true);
        expect(criteriaMock.isMatching(productBacklogItem2)).andReturn(true);
        replayAll();
        assertEquals(Arrays.asList(productBacklogItem1, productBacklogItem2),
                productBacklog.getMatchingProductBacklogItems(criteriaMock));
        verifyAll();
    }
    
    @Test
    public void updateAllItems() {
        final VelocityForecast selectedVelocityForecast = new VelocityForecast();

        sortingStrategyMock.sortProductBacklog(same(productBacklog), same(selectedVelocityForecast));
        visitorMock1.reset();
        visitorMock1.visit(same(productBacklogItem1), same(selectedVelocityForecast));
        visitorMock1.visit(same(productBacklogItem2), same(selectedVelocityForecast));
        visitorMock2.reset();
        visitorMock2.visit(same(productBacklogItem1), same(selectedVelocityForecast));
        visitorMock2.visit(same(productBacklogItem2), same(selectedVelocityForecast));
        replayAll();
        productBacklog.updateAllItems(selectedVelocityForecast);
        verifyAll();
    }

    @Test
    public void containsId() throws Exception {
        assertTrue(productBacklog.containsId(ID_1));
        assertFalse(productBacklog.containsId("Unknown ID"));
        assertTrue(productBacklog.containsId(ID_2));
    }

    @Test
    public void getItemById() throws Exception {
        assertEquals(productBacklogItem1, productBacklog.getItemById(ID_1));
        assertNull(productBacklog.getItemById("Unknown ID"));
        assertEquals(productBacklogItem2, productBacklog.getItemById(ID_2));
    }

    @Before
    public void setUp() {
        sortingStrategyMock = createMock(ProductBacklogSortingStrategy.class);
        visitorMock1 = createMock(ProductBacklogItemVisitor.class);
        visitorMock2 = createMock(ProductBacklogItemVisitor.class);
        productBacklog = new ProductBacklog(sortingStrategyMock, visitorMock1, visitorMock2);
        productBacklog.addItem(productBacklogItem1);
        productBacklog.addItem(productBacklogItem2);
    }
}
