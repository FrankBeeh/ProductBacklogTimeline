package de.frankbeeh.productbacklogtimeline.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeForecastForRelease;
import de.frankbeeh.productbacklogtimeline.service.visitor.ReleaseVisitor;

public class Releases {

    private final List<Release> releases;
    private final List<ReleaseVisitor> visitors;

    public Releases() {
        this(new ArrayList<Release>(), Arrays.asList((ReleaseVisitor) new ComputeForecastForRelease()));
    }

    public void addRelease(Release release) {
        releases.add(release);
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void updateAll(ProductBacklogComparison productBacklogComparison) {
        for (final ReleaseVisitor visitor : visitors) {
            visitor.reset();
            for (final Release release : releases) {
                visitor.visit(release, productBacklogComparison);
            }
        }
    }

    @VisibleForTesting
    public Releases(List<Release> releases, List<ReleaseVisitor> visitorMocks) {
        this.releases = releases;
        this.visitors = visitorMocks;
    }
}
