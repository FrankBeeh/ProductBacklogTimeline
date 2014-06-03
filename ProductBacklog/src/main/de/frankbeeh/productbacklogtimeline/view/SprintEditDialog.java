package de.frankbeeh.productbacklogtimeline.view;

import java.net.URL;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class SprintEditDialog extends BasicDialog<SprintData> {

    private SprintEditDialogController sprintEditDialogController;

    public SprintEditDialog() {
        super("Sprint bearbeiten");
    }

    @Override
    protected URL getFXMLResourceURL() {
        return SprintEditDialogController.class.getResource("editSprintDialog.fxml");
    }

    @Override
    protected SprintData createResult() {
        return sprintEditDialogController.getDialogResult();
    }

    @Override
    protected Object getDialogController() {
        sprintEditDialogController = new SprintEditDialogController(this);
        return sprintEditDialogController;
    }

    @Override
    public void setEntity(SprintData selectedItem) {
        sprintEditDialogController.initModel(selectedItem);
    }

}
