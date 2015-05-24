package de.frankbeeh.productbacklogtimeline.view;

import java.time.LocalDateTime;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.service.DateConverter;

public class LocalDateTimeCellFactory<S> implements Callback<TableColumn<S, LocalDateTime>, TableCell<S, LocalDateTime>> {
    @Override
    public TableCell<S, LocalDateTime> call(TableColumn<S, LocalDateTime> arg0) {
        final TableCell<S, LocalDateTime> tableCell = new TableCell<S, LocalDateTime>() {
            @Override
            public void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
            }

            private String getString() {
                return getItem() == null ? "" : DateConverter.formatLocalDateTime(getItem());
            }
        };
        return tableCell;
    }
}