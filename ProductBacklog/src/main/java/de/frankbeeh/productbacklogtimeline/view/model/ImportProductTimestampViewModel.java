package de.frankbeeh.productbacklogtimeline.view.model;

import java.io.File;

public class ImportProductTimestampViewModel {
    private File productBacklogFile;
    private File velocityForecastFile;

    public void setProductBacklogFile(File productBacklogFile) {
        this.productBacklogFile = productBacklogFile;
    }
    
    public File getProductBacklogFile() {
        return productBacklogFile;
    }

    public void setVelocityForecastFile(File velocityForecastFile) {
        this.velocityForecastFile = velocityForecastFile;
    }
    
    public File getVelocityForecastFile() {
        return velocityForecastFile;
    }
}
