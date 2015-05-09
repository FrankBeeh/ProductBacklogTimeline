package de.frankbeeh.productbacklogtimeline.service.criteria;

public class ReleaseCriteriaFactory {
    public static ReleaseCriteria getReleaseCriteria(String string) {
        final String[] split = string.split("=");
        final String value = split.length > 1 ? split[1].trim() : null;
        switch (split[0]) {
            case "plannedRelease":
                return new PlannedReleaseIsEqual(value);
            case "idOfPBI":
                return new ProductBacklogItemIdIsEqual(value);
            default:
                throw new IllegalArgumentException("Unknown criteria '" + string + "'!");
        }
    }
}
