package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class AccumulateEffortDoneTest {

    private AccumulateEffortDone visitor;

    @Test
    public void visit() {
        final double effortDone1 = 10d;
        final SprintData sprintData1 = new SprintData(null, null, null, null, null, null, effortDone1);
        visitor.visit(sprintData1);
        final double effortDone2 = 12d;
        assertEquals(Double.valueOf(effortDone1), sprintData1.getAccumulatedEffortDone());
        final SprintData sprintData2 = new SprintData(null, null, null, null, null, null, effortDone2);
        visitor.visit(sprintData2);
        assertEquals(Double.valueOf(effortDone1 + effortDone2), sprintData2.getAccumulatedEffortDone());
    }

    @Test
    public void visit_effortDoneMissing() {
        final double effortDone1 = 10d;
        final SprintData sprintData1 = new SprintData(null, null, null, null, null, null, effortDone1);
        visitor.visit(sprintData1);
        assertEquals(Double.valueOf(effortDone1), sprintData1.getAccumulatedEffortDone());
        final SprintData sprintData2 = new SprintData(null, null, null, null, null, null, null);
        visitor.visit(sprintData2);
        assertNull(sprintData2.getAccumulatedEffortDone());
    }

    @Test
    public void reset() throws Exception {
        final double effortDone1 = 10d;
        final SprintData sprintData1 = new SprintData(null, null, null, null, null, null, effortDone1);
        visitor.visit(sprintData1);

        visitor.reset();

        final double effortDone2 = 12d;
        assertEquals(Double.valueOf(effortDone1), sprintData1.getAccumulatedEffortDone());
        final SprintData sprintData2 = new SprintData(null, null, null, null, null, null, effortDone2);
        visitor.visit(sprintData2);
        assertEquals(Double.valueOf(effortDone2), sprintData2.getAccumulatedEffortDone());
    }

    @Before
    public void setUp() {
        visitor = new AccumulateEffortDone();
    }
}
