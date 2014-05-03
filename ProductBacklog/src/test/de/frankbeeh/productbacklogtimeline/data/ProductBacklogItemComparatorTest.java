package de.frankbeeh.productbacklogtimeline.data;

import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class ProductBacklogItemComparatorTest extends EasyMockSupport {
    private static final int RANK = 5;
    private static final int GREATER_RANK = 10;

    private Sprints sprintsMock;

    private ProductBacklogItemComparator comparator;

    @Test
    public void comparator_Equal() {
        final String sprintName = "sprint";
        expect(sprintsMock.getSortIndex(sprintName)).andReturn(1).times(2);
        replayAll();
        assertEquals(0, comparator.compare(createProductBacklogItem(sprintName, RANK), createProductBacklogItem(sprintName, RANK)));
        verifyAll();
    }

    @Test
    public void comparator_sprintDifferent() {
        final String sprintName1 = "sprint 1";
        final String sprintName2 = "sprint 2";
        expect(sprintsMock.getSortIndex(sprintName1)).andReturn(1).times(2);
        expect(sprintsMock.getSortIndex(sprintName2)).andReturn(10).times(2);
        replayAll();
        assertTrue(comparator.compare(createProductBacklogItem(sprintName1, RANK), createProductBacklogItem(sprintName2, RANK)) < 0);
        assertTrue(comparator.compare(createProductBacklogItem(sprintName2, RANK), createProductBacklogItem(sprintName1, RANK)) > 0);
        verifyAll();
    }

    @Test
    public void comparator_rankDifferent() {
        final String sprintName = "sprint";
        expect(sprintsMock.getSortIndex(sprintName)).andReturn(1).times(4);
        replayAll();
        assertTrue(comparator.compare(createProductBacklogItem(sprintName, GREATER_RANK), createProductBacklogItem(sprintName, RANK)) > 0);
        assertTrue(comparator.compare(createProductBacklogItem(sprintName, RANK), createProductBacklogItem(sprintName, GREATER_RANK)) < 0);
        verifyAll();
    }

    @Before
    public void setUp() {
        sprintsMock = createMock(Sprints.class);
        comparator = new ProductBacklogItemComparator(sprintsMock);
    }

    private ProductBacklogItem createProductBacklogItem(String sprintName, int rank) {
        return new ProductBacklogItem(null, null, null, null, null, sprintName, rank);
    }
}
