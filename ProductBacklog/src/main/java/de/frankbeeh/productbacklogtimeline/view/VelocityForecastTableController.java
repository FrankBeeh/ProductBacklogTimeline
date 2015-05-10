package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.SprintComparison;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecastComparison;

public class VelocityForecastTableController {
    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintComparison, String>, ObservableValue<String>> {
        private final String progressForecastName;

        public ProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<String> call(final CellDataFeatures<SprintComparison, String> cellDataFeatures) {
            return new ObservableValueBase<String>() {
                @Override
                public String getValue() {
                    return cellDataFeatures.getValue().getComparedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    private static final class AccumulatedProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintComparison, String>, ObservableValue<String>> {
        private final String progressForecastName;

        public AccumulatedProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<String> call(final CellDataFeatures<SprintComparison, String> cellDataFeatures) {
            return new ObservableValueBase<String>() {
                @Override
                public String getValue() {
                    return cellDataFeatures.getValue().getComparedAccumulatedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<SprintComparison> velocityForecastTable;
    @FXML
    private TableColumn<SprintComparison, String> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintComparison, String> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintComparison, String> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<SprintComparison, String> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<SprintComparison, String> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<SprintComparison, String> accumulatedForecastByMaxVelColumn;
    
    private final ObservableList<SprintComparison> model = FXCollections.<SprintComparison> observableArrayList();


    @FXML
    private void initialize() {
        setCellValueFactoryForForecast(forecastPerSprintByAvgVelColumn, VelocityForecast.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMinVelColumn, VelocityForecast.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMaxVelColumn, VelocityForecast.MAXIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByAvgVelColumn, VelocityForecast.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMinVelColumn, VelocityForecast.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMaxVelColumn, VelocityForecast.MAXIMUM_VELOCITY_FORECAST);
        velocityForecastTable.setItems(model);
    }

    public void initModel(VelocityForecastComparison velocityForecastComparison) {
        model.removeAll(model);
        model.addAll(velocityForecastComparison.getComparisons());
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintComparison, String> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<SprintComparison, String> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }
}
