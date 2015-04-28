package de.frankbeeh.productbacklogtimeline.domain;

import static org.easymock.EasyMock.same;

import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.Releases;
import de.frankbeeh.productbacklogtimeline.service.visitor.ReleaseVisitor;

@RunWith(EasyMockRunner.class)
public class ReleasesTest extends EasyMockSupport {
    @Mock
    private ReleaseVisitor visitorMock1;
    @Mock
    private ReleaseVisitor visitorMock2;

    private Releases releases;

    @Test
    public void updateAll() {
        final ProductBacklogComparison productBacklogComparison = new ProductBacklogComparison();
        releases = new Releases(Arrays.asList(visitorMock1, visitorMock2));
        final Release release1 = new Release("Release 1", null);
        releases.addRelease(release1);
        final Release release2 = new Release("Release 2", null);
        releases.addRelease(release2);
        visitorMock1.reset();
        visitorMock2.reset();
        visitorMock1.visit(same(release1), same(productBacklogComparison));
        visitorMock1.visit(same(release2), same(productBacklogComparison));
        visitorMock2.visit(same(release1), same(productBacklogComparison));
        visitorMock2.visit(same(release2), same(productBacklogComparison));
        replayAll();
        releases.updateAll(productBacklogComparison);
        verifyAll();
    }
}
