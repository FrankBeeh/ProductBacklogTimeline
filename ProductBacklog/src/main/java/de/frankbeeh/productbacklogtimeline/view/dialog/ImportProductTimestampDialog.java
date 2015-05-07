package de.frankbeeh.productbacklogtimeline.view.dialog;

import java.net.URL;

import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.view.BasicDialog;
import de.frankbeeh.productbacklogtimeline.view.model.ImportProductTimestampViewModel;

public class ImportProductTimestampDialog extends BasicDialog<ImportProductTimestampViewModel> {

    private ImportProductTimestampDialogController dialogController;
    private final Stage primaryStage;

    public ImportProductTimestampDialog(Stage primaryStage) {
        super("Import Product Timestamp");
        this.primaryStage = primaryStage;
    }

    @Override
    protected URL getFXMLResourceURL() {
        return ImportProductTimestampDialogController.class.getResource("importProductTimestampDialog.fxml");
    }

    @Override
    protected ImportProductTimestampViewModel createResult() {
        return dialogController.getDialogResult();
    }

    @Override
    protected Object getDialogController() {
        dialogController = new ImportProductTimestampDialogController(this, primaryStage);
        return dialogController;
    }

    @Override
    public void initModel(ImportProductTimestampViewModel viewModel) {
        dialogController.initModel(viewModel);
    }

}
