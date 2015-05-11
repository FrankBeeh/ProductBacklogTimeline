package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.ComparedValue;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogDirection;

public class ComparedValueCellFactory<S> implements Callback<TableColumn<S, ComparedValue>, TableCell<S, ComparedValue>> {
    private boolean alignRight;

    public ComparedValueCellFactory() {
        this(false);
    }
    
    public ComparedValueCellFactory(@NamedArg("alignRight") boolean alignRight) {
        this.alignRight = alignRight;
    }

    @Override
    public TableCell<S, ComparedValue> call(TableColumn<S, ComparedValue> arg0) {
        final TableCell<S, ComparedValue> tableCell = new TableCell<S, ComparedValue>() {
            @Override
            public void updateItem(ComparedValue item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
                getStyleClass().removeAll(ProductBacklogDirection.getAllStyleClasses());
                if (item != null) {
                    getStyleClass().add(item.getStyleClass());
                }
            }

            private String getString() {
                return getItem() == null ? "" : getItem().getComparedValue();
            }
        };
        if (alignRight) {
            tableCell.setAlignment(Pos.TOP_RIGHT);
        }
        return tableCell;
    }
}