package de.frankbeeh.productbacklogtimeline.view;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;

public class AlignRightCellFactory<T> implements Callback<TableColumn<Sprint, T>, TableCell<Sprint, T>> {
    @Override
    public TableCell<Sprint, T> call(TableColumn<Sprint, T> arg0) {
        final TableCell<Sprint, T> tableCell = new TableCell<Sprint, T>() {
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