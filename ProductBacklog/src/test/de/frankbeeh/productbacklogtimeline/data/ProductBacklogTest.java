package de.frankbeeh.productbacklogtimeline.data;

import static org.easymock.EasyMock.same;

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
    public void visitAllItems_noSprints() {
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
        productBacklog.visitAllItems(sprints);
        verifyAll();
    }
}
