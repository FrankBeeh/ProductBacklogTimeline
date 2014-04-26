package de.frankbeeh.productbacklogtimeline.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.ProductBacklogFromCsvImporter;
import de.frankbeeh.productbacklogtimeline.service.SprintsFromCsvImporter;

public class ProductBacklogTimelineMainController {
    private static final File CSV_DIRECTORY = new File(System.getProperty("user.dir"));
    @FXML
    private TableView<ProductBacklogItem> productBacklogTable;
    @FXML
    private TableColumn<ProductBacklogItem, String> idColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> titleColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> descriptionColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> estimateColumn;

    private ObservableList<ProductBacklogItem> model;
    private Stage primaryStage;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("id"));
        estimateColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("estimate"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("description"));
    }

    @FXML
    private void importProductBacklog() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final ProductBacklogFromCsvImporter importer = new ProductBacklogFromCsvImporter();
            final ProductBacklog productBacklog = importer.importData(new FileReader(selectedFile));
            initModel(productBacklog);
        }
    }

    @FXML
    private void importSprints() throws IOException, ParseException, FileNotFoundException {
        final File selectedFile = selectCsvFileForImport();
        if (selectedFile != null) {
            final SprintsFromCsvImporter importer = new SprintsFromCsvImporter();
            final Sprints sprints = importer.importData(new FileReader(selectedFile));
        }
    }

    public void initController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initModel(ProductBacklog productBacklog) {
        createModel(productBacklog);
        this.productBacklogTable.setItems(model);
    }

    private void createModel(ProductBacklog productBacklog) {
        model = FXCollections.<ProductBacklogItem> observableArrayList();
        model.addAll(productBacklog.getItems());
    }

    private File selectCsvFileForImport() {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(CSV_DIRECTORY);
        return fileChooser.showOpenDialog(primaryStage);
    }
}
