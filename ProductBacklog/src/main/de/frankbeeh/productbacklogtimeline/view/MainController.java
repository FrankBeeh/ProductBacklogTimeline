package de.frankbeeh.productbacklogtimeline.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.data.ProductTimeline;
import de.frankbeeh.productbacklogtimeline.service.importer.ProductBacklogFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.importer.SprintsFromCsvImporter;

public class MainController {
    private static final File CSV_DIRECTORY = new File(System.getProperty("user.dir"));

    @FXML
    private SprintsTableController sprintsTableController;
    @FXML
    private ProductBacklogTableController productBacklogTableController;
    @FXML
    private Tab releasesTab;
    private ReleaseTableController releaseTableController;

    private final ControllerFactory controllerFactory = new ControllerFactory();
    private Stage primaryStage;

    private final ProductTimeline productTimeline = new ProductTimeline();

    public void initController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() throws IOException {
        releaseTableController = controllerFactory.createReleaseTableController();
        releasesTab.setContent(this.releaseTableController.getView());
        releaseTableController.initModel(productTimeline.getReleases());
    }

    @FXML
    private void importProductBacklog() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final ProductBacklogFromCsvImporter importer = new ProductBacklogFromCsvImporter();
            productTimeline.addProductBacklog(importer.importData(new FileReader(selectedFile)));
            productBacklogTableController.initModel(productTimeline.getSelectedProductBacklog());
            releaseTableController.updateView();
        }
    }

    @FXML
    private void importSprints() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final SprintsFromCsvImporter importer = new SprintsFromCsvImporter();
            productTimeline.setSprints(importer.importData(new FileReader(selectedFile)));
            sprintsTableController.initModel(productTimeline.getSprints());
            productBacklogTableController.initModel(productTimeline.getSelectedProductBacklog());
            productBacklogTableController.updateView();
            releaseTableController.updateView();
        }
    }

    private File selectCsvFileForImport() {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(CSV_DIRECTORY);
        return fileChooser.showOpenDialog(primaryStage);
    }

}
