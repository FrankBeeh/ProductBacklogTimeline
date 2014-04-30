package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEffortForRelease;
import de.frankbeeh.productbacklogtimeline.service.visitor.ReleaseVisitor;

public class Releases {

    private final List<Release> releases = new ArrayList<Release>();
    private final List<ReleaseVisitor> visitors;

    public Releases() {
        this(Arrays.asList((ReleaseVisitor) new AccumulateEffortForRelease()));
    }

    public void addRelease(Release release) {
        releases.add(release);
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void updateAll(ProductBacklog productBacklog) {
        for (final ReleaseVisitor visitor : visitors) {
            visitor.reset();
            for (final Release release : releases) {
                visitor.visit(release, productBacklog);
            }
        }
    }

    // Visible for testing
    Releases(List<ReleaseVisitor> visitorMocks) {
        this.visitors = visitorMocks;
    }
}
