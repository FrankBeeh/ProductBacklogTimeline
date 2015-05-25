package de.frankbeeh.productbacklogtimeline.view;

import java.util.SortedSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampData;

public class TimelineTableController {
    @FXML
    private TableView<ProductTimestampData> timelineTable;
    private final ObservableList<ProductTimestampData> model = FXCollections.<ProductTimestampData> observableArrayList();
    private MainController mainController;

    @FXML
    private void initialize() {
        timelineTable.setItems(model);
    }

    public void initModel(SortedSet<ProductTimestampData> productTimestampData) {
        model.removeAll(model);
        model.addAll(productTimestampData);
    }

    @FXML
    private void deleteProductTimestamp() {
        mainController.deleteProductTimestamp(timelineTable.getSelectionModel().getSelectedItem().getDateTime());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
