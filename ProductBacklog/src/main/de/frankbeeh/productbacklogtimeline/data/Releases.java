package de.frankbeeh.productbacklogtimeline.data;

import java.util.ArrayList;
import java.util.List;

public class Releases {

    private final List<Release> releases = new ArrayList<Release>();

    public void addRelease(Release release) {
        releases.add(release);
    }

    public List<Release> getReleases() {
        return releases;
    }
}
