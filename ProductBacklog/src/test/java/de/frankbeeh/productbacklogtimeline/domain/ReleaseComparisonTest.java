package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;
import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

public class ReleaseComparisonTest {
    private static final String PROGRESS_FORECAST_NAME = "Avg. Vel.";
    private static final String RELEASE_NAME_1 = "Release 1";
    private static final String RELEASE_NAME_2 = "Release 2";
    private static final ReleaseCriteria CRITERIA_1 = new PlannedReleaseIsEqual(RELEASE_NAME_1);
    private static final ReleaseCriteria CRITERIA_2 = new ProductBacklogItemIdIsEqual("ID 1");

    @Test
    public void getComparedName_referenceNull() {
        assertEquals(RELEASE_NAME_1 + "\n(NEW)", new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1), null).getComparedName());
    }

    @Test
    public void getComparedName_noReference() {
        assertEquals(RELEASE_NAME_1, new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1)).getComparedName());
    }

    @Test
    public void getComparedName_valueNull() {
        assertNull(new ReleaseComparison(newReleaseWithName(null), newReleaseWithName(RELEASE_NAME_2)).getComparedName());
    }

    @Test
    public void getComparedName_referenceValueNull() {
        assertEquals(RELEASE_NAME_1 + "\n(NEW)", new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1), newReleaseWithName(null)).getComparedName());
    }

    @Test
    public void getComparedName_equal() {
        assertEquals(RELEASE_NAME_1, new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1), newReleaseWithName(RELEASE_NAME_1)).getComparedName());
    }

    @Test
    public void getComparedName_notEqual() {
        assertEquals(RELEASE_NAME_1 + "\n(" + RELEASE_NAME_2 + ")", new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1), newReleaseWithName(RELEASE_NAME_2)).getComparedName());
    }

    @Test
    public void getComparedCriteria_referenceNull() {
        assertEquals(CRITERIA_1 + "\n(NEW)", new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1), null).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_noReference() {
        assertEquals(CRITERIA_1.toString(), new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_valueNull() {
        assertNull(new ReleaseComparison(newReleaseWithCriteria(null), newReleaseWithCriteria(CRITERIA_2)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_referenceValueNull() {
        assertEquals(CRITERIA_1 + "\n(NEW)", new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1), newReleaseWithCriteria(null)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_equal() {
        assertEquals(CRITERIA_1.toString(), new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1), newReleaseWithCriteria(CRITERIA_1)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_notEqual() {
        assertEquals(CRITERIA_1.toString() + "\n(" + CRITERIA_2.toString() + ")", new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1), newReleaseWithCriteria(CRITERIA_2)).getComparedCriteria());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceNull() {
        assertEquals("   2.0\n(NEW)", new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d), null).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_noReference() {
        assertEquals("2.0", new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_valueNull() {
        assertNull(new ReleaseComparison(newReleaseWithAccumulatedEstimate(null), newReleaseWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceValueNull() {
        assertEquals("   2.0\n(NEW)", new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d), newReleaseWithAccumulatedEstimate(null)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_equal() {
        assertEquals("2.0", new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d), newReleaseWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_notEqual() {
        assertEquals("    1.0\n(-9.0)", new ReleaseComparison(newReleaseWithAccumulatedEstimate(1d), newReleaseWithAccumulatedEstimate(10d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedCompletionForecast_noReference() {
        assertEquals("Sprint 1\n01.01.2001",
                new ReleaseComparison(newReleaseWithCompletionForecast("Sprint 1", LocalDate.of(2001, Month.JANUARY, 1))).getComparedCompletionForecast(PROGRESS_FORECAST_NAME));
    }

    @Test
    public void getComparedCompletionForecast_notEqual() {
        assertEquals(
                "Sprint 1\n(Sprint 2)\n01.01.2001\n(-397d)",
                new ReleaseComparison(newReleaseWithCompletionForecast("Sprint 1", LocalDate.of(2001, Month.JANUARY, 1)), newReleaseWithCompletionForecast("Sprint 2",
                        LocalDate.of(2002, Month.FEBRUARY, 2))).getComparedCompletionForecast(PROGRESS_FORECAST_NAME));
    }

    private Release newReleaseWithCompletionForecast(String name, LocalDate endDate) {
        final Sprint sprint = newSprint(name, endDate);
        final Release release = new Release(null, null);
        release.setCompletionForecast(PROGRESS_FORECAST_NAME, sprint);
        return release;
    }

    private Sprint newSprint(String name, LocalDate endDate) {
        return new Sprint(name, null, endDate, null, null, null, null);
    }

    private Release newReleaseWithAccumulatedEstimate(Double accumulatedEstimate) {
        final Release release = new Release(null, null);
        release.setAccumulatedEstimate(accumulatedEstimate);
        return release;
    }

    private Release newReleaseWithName(String name) {
        return new Release(name, null);
    }

    private Release newReleaseWithCriteria(ReleaseCriteria criteria) {
        return new Release(null, criteria);
    }
}
