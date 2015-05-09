package de.frankbeeh.productbacklogtimeline.view.dialog;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.view.BasicDialog;
import de.frankbeeh.productbacklogtimeline.view.model.ImportProductTimestampViewModel;

public class ImportProductTimestampDialogController extends AnchorPane {
    private static final File CSV_DIRECTORY = new File(System.getProperty("user.dir"));

    @FXML
    private TextField name;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button chooseProductBacklog;
    @FXML
    private Button chooseVelocityForecast;
    @FXML
    private Button chooseReleases;
    @FXML
    private TextField productBacklogFile;
    @FXML
    private TextField velocityForecastFile;
    @FXML
    private TextField releasesFile;

    private BasicDialog<ImportProductTimestampViewModel> dialog;

    private ImportProductTimestampViewModel viewModel;

    private Stage primaryStage;

    public ImportProductTimestampDialogController(BasicDialog<ImportProductTimestampViewModel> dialog, Stage primaryStage) {
        this.dialog = dialog;
        this.primaryStage = primaryStage;
    }

    @FXML
    private void ok() {
        closeDialog();
    }

    @FXML
    private void chooseProductBacklogFile() {
        final File file = selectCsvFileForImport();
        viewModel.setProductBacklogFile(file);
        productBacklogFile.setText(file.getAbsolutePath());
    }

    @FXML
    private void chooseVelocityForecastFile() {
        final File file = selectCsvFileForImport();
        viewModel.setVelocityForecastFile(file);
        velocityForecastFile.setText(file.getAbsolutePath());
    }
    
    @FXML
    private void chooseReleasesFile() {
        final File file = selectCsvFileForImport();
        viewModel.setReleasesFile(file);
        releasesFile.setText(file.getAbsolutePath());
    }

    public ImportProductTimestampViewModel getDialogResult() {
        return viewModel;
    }

    private void closeDialog() {
        dialog.close();
    }

    public void initModel(final ImportProductTimestampViewModel viewModel) {
        unbindUI();
        this.viewModel = viewModel;
        bindUI();
    }

    private void unbindUI() {
        if (viewModel != null) {
            Bindings.unbindBidirectional(viewModel.nameProperty(), name.textProperty());
            Bindings.unbindBidirectional(viewModel.dateProperty(), datePicker.valueProperty());
        }
    }

    private void bindUI() {
        Bindings.bindBidirectional(viewModel.nameProperty(), name.textProperty());
        Bindings.bindBidirectional(viewModel.dateProperty(), datePicker.valueProperty());
    }

    private File selectCsvFileForImport() {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(CSV_DIRECTORY);
        return fileChooser.showOpenDialog(primaryStage);
    }
}
