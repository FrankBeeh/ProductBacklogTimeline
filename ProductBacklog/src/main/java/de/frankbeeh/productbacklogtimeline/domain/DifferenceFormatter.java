package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.google.common.base.Strings;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class DifferenceFormatter {
    public static String formatTextualDifference(String value, String referenceValue) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value.toString());
        if (referenceValue != null && !value.equals(referenceValue)) {
            stringBuilder.append("\n(").append(referenceValue).append(")");
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
                final String formattedDifference = "(" + FormatUtility.formatDifferenceLong(difference) + ")";
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
                final String formattedDifference = "(" + FormatUtility.formatDifferenceDouble(difference) + ")";
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
        stringBuilder.append(formatDateDifference(sprint.getEndDate(), referenceSprint.getEndDate()));
        return stringBuilder.toString();
    }

    public static String formatDateDifference(final LocalDate endDate, final LocalDate referenceEndDate) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (endDate != null) {
            stringBuilder.append(FormatUtility.formatLocalDate(endDate));
            if (referenceEndDate != null) {
                final long diffDays = ChronoUnit.DAYS.between(referenceEndDate, endDate);
                if (diffDays != 0) {
                    stringBuilder.append("\n(").append(FormatUtility.formatDifferenceLong(diffDays)).append("d)");
                }
            }
        }
        return stringBuilder.toString();
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
