package de.frankbeeh.productbacklogtimeline.service.visitor;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.Release;
import de.frankbeeh.productbacklogtimeline.service.criteria.Criteria;

@RunWith(EasyMockRunner.class)
public class AccumulateEffortForReleaseTest extends EasyMockSupport {
    @Mock
    private ProductBacklog productBacklog;
    @Mock
    private Criteria criteria;

    private ReleaseVisitor visitor;

    @Test
    public void visit_matchNoProductBacklogItem() throws Exception {
        final double accumulatedEstimate = 10d;
        final Release release = new Release(null, criteria);

        expect(productBacklog.getAccumulatedEstimate(criteria)).andReturn(accumulatedEstimate);
        replayAll();
        visitor.visit(release, productBacklog);
        verifyAll();
        assertEquals(accumulatedEstimate, release.getAccumulatedEstimate());
    }

    @Before
    public void setUp() {
        visitor = new AccumulateEffortForRelease();
    }
}
