package de.frankbeeh.productbacklogtimeline.view;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.SprintData;

public class AlignRightCellFactory<T> implements Callback<TableColumn<SprintData, T>, TableCell<SprintData, T>> {
    @Override
    public TableCell<SprintData, T> call(TableColumn<SprintData, T> arg0) {
        final TableCell<SprintData, T> tableCell = new TableCell<SprintData, T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }
        };

        tableCell.setAlignment(Pos.TOP_RIGHT);
        return tableCell;
    }
}