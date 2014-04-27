package de.frankbeeh.productbacklogtimeline.data;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

@RunWith(EasyMockRunner.class)
public class SprintsTest extends EasyMockSupport {

    @Mock
    private SprintDataVisitor visitorMock1;
    @Mock
    private SprintDataVisitor visitorMock2;

    @Test
    public void visitAllSprints() {
        final Sprints sprints = new Sprints(visitorMock1, visitorMock2);
        final SprintData sprint1 = new SprintData("Sprint 1", null, null, null, null, null, null);
        sprints.addItem(sprint1);
        final SprintData sprint2 = new SprintData("Sprint 2", null, null, null, null, null, null);
        sprints.addItem(sprint2);

        visitorMock1.reset();
        visitorMock1.visit(sprint1);
        visitorMock1.visit(sprint2);
        visitorMock2.reset();
        visitorMock2.visit(sprint1);
        visitorMock2.visit(sprint2);
        replayAll();
        sprints.visitAllSprints();
        verifyAll();
    }
}
