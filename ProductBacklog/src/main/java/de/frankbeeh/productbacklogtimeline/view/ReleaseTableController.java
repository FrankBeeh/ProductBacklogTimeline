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
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

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
    private TableColumn<Release, String> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<Release, String> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<Release, String> completionForecastByMaxVelColumn;

    private final ObservableList<Release> model = FXCollections.<Release> observableArrayList();

    @FXML
    private void initialize() {
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
        releasesTable.setItems(model);
    }

    public void initModel(ReleaseForecast releaseForecast) {
        model.removeAll(model);
        model.addAll(releaseForecast.getReleases());
    }

    public Parent getView() {
        return releasesTable;
    }

    public void updateView() {
        releasesTable.getColumns().get(0).setVisible(false);
        releasesTable.getColumns().get(0).setVisible(true);
    }
}
