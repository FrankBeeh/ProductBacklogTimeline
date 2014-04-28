package de.frankbeeh.productbacklogtimeline.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.importer.ProductBacklogFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.importer.SprintsFromCsvImporter;

public class MainController {
    private static final File CSV_DIRECTORY = new File(System.getProperty("user.dir"));

    @FXML
    private SprintsTableController sprintsTableController;
    @FXML
    private ProductBacklogTableController productBacklogTableController;

    private Stage primaryStage;
    private Sprints sprints = new Sprints();
    private ProductBacklog productBacklog = new ProductBacklog();

    @FXML
    private void importProductBacklog() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final ProductBacklogFromCsvImporter importer = new ProductBacklogFromCsvImporter();
            productBacklog = importer.importData(new FileReader(selectedFile));
            productBacklog.visitAllItems(sprints);
            productBacklogTableController.initModel(productBacklog);
        }
    }

    @FXML
    private void importSprints() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final SprintsFromCsvImporter importer = new SprintsFromCsvImporter();
            sprints = importer.importData(new FileReader(selectedFile));
            sprints.visitAllSprints();
            sprintsTableController.initModel(sprints);
            productBacklog.visitAllItems(sprints);
            productBacklogTableController.updateView();
        }
    }

    public void initController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private File selectCsvFileForImport() {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(CSV_DIRECTORY);
        return fileChooser.showOpenDialog(primaryStage);
    }
}
