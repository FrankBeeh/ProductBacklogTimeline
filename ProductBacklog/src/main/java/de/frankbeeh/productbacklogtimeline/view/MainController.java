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
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimeline;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.ProductTimestampService;
import de.frankbeeh.productbacklogtimeline.service.importer.ProductBacklogFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.importer.VelocityForecastFromCsvImporter;
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
    private void importProductTimestamp() throws FileNotFoundException, IOException{
        final BasicDialog<ImportProductTimestampViewModel> dialog = new ImportProductTimestampDialog(primaryStage);
        dialog.initModel(new ImportProductTimestampViewModel());
        final ImportProductTimestampViewModel result = dialog.openDialog();
        final ProductBacklog productBacklog = importProductBacklogFromCsv(result.getProductBacklogFile());
        final VelocityForecast velocityForecast = importVelocityForecastFromCsv(result.getVelocityForecastFile());
        final String name = result.getProductBacklogFile().getName();
        productTimeline.addProductTimestamp(new ProductTimestamp(LocalDateTime.now(), name, productBacklog, velocityForecast, null));
        setSelectableProductBacklogNames();
        changeSelectedProductTimestamp(name);
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

    private ProductBacklog importProductBacklogFromCsv(final File selectedFile) throws IOException, FileNotFoundException {
        return new ProductBacklogFromCsvImporter().importData(new FileReader(selectedFile));
    }

    private VelocityForecast importVelocityForecastFromCsv(final File selectedFile) throws IOException, FileNotFoundException {
        return new VelocityForecastFromCsvImporter().importData(new FileReader(selectedFile));
    }

    private void changeSelectedProductTimestamp(final String productBacklogName) {
        selectedProductTimestamp.selectionModelProperty().get().select(productBacklogName);
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
        releaseTableController.initModel(productTimeline.getSelectedReleases());
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
