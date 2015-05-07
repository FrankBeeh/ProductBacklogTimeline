package de.frankbeeh.productbacklogtimeline.view.model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImportProductTimestampViewModel {
    private File productBacklogFile;
    private File velocityForecastFile;
    private final StringProperty nameProperty = new SimpleStringProperty();
    private final ReadOnlyObjectWrapper<LocalDate> dateProperty = new ReadOnlyObjectWrapper<LocalDate>(LocalDate.now());

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

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public String getName() {
        return nameProperty.get();
    }

    public ReadOnlyObjectWrapper<LocalDate> dateProperty() {
        return dateProperty;
    }
    
    public LocalDateTime getDateTime() {
        return dateProperty.get().atStartOfDay();
    }
}
