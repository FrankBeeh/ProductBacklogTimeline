package de.frankbeeh.productbacklogtimeline.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimeline;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.domain.Releases;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
import de.frankbeeh.productbacklogtimeline.service.importer.ProductBacklogFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.importer.ReleasesFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.importer.VelocityForecastFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.view.dialog.ImportProductTimestampDialog;
import de.frankbeeh.productbacklogtimeline.view.model.ImportProductTimestampViewModel;

public class MainController {
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
        releaseTableController.initModel(productTimeline.getSelectedReleases());
        selectedProductTimestamp.itemsProperty().bind(selectProductBacklogItems);
        referencedProductTimestamp.itemsProperty().bind(selectProductBacklogItems);
        selectProductBacklogItems.set(FXCollections.<String> observableArrayList());
    }

    @FXML
    private void importProductTimestamp() throws FileNotFoundException, IOException {
        final BasicDialog<ImportProductTimestampViewModel> dialog = new ImportProductTimestampDialog(primaryStage);
        dialog.initModel(new ImportProductTimestampViewModel());
        final ImportProductTimestampViewModel result = dialog.openDialog();
        final ProductBacklog productBacklog = importProductBacklogFromCsv(result.getProductBacklogFile());
        final VelocityForecast velocityForecast = importVelocityForecastFromCsv(result.getVelocityForecastFile());
        final Releases releases = importReleaseFromCsv(result.getReleasesFile());
        final String name = result.getName();
        final LocalDateTime dateTime = result.getDateTime();
        productTimeline.addProductTimestamp(new ProductTimestamp(dateTime, name, productBacklog, velocityForecast, releases));
        setSelectableProductBacklogNames();
        changeSelectedProductTimestamp(dateTime, name);
        updateProductBacklogAndReleaseTable();
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

    private ProductBacklog importProductBacklogFromCsv(final File file) throws IOException, FileNotFoundException {
        return new ProductBacklogFromCsvImporter().importData(new FileReader(file));
    }

    private VelocityForecast importVelocityForecastFromCsv(final File file) throws IOException, FileNotFoundException {
        return new VelocityForecastFromCsvImporter().importData(new FileReader(file));
    }
    
    private Releases importReleaseFromCsv(File file) throws FileNotFoundException, IOException {
        return new ReleasesFromCsvImporter().importData(new FileReader(file));
    }

    private void changeSelectedProductTimestamp(LocalDateTime dateTime, final String productBacklogName) {
        selectedProductTimestamp.selectionModelProperty().get().select(FormatUtility.formatLocalDateTime(dateTime) + " - " + productBacklogName);
    }

    private void setSelectableProductBacklogNames() {
        selectProductBacklogItems.getValue().clear();
        selectProductBacklogItems.getValue().addAll(productTimeline.getProductTimestampFullNames());
    }

    private void updateProductBacklogAndReleaseTable() {
        productBacklogTableController.initModel(productTimeline.getProductBacklogComparison());
        productBacklogTableController.updateView();
        velocityForecastTableController.initModel(productTimeline.getSelectedVelocityForecast());
        releaseBurndownController.initModel(productTimeline.getSelectedProductTimestamp());
        releaseTableController.initModel(productTimeline.getSelectedReleases());
        releaseTableController.updateView();
    }

    private void loadFromDataBase() {
        productTimeline.loadFromDataBase();
    }
}
