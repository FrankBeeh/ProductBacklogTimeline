package de.frankbeeh.productbacklogtimeline.view.dialog;

import java.net.URL;

import de.frankbeeh.productbacklogtimeline.view.BasicDialog;
import de.frankbeeh.productbacklogtimeline.view.model.SprintDataViewModel;

public class SprintEditDialog extends BasicDialog<SprintDataViewModel> {

    private SprintEditDialogController sprintEditDialogController;

    public SprintEditDialog() {
        super("Sprint bearbeiten");
    }

    @Override
    protected URL getFXMLResourceURL() {
        return SprintEditDialogController.class.getResource("editSprintDialog.fxml");
    }

    @Override
    protected SprintDataViewModel createResult() {
        return sprintEditDialogController.getDialogResult();
    }

    @Override
    protected Object getDialogController() {
        sprintEditDialogController = new SprintEditDialogController(this);
        return sprintEditDialogController;
    }

    @Override
    public void initModel(SprintDataViewModel selectedItem) {
        sprintEditDialogController.initModel(selectedItem);
    }

}
