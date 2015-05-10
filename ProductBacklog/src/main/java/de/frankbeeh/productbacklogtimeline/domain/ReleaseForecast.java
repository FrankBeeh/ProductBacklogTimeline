package de.frankbeeh.productbacklogtimeline.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeForecastForRelease;
import de.frankbeeh.productbacklogtimeline.service.visitor.ReleaseVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Forecast the progress of {@link Release}s depending on the {@link VelocityForecast}.
 * </ul>
 */
public class ReleaseForecast {

    private final List<Release> releases;
    private final List<ReleaseVisitor> visitors;

    public ReleaseForecast() {
        this(new ArrayList<Release>(), Arrays.asList((ReleaseVisitor) new ComputeForecastForRelease()));
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

    @VisibleForTesting
    public ReleaseForecast(List<Release> releases, List<ReleaseVisitor> visitorMocks) {
        this.releases = releases;
        this.visitors = visitorMocks;
    }

    public Release getReleaseByName(String name) {
        for (Release release : releases) {
            if (name.equals(release.getName())) {
                return release;
            }
        }
        return null;
    }
}
