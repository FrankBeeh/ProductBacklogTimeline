package de.frankbeeh.productbacklogtimeline.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ReleaseTableController {
    @FXML
    private TableView<ProductBacklogItem> releasesTable;
    @FXML
    private TableColumn<ProductBacklogItem, String> nameColumn;
    @FXML
    private TableColumn<ProductBacklogItem, Double> accumulatedEstimateColumn;

    private final ObservableList<ProductBacklogItem> model = FXCollections.<ProductBacklogItem> observableArrayList();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("title"));
        accumulatedEstimateColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, Double>("accumulatedEstimate"));
        releasesTable.setItems(model);
    }

    public void initModel(ProductBacklog productBacklog) {
        model.removeAll(model);
        model.addAll(productBacklog.getItems());
    }

    public Parent getView() {
        return releasesTable;
    }
}
