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
import de.frankbeeh.productbacklogtimeline.domain.SprintData;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class VelocityForecastTableController {
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
    private TableView<SprintData> velocityForecastTable;
    @FXML
    private TableColumn<SprintData, String> startDateColumn;
    @FXML
    private TableColumn<SprintData, String> endDateColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedEffortDoneColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByMaxVelColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintData, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatLocalDate(cellDataFeatures.getValue().getStartDate());
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
                        return FormatUtility.formatLocalDate(cellDataFeatures.getValue().getEndDate());
                    }
                };
            }
        });
        accumulatedEffortDoneColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintData, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(final CellDataFeatures<SprintData, Double> cellDataFeatures) {
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

    public void initModel(VelocityForecast velocityForecast) {
        createModel(velocityForecast);
        velocityForecastTable.setItems(model);
    }

    private void createModel(VelocityForecast velocityForecast) {
        model = FXCollections.<SprintData> observableArrayList();
        for (SprintData sprintData : velocityForecast.getSprints()) {
            model.add(sprintData);
        }
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintData, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<SprintData, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }
}
