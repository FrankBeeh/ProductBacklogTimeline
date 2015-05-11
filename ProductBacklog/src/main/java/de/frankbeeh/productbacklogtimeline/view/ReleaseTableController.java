package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.ComparedValue;
import de.frankbeeh.productbacklogtimeline.domain.ReleaseComparison;
import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecastComparison;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public class ReleaseTableController {
    private static final class CompletionForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<ReleaseComparison, ComparedValue>, ObservableValue<ComparedValue>> {
        private final String progressForecastName;

        public CompletionForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<ComparedValue> call(final CellDataFeatures<ReleaseComparison, ComparedValue> cellDataFeatures) {
            return new ObservableValueBase<ComparedValue>() {
                @Override
                public ComparedValue getValue() {
                    return cellDataFeatures.getValue().getComparedCompletionForecast(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<ReleaseComparison> releasesTable;
    @FXML
    private TableColumn<ReleaseComparison, ComparedValue> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<ReleaseComparison, ComparedValue> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<ReleaseComparison, ComparedValue> completionForecastByMaxVelColumn;

    private final ObservableList<ReleaseComparison> model = FXCollections.<ReleaseComparison> observableArrayList();

    @FXML
    private void initialize() {
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
        releasesTable.setItems(model);
    }

    public void initModel(ReleaseForecastComparison releaseForecastComparison) {
        model.removeAll(model);
        model.addAll(releaseForecastComparison.getComparisons());
    }

    public Parent getView() {
        return releasesTable;
    }
}
