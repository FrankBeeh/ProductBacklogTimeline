package de.frankbeeh.productbacklogtimeline.data;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByHistory;

@RunWith(EasyMockRunner.class)
public class SprintsTest extends EasyMockSupport {

    @Mock
    private ComputeProgressForecastByHistory velocityMock1;
    @Mock
    private ComputeProgressForecastByHistory velocityMock2;

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

}
