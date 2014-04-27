package de.frankbeeh.productbacklogtimeline.data;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;

@RunWith(EasyMockRunner.class)
public class ProductBacklogTest extends EasyMockSupport {

    @Mock
    private AccumulateEstimate visitorMock1;
    @Mock
    private AccumulateEstimate visitorMock2;

    @Test
    public void visitAllSprints() {
        final ProductBacklog productBacklog = new ProductBacklog(visitorMock1, visitorMock2);
        final ProductBacklogItem productBacklogItem1 = new ProductBacklogItem("ID 1", null, null, null, null);
        productBacklog.addItem(productBacklogItem1);
        final ProductBacklogItem productBacklogItem2 = new ProductBacklogItem("ID 2", null, null, null, null);
        productBacklog.addItem(productBacklogItem2);

        visitorMock1.reset();
        visitorMock1.visit(productBacklogItem1);
        visitorMock1.visit(productBacklogItem2);
        visitorMock2.reset();
        visitorMock2.visit(productBacklogItem1);
        visitorMock2.visit(productBacklogItem2);
        replayAll();
        productBacklog.visitAllItems();
        verifyAll();
    }
}
