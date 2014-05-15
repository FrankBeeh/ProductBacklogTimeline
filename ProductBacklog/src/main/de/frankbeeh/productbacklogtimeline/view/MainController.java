package de.frankbeeh.productbacklogtimeline.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
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
    @FXML
    private Tab timelineTab;
    @FXML
    private ComboBox<String> selectedProductBacklog;
    @FXML
    private ComboBox<String> selectedReferenceProductBacklog;

    private final ObjectProperty<ObservableList<String>> selectProductBacklogItems = new SimpleObjectProperty<>();
    private ReleaseTableController releaseTableController;

    private final ControllerFactory controllerFactory = new ControllerFactory();
    private Stage primaryStage;

    private final ProductTimeline productTimeline = new ProductTimeline();

    private TimelineTableController timelineTableController;

    public void initController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() throws IOException {
        initReleaseTab();
        initTimelineTab();

        selectedProductBacklog.itemsProperty().bind(selectProductBacklogItems);
        selectedReferenceProductBacklog.itemsProperty().bind(selectProductBacklogItems);
        selectProductBacklogItems.set(FXCollections.<String> observableArrayList());
    }

    @FXML
    private void importProductBacklog() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final ProductBacklogFromCsvImporter importer = new ProductBacklogFromCsvImporter();
            final ProductBacklog importData = importer.importData(new FileReader(selectedFile));
            final String name = selectedFile.getName();
            productTimeline.addProductBacklog(name, importData);
            setSelectableProductBacklogNames();
            changeSelectedProductBacklog(name);
            updateProductBacklogAndReleaseTable();
        }
    }

    @FXML
    private void importSprints() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final SprintsFromCsvImporter importer = new SprintsFromCsvImporter();
            productTimeline.setSprints(importer.importData(new FileReader(selectedFile)));
            sprintsTableController.initModel(productTimeline.getSprints());
            updateProductBacklogAndReleaseTable();
        }
    }

    @FXML
    private void selectProductBacklog() {
        productTimeline.selectProductBacklog(selectedProductBacklog.selectionModelProperty().get().getSelectedItem());
        productBacklogTableController.initModel(productTimeline.getSelectedProductBacklog());
        productBacklogTableController.updateView();
        releaseTableController.initModel(productTimeline.getReleases());
        releaseTableController.updateView();
    }

    @FXML
    private void selectReferenceProductBacklog() {
        productTimeline.selectReferenceProductBacklog(selectedReferenceProductBacklog.selectionModelProperty().get().getSelectedItem());
        updateProductBacklogAndReleaseTable();
    }

    private void changeSelectedProductBacklog(final String productBacklogName) {
        selectedProductBacklog.selectionModelProperty().get().select(productBacklogName);
    }

    private File selectCsvFileForImport() {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(CSV_DIRECTORY);
        return fileChooser.showOpenDialog(primaryStage);
    }

    private void setSelectableProductBacklogNames() {
        selectProductBacklogItems.getValue().clear();
        selectProductBacklogItems.getValue().addAll(productTimeline.getProductBacklogNames());
    }

    private void updateProductBacklogAndReleaseTable() {
        productBacklogTableController.initModel(productTimeline.getSelectedProductBacklog());
        productBacklogTableController.updateView();
        releaseTableController.initModel(productTimeline.getReleases());
        releaseTableController.updateView();
    }

    private void initTimelineTab() throws IOException {
        timelineTableController = controllerFactory.createTimelineTableController();
        timelineTab.setContent(this.timelineTableController.getView());
        timelineTableController.initModel(productTimeline.getTimestamps());
    }

    private void initReleaseTab() throws IOException {
        releaseTableController = controllerFactory.createReleaseTableController();
        releasesTab.setContent(this.releaseTableController.getView());
        releaseTableController.initModel(productTimeline.getReleases());
    }
}
