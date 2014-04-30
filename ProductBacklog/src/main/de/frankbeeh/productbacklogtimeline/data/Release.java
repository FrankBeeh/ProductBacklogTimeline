package de.frankbeeh.productbacklogtimeline.data;

public class Release {
    private final String name;
    private final String criteria;

    public Release(String name, String criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    public String getName() {
        return name;
    }

    public String getCriteria() {
        return criteria;
    }
}
