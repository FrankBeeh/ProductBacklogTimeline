package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatterTest;
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
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, RELEASE_NAME_1 + "\n(NEW)"),
                new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1), null).getComparedName());
    }

    @Test
    public void getComparedName_noReference() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, RELEASE_NAME_1), new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1)).getComparedName());
    }

    @Test
    public void getComparedName_valueNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null),
                new ReleaseComparison(newReleaseWithName(null), newReleaseWithName(RELEASE_NAME_2)).getComparedName());
    }

    @Test
    public void getComparedName_referenceValueNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, RELEASE_NAME_1 + "\n(NEW)"), new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1),
                newReleaseWithName(null)).getComparedName());
    }

    @Test
    public void getComparedName_equal() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, RELEASE_NAME_1), new ReleaseComparison(newReleaseWithName(RELEASE_NAME_1),
                newReleaseWithName(RELEASE_NAME_1)).getComparedName());
    }

    @Test
    public void getComparedName_notEqual() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Changed, RELEASE_NAME_1 + "\n(" + RELEASE_NAME_2 + ")"), new ReleaseComparison(
                newReleaseWithName(RELEASE_NAME_1), newReleaseWithName(RELEASE_NAME_2)).getComparedName());
    }

    @Test
    public void getComparedCriteria_referenceNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, CRITERIA_1 + "\n(NEW)"),
                new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1), null).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_noReference() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, CRITERIA_1.toString()),
                new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_valueNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null),
                new ReleaseComparison(newReleaseWithCriteria(null), newReleaseWithCriteria(CRITERIA_2)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_referenceValueNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, CRITERIA_1 + "\n(NEW)"), new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1),
                newReleaseWithCriteria(null)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_equal() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, CRITERIA_1.toString()), new ReleaseComparison(newReleaseWithCriteria(CRITERIA_1),
                newReleaseWithCriteria(CRITERIA_1)).getComparedCriteria());
    }

    @Test
    public void getComparedCriteria_notEqual() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Changed, CRITERIA_1.toString() + "\n(" + CRITERIA_2.toString() + ")"), new ReleaseComparison(
                newReleaseWithCriteria(CRITERIA_1), newReleaseWithCriteria(CRITERIA_2)).getComparedCriteria());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "   2.0\n(NEW)"),
                new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d), null).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_noReference() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2.0"),
                new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_valueNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null), new ReleaseComparison(newReleaseWithAccumulatedEstimate(null),
                newReleaseWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_referenceValueNull() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "   2.0\n(NEW)"), new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d),
                newReleaseWithAccumulatedEstimate(null)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_equal() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2.0"), new ReleaseComparison(newReleaseWithAccumulatedEstimate(2d),
                newReleaseWithAccumulatedEstimate(2d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedAccumulatedEstimate_notEqual() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Earlier, "    1.0\n(-9.0)"), new ReleaseComparison(newReleaseWithAccumulatedEstimate(1d),
                newReleaseWithAccumulatedEstimate(10d)).getComparedAccumulatedEstimate());
    }

    @Test
    public void getComparedCompletionForecast_noReference() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "Sprint 1\n01.01.2001"),
                new ReleaseComparison(newReleaseWithCompletionForecast("Sprint 1", LocalDate.of(2001, Month.JANUARY, 1))).getComparedCompletionForecast(PROGRESS_FORECAST_NAME));
    }

    @Test
    public void getComparedCompletionForecast_notEqual() {
        DifferenceFormatterTest.assertComparedValueEquals(
                new ComparedValue(ProductBacklogDirection.Earlier, "Sprint 1\n(Sprint 2)\n01.01.2001\n(-397d)"),
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
