package de.frankbeeh.productbacklogtimeline.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogTableController {
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

    public void initModel(ProductBacklog productBacklog) {
        createModel(productBacklog);
        this.productBacklogTable.setItems(model);
    }

    private void createModel(ProductBacklog productBacklog) {
        model = FXCollections.<ProductBacklogItem> observableArrayList();
        model.addAll(productBacklog.getItems());
    }
}
