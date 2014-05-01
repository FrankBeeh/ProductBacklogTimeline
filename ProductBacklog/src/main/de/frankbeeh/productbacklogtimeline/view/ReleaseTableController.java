package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.data.Release;
import de.frankbeeh.productbacklogtimeline.data.Releases;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public class ReleaseTableController {
    private static final class CompletionForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<Release, String>, ObservableValue<String>> {
        private final String progressForecastName;

        public CompletionForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<Release, String> cellDataFeatures) {
            return new SimpleStringProperty(cellDataFeatures.getValue().getCompletionForecast(progressForecastName));
        }
    }

    @FXML
    private TableView<Release> releasesTable;
    @FXML
    private TableColumn<Release, String> nameColumn;
    @FXML
    private TableColumn<Release, String> criteriaColumn;
    @FXML
    private TableColumn<Release, Double> accumulatedEstimateColumn;
    @FXML
    private TableColumn<Release, String> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<Release, String> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<Release, String> completionForecastByMaxVelColumn;

    private final ObservableList<Release> model = FXCollections.<Release> observableArrayList();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Release, String>("name"));
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<Release, String>("criteria"));
        accumulatedEstimateColumn.setCellValueFactory(new PropertyValueFactory<Release, Double>("accumulatedEstimate"));
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(Sprints.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(Sprints.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(Sprints.MAXIMUM_VELOCITY_FORECAST));
        releasesTable.setItems(model);
    }

    public void initModel(Releases releases) {
        model.removeAll(model);
        model.addAll(releases.getReleases());
    }

    public Parent getView() {
        return releasesTable;
    }

    public void updateView() {
        releasesTable.getColumns().get(0).setVisible(false);
        releasesTable.getColumns().get(0).setVisible(true);
    }
}
