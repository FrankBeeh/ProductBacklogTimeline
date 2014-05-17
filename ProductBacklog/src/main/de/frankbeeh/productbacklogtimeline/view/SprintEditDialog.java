package de.frankbeeh.productbacklogtimeline.view;

import java.net.URL;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class SprintEditDialog extends BasicDialog<SprintData> {

    public SprintEditDialog() {
        super("Sprint bearbeiten");
    }

    @Override
    protected URL getFXMLResourceURL() {
        return SprintEditDialogController.class.getResource("editSprintDialog.fxml");
    }

}
