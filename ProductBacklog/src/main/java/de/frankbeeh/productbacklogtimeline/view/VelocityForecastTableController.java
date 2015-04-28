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
import de.frankbeeh.productbacklogtimeline.view.model.SprintDataViewModel;

public class VelocityForecastTableController {
    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintDataViewModel, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public ProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintDataViewModel, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().entityProperty().get().getProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    private static final class AccumulatedProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintDataViewModel, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public AccumulatedProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintDataViewModel, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().entityProperty().get().getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<SprintDataViewModel> velocityForecastTable;
    @FXML
    private TableColumn<SprintDataViewModel, String> startDateColumn;
    @FXML
    private TableColumn<SprintDataViewModel, String> endDateColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> accumulatedEffortDoneColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<SprintDataViewModel, Double> accumulatedForecastByMaxVelColumn;

    private ObservableList<SprintDataViewModel> model;

    @FXML
    private void initialize() {

        // FIXME: CellValueFactory should not be needed anymore, once PropertyFormatting works correctly can be directly binded to the property of the viewmodel
        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintDataViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintDataViewModel, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatDate(cellDataFeatures.getValue().entityProperty().get().getStartDate());
                    }
                };
            }
        });
        endDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintDataViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<SprintDataViewModel, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatDate(cellDataFeatures.getValue().entityProperty().get().getEndDate());
                    }
                };
            }
        });
        accumulatedEffortDoneColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SprintDataViewModel, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(final CellDataFeatures<SprintDataViewModel, Double> cellDataFeatures) {
                return new ObservableValueBase<Double>() {
                    @Override
                    public Double getValue() {
                        return cellDataFeatures.getValue().entityProperty().get().getAccumulatedEffortDone();
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

    @FXML
    private void editItem() {
        final SprintDataViewModel selectedItem = velocityForecastTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            final BasicDialog<SprintDataViewModel> dialog = new SprintEditDialog();
            dialog.initModel(selectedItem);
            dialog.openDialog();
        }
    }

    public void initModel(VelocityForecast velocityForecast) {
        createModel(velocityForecast);
        velocityForecastTable.setItems(model);
    }

    private void createModel(VelocityForecast velocityForecast) {
        model = FXCollections.<SprintDataViewModel> observableArrayList();

        // FIXME: Maybe introduce a listModel, once this is needed in several classes?
        for (SprintData sprintData : velocityForecast.getSprints()) {
            model.add(new SprintDataViewModel(sprintData));
        }
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintDataViewModel, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<SprintDataViewModel, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }
}
