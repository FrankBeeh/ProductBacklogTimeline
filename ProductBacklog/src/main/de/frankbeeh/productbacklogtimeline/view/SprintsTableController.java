package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByMinimumVelocity;

public class SprintsTableController {
    private final class EfforForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>> {
        private final String historyForecastName;

        public EfforForecastPropertyValueFactory(String historyForecastName) {
            this.historyForecastName = historyForecastName;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<SprintData, String> cellDataFeatures) {
            return new SimpleStringProperty(cellDataFeatures.getValue().getEffortForecastBasedOnHistory(historyForecastName).toString());
        }
    }

    @FXML
    private TableView<SprintData> sprintsTable;
    @FXML
    private TableColumn<SprintData, String> nameColumn;
    @FXML
    private TableColumn<SprintData, String> startDateColumn;
    @FXML
    private TableColumn<SprintData, String> endDateColumn;
    @FXML
    private TableColumn<SprintData, String> capacityForecastColumn;
    @FXML
    private TableColumn<SprintData, String> effortForecastColumn;
    @FXML
    private TableColumn<SprintData, String> capacityDoneColumn;
    @FXML
    private TableColumn<SprintData, String> effortDoneColumn;
    @FXML
    private TableColumn<SprintData, String> forecastByAverageVelocityColumn;
    @FXML
    private TableColumn<SprintData, String> forecastByMinimumVelocityColumn;
    @FXML
    private TableColumn<SprintData, String> forecastByMaximumVelocityColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("endDate"));
        capacityForecastColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("capacityForecast"));
        effortForecastColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("effortForecast"));
        capacityDoneColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("capacityDone"));
        effortDoneColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("effortDone"));
        forecastByAverageVelocityColumn.setCellValueFactory(new EfforForecastPropertyValueFactory(ComputeEffortForecastByAverageVelocity.HISTORY_FORECAST_NAME));
        forecastByMinimumVelocityColumn.setCellValueFactory(new EfforForecastPropertyValueFactory(ComputeEffortForecastByMinimumVelocity.HISTORY_FORECAST_NAME));
        forecastByMaximumVelocityColumn.setCellValueFactory(new EfforForecastPropertyValueFactory(ComputeEffortForecastByMaximumVelocity.HISTORY_FORECAST_NAME));
    }

    public void initModel(Sprints sprints) {
        createModel(sprints);
        this.sprintsTable.setItems(model);
    }

    private void createModel(Sprints sprints) {
        model = FXCollections.<SprintData> observableArrayList();
        model.addAll(sprints.getSprints());
    }

}
