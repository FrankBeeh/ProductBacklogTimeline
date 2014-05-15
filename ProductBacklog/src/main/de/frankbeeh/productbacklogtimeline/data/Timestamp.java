package de.frankbeeh.productbacklogtimeline.data;

import java.util.Date;

public class Timestamp {
    private final String name;
    private final Date date;
    private final String productBacklogName;
    private final String sprintsName;

    public Timestamp(String name, Date date, String productBacklogName, String sprintsName) {
        this.name = name;
        this.date = date;
        this.productBacklogName = productBacklogName;
        this.sprintsName = sprintsName;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getProductBacklogName() {
        return productBacklogName;
    }

    public String getSprintsName() {
        return sprintsName;
    }
}
