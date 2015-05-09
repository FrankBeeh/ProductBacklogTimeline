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
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class VelocityForecastTableController {
    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<Sprint, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public ProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<Sprint, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    private static final class AccumulatedProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<Sprint, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public AccumulatedProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<Sprint, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<Sprint> velocityForecastTable;
    @FXML
    private TableColumn<Sprint, String> startDateColumn;
    @FXML
    private TableColumn<Sprint, String> endDateColumn;
    @FXML
    private TableColumn<Sprint, Double> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<Sprint, Double> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<Sprint, Double> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<Sprint, Double> accumulatedEffortDoneColumn;
    @FXML
    private TableColumn<Sprint, Double> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<Sprint, Double> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<Sprint, Double> accumulatedForecastByMaxVelColumn;

    private ObservableList<Sprint> model;

    @FXML
    private void initialize() {
        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sprint, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<Sprint, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatLocalDate(cellDataFeatures.getValue().getStartDate());
                    }
                };
            }
        });
        endDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sprint, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<Sprint, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatLocalDate(cellDataFeatures.getValue().getEndDate());
                    }
                };
            }
        });
        accumulatedEffortDoneColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sprint, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(final CellDataFeatures<Sprint, Double> cellDataFeatures) {
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
        model = FXCollections.<Sprint> observableArrayList();
        for (Sprint sprintData : velocityForecast.getSprints()) {
            model.add(sprintData);
        }
    }

    private void setCellValueFactoryForForecast(TableColumn<Sprint, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<Sprint, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }
}
