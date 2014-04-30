package de.frankbeeh.productbacklogtimeline.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ReleaseTableController {

    @FXML
    private TableColumn<ProductBacklogItem, String> nameColumn;

    private final ObservableList<ProductBacklogItem> model = FXCollections.<ProductBacklogItem> observableArrayList();

    public void initController(Stage primaryStage) throws IOException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("summary"));
    }

    public void initModel(ProductBacklog productBacklog) {
        model.removeAll(model);
        model.addAll(productBacklog.getItems());
    }

}
