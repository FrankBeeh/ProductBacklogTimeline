package de.frankbeeh.productbacklogtimeline.view;

import java.text.SimpleDateFormat;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
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

public class SprintsTableController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public ProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintData, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    private static final class AccumulatedProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public AccumulatedProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintData, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
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
    private TableColumn<SprintData, Double> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByMaxVelColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("name"));
        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintData, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return DATE_FORMAT.format(cellDataFeatures.getValue().getStartDate());
                    }
                };
            }
        });
        endDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintData, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return DATE_FORMAT.format(cellDataFeatures.getValue().getEndDate());
                    }
                };
            }
        });
        setCellValueFactoryForDouble(capacityForecastColumn, "capacityForecast");
        setCellValueFactoryForDouble(effortForecastColumn, "effortForecast");
        setCellValueFactoryForDouble(capacityDoneColumn, "capacityDone");
        setCellValueFactoryForDouble(effortDoneColumn, "effortDone");
        setCellValueFactoryForDouble(accumulatedEffortDoneColumn, "accumulatedEffortDone");
        setCellValueFactoryForForecast(forecastPerSprintByAvgVelColumn, Sprints.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMinVelColumn, Sprints.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMaxVelColumn, Sprints.MAXIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByAvgVelColumn, Sprints.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMinVelColumn, Sprints.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMaxVelColumn, Sprints.MAXIMUM_VELOCITY_FORECAST);
    }

    public void initModel(Sprints sprints) {
        createModel(sprints);
        this.sprintsTable.setItems(model);
    }

    private void createModel(Sprints sprints) {
        model = FXCollections.<SprintData> observableArrayList();
        model.addAll(sprints.getSprints());
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintData, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<SprintData, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForDouble(TableColumn<SprintData, Double> tableColumn, String propertyName) {
        tableColumn.setCellValueFactory(new PropertyValueFactory<SprintData, Double>(propertyName));
    }
}
