package de.frankbeeh.productbacklogtimeline.view;

import java.util.List;

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
import de.frankbeeh.productbacklogtimeline.data.Timestamp;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class TimelineTableController {
    @FXML
    private TableView<Timestamp> timelineTable;
    @FXML
    private TableColumn<Timestamp, String> dateColumn;

    private final ObservableList<Timestamp> model = FXCollections.<Timestamp> observableArrayList();

    @FXML
    private void initialize() {
        timelineTable.setItems(model);
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Timestamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<Timestamp, String> cellDataFeatures) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return FormatUtility.formatDate(cellDataFeatures.getValue().getDate());
                    }
                };
            }
        });

    }

    public void initModel(List<Timestamp> timestamps) {
        model.removeAll(model);
        model.addAll(timestamps);
    }

    public Parent getView() {
        return timelineTable;
    }
}
