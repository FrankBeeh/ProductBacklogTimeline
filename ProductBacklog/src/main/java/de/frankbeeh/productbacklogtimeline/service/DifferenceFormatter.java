package de.frankbeeh.productbacklogtimeline.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.google.common.base.Strings;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteria;

/**
 * Responsibility:
 * <ul>
 * <li>Formats the differences between two values for different types.
 * </ul>
 */
public class DifferenceFormatter {
    private static final DecimalFormat DIFFERENCE_DOUBLE_FORMAT = new DecimalFormat("+0.0;-0.0");
    private static final DecimalFormat DIFFERENCE_LONG_FORMAT = new DecimalFormat("+0;-0");

    public static String formatTextualDifference(String value, String referenceValue) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value.toString());
        if (referenceValue != null) {
            if (!value.equals(referenceValue)) {
                stringBuilder.append("\n(").append(referenceValue).append(")");
            }
        } else {
            stringBuilder.append("\n(NEW)");
        }
        return stringBuilder.toString();
    }

    public static String formatProductBacklogRankDifference(Integer rank, Integer referenceRank) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = rank.toString();
        stringBuilder.append(formattedEstimate);
        if (referenceRank != null) {
            final Integer difference = rank - referenceRank;
            if (difference != 0) {
                final String formattedDifference = "(" + DIFFERENCE_LONG_FORMAT.format((long) difference) + ")";
                rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
            }
        } else {
            rigthAllign(stringBuilder, formattedEstimate, "(NEW)");
        }
        return stringBuilder.toString();
    }

    public static String formatDoubleDifference(Double value, Double referenceValue) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = value.toString();
        stringBuilder.append(formattedEstimate);
        if (referenceValue != null) {
            final Double difference = value - referenceValue;
            if (difference != 0) {
                final String formattedDifference = "(" + DIFFERENCE_DOUBLE_FORMAT.format(difference) + ")";
                rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
            }
        } else {
            rigthAllign(stringBuilder, formattedEstimate, "(NEW)");
        }
        return stringBuilder.toString();
    }

    public static String formatSprintDifference(Sprint sprint, Sprint referenceSprint) {
        if (sprint == null) {
            return null;
        }
        if (referenceSprint == null) {
            referenceSprint = sprint;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatTextualDifference(sprint.getName(), referenceSprint.getName()));
        if (sprint.getEndDate() != null) {
            stringBuilder.append("\n");
        }
        stringBuilder.append(formatLocalDateDifference(sprint.getEndDate(), referenceSprint.getEndDate()));
        return stringBuilder.toString();
    }

    public static String formatLocalDateDifference(final LocalDate endDate, final LocalDate referenceEndDate) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (endDate != null) {
            stringBuilder.append(DateConverter.formatLocalDate(endDate));
            if (referenceEndDate != null) {
                final long diffDays = ChronoUnit.DAYS.between(referenceEndDate, endDate);
                if (diffDays != 0) {
                    stringBuilder.append("\n(").append(DIFFERENCE_LONG_FORMAT.format(diffDays)).append("d)");
                }
            } else {
                stringBuilder.append("\n(NEW)");
            }
        }
        return stringBuilder.toString();
    }

    public static String formatStateDifference(State state, State referenceState) {
        return formatTextualDifference(state == null ? null : state.toString(), referenceState == null ? null : referenceState.toString());
    }

    public static String formatReleaseCriteriaDifference(ReleaseCriteria releaseCriteria, ReleaseCriteria referenceReleaseCriteria) {
        return formatTextualDifference(releaseCriteria == null ? null : releaseCriteria.toString(), referenceReleaseCriteria == null ? null : referenceReleaseCriteria.toString());
    }

    private static void rigthAllign(final StringBuilder stringBuilder, final String formattedEstimate, final String formattedDifference) {
        int count = formattedDifference.length() - formattedEstimate.length() + 1;
        if (count > 0) {
            stringBuilder.insert(0, Strings.repeat(" ", count));
            stringBuilder.append("\n");
        } else {
            stringBuilder.append("\n").append(Strings.repeat(" ", 3 - count));
        }
        stringBuilder.append(formattedDifference);
    }

}
