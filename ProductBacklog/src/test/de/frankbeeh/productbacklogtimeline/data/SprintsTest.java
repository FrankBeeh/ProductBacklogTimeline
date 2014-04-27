package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertEquals;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

@RunWith(EasyMockRunner.class)
public class SprintsTest extends EasyMockSupport {

    @Mock
    private SprintDataVisitor velocityMock1;
    @Mock
    private SprintDataVisitor velocityMock2;

    @Test
    public void computeEffortForecasts() {
        final Sprints sprints = new Sprints(velocityMock1, velocityMock2);
        final SprintData sprint1 = new SprintData("Sprint 1", null, null, null, null, null, null);
        sprints.addItem(sprint1);
        final SprintData sprint2 = new SprintData("Sprint 2", null, null, null, null, null, null);
        sprints.addItem(sprint2);

        velocityMock1.reset();
        velocityMock1.visit(sprint1);
        velocityMock1.visit(sprint2);
        velocityMock2.reset();
        velocityMock2.visit(sprint1);
        velocityMock2.visit(sprint2);
        replayAll();
        sprints.computeEffortForecasts();
        verifyAll();
    }

    @Test
    public void computeAccumumatedEffort() throws Exception {
        final double effortDone1 = 10d;
        final double effortDone2 = 12d;

        final Sprints sprints = new Sprints();
        final SprintData sprint1 = new SprintData("Sprint 1", null, null, null, null, null, effortDone1);
        sprints.addItem(sprint1);
        final SprintData sprint2 = new SprintData("Sprint 2", null, null, null, null, null, effortDone2);
        sprints.addItem(sprint2);

        sprints.computeAccumumatedEffort();

        assertEquals(Double.valueOf(effortDone1), sprint1.getAccumulatedEffortDone());
        assertEquals(Double.valueOf(effortDone1 + effortDone2), sprint2.getAccumulatedEffortDone());
    }

}
