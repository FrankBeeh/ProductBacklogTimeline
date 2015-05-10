package de.frankbeeh.productbacklogtimeline.domain;

import static org.easymock.EasyMock.same;

import java.util.Arrays;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.frankbeeh.productbacklogtimeline.service.visitor.ReleaseVisitor;

@RunWith(EasyMockRunner.class)
public class ReleasesTest extends EasyMockSupport {
    @Mock
    private ReleaseVisitor visitorMock1;
    @Mock
    private ReleaseVisitor visitorMock2;

    private ReleaseForecast releaseForecast;

    @Test
    public void updateAll() {
        final ProductBacklog productBacklog = new ProductBacklog();
        final Release release1 = new Release("Release 1", null);
        final Release release2 = new Release("Release 2", null);
        releaseForecast = new ReleaseForecast(Arrays.asList(release1, release2), Arrays.asList(visitorMock1, visitorMock2));
        visitorMock1.reset();
        visitorMock2.reset();
        visitorMock1.visit(same(release1), same(productBacklog));
        visitorMock1.visit(same(release2), same(productBacklog));
        visitorMock2.visit(same(release1), same(productBacklog));
        visitorMock2.visit(same(release2), same(productBacklog));
        replayAll();
        releaseForecast.updateAllReleases(productBacklog);
        verifyAll();
    }
}
