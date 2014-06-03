package de.frankbeeh.productbacklogtimeline.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import de.frankbeeh.productbacklogtimeline.data.SprintData;

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
    private BasicDialog<SprintData> sprintEditDialog;

    private SprintData result = null;
    private SprintData sprintModel;

    public SprintEditDialogController(BasicDialog<SprintData> sprintEditDialog) {
        this.sprintEditDialog = sprintEditDialog;
    }

    @FXML
    private void save() {
        result = new SprintData("nam", null, null, 2.0, 2.0, 2.0, 2.0);
        closeDialog();
    }

    @FXML
    private void cancel() {
        closeDialog();
    }

    public SprintData getDialogResult() {
        return result;
    }

    private void closeDialog() {
        sprintEditDialog.close();
    }

    public void initModel(final SprintData selectedItem) {
        unbindUI();
        this.sprintModel = selectedItem;
        bindUI();
    }

    private void bindUI() {
        sprintName.textProperty().bind(sprintModel.nameProperty());
        // FIXME: Make conversion in bindings.
        // startDate.textProperty().bind(sprintModel.startDateProperty());
        // endDate.textProperty().bind(sprintModel.endDateProperty());
        // plannedCapacity.textProperty().bind(sprintModel.capacityForecastProperty());
        // plannedEffort.textProperty().bind(sprintModel.effortForecastProperty());
        // doneCapacity.textProperty().bind(sprintModel.capacityDoneProperty());
        // doneEffort.textProperty().bind(sprintModel.capacityForecastProperty());
    }

    private void unbindUI() {

    }

}
