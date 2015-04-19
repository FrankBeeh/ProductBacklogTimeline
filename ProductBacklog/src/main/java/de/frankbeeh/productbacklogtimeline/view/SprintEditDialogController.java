package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import de.frankbeeh.productbacklogtimeline.data.viewmodel.SprintDataViewModel;

public class SprintEditDialogController extends AnchorPane {

    @FXML
    private TextField sprintName;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextField plannedCapacity;
    @FXML
    private TextField plannedEffort;
    @FXML
    private TextField doneCapacity;
    @FXML
    private TextField doneEffort;

    // FIXME: Get rid of dependency to Dialog
    private BasicDialog<SprintDataViewModel> sprintEditDialog;

    private SprintDataViewModel sprintModel;

    public SprintEditDialogController(BasicDialog<SprintDataViewModel> sprintEditDialog) {
        this.sprintEditDialog = sprintEditDialog;
    }

    @FXML
    private void ok() {
        closeDialog();
    }

    public SprintDataViewModel getDialogResult() {
        return null;
    }

    private void closeDialog() {
        sprintEditDialog.close();
    }

    public void initModel(final SprintDataViewModel sprintDataViewModel) {
        unbindUI();
        this.sprintModel = sprintDataViewModel;
        bindUI();
    }

    private void unbindUI() {
        if (sprintModel != null) {
            sprintName.textProperty().unbindBidirectional(sprintModel.nameProperty());
            startDate.textProperty().unbindBidirectional(sprintModel.startDateProperty());
            endDate.textProperty().unbindBidirectional(sprintModel.endDateProperty());
            plannedCapacity.textProperty().unbindBidirectional(sprintModel.capacityForecastProperty());
            plannedEffort.textProperty().unbindBidirectional(sprintModel.effortForecastProperty());
            doneCapacity.textProperty().unbindBidirectional(sprintModel.capacityDoneProperty());
            doneEffort.textProperty().unbindBidirectional(sprintModel.effortDoneProperty());
        }
    }

    private void bindUI() {
        Bindings.bindBidirectional(sprintName.textProperty(), sprintModel.nameProperty());
        startDate.textProperty().bindBidirectional(sprintModel.startDateProperty());
        endDate.textProperty().bindBidirectional(sprintModel.endDateProperty());
        plannedCapacity.textProperty().bindBidirectional(sprintModel.capacityForecastProperty());
        plannedEffort.textProperty().bindBidirectional(sprintModel.effortForecastProperty());
        doneCapacity.textProperty().bindBidirectional(sprintModel.capacityDoneProperty());
        doneEffort.textProperty().bindBidirectional(sprintModel.capacityForecastProperty());
    }
}
