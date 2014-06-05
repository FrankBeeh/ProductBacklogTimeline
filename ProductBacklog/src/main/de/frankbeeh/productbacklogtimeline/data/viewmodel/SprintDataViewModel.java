package de.frankbeeh.productbacklogtimeline.data.viewmodel;

import java.text.Format;
import java.text.SimpleDateFormat;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.service.DoubleConverterUtility;

public class SprintDataViewModel {

    private final ObjectProperty<SprintData> sprintData = new SimpleObjectProperty<SprintData>();

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty startDate = new SimpleStringProperty();
    private final StringProperty endDate = new SimpleStringProperty();
    private final StringProperty capacityForecast = new SimpleStringProperty();
    private final StringProperty effortForecast = new SimpleStringProperty();
    private final StringProperty capacityDone = new SimpleStringProperty();
    private final StringProperty effortDone = new SimpleStringProperty();

    final Format dateConverter = new SimpleDateFormat("dd.MM.yyyy");
    final Format doubleConverter = new DoubleConverterUtility();

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
            Bindings.bindBidirectional(startDate, sprintData.get().startDateProperty(), dateConverter);
            Bindings.bindBidirectional(endDate, sprintData.get().endDateProperty(), dateConverter);
            Bindings.bindBidirectional(capacityForecast, sprintData.get().capacityForecastProperty(), doubleConverter);
            Bindings.bindBidirectional(effortForecast, sprintData.get().effortForecastProperty(), doubleConverter);
            Bindings.bindBidirectional(capacityDone, sprintData.get().capacityDoneProperty(), doubleConverter);
            Bindings.bindBidirectional(effortDone, sprintData.get().effortDoneProperty(), doubleConverter);
        }
    }
}
