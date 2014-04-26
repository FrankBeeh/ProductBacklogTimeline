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
import de.frankbeeh.productbacklogtimeline.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.ProductBacklogFromCsvReader;
import de.frankbeeh.productbacklogtimeline.ProductBacklogItem;

public class ProductBacklogTimelineMainController {
    private static final String PBL_CSV_FILE = "PBL3.csv";

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

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("id"));
        estimateColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("estimate"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("description"));
    }

    @FXML
    public void importProductBacklog() throws IOException, ParseException, FileNotFoundException {
        final ProductBacklogFromCsvReader productBacklogFromCsvReader = new ProductBacklogFromCsvReader();
        final ProductBacklog productBacklog = productBacklogFromCsvReader.readProductBacklog(new FileReader(new File(PBL_CSV_FILE)));
        initModel(productBacklog);
    }

    public void initModel(ProductBacklog productBacklog) {
        model = FXCollections.<ProductBacklogItem> observableArrayList();
        model.addAll(productBacklog.getItems());
        this.productBacklogTable.setItems(model);
    }

}
