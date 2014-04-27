package de.frankbeeh.productbacklogtimeline.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMinimumVelocity;

public class SprintsTableController {
    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.0");

    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>> {
        private final String historyForecastName;

        public ProgressForecastPropertyValueFactory(String historyForecastName) {
            this.historyForecastName = historyForecastName;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<SprintData, String> cellDataFeatures) {
            final Double effortForecastBasedOnHistory = cellDataFeatures.getValue().getEffortForecastBasedOnHistory(historyForecastName);
            return new SimpleStringProperty(NUMBER_FORMAT.format(effortForecastBasedOnHistory.doubleValue()));
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
    private TableColumn<SprintData, Double> capacityForecastColumn;
    @FXML
    private TableColumn<SprintData, Double> effortForecastColumn;
    @FXML
    private TableColumn<SprintData, Double> capacityDoneColumn;
    @FXML
    private TableColumn<SprintData, Double> effortDoneColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedEffortDoneColumn;
    @FXML
    private TableColumn<SprintData, String> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, String> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintData, String> forecastPerSprintByMaxVelColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("endDate"));
        setCellValueFactoryForDouble(capacityForecastColumn, "capacityForecast");
        setCellValueFactoryForDouble(effortForecastColumn, "effortForecast");
        setCellValueFactoryForDouble(capacityDoneColumn, "capacityDone");
        setCellValueFactoryForDouble(effortDoneColumn, "effortDone");
        setCellValueFactoryForDouble(accumulatedEffortDoneColumn, "accumulatedEffortDone");
        setCellValueFactoryForForecast(forecastPerSprintByAvgVelColumn, ComputeProgressForecastByAverageVelocity.HISTORY_FORECAST_NAME);
        setCellValueFactoryForForecast(forecastPerSprintByMinVelColumn, ComputeProgressForecastByMinimumVelocity.HISTORY_FORECAST_NAME);
        setCellValueFactoryForForecast(forecastPerSprintByMaxVelColumn, ComputeProgressForecastByMaximumVelocity.HISTORY_FORECAST_NAME);
    }

    public void initModel(Sprints sprints) {
        createModel(sprints);
        this.sprintsTable.setItems(model);
    }

    private void createModel(Sprints sprints) {
        model = FXCollections.<SprintData> observableArrayList();
        model.addAll(sprints.getSprints());
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintData, String> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForDouble(TableColumn<SprintData, Double> tableColumn, String propertyName) {
        tableColumn.setCellValueFactory(new PropertyValueFactory<SprintData, Double>(propertyName));
    }
}
