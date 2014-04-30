package de.frankbeeh.productbacklogtimeline.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import de.frankbeeh.productbacklogtimeline.data.Release;
import de.frankbeeh.productbacklogtimeline.data.Releases;

public class ReleaseTableController {
    @FXML
    private TableView<Release> releasesTable;
    @FXML
    private TableColumn<Release, String> nameColumn;
    @FXML
    private TableColumn<Release, String> criteriaColumn;
    @FXML
    private TableColumn<Release, Double> accumulatedEstimateColumn;

    private final ObservableList<Release> model = FXCollections.<Release> observableArrayList();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Release, String>("name"));
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<Release, String>("criteria"));
        accumulatedEstimateColumn.setCellValueFactory(new PropertyValueFactory<Release, Double>("accumulatedEstimate"));
        releasesTable.setItems(model);
    }

    public void initModel(Releases releases) {
        model.removeAll(model);
        model.addAll(releases.getReleases());
    }

    public Parent getView() {
        return releasesTable;
    }
}
