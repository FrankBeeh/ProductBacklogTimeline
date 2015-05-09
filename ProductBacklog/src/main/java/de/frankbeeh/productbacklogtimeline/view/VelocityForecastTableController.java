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
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class VelocityForecastTableController {
    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintComparison, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public ProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintComparison, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    private static final class AccumulatedProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintComparison, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public AccumulatedProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintComparison, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<SprintComparison> velocityForecastTable;
    @FXML
    private TableColumn<SprintComparison, String> startDateColumn;
    @FXML
    private TableColumn<SprintComparison, String> endDateColumn;
    @FXML
    private TableColumn<SprintComparison, Double> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintComparison, Double> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintComparison, Double> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<SprintComparison, Double> accumulatedEffortDoneColumn;
    @FXML
    private TableColumn<SprintComparison, Double> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<SprintComparison, Double> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<SprintComparison, Double> accumulatedForecastByMaxVelColumn;

    private ObservableList<SprintComparison> model;

    @FXML
    private void initialize() {
        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintComparison, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintComparison, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatLocalDate(cellDataFeatures.getValue().getStartDate());
                    }
                };
            }
        });
        endDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintComparison, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintComparison, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatLocalDate(cellDataFeatures.getValue().getEndDate());
                    }
                };
            }
        });
        accumulatedEffortDoneColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintComparison, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(final CellDataFeatures<SprintComparison, Double> cellDataFeatures) {
                return new ObservableValueBase<Double>() {
                    @Override
                    public Double getValue() {
                        return cellDataFeatures.getValue().getAccumulatedEffortDone();
                    }
                };
            }
        });
        
        
        setCellValueFactoryForForecast(forecastPerSprintByAvgVelColumn, VelocityForecast.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMinVelColumn, VelocityForecast.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMaxVelColumn, VelocityForecast.MAXIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByAvgVelColumn, VelocityForecast.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMinVelColumn, VelocityForecast.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMaxVelColumn, VelocityForecast.MAXIMUM_VELOCITY_FORECAST);
    }

    public void initModel(VelocityForecastComparison velocityForecastComparison) {
        createModel(velocityForecastComparison);
        velocityForecastTable.setItems(model);
    }

    private void createModel(VelocityForecastComparison velocityForecastComparison) {
        model = FXCollections.<SprintComparison> observableArrayList();
        for (SprintComparison sprintDataComparison : velocityForecastComparison.getComparisons()) {
            model.add(sprintDataComparison);
        }
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintComparison, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<SprintComparison, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }
}
