package de.frankbeeh.productbacklogtimeline.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimeline;
import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.ProductTimestampService;
import de.frankbeeh.productbacklogtimeline.service.importer.ProductBacklogFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.importer.VelocityForecastFromCsvImporter;

public class MainController {
    private static final File CSV_DIRECTORY = new File(System.getProperty("user.dir"));

    @FXML
    private VelocityForecastTableController velocityForecastTableController;
    @FXML
    private ProductBacklogTableController productBacklogTableController;
    @FXML
    private ReleaseBurndownController releaseBurndownController;
    @FXML
    private Tab releasesTab;
    @FXML
    private ComboBox<String> selectedProductTimestamp;
    @FXML
    private ComboBox<String> referencedProductTimestamp;

    private final ObjectProperty<ObservableList<String>> selectProductBacklogItems = new SimpleObjectProperty<>();
    private ReleaseTableController releaseTableController;

    private final ControllerFactory controllerFactory = new ControllerFactory();
    private Stage primaryStage;

    private final ProductTimeline productTimeline = new ProductTimeline();

    public void initController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadFromDataBase();
        setSelectableProductBacklogNames();
    }

    @FXML
    private void initialize() throws IOException {
        releaseTableController = controllerFactory.createReleaseTableController();
        releasesTab.setContent(this.releaseTableController.getView());
        releaseTableController.initModel(productTimeline.getReleases());
        selectedProductTimestamp.itemsProperty().bind(selectProductBacklogItems);
        referencedProductTimestamp.itemsProperty().bind(selectProductBacklogItems);
        selectProductBacklogItems.set(FXCollections.<String> observableArrayList());
    }

    @FXML
    private void importTimestamp(){
        
    }
    
    @FXML
    private void importProductBacklog() throws IOException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final ProductBacklogFromCsvImporter importer = new ProductBacklogFromCsvImporter();
            final ProductBacklog importData = importer.importData(new FileReader(selectedFile));
            final String name = selectedFile.getName();
            productTimeline.addProductBacklog(LocalDateTime.now(), name, importData);
            setSelectableProductBacklogNames();
            changeSelectedProductTimestamp(name);
            updateProductBacklogAndReleaseTable();
        }
    }

    @FXML
    private void importVelocityForecast() throws IOException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final VelocityForecastFromCsvImporter importer = new VelocityForecastFromCsvImporter();
            productTimeline.setVelocityForecastForSelectedProductTimestamp(importer.importData(new FileReader(selectedFile)));
            updateProductBacklogAndReleaseTable();
        }
    }

    @FXML
    private void selectProductTimestamp() {
        productTimeline.selectProductTimestamp(selectedProductTimestamp.selectionModelProperty().get().getSelectedItem());
        updateProductBacklogAndReleaseTable();
    }

    @FXML
    private void referenceProductTimestamp() {
        productTimeline.selectReferenceProductTimestamp(referencedProductTimestamp.selectionModelProperty().get().getSelectedItem());
        updateProductBacklogAndReleaseTable();
    }

    private void changeSelectedProductTimestamp(final String productBacklogName) {
        selectedProductTimestamp.selectionModelProperty().get().select(productBacklogName);
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
        selectProductBacklogItems.getValue().addAll(productTimeline.getProductTimestampNames());
    }

    private void updateProductBacklogAndReleaseTable() {
        productBacklogTableController.initModel(productTimeline.getProductBacklogComparison());
        productBacklogTableController.updateView();
        velocityForecastTableController.initModel(productTimeline.getSelectedVelocityForecast());
        releaseBurndownController.initModel(productTimeline.getSelectedProductTimestamp());
        releaseTableController.initModel(productTimeline.getReleases());
        releaseTableController.updateView();
    }
    
    private void loadFromDataBase() {
        final ProductTimestampService service = ServiceLocator.getService(ProductTimestampService.class);
        final List<LocalDateTime> allIds = service.getAllIds();
        for (LocalDateTime localDateTime : allIds) {
            productTimeline.addProductTimestamp(service.get(localDateTime));
        }
    }
}
