package de.frankbeeh.productbacklogtimeline.view.model;

import java.text.Format;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import de.frankbeeh.productbacklogtimeline.domain.SprintData;
import de.frankbeeh.productbacklogtimeline.service.DoubleFormat;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
import de.frankbeeh.productbacklogtimeline.service.LocalDateFormat;

public class SprintDataViewModel {

    private final ObjectProperty<SprintData> sprintData = new SimpleObjectProperty<SprintData>();

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty startDate = new SimpleStringProperty();
    private final StringProperty endDate = new SimpleStringProperty();
    private final StringProperty capacityForecast = new SimpleStringProperty();
    private final StringProperty effortForecast = new SimpleStringProperty();
    private final StringProperty capacityDone = new SimpleStringProperty();
    private final StringProperty effortDone = new SimpleStringProperty();

    private final Format doubleFormat = new DoubleFormat();
    private final Format localDateFormat = new LocalDateFormat();

    public SprintDataViewModel(SprintData sprintData) {
        this.sprintData.set(sprintData);
        clear();
        bindModel();
    }

    public ObjectProperty<SprintData> entityProperty() {
        return sprintData;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public StringProperty capacityForecastProperty() {
        return capacityForecast;
    }

    public StringProperty effortForecastProperty() {
        return effortForecast;
    }

    public StringProperty capacityDoneProperty() {
        return capacityDone;
    }

    public StringProperty effortDoneProperty() {
        return effortDone;
    }

    private void clear() {
        name.set("");
        startDate.set("");
        endDate.set("");
        capacityForecast.set("");
        effortForecast.set("");
        capacityDone.set("");
        effortDone.set("");
    }

    private void bindModel() {
        if (sprintData.get() != null) {
            Bindings.bindBidirectional(name, sprintData.get().nameProperty());
            Bindings.bindBidirectional(startDate, sprintData.get().startDateProperty(), localDateFormat);
            Bindings.bindBidirectional(endDate, sprintData.get().endDateProperty(), localDateFormat);
            Bindings.bindBidirectional(capacityForecast, sprintData.get().capacityForecastProperty(), doubleFormat);
            Bindings.bindBidirectional(effortForecast, sprintData.get().effortForecastProperty(), doubleFormat);
            Bindings.bindBidirectional(capacityDone, sprintData.get().capacityDoneProperty(), doubleFormat);
            Bindings.bindBidirectional(effortDone, sprintData.get().effortDoneProperty(), doubleFormat);
        }
    }
}
