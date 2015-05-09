package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;

public class ReleaseTest {

    @Test
    public void hashReleaseName() {
        assertEquals(new Release("Release 1", null).getHash(), new Release("Release 1", null).getHash());
        assertNotEquals(new Release("Release 1", null).getHash(), new Release("Release 2", null).getHash());
    }

    @Test
    public void hashCriteria() {
        assertEquals(new Release(null, new PlannedReleaseIsEqual("Release 1")).getHash(), new Release(null, new PlannedReleaseIsEqual("Release 1")).getHash());
        assertNotEquals(new Release(null, new PlannedReleaseIsEqual("Release 1")).getHash(), new Release(null, new PlannedReleaseIsEqual("Release 2")).getHash());
    }
}
