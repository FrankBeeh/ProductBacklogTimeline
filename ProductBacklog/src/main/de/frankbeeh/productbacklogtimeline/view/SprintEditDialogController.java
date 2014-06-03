package de.frankbeeh.productbacklogtimeline.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class SprintEditDialogController extends AnchorPane {

    private SprintData result = null;

    // FIXME: Get rid of dependency to Dialog
    private SprintEditDialog sprintEditDialog;

    public SprintEditDialogController(SprintEditDialog sprintEditDialog) {
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

}
