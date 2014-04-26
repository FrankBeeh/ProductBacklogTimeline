package de.frankbeeh.productbacklogtimeline.data;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByAverageVelocity;

@RunWith(EasyMockRunner.class)
public class SprintsTest extends EasyMockSupport {

    @Mock
    private ComputeEffortForecastByAverageVelocity averageVelocityMock;

    @Test
    public void computeEffortForecasts() {
        final Sprints sprints = new Sprints(averageVelocityMock);
        final SprintData sprint1 = new SprintData("Sprint 1", null, null, null, null, null, null);
        sprints.addItem(sprint1);
        final SprintData sprint2 = new SprintData("Sprint 2", null, null, null, null, null, null);
        sprints.addItem(sprint2);

        averageVelocityMock.reset();
        averageVelocityMock.visit(sprint1);
        averageVelocityMock.visit(sprint2);
        replayAll();
        sprints.computeEffortForecasts();
        verifyAll();
    }

}
